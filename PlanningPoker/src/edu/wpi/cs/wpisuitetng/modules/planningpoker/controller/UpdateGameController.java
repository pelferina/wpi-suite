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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller handles game update calls
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class UpdateGameController {
	
	private final GameModel model;
	//TODO Remove depreciated methods once GUI is updated for new data definitions
	/**
	 * Construct an UpdateGameController for the given model
	 * @param model the model containing the games
	 */
	public UpdateGameController(){
		this.model = GameModel.getInstance();
	}
	/**
	 * This method sends a request to update GameSession and adds an observer
	 * @param ToSend the GameSession to send request about
	 */
	public void sendGame(GameSession ToSend){
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // PUT == create
		request.setBody(ToSend.toJSON()); // put the new session in the body of the request
		request.addObserver(new UpdateGameRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	/**
	 * When the new game is received back from the server, add it to the local model.
	 * @param game
	 */
	public void addGameToModel(GameSession game) {
		model.addGame(game);
	}

}
