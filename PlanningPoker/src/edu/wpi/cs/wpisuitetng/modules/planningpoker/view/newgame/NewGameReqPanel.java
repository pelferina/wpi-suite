/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.table.DefaultTableModel;

/**
 * This class shows the requirements that are currently in the game
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class NewGameReqPanel extends JPanel implements Refreshable {

	DefaultListModel<String> listValue = new DefaultListModel<String>();
	private List<Requirement> selected = new ArrayList<Requirement>();
	private final JTable unselectedTable;
	private final JTable selectedTable;
	private List<Requirement> reqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
	private NewGameDistributedPanel newGamePanel;
	// Declarations and initializations of GUI components
	JLabel lblRequirementsAvailable = new JLabel("Requirements Available");
	JButton btnAddReq = new JButton("Add New Requirement");
	JButton btnRemoveOne = new JButton("\u2191");
	JLabel lblRequirementsSelected = new JLabel("Requirements Selected");
	JButton btnAddOne = new JButton("\u2193");
	JButton btnRemoveAll = new JButton("\u21c8");
	JButton btnAddAll = new JButton("\u21ca");
	JScrollPane unselected_table = new JScrollPane();
	JScrollPane selected_table = new JScrollPane();

	/**
	 * Constructor for NewGameReqPanel
	 * @param ngdp The NewGamePanel it is a part of
	 */
	public NewGameReqPanel(NewGameDistributedPanel ngdp) {
		newGamePanel = ngdp;
		unselectedTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) { // makes the cells uneditable
		       //all -cells false
		       return false;
		    }
		};
		selectedTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) { // same as above
		       //all -cells false
		       return false;
		    }
		};
		init();
	}

	/**
	 * Constructor for NewGameReqPanel
	 * @param ngdp The newGameDistributedPanel it is a part of
	 * @param gameSession The GameSession it relates to
	 */
	public NewGameReqPanel(NewGameDistributedPanel ngdp, GameSession gameSession) {

		newGamePanel = ngdp;
		unselectedTable = new JTable();
		selectedTable = new JTable();

		final List<Requirement> reqList = new ArrayList<Requirement>(reqs);
		final List<Requirement> reqsToRemove = new ArrayList<Requirement>();
		final List<Integer> selectedIDs = gameSession.getGameReqs();
		
		/* Removes the selected reqs from the list of all the requirements. This is done to display only the requirements
		 * that are not in the game in the top table.
		 */
		
		for (int i = 0; i < reqList.size(); i++){
			Requirement req = reqList.get(i);
			for (int selectedReqID: selectedIDs) {
				if (req.getId() == selectedReqID) {
					int index = reqList.indexOf(req);
					Requirement temp_req = reqList.get(index); 
					reqsToRemove.add(temp_req);
					selected.add(temp_req);
				}
			}	
		}
		for (Requirement r: reqsToRemove){
			reqList.remove(r);
		}
		reqs = reqList;
		init();
	}

	//Initializes the reqpanel
	
	private void init()
	{	
		
		final SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		// Observers

		btnAddReq.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				newGamePanel.newReq();
			}

		});

		/*Adds the selected requirement in the unselected table and puts it into the selected table, as well as the list of
		 * selected requirements.
		 */
		btnAddOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int[] index = unselectedTable.getSelectedRows();
				int offset = 0;
				while(index.length > 0){
					final Requirement selectedReq = reqs.get(index[0]-offset);
					selected.add(selectedReq);
					reqs.remove(index[0]-offset);
					final String[] data = {selectedReq.getName(), selectedReq.getDescription()};
					final DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
					final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					dtm.setRowCount(reqs.size());
					for (int j = 0; j < reqs.size(); j++){
						dtm.setValueAt(reqs.get(j).getName(), j, 0);
						dtm.setValueAt(reqs.get(j).getDescription(), j, 1);
					}
					dtm_1.addRow(data);
					index = removeFirst(index);
					offset++;
				}
				selectedTable.clearSelection();
				unselectedTable.clearSelection();
			}
		});

		//Adds all of the games in the unselected table to the selected table, and adds all the requirements to the list of selected requirements
		btnAddAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(reqs.size() != 0){
					final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					final DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
					final int size = reqs.size();
					for(int i=0; i < size; i++){
						String[] data = {reqs.get(i).getName(), reqs.get(i).getDescription()};
						dtm_1.addRow(data);
						selected.add(reqs.get(i));
					}
					reqs = new ArrayList<Requirement>();
					dtm.setRowCount(0);
				}
				selectedTable.clearSelection();
				unselectedTable.clearSelection();
			}
		});

		//Removes the selected requirements from the selected table, as well as the list of selected requirements
		btnRemoveOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int[] index = selectedTable.getSelectedRows();
				int offset = 0;
					while(index.length >0){
						final Requirement selectedReq = selected.get(index[0]-offset);
						selected.remove(index[0]-offset);
						reqs.add(selectedReq);
						final String[] data = {selectedReq.getName(), selectedReq.getDescription()};
						final DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
						final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
						dtm_1.setRowCount(selected.size());
						for (int i = 0; i < selected.size(); i++){
							dtm_1.setValueAt(selected.get(i).getName(), i, 0);
							dtm_1.setValueAt(selected.get(i).getDescription(), i, 1);
						}
						dtm.addRow(data);
						index = removeFirst(index);
						offset++;
					}
				selectedTable.clearSelection();
				unselectedTable.clearSelection();
			}
		});

		//Removes all of the requirements that are currently in the game (table and list)
		btnRemoveAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (selected.size() != 0){
					final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					final DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
					final int size = selected.size();
					for(int i=0; i < size; i++){
						String[] data = {selected.get(i).getName(), selected.get(i).getDescription()};
						dtm.addRow(data);
						reqs.add(selected.get(i));
					}
					selected = new ArrayList<Requirement>();
					dtm_1.setRowCount(0);
				}
				selectedTable.clearSelection();
				unselectedTable.clearSelection();
			}
		});

		//Initializes the unselected requirements table
		unselectedTable.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null},
						{null, null},
						{null, null},
						{null, null},
						{null, null},
				},
				new String[] {
						"Name", "Description"
				}
				));


		// Layout configuration

		// Spring Layout of lblRequirementsAvailable
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblRequirementsAvailable, 0, SpringLayout.HORIZONTAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblRequirementsAvailable, 10, SpringLayout.NORTH, this);

		// Spring Layout of unselected_table
		springLayout.putConstraint(SpringLayout.WEST, unselected_table, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, unselected_table, -10, SpringLayout.NORTH, btnAddReq);
		springLayout.putConstraint(SpringLayout.EAST, unselected_table, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, unselected_table, 10, SpringLayout.SOUTH, lblRequirementsAvailable);

		// Spring Layout of Buttons
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnAddReq, 0, SpringLayout.VERTICAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAddReq, 0, SpringLayout.HORIZONTAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnRemoveOne, 0, SpringLayout.NORTH, btnAddReq);
		springLayout.putConstraint(SpringLayout.WEST, btnRemoveOne, 15, SpringLayout.EAST, btnAddReq); 
		springLayout.putConstraint(SpringLayout.NORTH, btnRemoveAll, 0, SpringLayout.NORTH, btnRemoveOne);
		springLayout.putConstraint(SpringLayout.WEST, btnRemoveAll, 0, SpringLayout.EAST, btnRemoveOne);
		springLayout.putConstraint(SpringLayout.NORTH, btnAddAll, 0, SpringLayout.NORTH, btnAddReq);
		springLayout.putConstraint(SpringLayout.EAST, btnAddAll, -15, SpringLayout.WEST, btnAddReq);
		springLayout.putConstraint(SpringLayout.NORTH, btnAddOne, 0, SpringLayout.NORTH, btnAddReq);
		springLayout.putConstraint(SpringLayout.EAST, btnAddOne, 0, SpringLayout.WEST, btnAddAll);

		// Spring Layout of lblRequirementsSelected
		springLayout.putConstraint(SpringLayout.NORTH, lblRequirementsSelected, 10, SpringLayout.SOUTH, btnRemoveOne);
		springLayout.putConstraint(SpringLayout.WEST, lblRequirementsSelected, 0, SpringLayout.WEST, lblRequirementsAvailable);

		// Spring Layout of selected_table
		springLayout.putConstraint(SpringLayout.NORTH, selected_table, 10, SpringLayout.SOUTH, lblRequirementsSelected);
		springLayout.putConstraint(SpringLayout.WEST, selected_table, 0, SpringLayout.WEST, unselected_table);
		springLayout.putConstraint(SpringLayout.SOUTH, selected_table, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, selected_table, -0, SpringLayout.EAST, unselected_table);

		// Adds elements to the panel
		add(lblRequirementsAvailable);
		add(unselected_table);
		add(btnAddReq);
		add(btnRemoveOne);
		add(btnRemoveAll);
		add(btnAddAll);
		add(btnAddOne);
		add(lblRequirementsSelected);
		add(selected_table);

		final DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
		dtm.setNumRows(reqs.size());
		dtm.setColumnCount(2);

		// Sets the column size of the unselected requirements table
		unselectedTable.getColumnModel().getColumn(0).setMinWidth(100);
		unselectedTable.getColumnModel().getColumn(0).setMaxWidth(200);
		unselectedTable.getColumnModel().getColumn(0).setPreferredWidth(150);

		//Puts the requirements from the requirement manager into the unselected requirements table
		
		for (int i = 0; i < reqs.size(); i++){
			dtm.setValueAt(reqs.get(i).getName(), i, 0);
			dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
		}
		unselected_table.setViewportView(unselectedTable);

		//Initializes the selected requirements table
		
		selectedTable.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null},
						{null, null},
						{null, null},
						{null, null},
						{null, null},
				},
				new String[] {
						"Name", "Description"
				}
				));
		final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
		dtm_1.setNumRows(selected.size());
		dtm_1.setColumnCount(2);
		selected_table.setViewportView(selectedTable);
		
		//Sets the column widths of the selected requirements table
		selectedTable.getColumnModel().getColumn(0).setMinWidth(100);
		selectedTable.getColumnModel().getColumn(0).setMaxWidth(200);
		selectedTable.getColumnModel().getColumn(0).setPreferredWidth(150);

		//Puts the selected requirements into the selected requirements table. This is only applicable when an edit game tab is opened
		
		for (int i = 0; i < selected.size(); i++){
			dtm_1.setValueAt(selected.get(i).getName(), i, 0);
			dtm_1.setValueAt(selected.get(i).getDescription(), i, 1);
		}
		
		
		unselectedTable.repaint();
	}
	
	//Getter for newGameInputDistributedPanel to get the selected requirements
	public List<Requirement> getSelected(){
		return selected;
	}

	//This gets only the requirements in the "Backlog" iteration from the requirements manager
	private void filterBacklog()
	{
		for (Requirement req : reqs) {
			if (!req.getIteration().equals("Backlog")) reqs.remove(req);
		}
	}

	@Override
	public void refreshRequirements() {
		reqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		filterBacklog();
		for (int i = 0; i < selected.size(); i++){
			reqs.remove(selected.get(i).getId());
		}
		final DefaultTableModel dtm = (DefaultTableModel) unselectedTable.getModel();
		dtm.setRowCount(reqs.size());
		for (int i = 0; i < reqs.size(); i++){
				dtm.setValueAt(reqs.get(i).getName(), i, 0);
				dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
		}
		unselectedTable.repaint();
	}

	//This gets only the requirements in the "Backlog" iteration from the requirements manager
	
	private void getReqs() {
		GetRequirementsController.getInstance().retrieveRequirements();
		reqs = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		final List<Requirement> reqsCopy = new ArrayList<Requirement>(reqs);
		for (Requirement req : reqsCopy) {
			System.out.println("Iteration: " + req.getIteration());
			if (!req.getIteration().equals("Backlog")) reqs.remove(req);
		}
	}
	
	public JTable getReqsTable(){
		return unselectedTable;
	}
	
	public JButton getAddOneButton(){
		return btnAddOne;
	}
	
	public JButton getAddAllButton(){
		return btnAddAll;
	}
	
	public JButton getRemoveOneButton(){
		return btnRemoveOne;
	}
	
	public JButton getRemoveAllButton(){
		return btnRemoveAll;
	}

	public JTable getSelectedTabel() {
		return selectedTable;
	}

	@Override
	public void refreshGames() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Receives a newly created Requirement
	 * @param r The Requirement to be received
	 */
	public void receiveCreatedReq(Requirement r){
		selected.add(r);
		final DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
		final String[] data = {r.getName(), r.getDescription()};
		dtm_1.addRow(data);
	}
	/**
	 * Function to remove the first element in the array
	 * @param Array The array that element is removed from 
	 * @return int[] The newly shortened array
	 */
	public int[] removeFirst(int[] Array){
		int[] newArray = new int[Array.length-1];
		for (int i =0; i<Array.length-1; i++){
			newArray[i]=Array[i+1];
		}
		return newArray;
	}

	@Override
	public void refreshDecks() {
		//intentionally left blank
	}
}


//The timer task for scheduling the initial refresh of the page

/**
 * This is a refresh task based on timer task, which refreshes everything based on a timer.
 * 
 * @author fff8e7
 * @version $Revision: 1.0 $
 *
 */
class RefreshTask extends TimerTask {

	Timer timer;
	NewGameReqPanel ngrp;

	private RefreshTask(Timer timer, NewGameReqPanel ngrp){
		this.timer = timer;
		this.ngrp = ngrp;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}



