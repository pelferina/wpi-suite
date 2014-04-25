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
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;

/**
 * This is a model for the game model. It contains all of the games
 * to be displayed on the board. It extends AbstractListModel so that
 * it can provide the model data to the JList component in the BoardPanel.
 *
 * @author FFF8E7
 * @version 6
 * 
 */
@SuppressWarnings({"serial"})
public class GameModel extends AbstractListModel {
	private static GameModel instance = null;
	
	/** The list of games on the board */
	private final List<GameSession> games;
	
	/**
	 * @return the games
	 */
	public List<GameSession> getGames() {
		return games;
	}
	
	/**
	 * @param userID the user to get games for
	 * @return the games for a current user
	 */
	public List<GameSession> getGames(int userID) {
		return filterUsersGames(games, userID);
	}

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
	 * Adds the given Game to the board
	 * 
	 * @param newGame the new Game to add
	 */
	public void addGame(GameSession newGame) {
		// Add the Game
		games.add(newGame);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/**
	 * Adds the given array of games to the board
	 * 
	 * @param games the array of games to add
	 */
	public void addGames(GameSession[] games) {
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
	 * references to it. Hence, we manually remove each Game
	 * from the model.
	 */
	public void emptyModel() {
		final int oldSize = getSize();
		final Iterator<GameSession> iterator = games.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	/**
	 * 
	 * Return a list of all draft game sessions in this game model
	 *
	 * @return List<GameSession> List of draft games
	 */
	public List<GameSession> getDraftGameSessions(){
		final List<GameSession> draftGames = new ArrayList<GameSession>();
		for (GameSession possibleDraft: games){
			if (possibleDraft.getGameStatus() == GameStatus.DRAFT){
				draftGames.add(possibleDraft);
			}
		}
		return draftGames;
	}
	
	/**
	 * 
	 * Return a list of all active game sessions in this game model
	 *
	 * @return List<GameSession> List of active games
	 */
	public List<GameSession> getActiveGameSessions(){
		final List<GameSession> activeGames = new ArrayList<GameSession>();
		for (GameSession possibleActive: games){
			if (possibleActive.getGameStatus() == GameStatus.ACTIVE){
				activeGames.add(possibleActive);
			}
		}
		return activeGames;
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model
	 *
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getInProgressGameSessions(){
		final List<GameSession> inProgressGames = new ArrayList<GameSession>();
		for (GameSession possibleInProgress: games){
			if (possibleInProgress.getGameStatus() == GameStatus.INPROGRESS){
				inProgressGames.add(possibleInProgress);
			}
		}
		return inProgressGames;
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model
	 *
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getCompletedGameSessions(){
		final List<GameSession> completedGames = new ArrayList<GameSession>();
		for (GameSession possibleCompleted: games){
			if (possibleCompleted.getGameStatus() == GameStatus.COMPLETED){
				completedGames.add(possibleCompleted);
			}
		}
		return completedGames;
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model
	 *
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getArchivedGameSessions(){
		final List<GameSession> archivedGames = new ArrayList<GameSession>();
		for (GameSession possibleArchived: games){
			if (possibleArchived.getGameStatus() == GameStatus.ARCHIVED){
				archivedGames.add(possibleArchived);
			}
		}
		return archivedGames;
	}
	
	/**
	 * 
	 * Return a list of all draft game sessions in this game model filtered by userID
	 * @param userID the userID to filter games by
	 * @return List<GameSession> List of draft games
	 */
	public List<GameSession> getDraftGameSessions(int userID){
		return filterUsersGames(getDraftGameSessions(), userID);
	}
	
	/**
	 * 
	 * Return a list of all active game sessions in this game model filtered by userID
	 * @param userID the userID to filter games by
	 * @return List<GameSession> List of active games
	 */
	public List<GameSession> getActiveGameSessions(int userID){
		return filterUsersGames(getActiveGameSessions(), userID);
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model filtered by userID
	 * @param userID the userID to filter games by
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getInProgressGameSessions(int userID){
		return filterUsersGames(getInProgressGameSessions(), userID);
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model filtered by userID
	 * @param userID the userID to filter games by
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getCompletedGameSessions(int userID){
		return filterUsersGames(getCompletedGameSessions(), userID);
	}
	
	/**
	 * 
	 * Return a list of all past game sessions in this game model filtered by userID
	 * @param userID the userID to filter games by
	 * @return List<GameSession> List of past games
	 */
	public List<GameSession> getArchivedGameSessions(int userID){
		return filterUsersGames(getArchivedGameSessions(), userID);
	}

	private List<GameSession> filterUsersGames(List<GameSession> games, int userID)
	{
		final List<GameSession> usersGames = new ArrayList<GameSession>();
		
		for (GameSession game : games)
			if (game.getOwnerID() == userID)
				usersGames.add(game);
		
		return usersGames;
	}
	
	/**
	 * This returns the game sessions needing a vote from the user
	 * @param userID the user's ID number, as integer
	 * @return a List<GameSessions> needing votes
	 */
	public List<GameSession> getGamesNeedingVote(int userID)
	{
		final List<GameSession> votedGames = new ArrayList<GameSession>();
		final List<GameSession> unVotedGames = new ArrayList<GameSession>(this.getActiveGameSessions());
		unVotedGames.addAll(this.getInProgressGameSessions());
		
		for (GameSession game : games)
			if (game.getGameStatus() == GameStatus.ACTIVE || game.getGameStatus() == GameStatus.INPROGRESS )
				for (Vote aVote : game.getVotes())
					if (aVote.getUID() == userID)
						votedGames.add(game);
		
		unVotedGames.removeAll(votedGames);
		
		return unVotedGames;
	}
	
	/**
	 * This function determines which games the user has voted on
	 * @param userID the user's ID, as integer
	 * @return a List<GameSession> of games the user has voted on
	 */
	public List<GameSession> getGamesVoted(int userID)
	{
		final List<GameSession> votedGames = new ArrayList<GameSession>();
		
		for (GameSession game : games)
			if (game.getGameStatus() == GameStatus.INPROGRESS )
				for (Vote aVote : game.getVotes())
					if (aVote.getUID() == userID)
						votedGames.add(game);
		
		return votedGames;
	}
	
	/* 
	 * Returns the Game at the given index. This method is called
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
