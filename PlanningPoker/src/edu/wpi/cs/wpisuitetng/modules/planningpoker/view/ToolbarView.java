/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;


import java.awt.Dimension;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.EditButtonsPanel;
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
public class ToolbarView  extends DefaultToolbarView {

//	TODO: Cancel Game
	public EditButtonsPanel editButton = new EditButtonsPanel();
	public PlanningPokerButtonsPanel gameButton = new PlanningPokerButtonsPanel();
	public OwnerButtonPanel ownerButton = new OwnerButtonPanel();
	public UserButtonPanel userButton = new UserButtonPanel();
	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {
		this.addGroup(gameButton);
		this.addGroup(userButton);
		this.addGroup(ownerButton);
		this.addGroup(editButton);
	}
	
	/**
	 * Method getEditButton.
	 * @return EditButtonsPanel */
	public EditButtonsPanel getEditButton(){
		return editButton;
	}
	/**
	 * Method getReqButton.
	
	 * @return GameButtonsPanel */
	public PlanningPokerButtonsPanel getReqButton() {
		return gameButton;
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
