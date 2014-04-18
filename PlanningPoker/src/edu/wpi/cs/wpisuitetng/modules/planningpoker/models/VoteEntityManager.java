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
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class VoteEntityManager implements EntityManager<Vote> {

	private Data db;

	public VoteEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public Vote makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		Vote v = Vote.fromJson(content);
		// update invalid information.
		if (v.getUID() == -1) v.setUID(s.getUser().getIdNum());
		// Check for valid game.
		GameSession[] session = null;
		try {
			session = db.retrieve(GameSession.class, "GameID", v.getGameID(), s.getProject()).toArray(new GameSession[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(session.length != 1) throw new WPISuiteException();
		// Delete previous votes with this voteID
		Vote[] prevVote = db.retrieve(Vote.class, "VoteID", v.getVoteID()).toArray(new Vote[0]);
		if(prevVote.length != 0){
			db.delete(prevVote[0]);
		}
		//check that vote saves properly
		if (!db.save(v, s.getProject())) {
			System.err.println("Vote not saved");
			throw new WPISuiteException();
		}else{
			System.out.println("Vote saved");
			}
		// Return the newly created vote
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
		return db.retrieveAll(new Vote(null,0,0), s.getProject()).toArray(new Vote[0]);
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
