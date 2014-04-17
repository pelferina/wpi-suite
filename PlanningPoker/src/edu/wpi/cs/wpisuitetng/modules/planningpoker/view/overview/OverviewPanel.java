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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons.TableSelectListener;


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
	
	public OverviewPanel(){
		
		gameModel = GameModel.getInstance();
		ggc = GetGamesController.getInstance();
		ggc.addRefreshable(this);
		final GameSession[] sessions = {};
		
		table = new JTable(new JTableModel(sessions));
		sorter = new TableRowSorter<JTableModel>((JTableModel)table.getModel());
		table.setRowSorter(sorter);
		
		//This is used to refresh the overview table
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				  final JTable target = (JTable)e.getSource();
			      final int row = target.getSelectedRow();
			      final int column = target.getSelectedColumn();
				    if (e.getClickCount() == 2) {
				      final int gameID = (Integer)((JTableModel)target.getModel()).getIDFromRow(row);
				      final List<GameSession> games = gameModel.getGames();
				      GameSession clickedGame = null;
				      for (GameSession gm: games){
				    	  if (gm.getGameID() == gameID){
				    		  clickedGame = gm;
				    	  }
				      }
				      if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.DRAFT){
				    	  ViewEventController.getInstance().editGameTab(clickedGame); // Make this edit insteadS
				      }
				      else if (clickedGame != null && clickedGame.getGameStatus() == GameStatus.ACTIVE){
				    	  ViewEventController.getInstance().playGameTab(clickedGame);
				      }
				    }
				    if(e.getClickCount() == 1){
				    	System.out.println("In mouse listener " + row);
				    }
				  }
				});
		table.setToolTipText("Double Click to Edit");
		table.getSelectionModel().addListSelectionListener(new TableSelectListener(table));
		
		
		tableView = new JScrollPane(table);
		setLayout(new BorderLayout(0, 0));
		
		//Initializes the game tree
		
		gameTreeModel = new GameTree(new DefaultMutableTreeNode("Planning Poker"));
		gameTree = new JTree(gameTreeModel.getTop());
		
		//Refreshes the table whenever the tree is selected
		
		gameTree.addTreeSelectionListener(    new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode)gameTree.getLastSelectedPathComponent();
	            
	            if (node == null){
	            	return;
	            }

	            final Object nodeInfo = node.getUserObject();
	            updateTable((String)nodeInfo);
	    }});
		
		//Refreshes the table when the focus is gained to the overview tab
		
		gameTree.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
	            gameTreeModel.update();
	            final DefaultTreeModel model = (DefaultTreeModel) gameTree.getModel();
	            model.setRoot(gameTreeModel.getTop());
	            model.reload();
				
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus Lost");
			}
			
		});


		
		treeView = new JScrollPane(gameTree);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		add(splitPane);	
	}
	

	/**
	 * This is the function that updates the table with the current games that are stored in the database
	 * @param s the string to update the table with
	 */
	public void updateTable(String s){
		
		List<GameSession> sessions = new ArrayList<GameSession>();
				
		if (s.equals("Drafts")){
			sessions = gameModel.getDraftGameSessions();
		} else if (s.equals("Active Games")){
			sessions = gameModel.getActiveGameSessions();
		} else if (s.equals("In Progress Games")){
			sessions = gameModel.getInProgressGameSessions();
		} else if (s.equals("Completed Games")){
			sessions = gameModel.getCompletedGameSessions();
		} else if (s.equals("Archived Games")){
			sessions =  gameModel.getArchivedGameSessions();
		} else if (s.equals("Planning Poker")) {
			sessions =  gameModel.getGames();
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
		gameTreeModel.update();
        final DefaultTreeModel model = (DefaultTreeModel) gameTree.getModel();
        model.setRoot(gameTreeModel.getTop());
        model.reload();
        updateTable("");
        
        System.out.println("Updating: Games are now " + table.getModel().getRowCount());
        
        gameTree.repaint();
        table.repaint();
        		
	}

	/**
	 * Refreshes the view event controller whenever a new game tab is created
	 */
	public void refresh(){
		ViewEventController.getInstance();
	}
	
	private int getUserID(String userName){
		final GetUsersController guc = GetUsersController.getInstance();
		final User users[];
		guc.actionPerformed();
		while (guc.getUsers() == null){
			try{
				Thread.sleep(100);
				System.out.println("Waiting for users");
			}
			catch(Exception e){
				System.err.println(e.getMessage());
			}
		}
		users = guc.getUsers();
		for (User u : users){
			if (u.getUsername().equals(userName)){
				return u.getIdNum();
			}
		}
		return -1;
	}
}
