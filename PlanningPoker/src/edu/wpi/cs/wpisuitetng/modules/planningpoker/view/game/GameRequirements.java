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

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;



import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;



/**
 * The gameRequirements class that handles information on each game's requirements
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class GameRequirements extends JSplitPane{
	
	private JTable estimatesPending;
	private JTable estimatesComplete;
	private final JLabel reqstoEstimate = new JLabel("Requirements to estimate");
	private final JLabel reqsEstimated = new JLabel("Requirements estimated");
	private JScrollPane pendingPane;
	private JScrollPane completePane;
	private final List<Integer> gameReqIDs;
	private final List<Requirement> gameReqs = new ArrayList<Requirement>();
	private final GameView gv;
	final private int COLUMN_NUM = 3;
	
	/**
	 * Constructor for GameRequirements
	 * @param gameToPlay the game session
	 * @param agv the active game view
	 */
	public GameRequirements(GameSession gameToPlay, GameView agv) {
		final List<Requirement> allReqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		gameReqIDs = gameToPlay.getGameReqs();
		gv = agv;
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
		       //all -cells false
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
		estimatesPending.setModel(new DefaultTableModel(new Object[][][]{}, new String[]{"ID", "Name", "Description"}));
		estimatesComplete.setModel(new DefaultTableModel(new Object[][][]{}, new String[]{"ID", "Name", "Description"}));
		init();
	}

	private void init(){
		final DefaultTableModel pendingModel = (DefaultTableModel) estimatesPending.getModel();
		pendingModel.setNumRows(gameReqs.size());
		pendingModel.setColumnCount(COLUMN_NUM);
		setColumnWidth(estimatesPending);
		
		//Fills the pending table with the name and description of the requirements that are in the game
		
		for (int i = 0; i < gameReqs.size(); i++){
			pendingModel.setValueAt(gameReqs.get(i).getId(), i, 0);
			pendingModel.setValueAt(gameReqs.get(i).getName(), i, 1);
			pendingModel.setValueAt(gameReqs.get(i).getDescription(), i, 2);
		}
		
		final DefaultTableModel completedModel = (DefaultTableModel) estimatesComplete.getModel();
		completedModel.setNumRows(0);
		completedModel.setColumnCount(COLUMN_NUM);
		setColumnWidth(estimatesComplete);
		pendingPane = new JScrollPane(estimatesPending);
		completePane = new JScrollPane(estimatesComplete);
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
	
	/**
	 * This function sends the clicked requirement to the PlayGame panel
	 * @param r the requirement to send
	 */
	public void sendReq(Requirement r){
		gv.sendReqToPlay(r);
	}

	/**
	 * This function updates the tables when an estimate is completed and it moves the requirement from the pending table to the completed table
	 * @param r the requirement
	 */
	public void updateTables(Requirement r) {
		final DefaultTableModel reqNames = (DefaultTableModel) estimatesPending.getModel();
		final DefaultTableModel complete = (DefaultTableModel) estimatesComplete.getModel();
		final int numberofReqs = estimatesPending.getRowCount();
		for (int i=0; i < numberofReqs; i++){
			if (r.getId() == (int)reqNames.getValueAt(i, 0)){
				int reqId = (int) reqNames.getValueAt(i, 0);
				String name = (String) reqNames.getValueAt(i, 1);
				String description = (String) reqNames.getValueAt(i, 2);
				reqNames.removeRow(i);
				complete.addRow(new Object[]{reqId, name, description});
				break;
			}
		}
		if (estimatesPending.getRowCount() > 0){
			final int nextID = (int) estimatesPending.getValueAt(0, 0);
			for (Requirement req: gameReqs){
				if (req.getId() == nextID){
					gv.sendReqToPlay(req);
					break;
				}
			}
		}
		else {
			gv.clearBoxes();
		}
	}
	
	
	/**
	 * This listener is used to select a requirement to estimate by double clicking on the estimate in the table
	 * @author Cosmic Latte
	 * @version $Revision: 1.0 $
	 */
	public class tableListener extends MouseAdapter{
		
		JTable tableClicked;
		
		/**
		 * constructor for the table listener
		 * @param table the table to listen to
		 */
		public tableListener(JTable table){
			tableClicked = table;
		}
		

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1){
				final JTable target = (JTable)e.getSource();
			    final int row = target.getSelectedRow();
			    final int column = target.getSelectedColumn();
				final List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
				Requirement req = null;
				for (Requirement r: allReqs){
					if (r.getId() == (int)tableClicked.getValueAt(row, 0)){
						req = r;
					}
				}
				gv.sendReqToPlay(req);
			}
		}
	}
	
}
