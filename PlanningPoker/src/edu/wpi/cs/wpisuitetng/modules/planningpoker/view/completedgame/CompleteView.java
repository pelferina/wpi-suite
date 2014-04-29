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
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
/**
 * The CompleteView class
 * @author FFF8E7
 * @version 6
 */
public class CompleteView extends JSplitPane {

	private final GameData gameData;
	private final VoteData voteData;

	public boolean isNew = true;

	/**
	 * The constructor for CompleteView
	 * @param completedGame The completed game to be viewed
	 */
	public CompleteView(GameSession completedGame){
		gameData = new GameData(completedGame, this);
		voteData = new VoteData(completedGame, this);
		
		addImpl(gameData, JSplitPane.LEFT, 1);
		final Dimension minimumSize = new Dimension(600, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(voteData, JSplitPane.RIGHT, 2);
		setDividerLocation(400);
		setEnabled(false);
		
		//Flatten the divider
		this.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                public void setBorder(Border b) {
                }
            };
            }
        });
        this.setBorder(null);
        this.setEnabled( false );
		
		
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
	 * @param req The requirement to be viewed
	 */
	public void sendReqToView(Requirement req) {
		voteData.receiveNewReq(req);
	}
	
	/**
	 * Returns the index of the given requirement id
	 * @param id The id of the requirement
	 * @return the index of a requirement with given ID, as integer
	 */
	public int getIndex(int id){
		return gameData.getReqIndex(id);
	}
	/**
	 * Getter for gameData
	 * @return gameData
	 */
	public GameData getGameData(){
		return gameData;
	}
	/**
	 * Getter for voteDate
	 * @return voteData
	 */
	public VoteData getVoteData(){
		return voteData;
	}
	/**
	 * Grabs the nextRequirment
	 * @param estimate The estimate to be passed around
	 */
	public void nextRequirement(int estimate) {
		gameData.nextRequirement(estimate);
		
	}
	/**
	 * Sends the final estimates to the gameData table
	 * @param finalVote The List<Integer> of final votes
	 */
	public void sendEstimatesToTable(List<Integer> finalVote) {
		gameData.receiveFinalVotes(finalVote);
	}
	
	/**
	 * This saves the vote data of the game
	 */
	public void saveGame(){
		voteData.saveGame();
	}
}
