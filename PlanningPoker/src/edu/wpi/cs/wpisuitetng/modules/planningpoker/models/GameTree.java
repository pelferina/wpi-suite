/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;
import javax.swing.tree.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * @author Anthony
 *
 */
@SuppressWarnings("serial")
public class GameTree extends DefaultMutableTreeNode {
	
	private DefaultMutableTreeNode top;
	private GameModel gameModel;

	public GameTree(DefaultMutableTreeNode top) {
        this.top = top;
        this.gameModel = GameModel.getInstance();
        createNodes(top);
	}

	private void createNodes(DefaultMutableTreeNode top) {
		//build tree for draft games
		DefaultMutableTreeNode drafts = new DefaultMutableTreeNode("Drafts");
		List<GameSession> draftGameSessionList = new ArrayList<GameSession>();
		if (gameModel != null) {
			draftGameSessionList = gameModel.getDraftGameSessions();
			//int listSize = draftGameSessionList.size();
			//drafts.add(new DefaultMutableTreeNode(listSize));
			for (GameSession draftGameSession: draftGameSessionList){
				drafts.add(new DefaultMutableTreeNode(draftGameSession.getGameName()));
			}
		}
		else {
			drafts.add(new DefaultMutableTreeNode("game model null :("));
		}
		//build tree for active games
		DefaultMutableTreeNode activeGames = new DefaultMutableTreeNode("Active Games");
		List<GameSession> activeGameSessionList = new ArrayList<GameSession>();
		if (gameModel != null){
			activeGameSessionList = gameModel.getActiveGameSessions();
			for (GameSession activeGameSession: activeGameSessionList){
				activeGames.add(new DefaultMutableTreeNode(activeGameSession.getGameName()));
			}
		}
		else {
			activeGames.add(new DefaultMutableTreeNode("game model null :("));
		}
		
		DefaultMutableTreeNode inProgressGames = new DefaultMutableTreeNode("In Progress Games");
		List<GameSession> inProgressGameSessionList = new ArrayList<GameSession>();
		if (gameModel != null){
			inProgressGameSessionList = gameModel.getActiveGameSessions();
			for (GameSession inProgressGameSession: inProgressGameSessionList){
				inProgressGames.add(new DefaultMutableTreeNode(inProgressGameSession.getGameName()));
			}
		}
		else {
			inProgressGames.add(new DefaultMutableTreeNode("game model null :("));
		}
		
		//build tree for past games
		DefaultMutableTreeNode completedGames = new DefaultMutableTreeNode("Completed Games");
		List<GameSession> CompletedGameSessionList  = new ArrayList<GameSession>();
		if (gameModel != null){
			CompletedGameSessionList = gameModel.getCompletedGameSessions();
			for (GameSession completedGameSession: CompletedGameSessionList){
				completedGames.add(new DefaultMutableTreeNode(completedGameSession.getGameName()));
			}
		}
		else {
			completedGames.add(new DefaultMutableTreeNode("game model null :("));
		}
		
		DefaultMutableTreeNode archivedGames = new DefaultMutableTreeNode("Archived Games");
		List<GameSession> ArchivedGameSessionList  = new ArrayList<GameSession>();
		if (gameModel != null){
			ArchivedGameSessionList = gameModel.getArchivedGameSessions();
			for (GameSession archivedGameSession: ArchivedGameSessionList){
				archivedGames.add(new DefaultMutableTreeNode(archivedGameSession.getGameName()));
			}
		}
		else {
			archivedGames.add(new DefaultMutableTreeNode("game model null :("));
		}
		
		
		top.add(drafts);
		top.add(activeGames);
		top.add(inProgressGames);
		top.add(completedGames);
		top.add(archivedGames);
	}
	
	public DefaultMutableTreeNode getTop(){
		return top;
	}
	
	public void update(){
		top.removeAllChildren();
		createNodes(top);
	}
}
