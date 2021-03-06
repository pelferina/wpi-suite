/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckManagingPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.CompleteView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.reqpanel.NewRequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * Main view of the PlanningPoker module
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {
	private final OverviewPanel overviewPanel;

	private int newGameTabs = 0;
	private int j = 0;
	private final List<Integer> openTabs = new ArrayList<Integer>();
	private final List<NewGameDistributedPanel> newGames = new ArrayList<NewGameDistributedPanel>();
	final int PERMANANT_TABS = 1;
	private int preferencePaneIndex = -1;
	private int manageDeckIndex = -1;
	private boolean hasPreferencePane = false;
	private boolean hasDeckManagingPane = false;

	public MainView() {
		overviewPanel = new OverviewPanel();
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		addTab("Overview", overviewPanel);
		ViewEventController.getInstance().setMainView(this);
	}
	//The function to add a new game tab

	/**
	 * This method adds a tab labeled "New Game" to the view
	 */
		public void addNewGameTab()
		{
			addTab("New Game", new GameSession(new String(), new String(), 0, 0, new Date(), new ArrayList<Integer>()));
		}
		
		/**
		 * This method adds a tab labeled "Edit Game" to the view
		 * @param gameSession the game session to edit
		 */
		public void addEditGameTab(GameSession gameSession)
		{
			addTab("Edit Game", gameSession);
		}
		/**
		 * function to add a ReqTab
		 * @param gs The GameSession to add the tab to
		 */
		public void addReqTab(GameSession gs){
			addTab("Req Tab", gs);
		}
		/**
		 * function to add a DeckManagementTab
		 */
		public void addDeckManagementTab(){
			if(!hasDeckManagingPane){
				addTab("Deck Management", new GameSession(null, null, 0, 0, null, null));
			}
			else{
				setSelectedIndex(manageDeckIndex);
			}
		}


		/**
		 * This method adds a tab lebeled "Play Game" to the view
		 * @param gametoPlay the game session to play
		 */
		public void addPlayGameTab(GameSession gametoPlay){
			if(gametoPlay.getGameStatus() == GameStatus.COMPLETED || gametoPlay.getGameStatus() == GameStatus.ARCHIVED)
			{
				addTab("View Estimates", gametoPlay);
			}
			else
			{
				addTab("Play Game", gametoPlay);
			}
		}

		private void addTab(String tabType, GameSession game){
			boolean exists = false;
			final int open = this.getTabCount();
			MyCloseActionHandler myCloseActionHandler = null;
			overviewPanel.refreshGames();
			GetRequirementsController.getInstance().retrieveRequirements();
			openTabs.add(newGameTabs, j);
			final JButton btnClose = new JButton("x");
			final List<Requirement> reqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
			if (tabType.equals("New Game")){
				final NewGameDistributedPanel newGame = new NewGameDistributedPanel(reqs, btnClose);
				newGame.setName(Integer.toString(game.getGameID()));
				myCloseActionHandler = new MyCloseActionHandler(tabType, j, this, newGame, 0);
				newGames.add(newGame);
				add(newGame, open);
			}
			else if (tabType.equals("Edit Game")){
				for(int i = 1; i < getTabCount(); i++)
				{
					if(getComponentAt(i).getName() != null)
					{
						if(getComponentAt(i).getName().equals(Integer.toString(game.getGameID())))
						{
							setSelectedIndex(i);
							exists = true;
						}
					}
				}
				if(!exists)
				{
					final NewGameDistributedPanel newEdit = new NewGameDistributedPanel(game, btnClose);
					newEdit.setName(Integer.toString(game.getGameID()));
					myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newEdit, 1);
					newGames.add(newEdit);
					add(newEdit, open);
				}
			}
			else if (tabType.equals("Play Game")){
				for(int i = 1; i < getTabCount(); i++)
				{
					if(getComponentAt(i).getName() != null)
					{
						if(getComponentAt(i).getName().equals(Integer.toString(game.getGameID())))
						{
							setSelectedIndex(i);
							exists = true;
						}
					}
				}
				if(!exists)
				{
					final GameView newGameView = new GameView(game);
					newGameView.setName(Integer.toString(game.getGameID()));
					myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newGameView, 2);
					add(newGameView, open);
				}
			}
			else if (tabType.equals("View Estimates")){
				for(int i = 1; i < getTabCount(); i++)
				{
					if(getComponentAt(i).getName() != null){
						if(getComponentAt(i).getName().equals(Integer.toString(game.getGameID())))
						{
							setSelectedIndex(i);
							exists = true;
						}
					}
				}
				if(!exists)
				{
					final CompleteView newCompleteView = new CompleteView(game);
					newCompleteView.setName(Integer.toString(game.getGameID()));
					myCloseActionHandler = new MyCloseActionHandler(game.getGameName(), j, this, newCompleteView, 3);
					add(newCompleteView, open);
				}
			}
			else if (tabType.equals("User Preferences")){
				final PreferencesPanel userPreferences = new PreferencesPanel(btnClose);
				myCloseActionHandler = new MyCloseActionHandler("User Preferences", j, this, userPreferences, 4);
				add(userPreferences, open);
				hasPreferencePane = true;
				preferencePaneIndex = open;
			}
			else if(tabType.equals("Deck Management")){
				final DeckManagingPanel deckManaging = new DeckManagingPanel(btnClose);
				myCloseActionHandler = new MyCloseActionHandler("Deck Management", j, this, deckManaging, 6);
				add(deckManaging, open);
				deckManaging.focusOnName();
				hasDeckManagingPane = true;
				manageDeckIndex = open;
			}
			
			final JPanel pnlTab = new JPanel(new GridBagLayout());
			pnlTab.setOpaque(false);
			final JLabel lblTitle = new JLabel(tabLabler(tabType, game));
			if (tabType.equals("User Preferences")) lblTitle.setText("User Preferences");
			btnClose.setMargin(new Insets(0, 0, 0, 0));
			btnClose.setFont(btnClose.getFont().deriveFont((float) 8));
			final GridBagConstraints gbc = new GridBagConstraints();

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
	
	
	/**
	 * The action listener for closing of tabs
	 * @param tabType the title of the tab being closed
	 * @param game the game session
	 * @return Returns the name of the game closed, or "New Game" if a new game tab, or "help" if an unexpected tab
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
		else if (tabType.equals("View Estimates")){
			return game.getGameName();
		}
		else if (tabType.equals("Req Tab")){
			return "New Requirement";
		}
		else if (tabType.equals("Deck Management")){
			return "Deck Management";
		}
		else return "help";
	}
			
	/**
	 * The action handler for closing a tab
	 * 	
	 * @author Cosmic Latte
	 * @version $Revision: 1.0 $
	 */
	public class MyCloseActionHandler implements ActionListener {

	    private final String tabName;
	    //0 - New Game
	    //1 - Edit Game
	    //2 - Game View
	    //3 - Complete Game
	    //4 - Preferences Panel
	    //5 - New Requirement
	    private final int type;
	    private GameView gameView;
	    private CompleteView completeView;
	    private NewGameDistributedPanel ngdp;
	    private PreferencesPanel userPreferences;
	    private NewRequirementPanel newReq;
	    private DeckManagingPanel deckManaging;
	    
	    /**
	     * Close action handler for NewGameDistributedPanel
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param ngdp the NewGameDistributedPanel
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv, NewGameDistributedPanel ngdp, int type) {
	        this.tabName = tabName;
	        this.ngdp = ngdp;
	        this.type = type;
	    }
	    /**
	     * Constructor for the closeActionHandler
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param rp the RequirementsPanel
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv, NewRequirementPanel rp, int type) {
	        this.tabName = tabName;
	        newReq = rp;
	        this.type = type;
	    }

	    /**
	     * Close action handler for game view
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param gv the GameView
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv, GameView gv, int type){
	    	this.tabName = tabName;
	    	gameView = gv;
	    	this.type = type;
	    }
	    /**
	     * constructor for CloseActionHandler 
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param cv the completeView panel
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv, CompleteView cv, int type) {
	        this.tabName = tabName;
	        completeView = cv;
	        this.type = type;
	    }
	    /**
	     * constructor for CloseActionHandler 
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param dm The DeckManagingPanel
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv, DeckManagingPanel dm, int type) {
	        this.tabName = tabName;
	        deckManaging = dm;
	        this.type = type;
	    }
	    
	    /**
	     * Close action handler for preferences panel
	     * @param tabName name of the tab being closed
	     * @param index index of that tab on the tab list
	     * @param mv the MainView
	     * @param userPreferences the UserPreferencesPanel
	     * @param type integer for type
	     */
	    public MyCloseActionHandler(String tabName, int index, MainView mv,
				PreferencesPanel userPreferences, int type) {
			this.tabName = tabName;
	    	this.userPreferences = userPreferences;
	    	this.type = type;
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
						final int option = JOptionPane.showOptionDialog(ngdp, "Discard unsaved changes and close tab?", "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						if (option == 0){
							ngdp.stopTimer();
							ViewEventController.getInstance().getMain().remove(ngdp);
						}
		        	}
		        	else{
		        		ngdp.stopTimer();
		        		ViewEventController.getInstance().getMain().remove(ngdp);
		        	}
		        }

	        else if (type == 2){
	        	if(!gameView.isNew){
	        		final int option = JOptionPane.showOptionDialog(gameView, "Discard unsaved changes and close tab?", "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (option == 0){
						ViewEventController.getInstance().getMain().remove(gameView);
					}
	        	}
	        	else{
	        		ViewEventController.getInstance().getMain().remove(gameView);
				}
			} else if (type == 3){
				completeView.saveGame();
				ViewEventController.getInstance().getMain().remove(completeView);
			}
			else if (type == 4){
				ViewEventController.getInstance().getMain().remove(userPreferences);
				hasPreferencePane = false;
			}
			else if (type == 5){
				ViewEventController.getInstance().getMain().remove(newReq);
			}
			else if (type == 6){
				ViewEventController.getInstance().getMain().remove(deckManaging);
				hasDeckManagingPane = false;
			}
	    	 
		}
	}


	/**
	 * This method adds the "User Preferences" tab
	 */
	public void addPreferencesPanel() {
		if (!hasPreferencePane){
			addTab("User Preferences", new GameSession(null, null, 0, 0, null, null));
		}
		else {
			setSelectedIndex(preferencePaneIndex);
		}
	}

}
