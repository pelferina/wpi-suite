package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.JTableView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OverviewPanel extends JPanel {
	
	GameSession[] curSessions = {}; // store gameSessions here
	
	
	JScrollPane tableView;
	JTable table;
	JPanel treeView; // TODO
	JSplitPane splitPane;
	
	public OverviewPanel(){
		
		
		//Get the sessions
		//table = new JTable(new JTableModel(SESSIONS));
		GameSession gs = new GameSession("Test", 0, 1, Calendar.getInstance(), new ArrayList<Requirement>());
		GameSession[] sessions = {gs};
		
		table = new JTable(new JTableModel(sessions));
		tableView = new JScrollPane(table);
		setLayout(new BorderLayout(0, 0));
		treeView = new JPanel();
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
		add(splitPane);
		
		
		
	}

}
