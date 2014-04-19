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

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This is the model for votes
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class VoteModel implements Model {
	private static VoteModel instance = null;
	private List<Vote> votes;
	private VoteModel() {
		votes = new ArrayList<Vote>();
	}
	
	/**
	
	 * @return the instance of the requirement model singleton. */
	public static VoteModel getInstance()
	{
		if(instance == null)
		{
			instance = new VoteModel();
		}
		
		return instance;
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermission(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPermission(Permission p, User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProject(Project p) {
		// TODO Auto-generated method stub

	}

	public void emptyModel() {
		votes = new ArrayList<Vote>();
	}

	public void addVotes(Vote[] votes) {
		this.emptyModel();
		for(Vote v : votes){
			this.votes.add(v);
		}
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public List<Vote> getVotes(int gameID) {
		ArrayList<Vote> ret = new ArrayList<Vote>();
		for(Vote v : votes){ 
			if(v.getGameID() == gameID) {
				ret.add(v);
				}
			}
		
		return ret;
	}

}
