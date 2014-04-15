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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.RefreshableController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the games
 * from the server. This controller is called when the user
 * clicks the refresh button.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class GetGamesController extends RefreshableController implements ActionListener {

	private static GetGamesController instance = null;
	private final GameModel model;

	/**
	 * This constructor populates the model variable with the instance of GameModel
	 */
	public GetGamesController() {
		model = GameModel.getInstance();
	}
	
	/**
	 * This method returns the instance of GetGamesController, or creates an instance if one does not exist
	 * @return the instance of GetGamesController
	 */
	public static GetGamesController getInstance(){
		if (instance==null){
			instance = new GetGamesController();
		}
		
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: Fix refresh function, possibly database calls having issues
		// Send a request to the core to save this game
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.GET); // GET == read
		request.addObserver(new GetGamesRequestObserver()); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Add the given games to the local model (they were received from the core).
	 * This method is called by the GetGamesRequestObserver
	 * 
	 * @param games an array of games received from the server
	 */
	public void receivedGames(GameSession[] games) {
		// Empty the local model to eliminate duplications
		model.emptyModel();
		// Make sure the response was not null
		if (games != null) {
			// add the games to the local model
			model.addGames(games);
		}
	}

	@Override
	public void refresh() {
		for (Refreshable r : refreshables){
			r.refreshGames();
		}
	}
}
