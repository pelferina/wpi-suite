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



import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableModel;










import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
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
	final private int COLUMN_NUM = 2;
	
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
		estimatesPending.setModel(new DefaultTableModel(new Object[][][]{}, new String[]{"ID", "Requirements to Estimate"}));
		estimatesComplete.setModel(new DefaultTableModel(new Object[][][]{}, new String[]{"ID", "Requirements Estimated", "Your Estimates"}));
		estimatesPending.setFillsViewportHeight(true);
		estimatesComplete.setFillsViewportHeight(true);
		init(gameToPlay);
	}

	private void init(GameSession gameToPlay){
		final DefaultTableModel pendingModel = (DefaultTableModel) estimatesPending.getModel();
		final DefaultTableModel completedModel = (DefaultTableModel) estimatesComplete.getModel();
		pendingModel.setNumRows(gameReqs.size());
		pendingModel.setColumnCount(COLUMN_NUM);
		setColumnWidth(estimatesPending);
		
		//Fills the pending table with the name and description of the requirements that are in the game
		
		Vote userVote = null;
		boolean hasVoted = false;
		for (Vote v: gameToPlay.getVotes()){
			if (v.getUID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum()){
				userVote = v;
				hasVoted = true;
			}
		}
		
		if (hasVoted){
			pendingModel.setNumRows(0);
			completedModel.setNumRows(gameReqs.size());
			for (int i = 0; i < userVote.getVote().size(); i++){
				completedModel.setValueAt(gameReqs.get(i).getId(), i, 0);
				completedModel.setValueAt(gameReqs.get(i).getName(), i, 1);
				completedModel.setValueAt(userVote.getVote().get(i), i, 2);
			}
		}
		
		else {
			for (int i = 0; i < gameReqs.size(); i++){
				pendingModel.setValueAt(gameReqs.get(i).getId(), i, 0);
				pendingModel.setValueAt(gameReqs.get(i).getName(), i, 1);
			}
			completedModel.setNumRows(0);
		}
		completedModel.setColumnCount(COLUMN_NUM + 1);
		setColumnWidth(estimatesComplete);
		pendingPane = new JScrollPane(estimatesPending);
		completePane = new JScrollPane(estimatesComplete);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		addImpl(pendingPane, JSplitPane.TOP, 1);
		addImpl(completePane, JSplitPane.BOTTOM, 1);
		pendingPane.setViewportView(estimatesPending);
		completePane.setViewportView(estimatesComplete);
		estimatesPending.getSelectionModel().addListSelectionListener(new tableListener(estimatesPending));
		estimatesComplete.getSelectionModel().addListSelectionListener(new tableListener(estimatesComplete));
		
		final Dimension minimumSize = new Dimension(200, 200);
		rightComponent.setMinimumSize(minimumSize);
		leftComponent.setMinimumSize(minimumSize);
		
		setResizeWeight(0.5);
		
		this.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                public void setBorder(Border b) {
                }
            };
            }
        });
        this.setBorder(null);
	}
	
	//This function is used to set the preferred width of JTables
	private void setColumnWidth(JTable table){
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
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
	 * @param estimate 
	 */
	public void updateTables(Requirement r, int estimate) {
		final DefaultTableModel reqNames = (DefaultTableModel) estimatesPending.getModel();
		final DefaultTableModel complete = (DefaultTableModel) estimatesComplete.getModel();
		final int numberofReqs = estimatesPending.getRowCount();
		boolean isRevote = true;
		int i;
		for (i = 0; i < numberofReqs; i++){
			if (r.getId() == (int)reqNames.getValueAt(i, 0)){
				int reqId = (int) reqNames.getValueAt(i, 0);
				String name = (String) reqNames.getValueAt(i, 1);
				reqNames.removeRow(i);
				complete.addRow(new Object[]{reqId, name, estimate});
				isRevote = false;
				break;
			}
		}
		if (isRevote){
			for (int j=0; j < complete.getRowCount(); j++){
				if (r.getId() == (int) complete.getValueAt(j, 0)){
					complete.setValueAt(estimate, j, 2);
				}
			}
		}
		while (i >= 0) {
  			if (estimatesPending.getRowCount() > i) {
  				estimatesPending.setRowSelectionInterval(i, i);
  				break;
  			}
  			i--;
  		}
		if (i == -1){
			gv.clearBoxes();
		}
	}
	
	
	/**
	 * This listener is used to select a requirement to estimate by double clicking on the estimate in the table
	 * @author Cosmic Latte
	 * @version $Revision: 1.0 $
	 */
	public class tableListener implements ListSelectionListener{
		
		JTable table;
		
		/**
		 * constructor for the table listener
		 * @param table the table to listen to
		 */
		public tableListener(JTable table){
			this.table = table;
			if(table.equals(estimatesPending) && estimatesPending.getRowCount() > 0){
				this.table.setRowSelectionInterval(0, 0);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (table.getSelectedRow() != -1){
			    final int row = table.getSelectedRow();
			    if (table.equals(estimatesPending)){
			    	estimatesComplete.clearSelection();
			    }
			    else {
			    	estimatesPending.clearSelection();	
			    }
				final List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
				Requirement req = null;
				for (Requirement r: allReqs){
					if (r.getId() == (int)table.getValueAt(row, 0)){
						req = r;
					}
				}
				gv.sendReqToPlay(req);
			}
		}
	}
	
}
