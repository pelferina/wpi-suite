/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;



/**
 * Provides an interface for interaction with the main GUI elements
 * All actions on GUI elements should be conducted through this controller.
 * @version $Revision: 1.0 $
 * @author Cosmic Latte
 */

public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
	private ToolbarView toolbar = null;

//	private OverviewTable overviewTable = null;
//	private OverviewTreePanel overviewTree = null;
//	private ArrayList<GamePanel> listOfEditingPanels = new ArrayList<GamePanel>();
//	private ArrayList<IterationPanel> listOfIterationPanels = new ArrayList<IterationPanel>();
//	private IterationOverviewPanel iterationOverview;
	
//	/**
//	 * Sets the OverviewTable for the controller
//	 * @param overviewTable a given OverviewTable
//	 */
//	public void setOverviewTable(OverviewTable overviewTable) {
//		this.overviewTable = overviewTable;
//	}

	/**
	 * Default constructor for ViewEventController.  Is protected to prevent instantiation.
	 */
	private ViewEventController() {}

	/**
	 * Returns the singleton instance of the vieweventcontroller.
	
	 * @return The instance of this controller. */
	public static ViewEventController getInstance() {
		if (instance == null) {
			instance = new ViewEventController();
		}
		return instance;
	}

	/**
	 * Sets the main view to the given view.
	
	 * @param mainview MainView
	 */
	public void setMainView(MainView mainview) {
		main = mainview;
		//hide all button
		main.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				ViewEventController.getInstance().makeEditGameButtonInVisible();
				ViewEventController.getInstance().makeOwnerButtonInvisible();
				ViewEventController.getInstance().makeUserButtonInvisible();
				
			}
			
		});
	}

	/**
	 * Sets the toolbarview to the given toolbar
	 * @param tb the toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView tb) {
		toolbar = tb;
		toolbar.repaint();
	}
	
	/**
	 * Opens a new tab for editing a game
	 * @param gameSession the GameSession the tab is editing
	 */
	public void editGameTab(GameSession gameSession) {
		main.addEditGameTab(gameSession);
	}
	
	/**
	 * Opens a new tab for playing a game
	 * @param gameSession the GameSession the user is playing
	 */
	public void playGameTab(GameSession gameSession){
		main.addPlayGameTab(gameSession);
	}
	
	/**
	 * Opens a new tab for managing decks.
	 */
	public void deckManagementTab(){
		main.addDeckManagementTab();
	}
	
	/**
	 * adds a playGameTab linked to the given gameSession
	 * @param gameSession the gameSession to add a playGameTab of
	 */
	public void viewGameTab(GameSession gameSession){
		main.addPlayGameTab(gameSession);
	}
	/**
	 * Adds a new ReqTab with given GameSession
	 * @param gameSession The GameSession it's a part of
	 */
	public void newReqTab(GameSession gameSession){
		main.addReqTab(gameSession);
	}
	
	/**
	 * Opens a new tab for the creation of a game.
	 */
	public void createGame() {
		main.addNewGameTab();
	}
	
	/**
	 * Opens a new tab for adding an email address
	 */
	public void options() {
		main.addPreferencesPanel();
	}
	
	public MainView getMain(){
		return main;
	}

	/**
	 * Opens a new tab for creating a new game
	 */
	public void newGameTab() {
		main.addNewGameTab();
	}
	//---------------Owner button ----------------------
	/**
	 * Set the end game button in tool bar visible 
	 * @param game
	 */
	public void makeEndGameButtonVisible(GameSession game){
		toolbar.makeEndGameButtonVisible(game);
	}
	
	/**
	 * Set the activate game button in tool bar visible 
	 * @param game
	 */
	public void makeActivateGameButtonVisible(GameSession game){
		toolbar.makeActivateGameButtonVisible(game);
	}
	
	/**
	 * Set the activate game button in tool bar visible but disable
	 * @param game
	 */
	public void makeActivateGameButtonDisable(GameSession game){
		toolbar.makeActivateGameButtonDisable(game);
	}
	
	/**
	 * Set the archive game button in tool bar visible 
	 * @param game
	 */
	public void makeArchiveGameButtonVisible(GameSession game){
		toolbar.makeArchiveGameButtonVisible(game);
	}
	/**
	 * Set the end game button in tool bar invisible 
	 */
	public void makeOwnerButtonInvisible(){
		toolbar.makeOwnerButtonInvisible();
	}
	//---------------User button ----------------------
	/**
	 * Set the activate game button in tool bar visible 
	 * @param game
	 */
	public void makeViewGameButtonVisible(GameSession game){
		toolbar.makeViewGameButtonVisible(game);
	}
	
	/**
	 * Set the archive game button in tool bar visible 
	 * @param game
	 */
	public void makeVoteGameButtonVisible(GameSession game){
		toolbar.makeVoteGameButtonVisible(game);
	}
	/**
	 * Set the end game button in tool bar invisible 
	 */
	public void makeUserButtonInvisible(){
		toolbar.makeUserButtonInvisible();
	}
	
	
	
	
	
	/**
	 * sets the edit game button to be visible
	 * @param gameID the id of the game to change the button for
	 */
	public void makeEditGameButtonVisible(GameSession gameSelected)
	{
		toolbar.makeEditGameButtonVisible(gameSelected);
		
	}

	/**
	 * sets the edit game button to be invisible
	 */
	public void makeEditGameButtonInVisible() {
		toolbar.makeEditGameButtonInvisible();		
	}
	
	public void changeButton(GameSession gameSelected){
		toolbar.changeButtons(gameSelected);
	}
	public void removeButtons(){
		toolbar.removeButtons();
	}
}
