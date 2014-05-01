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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetAllUsers;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.charts.BarChart;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
/**
 * VoteData class
 * @author FFF8E7
 * @version 6
 */
public class VoteData extends JPanel{

	private final JLabel reqNameLabel = new JLabel("Requirement Name:");
	private final JLabel reqDescriptionLabel = new JLabel("Requirement Description:");
	private final JLabel meanLabel = new JLabel("Mean:");
	private final JLabel medianLabel = new JLabel("Median:");
	private final JLabel estimatesLabel = new JLabel("Estimates");
	private final JLabel statsLabel = new JLabel("Statistics");
	private final JLabel finalEstimateLabel = new JLabel ("Final Estimate:");
	private final JLabel notAnIntegerError = new JLabel("Estimate must be a positive integer");
	private final JTextField finalEstimateText = new JTextField();
	private final JButton	submitEstimateButton = new JButton("Submit Estimate");
	private final JButton archiveGameButton = new JButton("Archive Game");
	private final JTextField reqNameText = new JTextField();
	private JLabel meanTextField;
	private JLabel medianTextField;
	private final JTextArea descriptionTextArea = new JTextArea();
	private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
	private JScrollPane estimatesPane;
	private JScrollPane statsPane;
	private JTable estimatesTable;
	private JTable statsTable;
	private final GameSession completedGame;
	private final CompleteView completeView;
	private List<Integer> gameReqIDs;
	private final List<Requirement> gameReqs;
	private Requirement currentReq;
	private int	reqIndex;
	private List<Integer> finalVote;
	private Timer setFocusTimer;
	private BarChart aChart;
	
