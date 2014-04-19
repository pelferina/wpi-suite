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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * adding the contents of the Game text field to the model as a new
 * Game.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class AddGameController implements ActionListener {
	
	private final GameModel model;
	//TODO Remove depreciated methods once GUI is updated for new data definitions
	
	/**
	 * Construct an AddGameController for the given model, view pair
	 * @param model the model containing the Games
	 */
	public AddGameController(GameModel model) {
		this.model = model;
	}
	
	/**
	 * This method sends a request and adds an observer to a GameSession
	 * @param ToSend The GameSession to send a request for
	 */
	public void sendGame(GameSession ToSend){
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.PUT); // PUT == create
		request.setBody(ToSend.toJSON()); // put the new session in the body of the request
		request.addObserver(new AddGameRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	/* 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// make sure there is a session
		//TODO DEPRECIATED
	}

	/**
	 * When the new Game is received back from the server, add it to the local model.
	 * @param Game
	 */
	public void addGameToModel(GameSession Game) {
		model.addGame(Game);
	}
}
