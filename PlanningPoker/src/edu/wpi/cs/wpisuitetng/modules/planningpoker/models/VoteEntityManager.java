package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;

public class VoteEntityManager implements EntityManager<Vote> {

	private Data db;

	public VoteEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public Vote makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		Vote v = Vote.fromJson(content);
		if (v.getUID() == -1) v.setUID(s.getUser().getIdNum());
		//TODO Replace SHADY Hash function
		if(v.getVoteID() == -1) v.setVoteID(v.getUID() + v.getGameID()*100000);
		// Check for duplication
		GameSession[] session = (GameSession[]) db.retrieve(GameSession.class, "GameID", v.getGameID()).toArray();
		if(session.length != 1) throw new WPISuiteException();
		session[0].addVote(v);
		db.update(GameSession.class, "GameID", session[0].getGameID(), "Votes", session[0].getVotes());
		return v;
	}

	/** Find a given entity from a example
	 * @param s the session
	 * @param example the example vote object.
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Vote[] getEntity(Session s, String example) throws NotFoundException,
			WPISuiteException {
			Vote v = Vote.fromJson(example);
			Vote[] vlist = (Vote[]) db.retrieve(Vote.class, "UID", v.getUID(), s.getProject()).toArray();
			ArrayList<Vote> voteList = new ArrayList<Vote>();
			for(Vote vo : vlist) if(v.getGameID() == vo.getGameID())  voteList.add(vo);
			return (Vote[]) voteList.toArray();
	}

	@Override
	public Vote[] getAll(Session s) throws WPISuiteException {
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
		Vote v = Vote.fromJson(content);
		if(v.getVoteID() == -1) v.setVoteID(v.getUID() + v.getGameID()*100000);
		db.update(Vote.class, "VoteID", v.getVoteID(), "Vote", v.getVote());
		return v;
	}

	@Override
	public void save(Session s, Vote model) throws WPISuiteException {
		db.save(model, s.getProject());

	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}
