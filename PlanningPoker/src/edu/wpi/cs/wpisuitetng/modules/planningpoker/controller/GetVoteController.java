/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.RefreshableController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
/**
 * The GetVoteController class
 * @author Cosmic Latte
 * @version 6
 */
public class GetVoteController extends RefreshableController implements ActionListener {

	private static GetVoteController instance = null;
	private final VoteModel model = VoteModel.getInstance();

	/**
	 * This returns the instance of the GetVoteController
	 * 
	 * @return the GetVoteController instance
	 */
	public static GetVoteController getInstance(){
		if (instance==null){
			instance = new GetVoteController();
		}

		return instance;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: Fix refresh function, possibly database calls having issues
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest("planningpoker/vote", HttpMethod.GET); // GET == read
		request.addObserver(new GetVotesRequestObserver()); // add an observer to process the response
		request.send(); // send the request
	}
	

	/**
	 * Add the given votes to the local model (they were received from the core).
	 * This method is called by the GetGamesRequestObserver
	 * 
	 * @param votes an array of games received from the server, as Vote
	 */
	public void receivedGames(Vote[] votes) {
		// Empty the local model to eliminate duplications
		model.emptyModel();
		// Make sure the response was not null
		if (votes != null) {
			// add the games to the local model
			model.addVotes(votes);
		}
	}

	@Override
	public void refresh() {
		for (Refreshable r : refreshables){
			r.refreshGames();
		}
	}

}
