/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Cosmic Latte
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;


import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;



/**
 * The panel for creating a new live Planning Poker game, along with fields
 * that have the parameters for what a new game should have.
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")

public class NewGameDistributedPanel extends JSplitPane {
	
	public boolean isNew = true;
	public NewGameInputDistributedPanel newGameInputPanel;
	public NewGameReqPanel newGameReqPanel;
	public JButton close;
	private GameSession editMode;
	
	/**
	 * Constructor for NewGameDistributedPanel
	 * @param gameSession the session to display
	 * @param close the close button to close the panel
	 */
	public NewGameDistributedPanel(GameSession gameSession, JButton close)
	{
		this.close = close;
		newGameReqPanel = new NewGameReqPanel(gameSession);
		GetRequirementsController.getInstance().addRefreshable(newGameReqPanel); /** TODO this adds a refreshable whenever a new game tab is created.  We need to clean up refreshables when the tab is closed*/
		newGameInputPanel = new NewGameInputDistributedPanel(this, gameSession);
		setPanel();
	}
	/**
	 * Constructor for NewGameDistributedPanel that gets requirements
	 * @param reqs a List<Requirement> of requirements
	 * @param close button to close the panel
	 */
	public NewGameDistributedPanel(List<Requirement> reqs, JButton close) {
		newGameReqPanel = new NewGameReqPanel();
		GetRequirementsController.getInstance().addRefreshable(newGameReqPanel);
		newGameInputPanel = new NewGameInputDistributedPanel(this);
		this.close = close;
		
		setEnabled(false);
		setPanel();
	}
	/**
	 * This sets up the panels
	 * Must be private so that it cannot be set up more than once
	 * Only to be called in the constructor
	 * 
	 */
	private void setPanel(){
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		final Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
		
		setDividerLocation(500);
	}
	
	public List<Requirement> getSelected(){
		return newGameReqPanel.getSelected();
	}
}
