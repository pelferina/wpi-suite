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

/**
 * @author Anthony
 *
 */
@SuppressWarnings("serial")
public class GameTree extends DefaultMutableTreeNode {
	
	private DefaultMutableTreeNode top;
	private GameModel gameModel;

	public GameTree(DefaultMutableTreeNode top, GameModel gameModel) {
        this.top = top;
        createNodes(top);
        this.gameModel = gameModel;
	}

	private void createNodes(DefaultMutableTreeNode top) {
		//build tree for draft games
		DefaultMutableTreeNode drafts = new DefaultMutableTreeNode("Drafts");
		List<GameSession> draftGameSessionList = new ArrayList<GameSession>();
		if (gameModel != null) {
			draftGameSessionList = gameModel.getDraftGameSessions();
			for (GameSession draftGameSession: draftGameSessionList){
				drafts.add(new DefaultMutableTreeNode(draftGameSession));
			}
		}
		//build tree for active games
		DefaultMutableTreeNode activeGames = new DefaultMutableTreeNode("Active Games");
		List<GameSession> activeGameSessionList = new ArrayList<GameSession>();
		if (gameModel != null){
			activeGameSessionList = gameModel.getActiveGameSessions();
			for (GameSession activeGameSession: activeGameSessionList){
				activeGames.add(new DefaultMutableTreeNode(activeGameSession));
			}
		}
		
		//build tree for past games
		DefaultMutableTreeNode pastGames = new DefaultMutableTreeNode("Past Games");
		List<GameSession> pastGameSessionList  = new ArrayList<GameSession>();
		if (gameModel != null){
			pastGameSessionList = gameModel.getPastGameSessions();
			for (GameSession pastGameSession: pastGameSessionList){
				pastGames.add(new DefaultMutableTreeNode(pastGameSession));
			}
		}
		
		
		top.add(drafts);
		top.add(activeGames);
		top.add(pastGames);
	}
	
	public DefaultMutableTreeNode getTop(){
		return top;
	}
}
