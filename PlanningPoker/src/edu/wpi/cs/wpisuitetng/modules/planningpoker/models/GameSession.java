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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Model to contain a single game for the Planning Poker module
 */
public class GameSession extends AbstractModel {

	/** The game */
	private String gameName;
	private String gameDescription;


	private final int ownerID;
	private final int gameID;
	/** game status indicator 
	 * 0  = game is in draft mode
	 * 1  = game is in progress
	 * 2  = game is finished.
	 * */
	
	private GameStatus gameStatus;
	
	/** The date-time stamp of the creation */
	private List<Integer> gameReqs;
	private List<Vote> votes;
	private final Date creationdate;
	/** The date that the game will end, if there is no end time then this value is null*/
	private Date endDate;
	public boolean emailSent = false;
	private List<Float> median;
	private List<Float> mean;
	
	public GameSession(String game, String description, int ownerID, int gameID, Date deadline, List<Integer> gameReqs){
		this.gameName = game;
		this.gameDescription = description;
		this.ownerID = ownerID;
		this.gameID = gameID;
		this.endDate = deadline;
		//this.endDate.setMonth(endDate.getMonth());
		this.gameReqs = gameReqs;
		this.gameStatus = GameStatus.DRAFT;
		creationdate = new Date();
		votes = (new ArrayList<Vote>());
		this.median = null;
		this.mean = null;
	}

	/**
	 * Returns a JSON-encoded string representation of this game object
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, GameSession.class);
	}

	/**
	 * Returns an instance of GameSession constructed using the given
	 * GameSession encoded as a JSON string.
	 * 
	 * @param json the json-encoded GameSession to deserialize
	 * @return the GameSession contained in the given JSON
	 */
	public static GameSession fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, GameSession.class);
	}
	
	/**
	 * Returns an array of GameSession parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of GameSession
	 * @return an array of GameSession deserialzied from the given json string
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
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy hh:mm a");
		DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yy");
		String returnStr = new String();
		returnStr = gameName + "	";
		if(creationdate != null)
			returnStr = returnStr + "     " + "	Start: " + dateFormat1.format(creationdate);
		if(endDate != null){
			returnStr = returnStr + "      " + "End:" + dateFormat1.format(endDate);
		}	
		//if(endDate != null)
		//	returnStr = returnStr + "	End: " + endDate.get(Calendar.MONTH) + '/'+ endDate.get(Calendar.DAY_OF_MONTH) + '/' + endDate.get(Calendar.YEAR);


		if(gameReqs != null){
			returnStr = returnStr +"      Requirements:";
			for(int i = 0; i < gameReqs.size(); i++){
			returnStr =  returnStr + gameReqs.get(i) + ';';
			}
		}
		return returnStr;
	}

	/*
	 * The methods below are required by the model interface, however they
	 * do not need to be implemented for a basic model like GameSession. 
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

	public Date getEndDate() {
		return endDate;
	}

	public GameSession setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public GameSession setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
		return this;
	}

	public List<Integer> getGameReqs() {
		return gameReqs;
	}

	public GameSession setGameReqs(List<Integer> gameReqs) {
		this.gameReqs = gameReqs;
		return this;
	}
	public String getGameName() {
		return gameName;
	}
	public String getGameDescription(){
		return gameDescription;
	}
	public GameSession setGameName(String gameName) {
		this.gameName = gameName;
		return this;
	}

	@Override
	public boolean equals(Object other){
		if (!(other instanceof GameSession))
			return false;
		
		GameSession o = (GameSession) other;
		
		if (this.gameName.equals(o.getGameName()))
		if (this.gameDescription.equals(o.getGameDescription()))
		if (this.ownerID == o.getOwnerID())
		if (this.gameID == o.getGameID())
		if (this.endDate.equals(o.getEndDate()))
		if (this.gameReqs.equals(o.getGameReqs()))
		if (this.gameStatus == o.getGameStatus())
		if (this.votes.equals(o.getVotes()))
			return true;
		
		return false;
		
		
	}
	public GameSession setVotes(List<Vote> v){
		this.votes = v;
		return this;
	}

	/**Gets the votes
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return votes;
	}
	/**
	 * Clears the votes
	 */
	public void clearVotes(){
		votes = (new ArrayList<Vote>());
	}
	/** Adds a vote
	 * @param v the vote to be added.
	 */
	public void addVote(Vote v){
		if(votes.contains(v)) votes.remove(v);
		votes.add(v);
	}
	
	public void calculateStats(){
		int requirementNum = gameReqs.size();
		int userNum = votes.size();
		this.mean = new ArrayList<Float>();
		this.median = new ArrayList<Float>();
		int[][] voteResult = new int[requirementNum][userNum];
		if (userNum == 0){
			mean.add(0f);
			median.add(0f);
			return;
		}
		
		for(int i=0; i < userNum; i++){
			for(int j=0;j < requirementNum; j++){
				voteResult[j][i] = votes.get(i).getVote().get(j);
			}
		}
		for(int i=0; i <requirementNum; i++){
			Arrays.sort(voteResult[i]);
			// calculate median
			if(userNum%2 == 0)
				median.add(((float)voteResult[i][(userNum-1)/2] + voteResult[i][(userNum-1)/2+1])/2);
			else
				median.add((float)voteResult[i][(userNum-1)/2]);
			// calculate mean
			int sum = 0;
			for(int j=0; j < userNum; j++){
				sum += voteResult[i][j];
			}
			mean.add(((float)sum) / userNum);
		}
	}
	public List<Float> getMean(){
		return mean;
	}
	public List<Float> getMedian(){
		return median;
	}
	
}
