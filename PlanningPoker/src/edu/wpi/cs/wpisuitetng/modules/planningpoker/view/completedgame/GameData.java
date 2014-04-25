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

import java.awt.Font;
import java.awt.Insets;
/**
 * The GameData class
 * @author FFF8E7
 * @version 6
 */
public class GameData extends JPanel{

	private final JLabel gameNameLabel = new JLabel("Game Name:");
	private final JLabel descriptionLabel = new JLabel("Game Description:");
	private final JLabel gameReqsLabel = new JLabel("Game Requirements");
	private JTextField gameNameTextBox = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
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
	 * @param gs The completed game session that is going to be viewed
	 * @param cv The CompleteView that called the constructor for GameData
	 */
	public GameData(GameSession gs, CompleteView cv){
		completeView = cv;
		completedGame = gs;
		gameNameTextBox.setText(gs.getGameName());
		descriptionTextArea.setText(gs.getGameDescription());
		gameNameTextBox.setEditable(false);
		gameNameTextBox.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		descriptionTextArea.setEditable(false);
		gameReqIDs = gs.getGameReqs();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setMargin(new Insets(2,2,2,2));
		
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
		
		gameReqsTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Name", "Description", "Mean Estimate", "Median Estimate"}));
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
			reqTableModel.setValueAt("", i, 2);
			reqTableModel.setValueAt("", i, 3);
		}
		reqPane = new JScrollPane(gameReqsTable);
		reqPane.setViewportView(gameReqsTable);
		gameReqsTable.getColumnModel().getColumn(0).setMinWidth(100);
		gameReqsTable.getColumnModel().getColumn(0).setMaxWidth(200);
		gameReqsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		gameReqsTable.addMouseListener(new tableListener(gameReqsTable));
		gameReqsTable.setRowSelectionInterval(0, 0);
		
		SpringLayout springLayout = new SpringLayout();
		
		//Sprint layout constraints for gameNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, gameNameLabel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNameLabel, 10, SpringLayout.WEST, this);
		
		//Spring layout constraints for gameNameTextBox
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextBox, 0, SpringLayout.NORTH, gameNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextBox, 5, SpringLayout.EAST, gameNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextBox, -10, SpringLayout.EAST, this);
		
		//Spring layout constraints for descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 20, SpringLayout.SOUTH, gameNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, gameNameLabel);
		
		//Spring layout constraints for descriptionScrollPane
		springLayout.putConstraint(SpringLayout.NORTH, descriptionScrollPane, 5, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionScrollPane, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionScrollPane, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionScrollPane, -20, SpringLayout.NORTH, gameReqsLabel);
		
		//Spring layout constraints for gameReqsLabel
		springLayout.putConstraint(SpringLayout.WEST, gameReqsLabel, 0, SpringLayout.WEST, reqPane);
		springLayout.putConstraint(SpringLayout.SOUTH, gameReqsLabel, -5, SpringLayout.NORTH, reqPane);
		
		//Spring layout constraints for reqPane
		springLayout.putConstraint(SpringLayout.WEST, reqPane, 0, SpringLayout.WEST, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.EAST, reqPane, 0, SpringLayout.EAST, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, reqPane, 192, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, reqPane, -10, SpringLayout.SOUTH, this);
		
		setLayout(springLayout);
		
		add(gameNameLabel);
		add(descriptionLabel);
		add(gameReqsLabel);
		add(gameNameTextBox);
		add(descriptionScrollPane);
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
	 * @param id The requirement id
	 * @return The requirement index as integer
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

	/**
	 * Selects the next requirement to be voted on
	 * @param estimate The estimate getting passed along
	 */
	public void nextRequirement(int estimate) {
		int selected = gameReqsTable.getSelectedRow();
		gameReqsTable.setValueAt(estimate, selected, 2);
		gameReqsTable.clearSelection();
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

	public void receiveFinalVotes(List<Integer> finalVote) {
		for (int i = 0; i < gameReqsTable.getRowCount(); i++){
			gameReqsTable.setValueAt(finalVote.get(i), i, 2);
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