	/**
	 * The constructor for the VoteData class
	 * @param gs The completed game session to be viewed
	 * @param cv The CompleteView that called the constructor for VoteData
	 */
	public VoteData(GameSession gs, CompleteView cv){
		boolean allVotes = true;
		completedGame = gs;
		setFocusTimer = new Timer();
		completeView = cv;
		gameReqs = cv.getGameRequirements();
		finalVote = new ArrayList<Integer>();
		notAnIntegerError.setVisible(false);
		archiveGameButton.setEnabled(false);
		submitEstimateButton.setEnabled(false);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		if(completedGame.getFinalVotes() != null){
			if (completedGame.getFinalVotes().size() > 0 && completedGame.getFinalVotes().get(0) >= 0){
				finalVote = completedGame.getFinalVotes();
				finalEstimateText.setText(Integer.toString(finalVote.get(0)));
				completeView.sendEstimatesToTable(finalVote);
				for (int i: finalVote){
					if (i == -1){
						allVotes = false;
					}
				}
			}
			else {
				for (int i = 0; i < gameReqs.size(); i++){
					finalVote.add(-1);
				}
			}
		}
		if (allVotes){
			submitEstimateButton.setEnabled(true);
		}
		
		currentReq = gameReqs.get(0);
		reqIndex = 0;
		
		//Enables the submit button and text box for final estimate if the user is the owner of the game
		if (completedGame.getOwnerID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum() && completedGame.getGameStatus() != GameStatus.ARCHIVED){
			finalEstimateText.setEnabled(true);
		}
		else {
			finalEstimateText.setEditable(false);
			submitEstimateButton.setVisible(false);
		}
		reqNameText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		//Sets the name and description text fields to the first requirements
		reqNameText.setText(currentReq.getName());
		reqNameText.setEditable(false);
		descriptionTextArea.setText(currentReq.getDescription());
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setEditable(false);
		gs.calculateStats();
		
		//Sets the statistic text fields to the stats of the first requirement in the game, and disables user edits
		if(completedGame.getMean().size() != 0){
			final float mean = completedGame.getMean().get(reqIndex);
			meanTextField = new JLabel(String.format("%.1f", mean));
			meanTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
			final float median = completedGame.getMedian().get(reqIndex);
			medianTextField = new JLabel(String.format("%.1f", median));
			medianTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		} else {
			meanTextField = new JLabel("");
			medianTextField = new JLabel("");
		}
		init();
		submitEstimateButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		//Action listener for the submit button that will save the final estimate for the requirement
		submitEstimateButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				boolean allVotes = true;
				final int finalEstimate = Integer.parseInt(finalEstimateText.getText());
				finalVote.set(reqIndex, finalEstimate);
				for (int i: finalVote){
					if (i == -1){
						allVotes = false;
					}
				}
				final UpdateRequirementController reqmsgr = UpdateRequirementController.getInstance();
				for (Requirement r: gameReqs){
					if (r.getId() == currentReq.getId()){
						r.setEstimate(finalEstimate);
						reqmsgr.updateRequirement(r);
						break;
					}
				}
				archiveGameButton.setEnabled(allVotes);
				completeView.nextRequirement(finalEstimate);
				completeView.isNew = false;
			}
		});
		
		archiveGameButton.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e){
				completedGame.setGameStatus(GameStatus.ARCHIVED);
				saveGame();
				ViewEventController.getInstance().getMain().remove(completeView);
				}
		});
		
		finalEstimateText.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				isValidEstimate();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				isValidEstimate();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				isValidEstimate();
			}
		});
	}
	
	/**
	 * Initializes the GUI components of VoteData class, and fills the table with the user names and estimates for the first 
	 * requirement in the game
	 */
	private void init(){
		aChart = new BarChart(completedGame, reqIndex);
		
		descriptionTextArea.setWrapStyleWord(true);
		
		// set colors
		reqNameText.setBackground(Color.WHITE);
		reqNameText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		
		//Adds padding
		descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(
				descriptionTextArea.getBorder(), 
		        BorderFactory.createEmptyBorder(GuiStandards.TEXT_AREA_MARGINS.getValue(), 
		        		GuiStandards.TEXT_AREA_MARGINS.getValue(), 
		        		GuiStandards.TEXT_AREA_MARGINS.getValue(), 
		        		GuiStandards.TEXT_AREA_MARGINS.getValue())));
		
		reqNameText.setBorder(BorderFactory.createCompoundBorder(
				reqNameText.getBorder(), 
				BorderFactory.createEmptyBorder(0, GuiStandards.TEXT_BOX_MARGIN.getValue(), 0, 0)));
		
		//Estimates Table (By User)
		estimatesTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		estimatesTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"User Name", "Estimate"}));
		estimatesTable.setFillsViewportHeight(true);
		final DefaultTableModel estimatesModel = (DefaultTableModel) estimatesTable.getModel();
		estimatesModel.setRowCount(completedGame.getVotes().size());
		
		int i = 0;
		for (Vote v: completedGame.getVotes()){
			for (User u: GetAllUsers.getInstance().getAllUsers()){
				if (u.getIdNum() == v.getUID()){
					estimatesModel.setValueAt(u.getName(), i, 0);
				}
			}
			estimatesModel.setValueAt(v.getVote().get(reqIndex), i, 1);
			i++;
		}
		
		estimatesPane = new JScrollPane(estimatesTable);
		estimatesPane.setViewportView(estimatesTable);
		
		//Statistics Table
		statsTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		statsTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Statistic", "Value"}));
		final DefaultTableModel statsModel = (DefaultTableModel) statsTable.getModel();
		statsModel.setRowCount(2);
		statsModel.setValueAt("Mean",0,0);
		statsModel.setValueAt("Median",1,0);
		if (completedGame.getVotes().size() > 0){
			statsModel.setValueAt(completedGame.getMean().get(reqIndex), 0, 1);
			statsModel.setValueAt(completedGame.getMedian().get(reqIndex), 1, 1);
		}
		statsPane = new JScrollPane(statsTable);
		statsPane.setViewportView(statsTable);	
		
		
		final SpringLayout springLayout = new SpringLayout();
		
		springLayout.putConstraint(SpringLayout.SOUTH, notAnIntegerError, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.WEST, notAnIntegerError, 0, SpringLayout.WEST, finalEstimateLabel);


		springLayout.putConstraint(SpringLayout.NORTH, meanLabel, 36, SpringLayout.SOUTH, descriptionScrollPane);
		
		//Spring layout constraints for medianTextField
		springLayout.putConstraint(SpringLayout.NORTH, medianTextField, 0, SpringLayout.NORTH, medianLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianTextField, 0, SpringLayout.WEST, meanTextField);
		springLayout.putConstraint(SpringLayout.EAST, medianTextField, 0, SpringLayout.EAST, meanTextField);
		
		//Spring layout constraints for medianLabel
		springLayout.putConstraint(SpringLayout.NORTH, medianLabel, 21, SpringLayout.SOUTH, meanLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for reqNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, reqNameLabel, GuiStandards.TOP_MARGIN.getValue(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqNameLabel, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);
		
		//Spring layout constraints for reqNameText
		springLayout.putConstraint(SpringLayout.NORTH, reqNameText, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, reqNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, reqNameText, 0, SpringLayout.WEST, reqNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, reqNameText, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		
		//Spring layout constraints for reqDescriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, reqDescriptionLabel, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, reqNameText);
		springLayout.putConstraint(SpringLayout.WEST, reqDescriptionLabel, 0, SpringLayout.WEST, reqNameLabel);
		
		//Spring layout constraints for descriptionScrollPane
		springLayout.putConstraint(SpringLayout.EAST, descriptionScrollPane, 0, SpringLayout.EAST, reqNameText);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionScrollPane, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, estimatesLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionScrollPane, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionScrollPane, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for meanTextField
		springLayout.putConstraint(SpringLayout.WEST, meanTextField, 30, SpringLayout.EAST, meanLabel);
		springLayout.putConstraint(SpringLayout.EAST, meanTextField, -50, SpringLayout.WEST, estimatesPane);
		springLayout.putConstraint(SpringLayout.NORTH, meanTextField, -8, SpringLayout.NORTH, estimatesPane);
		springLayout.putConstraint(SpringLayout.WEST, meanLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for estimatesLabel
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesLabel, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, estimatesPane);
		springLayout.putConstraint(SpringLayout.WEST, estimatesLabel, 0, SpringLayout.WEST, estimatesPane);
		
		//Spring layout constraints for estimatesPane
		springLayout.putConstraint(SpringLayout.EAST, estimatesPane, 0, SpringLayout.EAST, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, estimatesPane, -200, SpringLayout.EAST, estimatesPane);
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesPane, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, notAnIntegerError);
		springLayout.putConstraint(SpringLayout.NORTH, estimatesPane, -250, SpringLayout.SOUTH, estimatesPane);
		
		//Spring layout constraints for statsLabel
		springLayout.putConstraint(SpringLayout.SOUTH, statsLabel, 45, SpringLayout.SOUTH, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, statsLabel, 0, SpringLayout.WEST, statsPane);
		
		//Spring layout constraints for statsPane
		springLayout.putConstraint(SpringLayout.EAST, statsPane, -30, SpringLayout.WEST, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.WEST, statsPane, 100, SpringLayout.EAST, estimatesPane);
		springLayout.putConstraint(SpringLayout.SOUTH, statsPane, -30, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, statsPane, 15, SpringLayout.SOUTH, estimatesLabel);
		
		//Spring layout constraints for submitEstimateButton		
		springLayout.putConstraint(SpringLayout.SOUTH, submitEstimateButton, 0, SpringLayout.SOUTH, archiveGameButton);
//		springLayout.putConstraint(SpringLayout.WEST, submitEstimateButton, 0, SpringLayout.WEST, reqNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, submitEstimateButton, -GuiStandards.BUTTON_OFFSET.getValue(), SpringLayout.WEST, archiveGameButton);
		
		//Spring layout constraints for BarChart
		springLayout.putConstraint(SpringLayout.WEST, aChart, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, aChart, -100, SpringLayout.WEST, estimatesPane);
		springLayout.putConstraint(SpringLayout.SOUTH, aChart, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, notAnIntegerError);
		springLayout.putConstraint(SpringLayout.NORTH, aChart, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, descriptionScrollPane);
		
		//Spring layout constraints for archiveGameButton
		springLayout.putConstraint(SpringLayout.EAST, archiveGameButton, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, archiveGameButton, -GuiStandards.BOTTOM_MARGIN.getValue(), SpringLayout.SOUTH, this);
//		springLayout.putConstraint(SpringLayout.WEST, archiveGameButton, 0, SpringLayout.WEST, reqNameLabel);
		
		//Spring layout constraints for finalEstimateText
		springLayout.putConstraint(SpringLayout.SOUTH, finalEstimateText, 0, SpringLayout.SOUTH, submitEstimateButton);
		springLayout.putConstraint(SpringLayout.NORTH, finalEstimateText, 0, SpringLayout.NORTH, submitEstimateButton);
		springLayout.putConstraint(SpringLayout.EAST, finalEstimateText, -GuiStandards.BUTTON_OFFSET.getValue(), SpringLayout.WEST, submitEstimateButton);
		springLayout.putConstraint(SpringLayout.WEST, finalEstimateText, -50, SpringLayout.EAST, finalEstimateText);
		
		//Spring layout constraints for finalEstimateLabel
		springLayout.putConstraint(SpringLayout.EAST, finalEstimateLabel, -5, SpringLayout.WEST, finalEstimateText);
		springLayout.putConstraint(SpringLayout.SOUTH, finalEstimateLabel, 0, SpringLayout.SOUTH, finalEstimateText);
		
		
		setLayout(springLayout);
		add(notAnIntegerError);
		add(estimatesPane);
		//add(statsPane);
		add(descriptionScrollPane);
		//add(medianTextField);
		//add(meanTextField);
		add(reqNameText);
		//estimatesLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(estimatesLabel);
		//add(statsLabel);
		//reqDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(reqDescriptionLabel);
		//medianLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//add(medianLabel);
		//meanLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//add(meanLabel);
		//reqNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(reqNameLabel);
		finalEstimateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(finalEstimateLabel);
		finalEstimateText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(finalEstimateText);
		add(submitEstimateButton);
		archiveGameButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(archiveGameButton);
		add(aChart);
		
		//Schedules a timer task that will set the default text box and button
		
		TimerTask setFocus = new TimerTask(){

			@Override
			public void run() {
				if (completedGame.getGameStatus() != GameStatus.ARCHIVED){
					finalEstimateText.requestFocusInWindow();
					getRootPane().setDefaultButton(submitEstimateButton);
				}
			}
			
		};
		setFocusTimer.schedule(setFocus, 200);
	}
	
	/**
	 * Receives a new requirement to view, and displays the name, description, mean and median in the appropriate text fields, as well 
	 * as filling the table with the user IDs and votes.
	 * @param req The requirement to view (sent from GameData class)
	 */
	public void receiveNewReq(Requirement req) {
		currentReq = req;
		reqIndex = completeView.getIndex(currentReq.getId());
		
		
		//Repopulate statistics table
		final DefaultTableModel statsModel = (DefaultTableModel) statsTable.getModel();
		
		if (completedGame.getMean().size()>0){
		
			statsModel.setValueAt(completedGame.getMean().get(reqIndex), 0, 1);
			statsModel.setValueAt(completedGame.getMedian().get(reqIndex), 1, 1);
		}
		
		int i = 0;
		final DefaultTableModel estimatesModel = (DefaultTableModel) estimatesTable.getModel();
		for (Vote v: completedGame.getVotes()){
			for (User u: GetAllUsers.getInstance().getAllUsers()){
				if (u.getIdNum() == v.getUID()){
					estimatesModel.setValueAt(u.getName(), i, 0);
				}
			}
			estimatesModel.setValueAt(v.getVote().get(reqIndex), i, 1);
			i++;
		}
		if (finalVote.get(reqIndex) != -1){
			finalEstimateText.setText(Integer.toString(finalVote.get(reqIndex)));
		}
		else {
			finalEstimateText.setText("");
		}
		reqNameText.setText(req.getName());
		descriptionTextArea.setText(req.getDescription());
		if (completedGame.getVotes().size() > 0){
			meanTextField.setText(Float.toString(completedGame.getMean().get(reqIndex)));
			medianTextField.setText(Float.toString(completedGame.getMedian().get(reqIndex)));	
		}
		
		aChart.updateChart(completedGame, reqIndex);
		
	}
	
	private void isValidEstimate(){
		if (finalEstimateText.getText().length() > 0 && isInteger(finalEstimateText.getText())){
			if (Integer.parseInt(finalEstimateText.getText()) >= 0){
				submitEstimateButton.setEnabled(true);
				notAnIntegerError.setVisible(false);
			}
			else {
				submitEstimateButton.setEnabled(false);
				notAnIntegerError.setVisible(true);
			}
		}
		else{
			submitEstimateButton.setEnabled(false);
			notAnIntegerError.setVisible(true);
		}
	}
	/**
	 * Finds out if the parsed string is an int
	 * @param s The string to be parsed
	 * @return True if integer, false otherwise
	 */
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}

	public void saveGame() {
		completedGame.setFinalVotes(finalVote);
		final UpdateGameController msgr = new UpdateGameController();
		msgr.sendGame(completedGame);
	}
}
