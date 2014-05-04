/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Graphics;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.ButtonPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.GreetingPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.PlanningPokerButtonsPanel;

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
	public GreetingPanel greetingPanel = new GreetingPanel();
	public ButtonPanel buttonPanel = new ButtonPanel();
	ToolbarGroupView dummyPanel = new ToolbarGroupView("");
	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {
		this.addGroup(buttonPanel);
		this.addGroup(dummyPanel);
		this.addGroup(gameButton);
		
		
		this.updateUI();
		
	}
	
	/**
	 * Method getReqButton.
	
	 * @return GameButtonsPanel */
//	public PlanningPokerButtonsPanel getReqButton() {
//		return gameButton;
//	}
	
	/**
	 * Changes the buttons to show appropriately for the selected game
	 * @param gameSelected The game which the buttons are changed based on
	 */
	public void changeButtons(GameSession gameSelected){
		buttonPanel.showButton(gameSelected);
		dummyPanel.setPreferredWidth(getDummySize());
		this.updateUI();
		//this.updateUI();
		//this.repaint();
	}
	
	/**
	 * Removes all buttons from the button panel
	 */
	public void removeButtons(){
		buttonPanel.removeButtons();
	}

	
	public int getDummySize()
	{
		int size = (int) getWidth();
		size -= buttonPanel.getWidth();
		size -= gameButton.getWidth();

		return size;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		dummyPanel.setPreferredWidth(getDummySize());

		super.paintComponent(g);
		this.updateUI();
	}
}
