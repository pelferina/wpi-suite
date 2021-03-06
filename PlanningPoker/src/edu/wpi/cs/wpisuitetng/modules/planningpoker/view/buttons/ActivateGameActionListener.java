/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This listener watches for the end of a game
 * @author FFF8E7
 * @version 6
 */
public class ActivateGameActionListener implements ActionListener{
	GameSession game;
	/**
	 * Constructor to populate gameID
	 * @param game The GameSession to listen to
	 */
	public ActivateGameActionListener(GameSession game){
		this.game = game;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(game.getDeckId() >= 0) {
			updateDeck();
		}
		game.setGameStatus(GameStatus.ACTIVE);
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // POST == UPDAT
		request.setBody(game.toJSON()); // put the new session in the body of the request
		request.addObserver(new UpdateGameRequestObserver()); // add an observer to process the response
		request.send(); // send the request
	}
	/**
	 * Updates the deck
	 */
	public void updateDeck(){
		boolean deckFound = false;
		final String deckName = DeckModel.getInstance().getDeck(game.getDeckId()).getName();
		final List<Deck> decks = new ArrayList<Deck>(DeckModel.getInstance().getDecks());
		for (Deck d: decks){
			if(!d.getIsDeleted()) {
				if (deckName.equals(d.getName())) { 
					game.setDeckId(d.getId());
					deckFound = true;
				}
			}
		}
		if(!deckFound){
			game.setDeckId(0); // set id to default deck
		}
	}
}