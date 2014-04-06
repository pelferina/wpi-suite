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
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * This is a model for the game model. It contains all of the games
 * to be displayed on the board. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 * 
 * @author Chris Casola
 * 
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel {
	private static GameModel instance = null;
	
	/** The list of games on the board */
	private List<GameSession> games;
	
	/**
	 * Constructs a new board with no games.
	 */
	public GameModel() {
		games = new ArrayList<GameSession>();
	}

	/**
	
	 * @return the instance of the requirement model singleton. */
	public static GameModel getInstance()
	{
		if(instance == null)
		{
			instance = new GameModel();
		}
		
		return instance;
	}
	/**
	 * Adds the given message to the board
	 * 
	 * @param newMessage the new message to add
	 */
	public void addMessage(GameSession newMessage) {
		// Add the message
		games.add(newMessage);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/**
	 * Adds the given array of games to the board
	 * 
	 * @param games the array of games to add
	 */
	public void addMessages(GameSession[] games) {
		for (int i = 0; i < games.length; i++) {
			this.games.add(games[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/**
	 * Removes all games from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each message
	 * from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<GameSession> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	/* 
	 * Returns the message at the given index. This method is called
	 * internally by the JList in BoardPanel. Note this method returns
	 * elements in reverse order, so newest games are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return games.get(games.size() - 1 - index).toString();
	}

	/*
	 * Returns the number of games in the model. Also used internally
	 * by the JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return games.size();
	}
}
