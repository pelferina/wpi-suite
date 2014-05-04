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


/**
 * The gameTreeClass
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class GameTree extends DefaultMutableTreeNode {
	
	private final DefaultMutableTreeNode top;
	private final GameModel gameModel;

	/**
	 * This constructor creates a tree system and its highest node as well as populating variables
	 * 
	 * @param top the topmost node of the tree
	 */
	public GameTree(DefaultMutableTreeNode top) {
        this.top = top;
        gameModel = GameModel.getInstance();
        createNodes(top);
	}
	
	private void createNodes(DefaultMutableTreeNode top) {
		final DefaultMutableTreeNode myGames = new DefaultMutableTreeNode("My Games");
		final DefaultMutableTreeNode myDrafts = new DefaultMutableTreeNode("Drafts");
		final DefaultMutableTreeNode myActive = new DefaultMutableTreeNode("Active");
		final DefaultMutableTreeNode myComplete = new DefaultMutableTreeNode("Complete");
		final DefaultMutableTreeNode inProgressGames = new DefaultMutableTreeNode("In Progress");
		final DefaultMutableTreeNode needsVote = new DefaultMutableTreeNode("Needs Vote");
		final DefaultMutableTreeNode voted = new DefaultMutableTreeNode("Voted");
		final DefaultMutableTreeNode historyGames = new DefaultMutableTreeNode("History");
		final DefaultMutableTreeNode completedGames = new DefaultMutableTreeNode("Completed");
		final DefaultMutableTreeNode archivedGames = new DefaultMutableTreeNode("Archived");
		
		top.add(myGames);
		myDrafts.add(myActive);
			myGames.add(myDrafts);
			myGames.add(myActive);
			myGames.add(myComplete);
		top.add(inProgressGames);
			inProgressGames.add(needsVote);
			inProgressGames.add(voted);
		top.add(historyGames);
			historyGames.add(completedGames);
			historyGames.add(archivedGames);
	}
	
	public DefaultMutableTreeNode getTop(){
		return top;
	}
	
	/**
	 * This method updates all the nodes by removing the tops children and rebuilding
	 */
	public void update(){
		top.removeAllChildren();
		createNodes(top);
	}
}
