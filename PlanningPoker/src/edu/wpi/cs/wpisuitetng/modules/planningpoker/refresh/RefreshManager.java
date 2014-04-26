/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * This method manages refresh requests
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class RefreshManager {
	
	GetGamesController gameController;
	GetRequirementsController reqController;
	GetVoteController voteController;
	List<Vote> voteCache;
	List<Requirement> reqCache;
	List<GameSession> gameCache;
	
	public RefreshManager() {
	
		gameController = GetGamesController.getInstance();
		reqController = GetRequirementsController.getInstance();
		voteController = GetVoteController.getInstance();
		
		reqCache = new ArrayList<Requirement>();
		gameCache =  new ArrayList<GameSession>();
		voteCache = new ArrayList<Vote>();
		
		//Create action listener for Games
		final ActionListener gameCheck = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateGames();
					}
				}

				catch(RuntimeException exception){
					System.err.println(exception.getMessage());
					//exception.printStackTrace();
				}
			}
		};
		//Create action listener for Requirements
		final ActionListener reqCheck = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateRequirements();
					}
				}

				catch(RuntimeException exception){
					System.err.println(exception.getMessage());
				}
			}
		};
		
		// Timer will update RefreshManager every 2 seconds
		final Timer g = new Timer(1000, gameCheck);
		final Timer r = new Timer(1000, reqCheck);

		g.start();
		r.start();
	}

	/**
	 * This method updates games
	 */
	private void updateGames()
	{
		//Make a request to the database
		gameController.actionPerformed(null);
		voteController.actionPerformed(null);
		if ( differentList(gameCache, GameModel.getInstance().getGames()) || differentList(voteCache, VoteModel.getInstance().getVotes())){
			gameController.refresh();
			gameCache = new ArrayList<GameSession>(GameModel.getInstance().getGames());
			voteCache = new ArrayList<Vote>(VoteModel.getInstance().getVotes());

		}

		
	}
	
	private void updateRequirements()
	{
		//Make a request to the database
		
		if (differentList(reqCache, RequirementModel.getInstance().getRequirements())){
			reqController.refresh();
			reqCache = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		}	
	}
	
	
	//Assumes list are in the same order
	/**
	 * Checks whether two lists are different or not. Assumes they are sorted in the same order
	 * @param l1 the first list
	 * @param l2 the second list
	 * @return whether or not they are the same as a boolean
	 */
	public boolean differentList(List<?> l1, List<?> l2) {
		if (l1.size() != l2.size() ){
			return true;
		}
		
	    for (int i = 0; i < l1.size(); i++){
	    	if ( ! l1.get(i).equals(l2.get(i) )){
	        	return true;
	    	}
	    }
	    
	    return false;
	}

}
