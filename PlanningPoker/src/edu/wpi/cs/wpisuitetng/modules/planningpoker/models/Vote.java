/** Vote class for voting in a planning poker game
 * @version APR 11 2014
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.List;

/**
 * @author Team FFF8E7
 *
 */
public class Vote {

	private int gameID;
	private int UID;
	private List<Integer> vote;

	/** Constructs a vote submission object.
	 * @param vote 		A list of voted on requirements, must match the 
	 * 						list of requirements in the associated game in order
	 * @param UID		the user who voted
	 * @param gameID	the game the user was voting on 
	 */
	public Vote(List<Integer> vote, int UID, int gameID) {
		this.setVote(vote);
		this.setUID(UID);
		this.setGameID(gameID);
		
	}

	/**
	 * @return THE GAMEID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * @param gameID THE GAMEID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * @return THE USERID
	 */
	public int getUID() {
		return UID;
	}

	/**
	 * @param uID The new USERID
	 */
	public void setUID(int uID) {
		UID = uID;
	}

	/**
	 * @return the List of votes
	 */
	public List<Integer> getVote() {
		return vote;
	}

	/**
	 * @param vote The new votelist
	 */
	public void setVote(List<Integer> vote) {
		this.vote = vote;
	}

}
