/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Dimension;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * A split pane for when a game's information is being viewed
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class GameView extends JSplitPane{

	GameRequirements gameReqs;
	PlayGame playGame;
	ViewGame viewGame;
	
	public boolean isNew = false;
	
	/**
	 * Constructor to make a gameview with a given session
	 * @param gameToPlay the active game to be viewing
	 */
	public GameView (GameSession gameToPlay){
		this.gameReqs = new GameRequirements(gameToPlay, this);
		if(gameToPlay.getGameStatus() == GameStatus.COMPLETED)
		{
			this.viewGame = new ViewGame(gameToPlay, this);
		}
		else
		{
			this.playGame = new PlayGame(gameToPlay, this);
		}
		addImpl(gameReqs, JSplitPane.LEFT, 1);
		final Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		if(gameToPlay.getGameStatus() == GameStatus.COMPLETED)
		{
			addImpl(viewGame, JSplitPane.RIGHT, 2);
		}
		else
		{
			addImpl(playGame, JSplitPane.RIGHT, 2);
		}
		setDividerLocation(400);
	}

	/**
	 * This method chooses a requirement
	 * @param r the requirement
	 */
	public void sendReqToPlay(Requirement r) {
		playGame.chooseReq(r);
	}
	
	public void sendReqToView(Requirement r) {
		playGame.chooseReq(r);
	}

	/**
	 * This method updates the requirement tables with a requirement
	 * @param r the requirement
	 */
	public void updateReqTables(Requirement r) {
		gameReqs.updateTables(r);
	}
	/**
	 * clears the game view's boxes
	 */
	public void clearBoxes() {
		playGame.clear();
	}
	
}
