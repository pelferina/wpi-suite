package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import javax.swing.JTree;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.JTableView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class OverviewPanel extends JPanel {
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
	public OverviewPanel(GameModel gameModel){
		
		this.gameModel = gameModel;
		ggc = new GetGamesController(gameModel);
		GameSession[] sessions = {};
		
		table = new JTable(new JTableModel(sessions));
		sorter = new TableRowSorter<JTableModel>((JTableModel)table.getModel());
		table.setRowSorter(sorter);
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				    if (e.getClickCount() == 2) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				      
				      
				      // Open an appropriate page TODO
				    }
				  }
				});
		
		
		tableView = new JScrollPane(table);
		setLayout(new BorderLayout(0, 0));
		
		gameTreeModel = new GameTree(new DefaultMutableTreeNode("Planning Poker"), gameModel);
		gameTree = new JTree(gameTreeModel.getTop());
		gameTree.addTreeSelectionListener(    new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
	            DefaultMutableTreeNode node = (DefaultMutableTreeNode)gameTree.getLastSelectedPathComponent();

	            if (node == null)
	            return;

	            Object nodeInfo = node.getUserObject();
	            updateTable((String)nodeInfo);
	    }});

		
		treeView = new JScrollPane(gameTree);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		add(splitPane);
		
		
		
	}
	
	public void updateTable(String s){
		
		List<GameSession> sessions = new ArrayList<GameSession>();
		ggc.actionPerformed(new ActionEvent(new Object(), 5, "Go"));

		if (s.equals("Drafts")){
			sessions = (ArrayList<GameSession>) gameModel.getDraftGameSessions();
		} else if (s.equals("Active Games")){
			sessions = (ArrayList<GameSession>) gameModel.getActiveGameSessions();
		} else if (s.equals("In Progress Games")){
			sessions = (ArrayList<GameSession>) gameModel.getInProgressGameSessions();
		} else if (s.equals("Completed Games")){
			sessions = (ArrayList<GameSession>) gameModel.getCompletedGameSessions();
		} else if (s.equals("Archived Games")){
			sessions = (ArrayList<GameSession>) gameModel.getArchivedGameSessions();
		} else {
			//Get the sessions
			HashSet<GameSession> allGames = new HashSet<GameSession>(gameModel.getDraftGameSessions());
			allGames.addAll(gameModel.getActiveGameSessions());
			allGames.addAll(gameModel.getInProgressGameSessions());
			allGames.addAll(gameModel.getCompletedGameSessions());
			allGames.addAll(gameModel.getArchivedGameSessions());
			sessions.addAll(allGames);
		}
		JTableModel jModel = (JTableModel)table.getModel();
		jModel.update((ArrayList<GameSession>)sessions);
		table.setModel(jModel);
		jModel.fireTableDataChanged();
	}

}
