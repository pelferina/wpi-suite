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

/**
 * This observer handles responses to requests for all
 * post board games.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class GetGamesRequestObserver implements RequestObserver {
	
	private final GetGamesController controller;
	
	/**
	 * This constructor populates the controller variable with the instance of GetGamesController
	 */
	public GetGamesRequestObserver() {
		controller = GetGamesController.getInstance();
	}

	/*
	 * Parse the games out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final GameSession[] games = GameSession.fromJsonArray(iReq.getResponse().getBody());
//		System.out.println(games.length);
		controller.receivedGames(games);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/*
	 * placeholder for exception handling
	 * TODO Add swanky error handling.
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		if(exception == null) System.err.println("FAILURE in GetGamesRequestObserver");
		;//exception.printStackTrace();
	}

}
