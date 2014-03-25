/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameInputPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * adding the contents of the message text field to the model as a new
 * message.
 *
 */
public class AddGameController implements ActionListener {
	
	private final GameModel model;
	private final NewGameMainPanel view;
	
	/**
	 * Construct an AddMessageController for the given model, view pair
	 * @param model the model containing the messages
	 * @param view the view where the user enters new messages
	 */
	public AddGameController(GameModel model, NewGameMainPanel view) {
		this.model = model;
		this.view = view;
	}

	/* 
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Get the text that was entered
		String message = view.initialPanel.getTxtNewGame().getText();
		
		// Make sure there is text
		if (message.length() > 0) {
			// Clear the text field
			view.initialPanel.getTxtNewGame().setText("");
			
			// Send a request to the core to save this message
			final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.PUT); // PUT == create
			request.setBody(new GameSession(message).toJSON()); // put the new message in the body of the request
			request.addObserver(new AddGameRequestObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
	}

	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param message
	 */
	public void addMessageToModel(GameSession message) {
		model.addMessage(message);
	}
}
