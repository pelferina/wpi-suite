/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;


import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.EditButtonsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.EndGameButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.PlanningPokerButtonsPanel;

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
	public EndGameButtonPanel endGameButton = new EndGameButtonPanel();
	
	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {
		this.addGroup(gameButton);
		this.addGroup(editButton);
		this.addGroup(endGameButton);

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
	
	public void setEndGameVisible(){
		endGameButton.setEndGameButtonVisible();
	}
	public void setEndGameInvisible(){
		endGameButton.setEndGameButtonInvisible();
	}
}
