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
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Model to contain a single game on the PostBoard
 * 
 * @author Chris Casola
 *
 */
public class GameSession extends AbstractModel {

	/** The game */
	private String gameName;



	private final int ownerID;
	private final int gameID;
	/** game status indicator 
	 * 0  = game is in draft mode
	 * 1  = game is in progress
	 * 2  = game is finished.
	 * */
	
	private int gameStatus;
	/** The date-time stamp of the creation */
	private List<Requirement> gameReqs;
	private final Date creationdate;
	/** The date that the game will end, if there is no end time then this value is null*/
	private Calendar endDate;

	/**
	 * Constructs a PostBoardMessage for the given string game
	 * @param game the name of the game
	 * @param ownerID the user ID of the logged in user who created the game
	 * @param gameID the next ID in the list of game IDs
	 */
	public GameSession(String game, int ownerID, int gameID) {
		this.gameName = game;
		this.ownerID = ownerID;
		this.gameID = gameID;
		creationdate = new Date();
	}
	
	public GameSession(String game, int ownerID, int gameID, Calendar deadline, List<Requirement> gameReqs){
		this.gameName = game;
		this.ownerID = ownerID;
		this.gameID = gameID;
		this.endDate = deadline;
		this.gameReqs = gameReqs;
		creationdate = new Date();
	}

	/**
	 * Returns a JSON-encoded string representation of this game object
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, GameSession.class);
	}

	/**
	 * Returns an instance of PostBoardMessage constructed using the given
	 * PostBoardMessage encoded as a JSON string.
	 * 
	 * @param json the json-encoded PostBoardMessage to deserialize
	 * @return the PostBoardMessage contained in the given JSON
	 */
	public static GameSession fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, GameSession.class);
	}
	
	/**
	 * Returns an array of PostBoardMessage parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of PostBoardMessage
	 * @return an array of PostBoardMessage deserialzied from the given json string
	 */
	public static GameSession[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, GameSession[].class);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// Format the date-time stamp

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		if(creationdate == null || dateFormat == null) 
			return gameName;
		return dateFormat.format(creationdate) + ":    " + gameName;
	}

	/*
	 * The methods below are required by the model interface, however they
	 * do not need to be implemented for a basic model like PostBoardMessage. 
	 */

	@Override
	public void save() {}

	@Override
	public void delete() {}

	@Override
	public Boolean identify(Object o) {return null;}

	public int getOwnerID() {
		return ownerID;
	}

	public int getGameID() {
		return gameID;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public List<Requirement> getGameReqs() {
		return gameReqs;
	}

	public void setGameReqs(List<Requirement> gameReqs) {
		this.gameReqs = gameReqs;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
}
