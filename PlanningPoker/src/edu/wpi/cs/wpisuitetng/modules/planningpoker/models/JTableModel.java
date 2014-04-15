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

import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;

/**
 * This is a model for the JTabel
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
@SuppressWarnings("serial")
public class JTableModel extends AbstractTableModel {

	protected Object[][] Data; 	// Create new data array with the "X" position as each sessionData
								// and the "Y" as each Table spot
	protected Integer[] gameIDs;
	protected GameSession[] games;
	protected GetUsersController guc;
	protected User[] users;
	protected int size = 0;
    protected static final String[] COLUMN_NAMES = new String[] {"Name", "Deadline", "Owner", "Progress", "Status"};
    protected static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, String.class, String.class, String.class};
    
    /**
     * This constructor creates the JTableModel from an arrayList of sessions
     * @param sessions an ArrayList<GameSession> of sessions to create the table from
     */
    public JTableModel(ArrayList<GameSession> sessions){
    	this(sessions.toArray(new GameSession[0]));
    }

    /**
     * This constructor creates the JTableModel from an array of sessions
     * @param sessions GameSession[] to create the table from
     */
	public JTableModel(GameSession[] sessions){
		users = null;
		guc = GetUsersController.getInstance();
    	setUpTable(sessions);
    }
	
	/**
	 * This method sets up the JTableModel when given an array of GameSessions
	 * @param sessions GameSession[] of sessions to create the model from
	 */
	private void setUpTable(GameSession[] sessions){
		games = sessions;
		Data = new Object[sessions.length][COLUMN_NAMES.length];
		gameIDs = new Integer[sessions.length];
    	size = sessions.length;
    	for (int i=0; i<sessions.length; i++){
    		Object[] curRow = {sessions[i].getGameName(),
								sessions[i].getEndDate()!=null ? sessions[i].getEndDate().toString() : "No Deadline", 
    							getUserFromID(sessions[i].getOwnerID()), 
    							"To Be Implemented", // Progress
    							sessions[i].getGameStatus().name()
    							};
    		Data[i] = curRow;
    		gameIDs[i] = sessions[i].getGameID();
    	}
	}
    
	/**
	 * This method returns the status of the game as a string.
	 * "Draft" means it is being edited and not visible to the public yet.
	 * "Active" means it has been released to the public but no one has voted yet, and it can be edited.
	 * "In Progress" means at least one vote has been cast, but not everyone has voted yet.
	 * "Completed" means all users have voted.
	 * "Archived" means that the session has been rendered unchangeable.
	 * 
	 * @param game The session to check
	 * @return A string "Draft", "Active", "In Progress", "Completed", or "Archived"
	 */
	private String gameStatus(GameSession game){
		if (game.getGameStatus() == GameStatus.DRAFT){
			return "Draft";
		}
		else if (game.getGameStatus() == GameStatus.ACTIVE){
			return "Active";
		}
		else if (game.getGameStatus() == GameStatus.INPROGRESS){
			return "In Progress";
		}
		else if (game.getGameStatus() == GameStatus.COMPLETED){
			return "Completed";
		}
		else {
			return "Archived";
		}
	}
	
	/**
	 * This method returns a corresponding user when given an id number
	 * @param userID the user ID number as an int
	 * @return the String of the user's name
	 */
	private String getUserFromID(int userID){
		guc.actionPerformed();
		while (guc.getUsers() == null){
			try{
				Thread.sleep(10);
				System.out.println("Waiting for users");
			}
			catch(Exception e){
				
			}
		}
		users = guc.getUsers();
		for (User u : users){
			if (u.getIdNum() == userID){
				return u.getName();
			}
		}
		return "";
	}

    @Override public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override public int getRowCount() {
        return size;
    }

    @Override public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
       	if (rowIndex>=size)
    		return "";
    	
            /*Adding components*/
        switch (columnIndex) {
            case 0: 
            case 1:
            case 2: 
            case 3:
            case 4:
            case 5:
            	return Data[rowIndex][columnIndex];
            	
            default: return "Error";
        }
    }

    /**
     * This method updates the table of sessions with a new array of sessions
     * @param sessions the GameSession[] of sessions to populate the model with
     */
	public void update(GameSession[] sessions){
		setUpTable(sessions);
	}
	/**
     * This method updates the table of sessions with a new ArrayList<GameSession> of sessions
     * @param sessions the ArrayList<GameSession> of sessions to populate the model with
     */
	public void update(ArrayList<GameSession> sessions){
		setUpTable(sessions.toArray(new GameSession[0]));
	}
	
	/**
	 * This method gets the game ID from a given row
	 * @param row the row number to check
	 * @return the id of the game in that row
	 */
	public int getIDFromRow(int row){
		return gameIDs[row];
	}
	
	/**
	 * 
	 * @param i the row number
	 * @return the game owner id of the ith row
	 */
	public int getOwnerID(int i){
		return games[i].getOwnerID();
	}
	/**
	 * 
	 * @param i the row number
	 * @return the game id of the ith row
	 */
	public int getGameID(int i){
		return games[i].getGameID();
	}
}