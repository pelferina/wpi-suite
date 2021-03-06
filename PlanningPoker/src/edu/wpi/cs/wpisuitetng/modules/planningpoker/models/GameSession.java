/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Team Cosmic Latte
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


/**
 * Model to contain a single game for the Planning Poker module
 * @author FFF8E7
 * @version 6
 */
public class GameSession extends AbstractModel {

	/** The game */
	private String gameName;
	private String gameDescription;


	private final int ownerID;
	private final int gameID;

	private GameStatus gameStatus;
	
	/** The date-time stamp of the creation */
	private List<Integer> gameReqs;
	private final Date creationdate;
	/** The date that the game will end, if there is no end time then this value is null*/
	private Date endDate;
	private List<Float> median;
	private List<Float> mean;
	private List<Double> standardDeviation;
	private int deckId;
	private List<Integer> finalVotes;
	

	/**
	 * This constructor generates a game session
	 * @param game the name of the game as a string
	 * @param description the description of the game as a string
	 * @param ownerID the id number of the owner as an int
	 * @param gameID the id number of the game as an int
	 * @param deadline the time of the deadline as a date
	 * @param gameReqs the requirements of the game, as a List<Integers>
	 */
	public GameSession(String game, String description, int ownerID, int gameID, Date deadline, List<Integer> gameReqs){
		gameName = game;
		gameDescription = description;
		this.ownerID = ownerID;
		this.gameID = gameID;
		endDate = deadline;
		this.gameReqs = gameReqs;
		gameStatus = GameStatus.DRAFT;
		creationdate = new Date();
		deckId = -1;
		median = null;
		mean = null;
		standardDeviation = null;
		finalVotes = new ArrayList<Integer>();
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
		final DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy hh:mm a");
		String returnStr = new String();
		returnStr = gameName + "	" + gameStatus + " ";
		if(creationdate != null){
			returnStr = returnStr + "     " + "	Start: " + dateFormat1.format(creationdate);
		}
		if(endDate != null){
			returnStr = returnStr + "      " + "End:" + dateFormat1.format(endDate);
		}
		//if(endDate != null)
		//	returnStr = returnStr + "	End: " + endDate.get(Calendar.MONTH) + '/'+ endDate.get(Calendar.DAY_OF_MONTH) + '/' + endDate.get(Calendar.YEAR);


		if(gameReqs != null){
			returnStr = returnStr + "      Requirements:";
			for(int i = 0; i < gameReqs.size(); i++){
			returnStr =  returnStr + gameReqs.get(i) + ';';
			}
		}
		return returnStr;
	}
	
