/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.CurrentGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;

import javax.swing.SpringLayout;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class MainView extends JPanel implements TreeSelectionListener{
	
    private JTree tree;
    private JLabel infoPanel = new JLabel();

	public MainView(GameModel gameModel, DeckModel deckModel, boolean hasNewGame) {
		
		//Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Planning Poker");
        createNodes(top);
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        
        infoPanel.setText("Default");
        
        
        tree = new JTree(top);
        springLayout.putConstraint(SpringLayout.NORTH, infoPanel, 0, SpringLayout.NORTH, tree);
        springLayout.putConstraint(SpringLayout.WEST, infoPanel, 6, SpringLayout.EAST, tree);
        springLayout.putConstraint(SpringLayout.SOUTH, infoPanel, 0, SpringLayout.SOUTH, tree);
        springLayout.putConstraint(SpringLayout.EAST, infoPanel, 190, SpringLayout.EAST, tree);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        
        JScrollPane treeView = new JScrollPane(tree);
        
        add(treeView);
        add(infoPanel);
		
	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode drafts = new DefaultMutableTreeNode("Drafts");
		DefaultMutableTreeNode draftGame1 = new DefaultMutableTreeNode("Game 1");
		
		drafts.add(draftGame1);
		
		DefaultMutableTreeNode activeGames = new DefaultMutableTreeNode("Active Games");
		DefaultMutableTreeNode activeGame1 = new DefaultMutableTreeNode("Game 1");
		
		activeGames.add(activeGame1);
		
		DefaultMutableTreeNode pastGames = new DefaultMutableTreeNode("Past Games");
		DefaultMutableTreeNode pastGame1 = new DefaultMutableTreeNode("Game 1");
		
		pastGames.add(pastGame1);
		
		top.add(drafts);
		top.add(activeGames);
		top.add(pastGames);
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		//Returns the last path element of the selection.
		//This method is useful only when the selection model allows a single selection.
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		    if (node == null)
		    //Nothing is selected.     
		    return;

		    Object nodeInfo = node.getUserObject();
		    changeInfoPanel(nodeInfo);
	}
	
	private void changeInfoPanel(Object nodeInfo) {
		infoPanel.setText((String)nodeInfo);
	}
	
}
