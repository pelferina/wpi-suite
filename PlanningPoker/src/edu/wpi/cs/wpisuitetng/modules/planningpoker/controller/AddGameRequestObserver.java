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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a game.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class AddGameRequestObserver implements RequestObserver {
	
	private final AddGameController controller;
	/**
	 * This constructor populates the controller variable with the inputted AddGameController
	 * @param controller The controller to observe
	 */
	public AddGameRequestObserver(AddGameController controller) {
		this.controller = controller;
	}
	
	/*
	 * Parse the game that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the game out of the response body
		final GameSession game = GameSession.fromJson(response.getBody());
		
		// Pass the games back to the controller
		controller.addGameToModel(game);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a gamemodel failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a gamemodel failed.");
	}

}
