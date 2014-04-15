/** Vote class for voting in a planning poker game
 * @version APR 11 2014
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author Team FFF8E7
 *
 */
public class Vote extends AbstractModel implements Comparable<Vote>{

	private int gameID;
	private int UID;
	private int VoteID;
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
		this.setVoteID();
		
	}
	
	/** Constructs a vote submission object, the UID must be set if this is on the server.
	 * @param vote 		A list of voted on requirements, must match the 
	 * 						list of requirements in the associated game in order
	 * @param gameID	the game the user was voting on 
	 */
	public Vote(List<Integer> vote, int gameID) {
		this.setVote(vote);
		this.setUID(-1);
		this.setGameID(gameID);
		this.setVoteID();
		
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
	public Vote setGameID(int gameID) {
		this.gameID = gameID;
		this.setVoteID();
		return this;
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
	public Vote setUID(int uID) {
		UID = uID;
		this.setVoteID();
		return this;
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
	public Vote setVote(List<Integer> vote) {
		this.vote = vote;
		return this;
	}
	public String toJSON() {
		return new Gson().toJson(this, Vote.class);
	}

	/**
	 * Returns an instance of GameSession constructed using the given
	 * GameSession encoded as a JSON string.
	 * 
	 * @param json the json-encoded GameSession to deserialize
	 * @return the GameSession contained in the given JSON
	 */
	public static Vote fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Vote.class);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		if(o == null || o.getClass() != this.getClass()) return false;
		Vote other = (Vote) o;
		return (this.gameID == other.gameID && this.UID == other.UID);
	}

	/**
	 * @return the voteID
	 */
	public int getVoteID() {
		return VoteID;
	}
	/**
	 * Sets the vote id with the tohash method
	 */
	public Vote setVoteID(){
		this.VoteID = toHash(this);
		return this;
	}
	/**
	 * @param voteID the voteID to set
 	 * should use the tohash method
	 */
	public Vote setVoteID(int voteID) {
		VoteID = voteID;
		return this;
	}
	/** (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other){
		if(other.getClass() != Vote.class) return false;
		return ((Vote) other).VoteID == this.VoteID;
		
	}
	/**
	 * Compares a vote to another vote
	 * @param o the other vote
	 */
	@Override
	public int compareTo(Vote o) {
		return o.VoteID - this.VoteID;
	}
	public static int toHash(Vote v){
		return v.gameID* 100000 + v.UID;
	}
	
}
