/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Cosmic Latte
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all
 * decks.
 * 
 * @author FFF8E7
 * @version 6
 */
public class GetDecksRequestObserver implements RequestObserver {

	private final GetDecksController controller;
	
	/**
	 * Constructs the observer given a GetDecksController
	 * @param controller the controller used to retrieve decks
	 */
	public GetDecksRequestObserver(GetDecksController controller) {
		this.controller = controller;
	}

	/**
	 * Parse the Decks out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Convert the JSON array of Decks to a Deck object array
		final Deck[] Decks = Deck.fromJsonArray(iReq.getResponse().getBody());
		
		// Pass these Decks to the controller
		controller.receivedDecks(Decks);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/**
	 * Put an error Deck in the GamePanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		final Deck[] errorDeck = { new Deck() };
		controller.receivedDecks(errorDeck);
	}

}
