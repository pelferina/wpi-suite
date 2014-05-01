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
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;

/**
 * This is the listener that listens for when voting is complete
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class VotingCompleteListener implements ActionListener {
	Data db;
	GameEntityManager manager;

	/**
	 * Constructor to create the listener for a database
	 * @param db the database to be listening to
	 * @param manager the GameEntityManager that is listened to
	 */
	public VotingCompleteListener(Data db, GameEntityManager manager) {
		this.db = db;
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GameSession[] gameArray = {};
		final int numOfUser;
		VoteModel.getInstance().addVotes(db.retrieveAll(new Vote(null,0)).toArray(new Vote[0]));

		gameArray = db.retrieveAll(
				new GameSession(new String(), new String(), new String(), 0, new Date(),
						null)).toArray(new GameSession[0]);

		numOfUser = db.retrieveAll(new User(null, null, null, 0)).size();
		//System.out.println(numOfUser);
		for (int i = 0; i < gameArray.length; i++) {
//			System.out.println("GameID being processed" + gameArray[i].getGameID());
//			System.out.println("GameVoteList being is of size" + 
//					VoteModel.getInstance().getVotes(gameArray[i].getGameID()).size());
			if(VoteModel.getInstance().getVotes(gameArray[i].getGameID()) != null
					&& VoteModel.getInstance().getVotes(gameArray[i].getGameID()).size() > 0
					&& gameArray[i].getGameStatus().equals(GameStatus.ACTIVE) ){
				try {
					db.update(GameSession.class, "GameID", gameArray[i].getGameID(), "GameStatus", GameStatus.INPROGRESS);
				} catch (WPISuiteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (VoteModel.getInstance().getVotes(gameArray[i].getGameID()) != null 
					&& VoteModel.getInstance().getVotes(gameArray[i].getGameID()).size() == numOfUser
					&& (gameArray[i].getGameStatus().equals(GameStatus.ACTIVE) || 
							gameArray[i].getGameStatus().equals(GameStatus.INPROGRESS))) {
				// change the status of gameSession
				try {
					db.update(GameSession.class, "GameID",
							gameArray[i].getGameID(), "GameStatus",
							GameStatus.COMPLETED);
				} catch (WPISuiteException ex) {
					System.err.println("fail to set the gameStatus to completed");
					ex.printStackTrace();
				}
				String textToSend;
				textToSend = gameArray[i].getGameName() + " has received votes from all users, and the game is completed";
				EmailAddressEntityManager.sendToALL("End game notification",  textToSend, gameArray[i].getProject());

			}
		}
	}

}
