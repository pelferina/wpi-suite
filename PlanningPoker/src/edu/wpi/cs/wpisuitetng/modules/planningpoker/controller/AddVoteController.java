/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user casts their vote during a game
 * 
 * @author FFF8E7
 * @version 6
 */
public class AddVoteController {

	private final VoteModel model;

	/**
	 * This constructor populates the model variable with the inputted model
	 * @param model The VoteModel to control over
	 */
	public AddVoteController(VoteModel model) {
		this.model = model;
	}
	
	/** Sends a new vote object to the server
	 * @param v the vote to be sent
	 */
	public void sendVote(Vote v){
		final Request request = Network.getInstance().makeRequest("planningpoker/vote", HttpMethod.PUT); // PUT == create
		request.setBody(v.toJSON()); // put the new session in the body of the request
		request.addObserver(new AddVoteRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Getter for model
	 * @return the model variable
	 */
	public VoteModel getModel() {
		return model;
	}

}
