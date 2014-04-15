/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;

public class GameRequirements extends JSplitPane{
	
	private JTable estimatesPending;
	private JTable estimatesComplete;
	private final JLabel reqstoEstimate = new JLabel("Requirements to estimate");
	private final JLabel reqsEstimated = new JLabel("Requirements estimated");
	private JScrollPane pendingPane;
	private JScrollPane completePane;
	private List<Integer> gameReqIDs;
	private List<Requirement> gameReqs = new ArrayList<Requirement>();
	private GameView gv;
	
	public GameRequirements(GameSession gameToPlay, GameView agv) {
		List<Requirement> allReqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		this.gameReqIDs = gameToPlay.getGameReqs();
		this.gv = agv;
		setTopComponent(new JScrollPane(estimatesPending));
		setBottomComponent(new JScrollPane(estimatesComplete));
		//Gets all the requirements and adds the requirements that are in the game to gameReqs
		for (Requirement r: allReqs){
			if (gameReqIDs.contains(r.getId())){
				gameReqs.add(r);
			}
		}
		
		//Initializes the two JTables
		estimatesPending = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		estimatesComplete = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		estimatesPending.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Name", "Description"}));
		estimatesComplete.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Name", "Description"}));
		init();
	}

	private void init(){
		DefaultTableModel pendingModel = (DefaultTableModel) estimatesPending.getModel();
		pendingModel.setNumRows(gameReqs.size());
		pendingModel.setColumnCount(2);
		setColumnWidth(estimatesPending);
		
		//Fills the pending table with the name and description of the requirements that are in the game
		
		for (int i = 0; i < gameReqs.size(); i++){
			pendingModel.setValueAt(gameReqs.get(i).getName(), i, 0);
			pendingModel.setValueAt(gameReqs.get(i).getDescription(), i, 1);
		}
		
		DefaultTableModel completedModel = (DefaultTableModel) estimatesComplete.getModel();
		completedModel.setNumRows(0);
		completedModel.setColumnCount(2);
		setColumnWidth(estimatesComplete);
		this.pendingPane = new JScrollPane(estimatesPending);
		this.completePane = new JScrollPane(estimatesComplete);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		addImpl(pendingPane, JSplitPane.TOP, 1);
		addImpl(completePane, JSplitPane.BOTTOM, 1);
		pendingPane.setViewportView(estimatesPending);
		completePane.setViewportView(estimatesComplete);
		estimatesPending.addMouseListener(new tableListener(estimatesPending));
		estimatesComplete.addMouseListener(new tableListener(estimatesComplete));
		
		setDividerLocation(250);
	}
	
	//This function is used to set the preferred width of JTables
	private void setColumnWidth(JTable table){
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
	}
	
	//This function will send the clicked requirement to the PlayGame panel
	public void sendReq(Requirement r){
		gv.sendReqToPlay(r);
	}

	//This function is called when an estimate is completed, and it moves the requirement from the pending table to the completed table
	
	public void updateTables(Requirement r) {
		DefaultTableModel reqNames = (DefaultTableModel) estimatesPending.getModel();
		DefaultTableModel complete = (DefaultTableModel) estimatesComplete.getModel();
		int numberofReqs = estimatesPending.getRowCount();
		for (int i=0; i < numberofReqs; i++){
			if (r.getName().equals(reqNames.getValueAt(i, 0))){
				String name = (String) reqNames.getValueAt(i, 0);
				String description = (String) reqNames.getValueAt(i, 1);
				reqNames.removeRow(i);
				complete.addRow(new Object[]{name, description});
			}
		}
	}
	
	
	//This listener is used to select a requirement to estimate by double clicking on the estimate in the table
	public class tableListener extends MouseAdapter{
		
		JTable tableClicked;
		
		public tableListener(JTable table){
			this.tableClicked = table;
		}
		

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1){
				JTable target = (JTable)e.getSource();
			    int row = target.getSelectedRow();
			    int column = target.getSelectedColumn();
				List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
				Requirement req = null;
				for (Requirement r: allReqs){
					if (r.getName().equals(tableClicked.getValueAt(row, 0))){
						req = r;
					}
				}
				gv.sendReqToPlay(req);
			}
		}
	}
}
