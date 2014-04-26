/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.TableSelectListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;


/**
 * This is an OverviewPanel which extends JPanel and implements Refreshable
 * 
 * @author fff8e7
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class OverviewPanel extends JPanel implements Refreshable {
	GetGamesController ggc; 
	GameSession[] curSessions = {}; // store gameSessions here
	GameModel gameModel;
	JScrollPane tableView;
	JTable table;
	JScrollPane treeView; // TODO
	JSplitPane splitPane;
	GameTree gameTreeModel;
	JTree gameTree;
	TableRowSorter<JTableModel> sorter;
	int currentUser;
	
	public OverviewPanel(){
		gameModel = GameModel.getInstance();
		ggc = GetGamesController.getInstance();
		ggc.addRefreshable(this);
		final GameSession[] sessions = {};
		
		table = new JTable(new JTableModel(sessions));
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		

		//sort the table
		table.setRowSorter(new TableRowSorter(table.getModel()));
		
		//This is used to refresh the overview table
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  final JTable target = (JTable)e.getSource();
			      int selectedGame = target.getSelectedRow();
			      selectedGame = target.convertRowIndexToModel(selectedGame);
			      
				    if (e.getClickCount() == 2) {
				      final int gameID = (Integer)((JTableModel)target.getModel()).getIDFromRow(selectedGame);
				      final List<GameSession> games = gameModel.getGames();
				      GameSession clickedGame = null;
				      for (GameSession gm: games){
				    	  if (gm.getGameID() == gameID){
				    		  clickedGame = gm;
				    	  }
				      }
				      if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.DRAFT){
				    	  final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
					      //End game button
					      if(currentUser.getIdNum() == clickedGame.getOwnerID()){
					    	  ViewEventController.getInstance().editGameTab(clickedGame); // Make this edit insteadS
					      }
				      }
				      else if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.ACTIVE){
				    	  ViewEventController.getInstance().playGameTab(clickedGame);
				      }
				      else if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.INPROGRESS){
				    	  ViewEventController.getInstance().playGameTab(clickedGame);
				      }
				      else if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.COMPLETED){
				    	  ViewEventController.getInstance().viewGameTab(clickedGame);
				      }
				      else if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.ARCHIVED){
				    	  ViewEventController.getInstance().viewGameTab(clickedGame);
				      }
				    }
				    if(e.getClickCount() == 1){
				    	//System.out.println("In mouse listener " + row);
				    }
				  }
				});
		//table.setToolTipText("Double Click to Edit");
		table.getSelectionModel().addListSelectionListener(new TableSelectListener(table));
		
		
		tableView = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		setLayout(new BorderLayout(0, 0));
		
		//Initializes the game tree
		
		gameTreeModel = new GameTree(new DefaultMutableTreeNode("All Games"));
		gameTree = new JTree(gameTreeModel.getTop());
		gameTree.setCellRenderer(new CustomTreeCellRenderer());
		gameTree.addTreeSelectionListener(    new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode)gameTree.getLastSelectedPathComponent();
	            
	            if (node == null){
	            	return;
	            }

	            final Object nodeInfo = node.getUserObject();
	            updateTable((String)nodeInfo);
	    }});

		treeView = new JScrollPane(gameTree);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		splitPane.setDividerLocation(135);
		
		splitPane.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                public void setBorder(Border b) {
                }
            };
            }
        });
        splitPane.setBorder(null);
		
		add(splitPane);	
		
	}
	

	/**
	 * This is the function that updates the table with the current games that are stored in the database
	 * @param s the string to update the table with
	 */
	public void updateTable(String s){
		/** TODO this should be in the constructor, but this panel gets loaded before a user logs in*/
		currentUser = GetCurrentUser.getInstance().getCurrentUser().getIdNum();
		
		List<GameSession> sessions = new ArrayList<GameSession>();
		
		if (s.equals("All Games"))
		{
			sessions = gameModel.getDraftGameSessions(currentUser);
			sessions.addAll(gameModel.getActiveGameSessions());
			sessions.addAll(gameModel.getInProgressGameSessions());
			sessions.addAll(gameModel.getCompletedGameSessions());
			sessions.addAll(gameModel.getArchivedGameSessions());
		}
		else if (s.equals("My Games"))
		{
			sessions = gameModel.getGames(currentUser);
		}
		else if (s.equals("Drafts"))
		{
			sessions = gameModel.getDraftGameSessions(currentUser);
		}
		else if (s.equals("Active"))
		{
			sessions = gameModel.getActiveGameSessions(currentUser);
			sessions.addAll(gameModel.getInProgressGameSessions(currentUser));
		}
		else if (s.equals("Complete"))
		{
			sessions = gameModel.getCompletedGameSessions(currentUser);
			sessions.addAll(gameModel.getArchivedGameSessions(currentUser));
		}
		else if (s.equals("In Progress"))
		{
			sessions = gameModel.getActiveGameSessions();
			sessions.addAll(gameModel.getInProgressGameSessions());
		}
		else if (s.equals("Needs Vote")){
			sessions = gameModel.getGamesNeedingVote(currentUser);
		}
		else if (s.equals("Voted")){
			sessions = gameModel.getGamesVoted(currentUser);
		}
		else if (s.equals("History"))
		{
			sessions = gameModel.getArchivedGameSessions();
			sessions.addAll(gameModel.getCompletedGameSessions());
		}
		else if (s.equals("Completed")){
			sessions = gameModel.getCompletedGameSessions();
		}
		else if (s.equals("Archived")){
			sessions = gameModel.getArchivedGameSessions();	
		}
		
		final JTableModel jModel = (JTableModel)table.getModel();
		jModel.update((ArrayList<GameSession>)sessions);
		table.setModel(jModel);
		jModel.fireTableDataChanged();
		
	}

	@Override
	public void refreshRequirements() {
		
	}

	@Override
	public void refreshGames() {
        //update the table for my selection.
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)gameTree.getLastSelectedPathComponent();
        if (node != null)
        {
            final Object nodeInfo = node.getUserObject();
            updateTable((String)nodeInfo);        	
        }
        table.repaint();
        
		//Expand all folders
		for (int i = 0; i < gameTree.getRowCount(); i++){
	         gameTree.expandRow(i);
		}
	}

	/**
	 * Refreshes the view event controller whenever a new game tab is created
	 */
	public void refresh(){
		ViewEventController.getInstance();
	}	
}
