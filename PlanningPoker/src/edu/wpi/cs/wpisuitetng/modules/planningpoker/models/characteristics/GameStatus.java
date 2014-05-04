/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics;

/**
 * Various status' that a Game can have 
 * @author FFF8E7
 * @version 6
 */
public enum GameStatus {
	/**
	 * Indicates that the Game has only been created
	 */
	DRAFT("Draft"),
	/**
	 * Indicates that the Game is active/open for voting
	 */
	ACTIVE("Active"),
	/**
	 * Indicates that the Game has at least 1 vote
	 */
	INPROGRESS("In Progress"),
	/**
	 * Indicates that all users have voted
	 */
	COMPLETED("Completed"),
	/**
	 * Indicates that the Game has been archived such that the deadline has passed or the owner has ended the game
	 */
	ARCHIVED("Archived");
	
	private final String name;
	
	/**
	 * Constructor for GameStatus.
	 * @param stat String
	 */
	private GameStatus(String stat)
	{
		name = stat;
	}
	
	/**
	 * Method toString.
	
	 * @return String */
	public String toString()
	{
		return name;
	}
}
