/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.CurrentGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.iterations.IterationOverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

@SuppressWarnings("serial")
public class MainView extends JTabbedPane {
	private OverviewPanel overviewPanel;
	private GameModel gameModel;
	private int newGameTabs = 0;
	private GetRequirementsRequestObserver refresher;
	private int j = 0;
	private List<Integer> openTabs = new ArrayList<Integer>();
	private List<NewGameDistributedPanel> newGames = new ArrayList<NewGameDistributedPanel>();
	final int PERMANANT_TABS = 1;
	
	public MainView() {
		overviewPanel = new OverviewPanel();
		
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		addTab("Overview", overviewPanel);
		
		ViewEventController.getInstance().setMainView(this);
	}
	//The function to add a new game tab
	//The function to add a new game tab

		public void addNewGameTab()
		{
			addTab("New Game", new GameSession(new String(), new String(), 0, 0, new Date(), new ArrayList<Integer>()));
		}

		//Adds a new edit game tab
		public void addEditGameTab(GameSession gameSession)
		{
			addTab("Edit Game", gameSession);
		}

		//Adds a new game view tab
		public void addPlayGameTab(GameSession gametoPlay){
			if(gametoPlay.getGameStatus() == GameStatus.COMPLETED)
			{
				addTab("View Estimates", gametoPlay);
			}
			else
			{
				addTab("Play Game", gametoPlay);
			}
		}

		private void addTab(String tabType, GameSession game){
			int open = this.getTabCount();
			MyCloseActionHandler myCloseActionHandler = null;
			overviewPanel.refreshGames();
			GetRequirementsController.getInstance().retrieveRequirements();
			openTabs.add(newGameTabs, j);
			JButton btnClose = new JButton("x");
			List<Requirement> reqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
			if (tabType.equals("New Game")){
				NewGameDistributedPanel newGame = new NewGameDistributedPanel(reqs, btnClose);
				myCloseActionHandler = new MyCloseActionHandler(tabType, j, this, newGame, 0);
				newGames.add(newGame);
				add(newGame, open);
			}
			else if (tabType.equals("Edit Game")){
				NewGameDistributedPanel newEdit = new NewGameDistributedPanel(game, btnClose);
				myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newEdit, 1);
				newGames.add(newEdit);
				add(newEdit, open);
			}
			else if (tabType.equals("Play Game")){
				GameView newGameView = new GameView(game);
				myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newGameView, 2);
				add(newGameView, open);
			}
			else if (tabType.equals("View Estimates")){
				GameView newGameView = new GameView(game);
				myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newGameView, 3);
				add(newGameView, open);
			}
			else if (tabType.equals("Add Email")){
				AddEmailPanel addEmailPanel = new AddEmailPanel(btnClose);
				myCloseActionHandler = new MyCloseActionHandler("options", j, this, addEmailPanel, 4);
				add(addEmailPanel, open);
			}
			JPanel pnlTab = new JPanel(new GridBagLayout());
			pnlTab.setOpaque(false);
			JLabel lblTitle = new JLabel(tabLabler(tabType, game));
			if (tabType.equals("Add Email")) lblTitle.setText("Options");
			btnClose.setMargin(new Insets(0, 0, 0, 0));
			btnClose.setFont(btnClose.getFont().deriveFont((float) 8));
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			pnlTab.add(lblTitle, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			pnlTab.add(btnClose, gbc);

			setTabComponentAt(open, pnlTab);

			btnClose.addActionListener(myCloseActionHandler);
			setSelectedIndex(open);
			newGameTabs++;
			j++;
		}

	/*
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!(component instanceof OverviewPanel) && !(component instanceof IterationOverviewPanel)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	*/
	
	//The action listener for closing of tabs
	public String tabLabler(String tabType, GameSession game){
		if (tabType.equals("Play Game")){
			return game.getGameName();
		}
		else if (tabType.equals("New Game")){
			return "New Game";
		}
		else if (tabType.equals("Edit Game")){
			return game.getGameName();
		}
		else return "help";
	}
			
			
	public class MyCloseActionHandler implements ActionListener {

	    private String tabName;
	    private int index;
	    //0 - New Game
	    //1 - Edit Game
	    //2 - Game View
	    private int type;
	    private MainView mv;
	    private GameView gameView;
	    private NewGameDistributedPanel ngdp;
	    private AddEmailPanel addEmailPanel;
	    
	    public MyCloseActionHandler(String tabName, int index, MainView mv, NewGameDistributedPanel ngdp, int type) {
	        this.tabName = tabName;
	        this.index = index;
	        this.ngdp = ngdp;
	        this.type = type;
	        this.mv = mv;
	    }
	    
	    public MyCloseActionHandler(String tabName, int index, MainView mv, GameView gv, int type){
	    	this.tabName = tabName;
	    	this.index = index;
	    	this.gameView = gv;
	    	this.type = type;
	    	this.mv = mv;
	    }
	    
	    public MyCloseActionHandler(String tabName, int index, MainView mv,
				AddEmailPanel addEmailPanel, int type) {
			this.tabName = tabName;
	    	this.index = index;
	    	this.addEmailPanel = addEmailPanel;
	    	this.type = type;
	    	this.mv = mv;
		}

		public String getTabName() {
	        return tabName;
	    }

	    
	    //Helper function to remove a tab
	    /*
	    public void removeTab(int index){
	    	for(int i=0; i<newGameTabs; i++){
				if (openTabs.get(i) == index){
					removeTabAt(i + PERMANANT_TABS);
					openTabs.remove(i);
					newGameTabs--;
				}
			}
	    }
	    */
	    
	    //When the 'x' button is selected, it will prompt the user if they want to discard changes if the user has made inputs in any of the 
	    //new game creation fields
	    
	    public void actionPerformed(ActionEvent evt) {
	    	 if (type == 0 || type == 1) {
		        	if (!ngdp.isNew){
						int option = JOptionPane.showOptionDialog(ngdp, "Discard unsaved changes and close tab?", "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (option == 0){
							ViewEventController.getInstance().getMain().remove(ngdp);
						}
		        	}
		        	else{
		        		ViewEventController.getInstance().getMain().remove(ngdp);
		        	}
		        }

	        else if (type == 2){
	        	if(!gameView.isNew){
	        		int option = JOptionPane.showOptionDialog(gameView, "Discard unsaved changes and close tab?", "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (option == 0){
						ViewEventController.getInstance().getMain().remove(gameView);
					}
	        	}
	        	else{
	        		ViewEventController.getInstance().getMain().remove(gameView);
				}
			} else if (type == 3){
				if (!gameView.isNew) {
						ViewEventController.getInstance().getMain()
								.remove(gameView);
				} else {
					ViewEventController.getInstance().getMain().remove(gameView);
				}
			}
			else if (type == 4){
				if (!addEmailPanel.isNew) {
					int option = JOptionPane.showOptionDialog(addEmailPanel,
							"Discard unsaved changes and close tab?",
							"Discard changes?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (option == 0) {
						ViewEventController.getInstance().getMain()
								.remove(addEmailPanel);
					}
				} else {
					ViewEventController.getInstance().getMain().remove(addEmailPanel);
				}
			}
		}
	}


	public void addEmailAddress() {
		this.addTab("Add Email", new GameSession(null, null, 0, 0, null, null));
		
	}

}