	/**
	 * @return String Deadline 
	 */
	public String getDeadlineString()
	{
		if (endDate != null){
			final DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			return dateFormat1.format(endDate);
		}
		else {
			return "No deadline";
		}
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
	public Boolean identify(Object o) {
		return null;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public int getGameID() {
		return gameID;
	}

	public Date getEndDate() {
		return endDate;
	}

	/**
	 * sets an end date for game session
	 * @param endDate the date
	 * @return GameSession, the game session we are changing
	 */
	public GameSession setEndDate(Date endDate) { // $codepro.audit.disable accessorMethodNamingConvention
		this.endDate = endDate;
		return this;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * sets the game status of the game session
	 * @param gameStatus the game status to set to
	 * @return GameSession the game session being changed
	 */
	public GameSession setGameStatus(GameStatus gameStatus) { // $codepro.audit.disable accessorMethodNamingConvention
		this.gameStatus = gameStatus;
		return this;
	}

	public List<Integer> getGameReqs() {
		return gameReqs;
	}

	/**
	 * sets the game requirements of a game session
	 * @param gameReqs the list of requirements to set
	 * @return GameSession the session being set
	 */
	public GameSession setGameReqs(List<Integer> gameReqs) { // $codepro.audit.disable accessorMethodNamingConvention
		this.gameReqs = gameReqs;
		return this;
	}
	/**
	 * This is sets the game session description
	 * @param description the string you want to set as the description
	 * @return returns the game session called on
	 */
	public GameSession setGameDescription(String description){ // $codepro.audit.disable accessorMethodNamingConvention
		gameDescription = description;
		return this;
	}
	public String getGameName() {
		return gameName;
	}
	public String getGameDescription(){
		return gameDescription;
	}
	/**
	 * sets the name of the game
	 * @param gameName the name of the game
	 * @return GameSession the session of the game to set the name of
	 */
	public GameSession setGameName(String gameName) { // $codepro.audit.disable accessorMethodNamingConvention
		this.gameName = gameName;
		return this;
	}

	@Override
	public boolean equals(Object other){
		if (!(other instanceof GameSession)){
			return false;
		}
		
		final GameSession o = (GameSession) other;

		if (gameName.equals(o.getGameName())){
			if (gameDescription.equals(o.getGameDescription())){
				if (ownerID == o.getOwnerID()){
					if (gameID == o.getGameID()){
						if ((endDate == null && o.getEndDate() == null) || (endDate != null && o.getEndDate() != null && endDate.equals(o.getEndDate()))){ // if both are null or they are equal
							if (gameReqs.equals(o.getGameReqs())){
								if (gameStatus == o.getGameStatus()){
										if(deckId == o.getDeckId()){
											return true;
										}
											
								}
							}
						}
					}
				}
			}
		}
		
		return false;
		
		
	}
	/**
	 * Never used, only for CodePro
	 * @return The gameID as it is unique
	 */
	@Override
	public int hashCode(){
		return this.getGameID();
	}
	
	/**Gets the votes
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return VoteModel.getInstance().getVotes(gameID, this.getProject());
	}

	/**
	 * This method calculates the statistics of the votes, such as mean and median
	 */
	public void calculateStats(){
		final List<Vote> votes = this.getVotes();
		final int requirementNum = gameReqs.size();
		final int userNum = votes.size();
		mean = new ArrayList<Float>();
		median = new ArrayList<Float>();
		standardDeviation = new ArrayList<Double>();
		final int[][] voteResult = new int[requirementNum][userNum];
		for(int i=0; i < userNum; i++){
			for(int j=0;j < requirementNum; j++){
				voteResult[j][i] = votes.get(i).getVote().get(j);
			}
		}
		if(votes.size() != 0){
			for(int i=0; i < requirementNum; i++){
				Arrays.sort(voteResult[i]);
				
				// determine number of zeros in current operation
				int shift = 0;
				while(shift < voteResult[i].length && voteResult[i][shift] == -2){
					shift++;
				}
				
				// if there are only zero votes, let the zero fall thru
				if(shift == userNum){
					shift = 0;
				}
				// calculate median
				if((userNum + shift) % 2 == 0){
					median.add(((float)voteResult[i][(userNum + shift - 1) / 2] + voteResult[i][(userNum + shift - 1) / 2 + 1]) / 2);
				}
				else if ((userNum + shift) > 1){
					median.add((float)voteResult[i][(userNum + shift - 1) / 2]);
				}
				else {
					median.add((float)voteResult[i][(userNum + shift) - 1]);
				}
				// calculate mean
				int sum = 0;
				for(int j=0; j < userNum; j++){
					sum += voteResult[i][j];
				}
				mean.add(((float)sum) / (userNum - shift));
				//calculate standard deviation
				List<Integer> reqEstimates = new ArrayList<Integer>();
				for (Vote v: votes){
					if (v.getVote().get(i) != -2){
						reqEstimates.add(v.getVote().get(i));
					}
				}
				standardDeviation.add(calculateStdDev(mean.get(i), reqEstimates));
			}
		}
	}
	public List<Float> getMean(){
		return mean;
	}
	public List<Float> getMedian(){
		return median;
	}
	
	/**
	 * Setter for deckID
	 * @param deckId the ID of the deck, as integer
	 * @return the GameSession the deck is from
	 */
	public GameSession setDeckId(int deckId){ // $codepro.audit.disable accessorMethodNamingConvention
		this.deckId = deckId;
		return this;
	}
	
	public int getDeckId(){
		return deckId;
	}

	/**
	 * Setter for finalVotes
	 * @param finalVote the List<Integer> of all of the votes
	 * @return the GameSession the votes are from
	 */
	public GameSession setFinalVotes(List<Integer> finalVote) { // $codepro.audit.disable accessorMethodNamingConvention
		finalVotes = finalVote;
		return this;
	}

	public List<Integer> getFinalVotes() {
		return finalVotes;
	}
	
	/**
	 * Calculates the stdDev using mean and list of estimates
	 * @param mean The average of estimates
	 * @param Estimates The data
	 * @return Calculates The standard deviation of the given data
	 */
	public double calculateStdDev(float mean, List<Integer> Estimates){
		double estimatesSum = 0;
		double estimateMinusMeanSquare;
		final double stddev;
		
		if(Estimates.size() == 0)
		{
			return 0; // No non-zero estimates
		}
		for (int i: Estimates){
			estimateMinusMeanSquare = Math.pow((double)i - mean, 2);
			estimatesSum += estimateMinusMeanSquare;
		}
		stddev = Math.pow((1 / (double)Estimates.size()) * estimatesSum, 0.5);

		return stddev;
	}

	public List<Double> getStandardDeviation() {
		return standardDeviation;
	}
	
	/**
	 * Only use this for testing purposes
	 * @param votes
	 */
	public void setVotes(List<Vote> votes){
	}
}
