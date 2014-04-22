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
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;

/**
 * This is the entity manager for the Planning Poker Module.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class GameEntityManager implements EntityManager<GameSession> {

	/** The database */
	Data db;
	Timer deadlineCheck; // Timer for checking deadline
	Timer votingCompleteCheck;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in the
	 * ManagerLayer file.
	 * 
	 * @param db
	 *            a reference to the persistent database
	 */
	public GameEntityManager(Data db) {
		this.db = db;

		// set up and start timer to check deadline every 3 seconds.
		deadlineCheck = new Timer(3000, new DeadLineListener(db, this));
		deadlineCheck.start();
		// set up and start a timer to check if all users has voted every 3
		// seconds.
		votingCompleteCheck = new Timer(3000, new VotingCompleteListener(db, this));
		votingCompleteCheck.start();
	}

	/*
	 * Saves a PostBoardMessage when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.
	 * wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public GameSession makeEntity(Session s, String content)
			throws WPISuiteException {	

		// Parse the message from JSON
		final GameSession importedGame = GameSession.fromJson(content);

		System.out.println("Adding: " + content);

		final GameSession[] games = getAll(s);

		final GameSession newGame = new GameSession(importedGame.getGameName(),
				importedGame.getGameDescription(), s.getUser().getIdNum(),
				games.length + 1, importedGame.getEndDate(),
				importedGame.getGameReqs());
		newGame.setGameStatus(importedGame.getGameStatus());
		newGame.setProject(s.getProject());
		newGame.setDeckId(importedGame.getDeckId());

		// Save the message in the database if possible, otherwise throw an
		// exception
		// We want the message to be associated with the project the user logged
		// in to
		if (!db.save(newGame, s.getProject())) {
			System.err.println("Game not saved");
			throw new WPISuiteException();
		} else {
			System.out.println("Game saved");
			if (newGame.getGameStatus().equals(GameStatus.ACTIVE)) {
				sendActiveNotification(newGame, s.getProject());
			}
		}
		// Return the newly created message (this gets passed back to the
		// client)
		return newGame;
	}

	/*
	 * Individual messages cannot be retrieved. This message always throws an
	 * exception.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng
	 * .Session, java.lang.String)
	 */
	@Override
	public GameSession[] getEntity(Session s, String id)
			throws WPISuiteException {
		// Throw an exception if an ID was specified, as this module does not
		// support
		// retrieving specific PostBoardMessages.
		try {
			final int ID = Integer.parseInt(id);
			final GameSession aSample = new GameSession(null, null, 0, 0, null, null);

			return (GameSession[]) db.retrieveAll(aSample).toArray();
		} catch (NumberFormatException e) {
			throw new WPISuiteException(e.getMessage());
		}
	}

	/*
	 * Returns all of the messages that have been stored.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng
	 * .Session)
	 */
	@Override
	public GameSession[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type
		// PostBoardMessage.
		// Passing a dummy PostBoardMessage lets the db know what type of object
		// to retrieve
		// Passing the project makes it only get messages from that project

		final GameSession[] messages = db.retrieveAll(
				new GameSession(new String(), new String(), 0, 0, new Date(),
						new ArrayList<Integer>()), s.getProject()).toArray(
				new GameSession[0]);
		// GameSession(String game, int OwnerID, int GameID, Date date, List<>
		// gameReqs)
		// Return the list of messages as an array
		return (messages);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng
	 * .Session, java.lang.String)
	 */
	@Override
	public GameSession update(Session s, String content) {

		// Parse the message from JSON
		final GameSession importedGame = GameSession.fromJson(content);
		System.out.println(importedGame);
		try {
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"GameReqs", importedGame.getGameReqs());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"EndDate", importedGame.getEndDate());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"GameName", importedGame.getGameName());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"GameStatus", importedGame.getGameStatus());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"GameDescription", importedGame.getGameDescription());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"DeckId", importedGame.getDeckId());
			db.update(GameSession.class, "GameID", importedGame.getGameID(),
					"FinalVotes", importedGame.getFinalVotes());
		} catch (WPISuiteException e) {
			System.out.println("Update Game Exception");
			e.printStackTrace();
		}
		return importedGame;
	}

	/*
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng
	 * .Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, GameSession model) throws WPISuiteException {

		// Save the given defect in the database
		db.save(model);
	}

	/**
	 * Ends a game
	 * 
	 * @param gameID the game to be ended
	 * @param project the project from which this was called
	 * @throws WPISuiteException
	 */
	public void endGame(int gameID, Project project) throws WPISuiteException {
		db.update(GameSession.class, "GameID", gameID, "Status", 3);
		try {
			sendUserEmails("Planning Poker Alert",
					"Planning Poker voting has ended for game: " + gameID,
					project);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new WPISuiteException(e.toString());
		}

	}

	private void sendActiveNotification(GameSession game, Project project) {
		final String textToSend = "Hello user\r\n\t"
				+ game.getGameName()
				+ " just started. Please go to PlanningPoker to vote.\r\nSent by fff8e7";
		try {
			sendUserEmails("New game", textToSend, project);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void sendEndNotification(GameSession game, Project project) {
		final String textToSend = "Hello user\r\n\t"
				+ game.getGameName()
				+ " just ended.\r\nSent by fff8e7";
		try {
			sendUserEmails("Game Ended", textToSend, project);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a email message to the users in given session.
	 * 
	 * @param textToSend the message to be sent
	 * @param subject the subject of the email
	 * @param project the project it is being called from
	 * @throws UnsupportedEncodingException
	 */
	public void sendUserEmails(String subject, String textToSend,
			Project project) throws UnsupportedEncodingException {
		final String username = "fff8e7.email@gmail.com";
		final String password = "fff8e7team5";

		final Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		final javax.mail.Session session = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("fff8e7.email@gmail.com"));

			final List<Model> model_emails = db.retrieveAll(
					new EmailAddressModel("", 0, false), project);
			if(model_emails.size() == 0){
				System.out.println("No email address has been set");
				return;
			}
			final EmailAddressModel[] emails = model_emails
					.toArray(new EmailAddressModel[0]);

			message.addRecipients(Message.RecipientType.TO,
					InternetAddress.parse(username));
			/** TODO find a more elegent solution can't send only bcc's */

			for (EmailAddressModel email : emails) {
				if(email.getEnable() && email.getAddress()!=null)
					message.addRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(email.getAddress()));
			}

			message.setSubject(subject);
			message.setText(textToSend);

			Transport.send(message);

			System.out.println("Send group emails. Content are'" + textToSend
					+ "'");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.
	 * wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {

		// This module does not allow PostBoardMessages to be deleted, so throw
		// an exception
		throw new WPISuiteException();
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng
	 * .Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {

		// This module does not allow PostBoardMessages to be deleted, so throw
		// an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count(){
		// Return the number of PostBoardMessages currently in the database
		return db.retrieveAll(new GameSession(null, null, 0, 0, null, null))
				.size();
	}

	@Override
	public String advancedGet(Session s, String[] args){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content){
		// TODO Auto-generated method stub
		return null;
	}

}
