/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.ButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.GreetingPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.OwnerButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.PlanningPokerButtonsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.UserButtonPanel;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
@SuppressWarnings("serial")
public class ToolbarView  extends DefaultToolbarView {

//	TODO: Cancel Game
	public PlanningPokerButtonsPanel gameButton = new PlanningPokerButtonsPanel();
	public OwnerButtonPanel ownerButton = new OwnerButtonPanel();
	public UserButtonPanel userButton = new UserButtonPanel();
	public GreetingPanel greetingPanel = new GreetingPanel();
	public ButtonPanel buttonPanel = new ButtonPanel();
	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {
		this.insertGroupAt(greetingPanel, 0);
		
		this.addGroup(gameButton);
		this.addGroup(buttonPanel);
		//this.addGroup(userButton);
		//this.addGroup(ownerButton);
	}
	
	/**
	 * Method getReqButton.
	
	 * @return GameButtonsPanel */
	public PlanningPokerButtonsPanel getReqButton() {
		return gameButton;
	}
	
	public void changeButtons(GameSession gameSelected){
		buttonPanel.showButton(gameSelected);
		//this.updateUI();
		//this.repaint();
	}
	
	public void removeButtons(){
		buttonPanel.removeButtons();
	}
	
	//---------------Owner button ----------------------
	/**
	 * Enables the end game button
	 * @param game the game associated with this button
	 */
	public void makeEndGameButtonVisible(GameSession game){
		ownerButton.makeEndGameButtonVisible(game);
	}
	/**
	 * Enables the activate game button
	 * @param game the game associated with this button
	 */
	public void makeActivateGameButtonVisible(GameSession game){
		ownerButton.makeActivateGameButtonVisible(game);
	}
	
	/**
	 * Disable the activate game button
	 * @param game the game associated with this button
	 */
	public void makeActivateGameButtonDisable(GameSession game){
		ownerButton.makeActivateGameButtonDisable(game);
	}
	/**
	 * Enables the archive game button
	 * @param game the game associated with this button
	 */
	public void makeArchiveGameButtonVisible(GameSession game){
		ownerButton.makeArchiveGameButtonVisible(game);
	}
	/**
	 * make the owner game button to be invisible
	 */
	public void makeOwnerButtonInvisible(){
		ownerButton.makeOwnerButtonInvisible();
	}
	
	/**
	 * This method sets the button to read "activate"
	 *
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param game
	 */
	public void makeEditGameButtonVisible(GameSession gameSelected){
		ownerButton.makeEditGameButtonVisible(gameSelected);
	}
	
	/**
	 * Makes the editGameButton invisible
	 */
	public void makeEditGameButtonInvisible(){
		ownerButton.makeEditGameButtonInvisible();
	}
	
	//---------------User button ----------------------
	/**
	 * Enables the vote game button
	 * @param game the game associated with this button
	 */
	public void makeVoteGameButtonVisible(GameSession game){
		userButton.makeVoteGameButtonVisible(game);
	}
	/**
	 * Enables the view game button
	 * @param game the game associated with this button
	 */
	public void makeViewGameButtonVisible(GameSession game){
		userButton.makeViewGameButtonVisible(game);
	}
	/**
	 * make the owner game button to be invisible
	 */
	public void makeUserButtonInvisible(){
		userButton.makeUserButtonInvisible();
	}
}
