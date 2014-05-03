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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * UpdateDeckController class
 * @author FFF8E7
 * @version 6
 */
public class UpdateDeckController {
	private static UpdateDeckController instance = null;
	private final UpdateDeckRequestObserver observer = UpdateDeckRequestObserver.getInstance();
	private final DeckModel model = DeckModel.getInstance();
	
	/**
	 * Private constructor to force singleton class
	 */
	private UpdateDeckController(){
	}
	
	/**
	 * Send a deck update to the server
	 * @param d The deck to be updated, the ID MUST match
	 */
	public void updateDeck(Deck d){
		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.POST); // PUT == create
		request.setBody(d.toJSON()); // put the new Deck in the body of the request		
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}
	/**
	 * Adds a deck to the model
	 * @param d the deck to add
	 */
	public void addDeckToModel(Deck d){
		model.addDeck(d);
	}
	/**
	 * @return the instance
	 */
	public static UpdateDeckController getInstance()
	{
		if(instance == null)
		{
			instance = new UpdateDeckController();
		}
		
		return instance;
	}
	
	
}
