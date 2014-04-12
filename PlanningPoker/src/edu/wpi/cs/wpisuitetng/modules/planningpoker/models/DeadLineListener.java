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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;


/**
 * This is the action listener for entity manager to check deadline on each games.
 * It will be called every few seconds on server side
 * 
 */
public class DeadLineListener implements ActionListener{
	private Data db;
	public DeadLineListener(Data db){
		this.db = db;
		System.err.println("create a listener");
	}

	@Override
	/**
	 * Update gameStatus if the game reaches the deadline.
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		System.err.println("Refresh");
		GameSession[] gameArray = {};
		
		gameArray = db.retrieveAll(new GameSession(new String(), new String(), 0 , 0, new Date(), null)).toArray(new GameSession[0]);

		Date today = new Date();
		
		for(int i=0; i<gameArray.length; i++){
			//System.err.println("Name "+gameArray[i].getGameName() + " Deadline: " + gameArray[i].getEndDate() + " " +today.toString());
			if (gameArray[i].getEndDate() != null){
				if (gameArray[i].getEndDate().before(today) && (gameArray[i].getGameStatus().compareTo(GameStatus.ARCHIVED) != 0)){
					System.err.println("Name "+gameArray[i].getGameName() + " reaches deadline at" + today);
					try {
						db.update(GameSession.class, "GameID", gameArray[i].getGameID(), "GameStatus",  GameStatus.ARCHIVED);
					} catch (WPISuiteException ex) {
						System.err.println("fail to set the gameStatus");
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
			}
		}
		
	}
}