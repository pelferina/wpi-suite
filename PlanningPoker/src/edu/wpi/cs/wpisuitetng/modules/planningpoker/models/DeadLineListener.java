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
import java.io.UnsupportedEncodingException;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;


/**
 * This is the action listener for entity manager to check deadline on each games.
 * It will be called every few seconds on server side
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class DeadLineListener implements ActionListener{
	final private Data db;
	final private GameEntityManager entityManager;
	/**
	 * This constructor creates a DeadLineListener with the given database and GameEntityManager
	 * 
	 * @param db The database to create with
	 * @param ppem 
	 */
	public DeadLineListener(Data db, GameEntityManager ppem){
		this.db = db;
		entityManager = ppem;
		System.err.println("create a listener");
	}

	@Override
	/**
	 * Update gameStatus if the game reaches the deadline.
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		//System.err.println("Refresh");
		GameSession[] gameArray = {};
		
		gameArray = db.retrieveAll(new GameSession(new String(), new String(), 0 , 0, new Date(), null)).toArray(new GameSession[0]);

		Date today = new Date();
		
		for(int i=0; i<gameArray.length; i++){
			if (gameArray[i].getEndDate() != null){
				GameStatus status = gameArray[i].getGameStatus();
				if (gameArray[i].getEndDate().before(today) && (status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS))){
					System.out.println("Name "+gameArray[i].getGameName() + " reaches deadline at" + today);
					
					// change the status of gameSession
					try {
						db.update(GameSession.class, "GameID", gameArray[i].getGameID(), "GameStatus",  GameStatus.COMPLETED);
					} catch (WPISuiteException ex) {
						System.err.println("fail to set the gameStatus to completed");
						ex.printStackTrace();
					}
					// send notification email.
					try {
						String textToSend;
						textToSend = "The game '"+ gameArray[i].getGameName() + "' has reached its deadline at" + gameArray[i].getEndDate() +"\r\n" + "Sent by fff8e7";
						entityManager.sendUserEmails("End game notification",  textToSend, gameArray[i].getProject());
					} catch (Exception e1) {
						System.out.println("fail to send end notification email");
						e1.printStackTrace();
					}
				}
			}
		}
		
	}
}