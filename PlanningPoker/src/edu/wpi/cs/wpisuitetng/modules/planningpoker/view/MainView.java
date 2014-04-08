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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.CurrentGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameInputDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.OverviewPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;



/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {
	private JPanel currentGame;
	private JPanel pastGames;
	private JPanel deckPanel;
	private JPanel overviewPanel;
	private JPanel newGame;
	private GameModel gameModel;
	private int newGameTabs = 0;
	private int j = 0;
	private List<Integer> openTabs = new ArrayList<Integer>();
	private List<NewGameDistributedPanel> newGames = new ArrayList<NewGameDistributedPanel>();
	final int PERMANANT_TABS = 4;
	private final AddEmailPanel addEmailPanel;
	
	public MainView(DeckModel deckModel) {
		currentGame = new CurrentGamePanel();
		pastGames = new JPanel();
		deckPanel = new DeckPanel(deckModel);
		overviewPanel = new OverviewPanel();
		addEmailPanel = new AddEmailPanel();
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		addTab("Overview", overviewPanel);
		addTab("Current Game", currentGame);
		addTab("Past Game", pastGames);
		addTab("Deck", deckPanel);
		this.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				for (int i = 0; i<newGames.size(); i++){
					GetRequirementsController.getInstance().retrieveRequirements();
					newGames.get(i).refresh();
				}
			}
		});
		addTab("Add Email", addEmailPanel);
	}
	
	public void addNewGameTab()
	{
		GetRequirementsController.getInstance().retrieveRequirements();
		openTabs.add(newGameTabs, j);
		JButton btnClose = new JButton("x");
		List<Requirement> reqs = RequirementModel.getInstance().getRequirements();
		NewGameDistributedPanel newGame = new NewGameDistributedPanel(reqs, btnClose);
		newGames.add(newGame);
		MyCloseActionHandler myCloseActionHandler = new MyCloseActionHandler("New Game", j, newGame);
		add(newGame, newGameTabs + PERMANANT_TABS);
		JPanel pnlTab = new JPanel(new GridBagLayout());
		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel("New Game");
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

		setTabComponentAt(newGameTabs + PERMANANT_TABS, pnlTab);

		btnClose.addActionListener(myCloseActionHandler);
		setSelectedIndex(newGameTabs+PERMANANT_TABS);
		newGameTabs++;
		j++;
	}
	public void addEditGameTab(GameSession gameSession)
	{
		GetRequirementsController.getInstance().retrieveRequirements();
		openTabs.add(newGameTabs, j);
		JButton btnClose = new JButton("x");
		List<Requirement> reqs = RequirementModel.getInstance().getRequirements();
		NewGameDistributedPanel newGame = new NewGameDistributedPanel(reqs, btnClose,gameSession);
		MyCloseActionHandler myCloseActionHandler = new MyCloseActionHandler("New Game", j, newGame);
		add(newGame, newGameTabs + PERMANANT_TABS);
		JPanel pnlTab = new JPanel(new GridBagLayout());
		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel("New Game");
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

		setTabComponentAt(newGameTabs + PERMANANT_TABS, pnlTab);

		btnClose.addActionListener(myCloseActionHandler);
		setSelectedIndex(newGameTabs+PERMANANT_TABS);
		newGameTabs++;
		j++;
	}
	public class MyCloseActionHandler implements ActionListener {

	    private String tabName;
	    private int index;
	    private NewGameDistributedPanel ngdp;
	    
	    public MyCloseActionHandler(String tabName, int index, NewGameDistributedPanel ngdp) {
	        this.tabName = tabName;
	        this.index = index;
	        this.ngdp = ngdp;
	    }

	    public String getTabName() {
	        return tabName;
	    }

	    
	    //Helper function to remove a tab
	    public void removeTab(int index){
	    	for(int i=0; i<newGameTabs; i++){
				if (openTabs.get(i) == index){
					removeTabAt(i + PERMANANT_TABS);
					openTabs.remove(i);
					newGameTabs--;
				}
			}
	    }
	    
	    public void actionPerformed(ActionEvent evt) {

	        if (index >= 0) {
	        	if (!ngdp.isNew){
					int option = JOptionPane.showOptionDialog(ngdp, "Discard unsaved changes and close tab?", "Discard changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (option == 0){
						removeTab(index);
					}
	        	}
	        	else{
	        		removeTab(index);
	        	}
	        }
	            // It would probably be worthwhile getting the source
	            // casting it back to a JButton and removing
	            // the action handler reference ;)
	    }
	}   
}
