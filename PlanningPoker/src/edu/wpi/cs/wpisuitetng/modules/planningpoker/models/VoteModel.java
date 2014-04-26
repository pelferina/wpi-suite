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
 * @author FFF8E7
 * @version 6
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

	/**
	 * Empties the votes ArrayList
	 */
	public void emptyModel() {
		votes = new ArrayList<Vote>();
	}

	/**
	 * Adds an array of votes to the votes ArrayList
	 * @param votes the array of votes to be added, as Vote[]
	 */
	public void addVotes(Vote[] votes) {
		this.emptyModel();
		for(Vote v : votes){
			this.votes.add(v);
		}
	}

	public List<Vote> getVotes() {
		return votes;
	}

	/**
	 * This gets all the votes of a gameSession by using a gameID
	 * @param gameID the game's ID as an integer
	 * @return the ArrayList<Vote> of the gameSession
	 */
	public List<Vote> getVotes(int gameID) {
		final List<Vote> ret = new ArrayList<Vote>();
		for(Vote v : votes){ 
			if(v.getGameID() == gameID) {
				ret.add(v);
				}
			}
		
		return ret;
	}
	/**
	 * This gets the list of votes of a Project
	 * @param P the Project to check
	 * @return the List<Vote> of the Project's votes
	 */
	public List<Vote> getVotes(Project P){
		final List<Vote> ret = new ArrayList<Vote>();
		for(Vote v : votes){ 
			if(v.getProject().equals(P)) {
				ret.add(v);
			}
		}
		return ret;
	}
	/**
	 * This gets the votes of a gameSession using a gameID and Project
	 * @param gameID the game's ID as an integer
	 * @param P the Project to check
	 * @return the List<Vote> of votes from the game session
	 */
	public List<Vote> getVotes(int gameID, Project P) {
		final List<Vote> VotesByProject = this.getVotes(P);
		final List<Vote> ret = new ArrayList<Vote>();
		for(Vote v : VotesByProject){ 
			if(v.getGameID() == gameID) {
				ret.add(v);
				}
			}
		
		return ret;
	}
}
