/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class CompleteView extends JSplitPane {

	private GameData gameData;
	private VoteData voteData;

	public boolean isNew = false;

	/**
	 * The constructor for CompleteView
	 * @param completedGame, the completed game to be viewed
	 */
	public CompleteView(GameSession completedGame){
		gameData = new GameData(completedGame, this);
		voteData = new VoteData(completedGame, this);
		
		addImpl(gameData, JSplitPane.LEFT, 1);
		final Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(voteData, JSplitPane.RIGHT, 2);
		setDividerLocation(400);
	}

	/**
	 * Gets the game requirements from game data, and returns it. This is used by the VoteData class
	 * @return
	 */
	
	public List<Requirement> getGameRequirements() {
		// TODO Auto-generated method stub
		return gameData.getGameReqs();
	}

	/**
	 * Sends the given requirement to the VoteData class
	 * @param req, the requirement to be viewed
	 */
	public void sendReqToView(Requirement req) {
		voteData.receiveNewReq(req);
	}
	
	/**
	 * Returns the index of the given requirement id
	 * @param id, the id of the requirement
	 * @return
	 */
	public int getIndex(int id){
		return gameData.getReqIndex(id);
	}
	
	public GameData getGameData(){
		return gameData;
	}
	
	public VoteData getVoteData(){
		return voteData;
	}
}