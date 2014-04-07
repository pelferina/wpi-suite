package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameTree;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.JTableView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OverviewPanel extends JPanel {
	
	GameSession[] curSessions = {}; // store gameSessions here
	
	JScrollPane tableView;
	JTable table;
	JScrollPane treeView; // TODO
	JSplitPane splitPane;
	GameTree gameTreeModel;
	JTree gameTree;
	
	public OverviewPanel(){
		
		
		//Get the sessions
		//table = new JTable(new JTableModel(SESSIONS));
		GameSession gs = new GameSession("Test", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		GameSession[] sessions = {gs};
		
		table = new JTable(new JTableModel(sessions));
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
		
		gameTreeModel = new GameTree(new DefaultMutableTreeNode("Planning Poker"));
		gameTree = new JTree(gameTreeModel.getTop());
		treeView = new JScrollPane(gameTree);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		add(splitPane);
		
		
		
	}
	
	public void updatetableView(){
		GameSession gs = new GameSession("Test", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		GameSession[] sessions = {gs};
		
		
		// UPDATE THIS TODO
		table.setModel(new JTableModel(sessions));
		table.repaint();
	}

}
