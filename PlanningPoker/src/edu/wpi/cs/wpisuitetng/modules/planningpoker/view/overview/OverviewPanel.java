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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;





import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.TableModel;
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




/**
 * This is an OverviewPanel which extends JPanel and implements Refreshable
 * @author FFF8E7
 * @version 6
 */

@SuppressWarnings("serial")
public class OverviewPanel extends JPanel implements Refreshable {

	public static OverviewPanel instance;
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
	private boolean hasPulled = false;

	public OverviewPanel(){
		instance = this;
		gameModel = GameModel.getInstance();
		ggc = GetGamesController.getInstance();
		
		ggc.addRefreshable(this);
		final GameSession[] sessions = {};

		table = new JTable(new JTableModel(sessions));
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		

		//sort the table
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));

		//This is used to refresh the overview table

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				final JTable target = (JTable)e.getSource();
				int selectedGame = target.getSelectedRow();
				if(selectedGame != -1){
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
						// if the Y location of the mouse click is off the table rows de-select from the table.
						if(e.getY() > target.getRowHeight() * target.getRowCount()){
							target.clearSelection();
						}
					}
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
		gameTree.setSelectionRow(0);
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
        splitPane.setEnabled( false );


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
		if(!hasPulled){
			GetRequirementsController.getInstance().actionPerformed(null);
			hasPulled = true;
		}
	}

	/**
	 * Refreshes the view event controller whenever a new game tab is created
	 */
	public void refresh(){
		ViewEventController.getInstance();
	}


	@Override
	public void refreshDecks() {
		//intentionally left blank
	}


	public static OverviewPanel getInstance() {
		return instance;
	}

	/**
	 * Removes a game from the table
	 * @param gameID The ID of the game to be removed
	 */
	public void removeGameFromTable(int gameID) {
		final JTableModel model = (JTableModel) table.getModel();
		model.removeGameFromList(gameID);

	}
	
}
