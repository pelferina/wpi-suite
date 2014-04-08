/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;


/**
 * The panel for creating a new live Planning Poker game, along with fields
 * that have the parameters for what a new game should have.
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")

public class NewGameDistributedPanel extends AbsNewGamePanel {
	
	public boolean isNew = true;
	public NewGameInputDistributedPanel newGameInputPanel;
	public NewGameReqPanel newGameReqPanel;
	public JButton close;
	private GameSession editMode;
	
	public NewGameDistributedPanel(List<Requirement> reqs, JButton close, GameSession gameSession)
	{
		this.close = close;
		this.newGameReqPanel = new NewGameReqPanel(reqs, gameSession);
		this.newGameInputPanel = new NewGameInputDistributedPanel(this, gameSession);
		setPanel();
	}
	public NewGameDistributedPanel(List<Requirement> reqs, JButton close) {
		newGameReqPanel = new NewGameReqPanel(reqs);
		this.newGameInputPanel = new NewGameInputDistributedPanel(this);
		this.close = close;
		
		setEnabled(false);
		setPanel();
	}
	/**
	 * This sets up the panels
	 * Must be private so that it cannot be set up more than once
	 * Only to be called in the constructor
	 * <3
	 */
	private void setPanel(){
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
		
		setDividerLocation(500);
	}
	
	//Added by Ruofan 
	public void setGameName(String gameName){
		newGameInputPanel.setGameName(gameName);
	}
	
	public List<Requirement> getSelected(){
		return newGameReqPanel.getSelected();
	}
	/**
	 * Takes in a requirement from the NewGameInputPanel
	 * and sets it into the newGameReqPanel
	 * @param requirment
	 */
}
