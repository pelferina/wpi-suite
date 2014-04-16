/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;



import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;


/**
 * This class manages all votes
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class VoteEntityManager implements EntityManager<Vote> {

	private final Data db;

	/**
	 * This constructor populates the database variable with input
	 * @param db the database to manage
	 */
	public VoteEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public Vote makeEntity(Session s, String content)
			throws WPISuiteException {
		final Vote v = Vote.fromJson(content);
		// update invalid information.
		if (v.getUID() == -1) v.setUID(s.getUser().getIdNum());
		// Check for duplication
		GameSession[] session = null;
		try {
			session = db.retrieve(GameSession.class, "GameID", v.getGameID(), s.getProject()).toArray(new GameSession[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(session.length != 1) throw new WPISuiteException();
		session[0].addVote(v);
		session[0].setGameStatus(GameStatus.INPROGRESS);
		db.update(GameSession.class, "GameID", session[0].getGameID(), "Votes", session[0].getVotes());
		db.update(GameSession.class, "GameID", session[0].getGameID(), "GameStatus", session[0].getGameStatus());
		return v;
	}

	/** Find a given entity from a example
	 * @param s the session
	 * @param example the example vote object.
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Vote[] getEntity(Session s, String example) throws WPISuiteException {
		final Vote v = Vote.fromJson(example);
		final Vote[] vlist = (Vote[]) db.retrieve(Vote.class, "UID", v.getUID(), s.getProject()).toArray();
		final List<Vote> voteList = new ArrayList<Vote>();
		for(Vote vo : vlist) if(v.getGameID() == vo.getGameID())  voteList.add(vo);
		return (Vote[]) voteList.toArray();
	}

	@Override
	public Vote[] getAll(Session s) {
		// TODO Auto-generated method stub
		return (Vote[]) db.retrieveAll(new Vote(null,0,0), s.getProject()).toArray();
	}
	//TODO Overload update in order to not parse the same game twice
	/**Updates a vote
	 * @param s the session
	 * @param content the vote to be updated in string format
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Vote update(Session s, String content) throws WPISuiteException {
		final Vote v = Vote.fromJson(content);
		if(v.getVoteID() == -1) v.setVoteID(v.getUID() + v.getGameID()*100000);
		db.update(Vote.class, "VoteID", v.getVoteID(), "Vote", v.getVote());
		return v;
	}

	@Override
	public void save(Session s, Vote model) {
		db.save(model, s.getProject());

	}

	@Override
	public boolean deleteEntity(Session s, String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s){
		// TODO Auto-generated method stub

	}

	@Override
	public int Count(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content){
		// TODO Auto-generated method stub
		return null;
	}

}
