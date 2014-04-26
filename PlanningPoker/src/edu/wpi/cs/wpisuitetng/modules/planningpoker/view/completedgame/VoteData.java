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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
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
	private final JLabel finalEstimateLabel = new JLabel ("Final Estimate:");
	private final JLabel notAnIntegerError = new JLabel("Estimate must be a positive integer");
	private final JTextField finalEstimateText = new JTextField();
	private final JButton	finalSubmitButton = new JButton("Submit");
	private final JButton sendEstimatesButton = new JButton("Send Final Estimates");
	private final JTextField reqNameText = new JTextField();
	private JLabel meanTextField;
	private JLabel medianTextField;
	private final JTextArea descriptionTextArea = new JTextArea();
	private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
	private JScrollPane estimatesPane;
	private JTable estimatesTable;
	private final GameSession completedGame;
	private final CompleteView completeView;
	private List<Integer> gameReqIDs;
	private final List<Requirement> gameReqs;
	private Requirement currentReq;
	private int	reqIndex;
	private List<Integer> finalVote;
	private Timer setFocusTimer;
	
	/**
	 * The constructor for the VoteData class
	 * @param gs The completed game session to be viewed
	 * @param cv The CompleteView that called the constructor for VoteData
	 */
	public VoteData(GameSession gs, CompleteView cv){
		completedGame = gs;
		
		//This timer schedules a TimerTask that will set the default text field and buttons for the panel
		TimerTask setFocus = new TimerTask(){

			@Override
			public void run() {
				if (completedGame.getGameStatus() != GameStatus.ARCHIVED){
					finalEstimateText.requestFocusInWindow();
					getRootPane().setDefaultButton(finalSubmitButton);
				}
			}
			
		};
		setFocusTimer = new Timer();
		setFocusTimer.schedule(setFocus, 100);
		completeView = cv;
		gameReqs = cv.getGameRequirements();
		finalVote = new ArrayList<Integer>();
		notAnIntegerError.setVisible(false);
		sendEstimatesButton.setEnabled(false);
		finalSubmitButton.setEnabled(false);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		if(completedGame.getFinalVotes() != null){
			if (completedGame.getGameStatus() == GameStatus.ARCHIVED && completedGame.getFinalVotes().size() > 0){
				finalVote = completedGame.getFinalVotes();
				finalEstimateText.setText(Integer.toString(finalVote.get(0)));
				completeView.sendEstimatesToTable(finalVote);
			}
			else {
				for (int i = 0; i < gameReqs.size(); i++){
					finalVote.add(-1);
				}
			}
		}
		currentReq = gameReqs.get(0);
		reqIndex = 0;
		
		//Enables the submit button and text box for final estimate if the user is the owner of the game
		if (completedGame.getOwnerID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum() && completedGame.getGameStatus() != GameStatus.ARCHIVED){
			finalEstimateText.setEnabled(true);
		}
		else {
			finalEstimateText.setEditable(false);
			finalSubmitButton.setVisible(false);
		}
		reqNameText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		//Sets the name and description text fields to the first requirements
		reqNameText.setText(currentReq.getName());
		reqNameText.setEditable(false);
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		descriptionTextArea.setText(currentReq.getDescription());
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setEditable(false);
		gs.calculateStats();
		
		//Sets the statistic text fields to the stats of the first requirement in the game, and disables user edits
		if(completedGame.getMean().size() != 0){
			final float mean = completedGame.getMean().get(reqIndex);
			meanTextField = new JLabel(String.format("%.2f", mean));
			meanTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
			final float median = completedGame.getMedian().get(reqIndex);
			medianTextField = new JLabel(String.format("%.2f", median));
			medianTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		} else {
			meanTextField = new JLabel("");
			medianTextField = new JLabel("");
		}
		init();
		finalSubmitButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		//Action listener for the submit button that will save the final estimate for the requirement
		finalSubmitButton.addActionListener(new ActionListener(){
			
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
				sendEstimatesButton.setEnabled(allVotes);
				completeView.nextRequirement(finalEstimate);
				completeView.isNew = false;
			}
		});
		
		sendEstimatesButton.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e){
				completedGame.setFinalVotes(finalVote);
				completedGame.setGameStatus(GameStatus.ARCHIVED);
				final UpdateGameController msgr = new UpdateGameController();
				msgr.sendGame(completedGame);
				final UpdateRequirementController reqmsgr = UpdateRequirementController.getInstance();
				for (Requirement r: gameReqs){
					r.setEstimate(finalVote.get(completeView.getIndex(r.getId())));
					reqmsgr.updateRequirement(r);
				}
				completeView.isNew = true;
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
		
		estimatesTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		estimatesTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"User Name", "Estimate"}));
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
		
		final SpringLayout springLayout = new SpringLayout();
		
		// Spring layout constraints for sendEstimatesButton
		springLayout.putConstraint(SpringLayout.NORTH, sendEstimatesButton, 24, SpringLayout.SOUTH, finalSubmitButton);
		springLayout.putConstraint(SpringLayout.WEST, sendEstimatesButton, 0, SpringLayout.WEST, descriptionScrollPane);
		
		//Spring layout constraints for finalEstimatetext
		springLayout.putConstraint(SpringLayout.WEST, finalEstimateText, 6, SpringLayout.EAST, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.EAST, finalEstimateText, -50, SpringLayout.WEST, estimatesPane);
		springLayout.putConstraint(SpringLayout.NORTH, finalEstimateText, -3, SpringLayout.NORTH, finalEstimateLabel);
		
		//Spring layout constraints for medianTextField
		springLayout.putConstraint(SpringLayout.NORTH, medianTextField, 0, SpringLayout.NORTH, medianLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianTextField, 0, SpringLayout.WEST, meanTextField);
		springLayout.putConstraint(SpringLayout.EAST, medianTextField, 0, SpringLayout.EAST, meanTextField);
		
		//Spring layout constraints for medianLabel
		springLayout.putConstraint(SpringLayout.NORTH, medianLabel, 21, SpringLayout.SOUTH, meanLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for finalSubmitButton
		springLayout.putConstraint(SpringLayout.NORTH, finalSubmitButton, 22, SpringLayout.SOUTH, finalEstimateText);
		springLayout.putConstraint(SpringLayout.EAST, finalSubmitButton, 0, SpringLayout.EAST, reqDescriptionLabel);
		
		//Spring layout constraints for reqNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, reqNameLabel, 9, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqNameLabel, 10, SpringLayout.WEST, this);
		
		//Spring layout constraints for reqNameText
		springLayout.putConstraint(SpringLayout.EAST, reqNameText, -15, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameText, 0, SpringLayout.NORTH, reqNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, reqNameText, 5, SpringLayout.EAST, reqNameLabel);
		
		//Spring layout constraints for reqDescriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, reqDescriptionLabel, 19, SpringLayout.SOUTH, reqNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, reqDescriptionLabel, 0, SpringLayout.WEST, reqNameLabel);
		
		//Spring layout constraints for finalEstimatesLabel
		springLayout.putConstraint(SpringLayout.SOUTH, finalEstimateLabel, -141, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, finalEstimateLabel, 5, SpringLayout.WEST, this);
		
		//Spring layout constraints for meanTextField
		springLayout.putConstraint(SpringLayout.WEST, meanTextField, 30, SpringLayout.EAST, meanLabel);
		springLayout.putConstraint(SpringLayout.EAST, meanTextField, -50, SpringLayout.WEST, estimatesPane);
		springLayout.putConstraint(SpringLayout.NORTH, meanTextField, -8, SpringLayout.NORTH, estimatesPane);
		
		//Spring layout constraints for descriptionScrollPane
		springLayout.putConstraint(SpringLayout.WEST, descriptionScrollPane, 0, SpringLayout.WEST, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionScrollPane, -5, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionScrollPane, -20, SpringLayout.NORTH, estimatesLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionScrollPane, 5, SpringLayout.SOUTH, reqDescriptionLabel);
		
		//Spring layout constraints for estimatesLabel
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesLabel, -5, SpringLayout.NORTH, estimatesPane);
		springLayout.putConstraint(SpringLayout.WEST, estimatesLabel, 0, SpringLayout.WEST, estimatesPane);
		
		//Spring layout constraints for meanLabel
		springLayout.putConstraint(SpringLayout.NORTH, meanLabel, -8, SpringLayout.NORTH, estimatesPane);
		springLayout.putConstraint(SpringLayout.WEST, meanLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for estimatesPane
		springLayout.putConstraint(SpringLayout.WEST, estimatesPane, 312, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, estimatesPane, -61, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, estimatesPane, 192, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesPane, -10, SpringLayout.SOUTH, this);
		
		//Spring layout constraints for notAnIntegerError
		springLayout.putConstraint(SpringLayout.WEST, notAnIntegerError, 0, SpringLayout.WEST, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, notAnIntegerError, 20, SpringLayout.SOUTH, finalEstimateLabel);
		
		setLayout(springLayout);
		add(notAnIntegerError);
		add(estimatesPane);
		add(descriptionScrollPane);
		add(medianTextField);
		add(meanTextField);
		add(reqNameText);
		estimatesLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(estimatesLabel);
		reqDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(reqDescriptionLabel);
		medianLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(medianLabel);
		meanLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(meanLabel);
		reqNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(reqNameLabel);
		finalEstimateLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(finalEstimateLabel);
		finalEstimateText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(finalEstimateText);
		add(finalSubmitButton);
		sendEstimatesButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(sendEstimatesButton);
	}
	
	/**
	 * Receives a new requirement to view, and displays the name, description, mean and median in the appropriate text fields, as well 
	 * as filling the table with the user IDs and votes.
	 * @param req The requirement to view (sent from GameData class)
	 */
	public void receiveNewReq(Requirement req) {
		currentReq = req;
		reqIndex = completeView.getIndex(currentReq.getId());
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
	}
	
	private void isValidEstimate(){
		if (finalEstimateText.getText().length() > 0 && isInteger(finalEstimateText.getText())){
			if (Integer.parseInt(finalEstimateText.getText()) >= 0){
				finalSubmitButton.setEnabled(true);
				notAnIntegerError.setVisible(false);
			}
			else {
				finalSubmitButton.setEnabled(false);
				notAnIntegerError.setVisible(true);
			}
		}
		else{
			finalSubmitButton.setEnabled(false);
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
}
