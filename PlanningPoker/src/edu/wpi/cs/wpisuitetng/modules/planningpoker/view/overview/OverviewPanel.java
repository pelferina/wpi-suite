package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableRowSorter;
import javax.swing.JTree;



import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("serial")
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
	Timer deadlineCheck;
	
	public OverviewPanel(){
		
		this.gameModel = GameModel.getInstance();
		ggc = new GetGamesController(gameModel);
		GameSession[] sessions = {};
		
		table = new JTable(new JTableModel(sessions));
		sorter = new TableRowSorter<JTableModel>((JTableModel)table.getModel());
		table.setRowSorter(sorter);

		
		//This is used to refresh the overview table
		
		
		table.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
				    if (e.getClickCount() == 2) {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				      
				      int gameID = (Integer)((JTableModel)target.getModel()).getIDFromRow(row);
				      List<GameSession> games = gameModel.getGames();
				      GameSession clickedGame = null;
				      for (GameSession gm: games){
				    	  if (gm.getGameID() == gameID){
				    		  clickedGame = gm;
				    	  }
				      }
				      if (clickedGame != null){
				    	  ViewEventController.getInstance().editGameTab(clickedGame); // Make this edit insteadS
				      }
				    }
				  }
				});
		table.setToolTipText("Double Click to Edit");
		
		tableView = new JScrollPane(table);
		setLayout(new BorderLayout(0, 0));
		
		//Initializes the game tree
		
		gameTreeModel = new GameTree(new DefaultMutableTreeNode("Planning Poker"));
		gameTree = new JTree(gameTreeModel.getTop());
		
		//Refreshes the table whenever the tree is selected
		
		gameTree.addTreeSelectionListener(    new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)gameTree.getLastSelectedPathComponent();
	            
	            if (node == null)
	            return;

	            Object nodeInfo = node.getUserObject();
	            updateTable((String)nodeInfo);
	    }});
		
		//Refreshes the table when the focus is gained to the overview tab
		
		gameTree.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
	            gameTreeModel.update();
	            DefaultTreeModel model = (DefaultTreeModel) gameTree.getModel();
	            model.setRoot(gameTreeModel.getTop());
	            model.reload();
				
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Focus Lost");
			}
			
		});
		// Refreshes the gamemodel every time you mouse over the JTree. crude but effective.
		gameTree.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				ggc.actionPerformed(new ActionEvent(new Object(), 5, "Go"));
			}

		
		});

		
		treeView = new JScrollPane(gameTree);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		add(splitPane);
		
		
		
	}
	
	// This is the function that updates the table with the current games that are stored in the database
	
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
			sessions = gameModel.getArchivedGameSessions();
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

	//Refreshes the view event controller whenever a new game tab is created
	public void refresh(){
		ViewEventController.getInstance();
	}
	
}
