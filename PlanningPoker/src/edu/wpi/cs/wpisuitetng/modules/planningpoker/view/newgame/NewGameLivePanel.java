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
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameLivePanel extends JSplitPane {
	NewGameInputPanel newGameInputPanel;
	NewGameReqPanel newGameReqPanel;
	private GameModel lstgameModel;
	public NewGameLivePanel(GameModel gameModel) {
		lstgameModel = gameModel;
		newGameInputPanel = new NewGameInputPanel(lstgameModel);
		newGameReqPanel = new NewGameReqPanel();
		setPanel();
	}
	
	private void setPanel(){
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
	}

}
