/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;

public class GameData extends JPanel{

	private final JLabel gameNameLabel = new JLabel("Game Name:");
	private final JLabel descriptionLabel = new JLabel("Game Description:");
	private final JLabel gameReqsLabel = new JLabel("Game Requirements:");
	private JTextField gameNameTextBox = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private JTable gameReqsTable;
	private JScrollPane reqPane;
	private List<Integer> gameReqIDs;
	private List<Requirement> gameReqs = new ArrayList<Requirement>();
	private List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
	private GameSession completedGame;
	private CompleteView completeView;
	private HashMap<Integer, Integer> requirementIndexHash = new HashMap<Integer, Integer>();
	
	/**
	 * Constructor for the GameData class
	 * @param gs, the completed game session that is going to be viewed
	 * @param cv, the CompleteView that called the constructor for GameData
	 */
	public GameData(GameSession gs, CompleteView cv){
		completeView = cv;
		completedGame = gs;
		gameNameTextBox.setText(gs.getGameName());
		descriptionTextArea.setText(gs.getGameDescription());
		gameNameTextBox.setEnabled(false);
		descriptionTextArea.setEnabled(false);
		gameReqIDs = gs.getGameReqs();
		
		for (Requirement r: allReqs){
			if (gameReqIDs.contains(r.getId())){
				gameReqs.add(r);
			}
		}
		
		gameReqsTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		gameReqsTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Name", "Description"}));
		init();	
	}
	
	/**
	 * Places the GUI components for the panel, as well as filling the table with the requirements that are in the game name and descriptions
	 */
	private void init(){
		DefaultTableModel reqTableModel = (DefaultTableModel) gameReqsTable.getModel();
		reqTableModel.setRowCount(gameReqs.size());
		
		//Adds the game requirements names and descriptions to the table
		for (int i = 0; i < gameReqs.size(); i++){
			requirementIndexHash.put(gameReqs.get(i).getId(), i);
			reqTableModel.setValueAt(gameReqs.get(i).getName(), i, 0);
			reqTableModel.setValueAt(gameReqs.get(i).getDescription(), i, 1);
		}
		reqPane = new JScrollPane(gameReqsTable);
		reqPane.setViewportView(gameReqsTable);
		gameReqsTable.getColumnModel().getColumn(0).setMinWidth(100);
		gameReqsTable.getColumnModel().getColumn(0).setMaxWidth(200);
		gameReqsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		gameReqsTable.addMouseListener(new tableListener(gameReqsTable));
		gameReqsTable.setRowSelectionInterval(0, 0);
		
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout constraints for gameNameTextBox
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextBox, -3, SpringLayout.NORTH, gameNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextBox, 6, SpringLayout.EAST, gameNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextBox, 153, SpringLayout.EAST, gameNameLabel);
		
		//Spring layout constraints for descriptionTextArea
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionTextArea, 60, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextArea, 361, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionTextArea, 6, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextArea, 10, SpringLayout.WEST, this);
		
		//Spring layout constraints for descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 6, SpringLayout.SOUTH, gameNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, gameNameLabel);
		
		//Spring layout constraints for reqPane
		springLayout.putConstraint(SpringLayout.NORTH, reqPane, 124, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, reqPane, -29, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqPane, -1, SpringLayout.WEST, this);
		
		//Spring layout constraints for gameNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, gameNameLabel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNameLabel, 0, SpringLayout.WEST, gameReqsLabel);
		
		//Spring layout constraints for gameReqsLabel
		springLayout.putConstraint(SpringLayout.WEST, gameReqsLabel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, gameReqsLabel, -6, SpringLayout.NORTH, reqPane);
		
		setLayout(springLayout);
		
		add(gameNameLabel);
		add(descriptionLabel);
		add(gameReqsLabel);
		add(gameNameTextBox);
		add(descriptionTextArea);
		add(reqPane);
		
	}

	/**
	 * A getter for gameReqs
	 * @return
	 */
	public List<Requirement> getGameReqs() {
		// TODO Auto-generated method stub
		return gameReqs;
	}
	
	/**
	 * Returns the index of the given requirement id
	 * @param id, the requirement id
	 * @return
	 */
	public int getReqIndex(int id){
		return requirementIndexHash.get(id);
	}

	public JTextField getGameNameTextBox() {
		return gameNameTextBox;
	}

	public void setGameNameTextBox(JTextField gameNameTextBox) {
		this.gameNameTextBox = gameNameTextBox;
	}

	public JTextArea getDescriptionTextArea() {
		return descriptionTextArea;
	}

	public void setDescriptionTextArea(JTextArea descriptionTextArea) {
		this.descriptionTextArea = descriptionTextArea;
	}

	public void nextRequirement() {
		int selected = gameReqsTable.getSelectedRow();
		gameReqsTable.removeRowSelectionInterval(selected, selected);
		if (selected + 1 < gameReqs.size()){
			gameReqsTable.addRowSelectionInterval(selected + 1, selected + 1);
			Requirement req = null;
			for (Requirement r: allReqs){
				if (r.getName().equals(gameReqsTable.getValueAt(selected + 1, 0))){
					req = r;
				}
			}
			completeView.sendReqToView(req);
		}
		else {
			gameReqsTable.addRowSelectionInterval(selected, selected);
		}
	}

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
					if (r.getName().equals(tableClicked.getValueAt(row, 0))){
						req = r;
					}
				}
				completeView.sendReqToView(req);
			}
		}
	}
	
}
