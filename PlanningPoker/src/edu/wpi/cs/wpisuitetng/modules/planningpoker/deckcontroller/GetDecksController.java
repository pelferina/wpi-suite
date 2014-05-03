/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.RefreshableController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the decks
 * from the server. This controller is called when the user
 * clicks the deck from the Drop-down menu.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class GetDecksController extends RefreshableController implements ActionListener {

	private final GetDecksRequestObserver observer;
	private static GetDecksController instance = null;

	/**
	 * Constructs the controller given a DeckModel
	 */
	private GetDecksController() {
		
		observer = new GetDecksRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetDecksController or creates one if it does not
	 * exist. */
	public static GetDecksController getInstance()
	{
		if(instance == null)
		{
			instance = new GetDecksController();
		}
		
		return instance;
	}

	/**
	 * Sends an HTTP request to store a Deck when the
	 * create deck button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this Deck
		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all Decks
	 */
	public void retrieveDecks() {
		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}

	/**
	 * Add the given Decks to the local model (they were received from the core).
	 * This method is called by the GetDecksRequestObserver
	 * 
	 * @param decks array of Decks received from the server
	 */
	public void receivedDecks(Deck[] decks) {
		// Make sure the response was not null
		if (decks != null) 
		{
			// add the Decks to the local model
			DeckModel.getInstance().addDecks(decks);
		}
	}

	@Override
	public void refresh() {
		for (Refreshable r: refreshables){
			r.refreshDecks();
		}
	}
}
