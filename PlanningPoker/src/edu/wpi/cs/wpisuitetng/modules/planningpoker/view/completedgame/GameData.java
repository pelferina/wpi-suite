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



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;

import java.awt.Color;
import java.awt.Font;

/**
 * The GameData class
 * @author FFF8E7
 * @version 6
 */
public class GameData extends JPanel{

	private final JLabel gameNameLabel = new JLabel("Game Name:");
	private final JLabel descriptionLabel = new JLabel("Game Description:");
	private final JLabel gameReqsLabel = new JLabel("Game Requirements:");
	private JTextField gameNameTextBox = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
	private final JTable gameReqsTable;
	private JScrollPane reqPane;
	private final List<Integer> gameReqIDs;
	private final List<Requirement> gameReqs = new ArrayList<Requirement>();
	private final List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
	private final GameSession completedGame;
	private final CompleteView completeView;
	private final HashMap<Integer, Integer> requirementIndexHash = new HashMap<Integer, Integer>();

	/**
	 * Constructor for the GameData class
	 * @param gs The completed game session that is going to be viewed
	 * @param cv The CompleteView that called the constructor for GameData
	 */
	public GameData(GameSession gs, CompleteView cv){
		completeView = cv;
		completedGame = gs;
		gameNameTextBox.setText(gs.getGameName());
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		descriptionTextArea.setText(gs.getGameDescription());
		gameNameTextBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		gameNameTextBox.setEditable(false);
		descriptionTextArea.setEditable(false);
		gameReqIDs = gs.getGameReqs();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

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

		gameReqsTable.getTableHeader().setReorderingAllowed(false);
		gameReqsTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Name", "Mean", "Median", "Std Dev", "Estimate"}));
		gameReqsTable.setFillsViewportHeight(true);
		gameReqsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		init();	
	}

	/**
	 * Places the GUI components for the panel, as well as filling the table with the requirements that are in the game name and descriptions
	 */
	private void init(){
		final DefaultTableModel reqTableModel = (DefaultTableModel) gameReqsTable.getModel();
		reqTableModel.setRowCount(gameReqs.size());
		completedGame.calculateStats();

		descriptionTextArea.setWrapStyleWord(true);
		
		// set colors
		gameNameTextBox.setBackground(Color.WHITE);
		gameNameTextBox.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		
		//Adds padding
		descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
				descriptionTextArea.getBorder(), 
				BorderFactory.createEmptyBorder(GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue())));
		

		gameNameTextBox.setBorder(BorderFactory.createCompoundBorder(
				gameNameTextBox.getBorder(), 
				BorderFactory.createEmptyBorder(0, GuiStandards.TEXT_BOX_MARGIN.getValue(), 0, 0)));

		//Adds the game requirements names and descriptions to the table
		for (int i = 0; i < gameReqs.size(); i++){
			float mean;
			float median;
			double stddev;
			
			if(completedGame.getMean().size() != 0){
				mean = completedGame.getMean().get(i);
				median = completedGame.getMedian().get(i);
				stddev = completedGame.getStandardDeviation().get(i);
			} else {
				mean = -1;
				median = -1;
				stddev = -1;
			}

			requirementIndexHash.put(gameReqs.get(i).getId(), i);
			reqTableModel.setValueAt(gameReqs.get(i).getName(), i, 0);

			if (mean != -1)
			{
				reqTableModel.setValueAt(String.format("%.2f", mean), i, 1);
			}
			else
			{
				reqTableModel.setValueAt("", i, 1);
			}
			if (median != -1)
			{
				reqTableModel.setValueAt(String.format("%.2f", median), i, 2);
			}
			else
			{
				reqTableModel.setValueAt("", i, 2);
			}
			if (stddev != -1){
				reqTableModel.setValueAt(String.format("%.2f", stddev), i, 3);
			}
			else{
				reqTableModel.setValueAt("", i, 3);
			}
			reqTableModel.setValueAt("", i, 4);
		}
		reqPane = new JScrollPane(gameReqsTable);
		reqPane.setViewportView(gameReqsTable);
		gameReqsTable.getColumnModel().getColumn(0).setMinWidth(100);
		gameReqsTable.getColumnModel().getColumn(0).setMaxWidth(200);
		gameReqsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		if (gameReqsTable.getRowCount() > 0){
			gameReqsTable.setRowSelectionInterval(0, 0);
			gameReqsTable.getSelectionModel().addListSelectionListener(new tableListener(gameReqsTable));
		}

		final SpringLayout springLayout = new SpringLayout();

		//Spring layout constraints for gameNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, gameNameLabel, GuiStandards.TOP_MARGIN.getValue(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameNameLabel, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.WEST, this);

		//Spring layout constraints for gameNameTextBox
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextBox, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, gameNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextBox, 0, SpringLayout.WEST, gameNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextBox, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);

		//Spring layout constraints for descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, gameNameTextBox);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, gameNameLabel);

		//Spring layout constraints for descriptionScrollPane
		springLayout.putConstraint(SpringLayout.NORTH, descriptionScrollPane, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionScrollPane, 100, SpringLayout.NORTH, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, descriptionScrollPane, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionScrollPane, 0, SpringLayout.EAST, gameNameTextBox);

		//Spring layout constraints for gameReqsLabel
		springLayout.putConstraint(SpringLayout.NORTH, gameReqsLabel, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, gameReqsLabel, 0, SpringLayout.WEST, descriptionScrollPane);
		
		
		//Spring layout constraints for reqPane
		springLayout.putConstraint(SpringLayout.WEST, reqPane, 0, SpringLayout.WEST, gameNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, reqPane, 0, SpringLayout.EAST, gameNameTextBox);
		springLayout.putConstraint(SpringLayout.SOUTH, reqPane, -GuiStandards.BOTTOM_MARGIN.getValue(), SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqPane, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, gameReqsLabel);


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
		final int selected = gameReqsTable.getSelectedRow();
		gameReqsTable.setValueAt(estimate, selected, 4);
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
	/**
	 * Grabs the final votes from the gameReqsTable
	 * @param finalVote The List<Integer> of final votes
	 */
	public void receiveFinalVotes(List<Integer> finalVote) {
		for (int i = 0; i < gameReqsTable.getRowCount(); i++){
			if (finalVote.get(i) != -1){
				gameReqsTable.setValueAt(finalVote.get(i), i, 4);
			}
		}
	}
	/**
	 * A tableListener class
	 * @author Cosmic Latte
	 * @version 6
	 */
	public class tableListener implements ListSelectionListener{

		JTable table;

		/**
		 * constructor for the table listener
		 * @param table the table to listen to
		 */
		public tableListener(JTable table){
			this.table = table;
		}


		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (table.getSelectedRow() != -1){
				final int row = table.getSelectedRow();
				final List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
				Requirement req = null;
				for (Requirement r: allReqs){
					if (r.getName().equals(table.getValueAt(row, 0))){
						req = r;
					}
				}
				completeView.sendReqToView(req);
			}
		}
	}

}
