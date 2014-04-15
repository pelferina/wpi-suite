/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

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
	public void sendGame(GameSession ToSend){
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // PUT == create
		request.setBody(ToSend.toJSON()); // put the new session in the body of the request
		request.addObserver(new UpdateGameRequestObserver()); // add an observer to process the response
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
