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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the add deck button by
 * adding the contents of the deck text field to the model as a new
 * deck.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class AddDeckController implements ActionListener {
	
	private static AddDeckController instance = null;
	final private AddDeckRequestObserver observer;
	
	/**
	 * Construct an AddDeckController for the given model, view pair
	 */
	private AddDeckController() {
		observer = new AddDeckRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the AddDeckController or creates one if it does not
	 * exist. */
	public static AddDeckController getInstance()
	{
		if(instance == null)
		{
			instance = new AddDeckController();
		}
		
		return instance;
	}

	/**
	 * This method adds a deck to the server.
	 * @param newDeck is the Deck to be added to the server.
	 */
	public void addDeck(Deck newDeck) 
	{
		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.PUT); // PUT == create
		request.setBody(newDeck.toJSON()); // put the new Deck in the body of the request		
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * When the new Deck is received back from the server, add it to the local model.
	 * @param deck The deck to add
	 */
	public void addDeckToModel(Deck deck) {
		DeckModel.getInstance().addDeck(deck);
	}
}
