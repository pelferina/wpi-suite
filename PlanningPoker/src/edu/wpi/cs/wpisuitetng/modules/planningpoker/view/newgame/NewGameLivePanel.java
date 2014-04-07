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

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;

/**
 * The panel for creating a new live Planning Poker game, along with fields
 * that have the parameters for what a new game should have.
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameLivePanel extends AbsNewGamePanel {
	
	public NewGameLivePanel(GameModel gameModel) {
		newGameInputPanel = new NewGameInputLivePanel(this);
		//newGameReqPanel = new NewGameReqPanel();
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
		//addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
		
		setDividerLocation(500);
	}
	
	//Added by Ruofan
	public void setGameName(String gameName){
		newGameInputPanel.setGameName(gameName);
	}
	/**
	 * Takes in a requirement from the NewGameInputPanel
	 * and sets it into the newGameReqPanel
	 * @param requirment
	 */
	
}
