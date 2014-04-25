/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A panel for playing a game session
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class PlayGame extends JPanel{

	private final List<Integer> gameReqs;
	private final JLabel gameName = new JLabel("Game Name:");
	private final JLabel gameDesc = new JLabel("Game Description:");
	private final JLabel reqName = new JLabel("Requirement Name:");
	private final JLabel reqDesc = new JLabel("Requirement Description:");
	private final JLabel estimateLabel = new JLabel("Input Estimate:");
	private final JLabel notAnIntegerError = new JLabel("Estimate must be a positive integer");
	private final JTextField estimateTextField = new JTextField();
	private final JTextField gameNameTextField = new JTextField();
	private final JTextField reqNameTextField = new JTextField();
	private final JTextArea gameDescTextArea = new JTextArea();
	private final JTextArea reqDescTextArea = new JTextArea();
	private final JScrollPane nd = new JScrollPane(gameDescTextArea);
	private final JScrollPane rd = new JScrollPane(reqDescTextArea);
	private final JButton submit = new JButton("Submit");
	private final JButton voteButton = new JButton("Vote");
	private Vote userEstimates;
	private Requirement currentReq;
	private GameView gv;
	private GameSession currentGame;
	private boolean hasVoted = false;
	
	/**
	 * Constructor for a PlayGame panel
	 * @param gameToPlay the game session that is being played
	 * @param agv the active game view
	 */
	public PlayGame(GameSession gameToPlay, GameView agv){
		currentGame = gameToPlay;
		gameReqs = currentGame.getGameReqs();
		notAnIntegerError.setVisible(false);
		final ArrayList<Integer> estimates = new ArrayList<Integer>();

		for (int i = 0; i < gameReqs.size(); i++){
			estimates.add(-1);
		}
		for (Vote v: gameToPlay.getVotes()){
			if (v.getUID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum()){
				userEstimates = v;
				hasVoted = true;
			}
		}
		if (hasVoted){
			estimateTextField.setText(Integer.toString(userEstimates.getVote().get(0)));
		}
		else {
			userEstimates = new Vote(estimates, currentGame.getGameID());
		}
		reqDescTextArea.setWrapStyleWord(true);
		voteButton.setEnabled(false);
		submit.setEnabled(false);
		gv = agv;
		final List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
		
		//Finds the requirement that is first in the to estimate table. The play game screen will default to displaying the first requirement
		//in the estimates pending table
		for (Requirement r: allReqs){
			if (r.getId() == gameReqs.get(0)){
				currentReq = r;
				break;
			}
		}
		
		//Sets the description and name text fields to the first requirement in the to estimate table
		gameNameTextField.setText(currentGame.getGameName());
		if (currentReq != null){
			reqNameTextField.setText(currentReq.getName());
			reqDescTextArea.setText(currentReq.getDescription());
		}
		gameDescTextArea.setText(currentGame.getGameDescription());
		gameNameTextField.setEditable(false);
		reqNameTextField.setEditable(false);
		gameDescTextArea.setEditable(false);
		reqDescTextArea.setEditable(false);
		gameDescTextArea.setLineWrap(true);
		gameDescTextArea.setWrapStyleWord(true);
		reqDescTextArea.setLineWrap(true);
		reqDescTextArea.setWrapStyleWord(true);
		
		//This document listener will enable the submit button when something is inputted into the estimate text field
		estimateTextField.getDocument().addDocumentListener(new DocumentListener(){
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
		
		//Observer for the vote button. It will save the vote client side, the submit button will handle sending it to the database.
		
		voteButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				final int estimate = Integer.parseInt(estimateTextField.getText());
				if (estimate < 0){
					//TODO error message
					return;
				}
				else{
					for(int i = 0; i < gameReqs.size(); i++){
						if (gameReqs.get(i) == currentReq.getId()){
							ArrayList<Integer> votes = (ArrayList<Integer>) userEstimates.getVote();
							votes.set(i, estimate);
							userEstimates.setVote(votes);
							break;
						}
					}
					checkCanSubmit();
					System.out.println(userEstimates.getVote());
					sendEstimatetoGameView(currentReq, estimate);
				}
				gv.isNew = false;
			}
			
		});
		
		//adds the action listener for controlling the submit button
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				AddVoteController msgr = new AddVoteController(VoteModel.getInstance());
				msgr.sendVote(userEstimates);
				gv.isNew = true;
				ViewEventController.getInstance().getMain().remove(gv);
			}
		});
		
		final SpringLayout springLayout = new SpringLayout();
		

		//Spring layout placement for gameName label
		springLayout.putConstraint(SpringLayout.NORTH, gameName, 15, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, 30, SpringLayout.WEST, this);
		
		//Spring layout placement for gameDesc label
		springLayout.putConstraint(SpringLayout.NORTH, gameDesc, 15, SpringLayout.SOUTH, gameName);
		springLayout.putConstraint(SpringLayout.WEST, gameDesc, 0, SpringLayout.WEST, gameName);
		
		//Spring layout placement for vote button
		springLayout.putConstraint(SpringLayout.NORTH, voteButton, 0, SpringLayout.NORTH, estimateTextField);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, 30, SpringLayout.EAST, estimateTextField);
		
		//Spring layout placement for submit button
		springLayout.putConstraint(SpringLayout.SOUTH, submit, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, submit, -10, SpringLayout.EAST, this);
		
		//Spring layout placement for estimateTextField
		springLayout.putConstraint(SpringLayout.NORTH, estimateTextField, 0, SpringLayout.NORTH, estimateLabel);
		springLayout.putConstraint(SpringLayout.WEST, estimateTextField, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, estimateTextField, 40, SpringLayout.WEST, estimateTextField);
		
		//Spring layout for placement of gameNameTextField
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextField, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextField, 0, SpringLayout.NORTH, gameName);
		
		//Spring layout for placement of reqNameTextField
		springLayout.putConstraint(SpringLayout.WEST, reqNameTextField, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, reqNameTextField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameTextField, 0, SpringLayout.NORTH, reqName);
		
		//Spring layout for estimateLabel
		springLayout.putConstraint(SpringLayout.NORTH, estimateLabel, 30, SpringLayout.SOUTH, rd);
		springLayout.putConstraint(SpringLayout.WEST, estimateLabel, 0, SpringLayout.WEST, reqDesc);
		
		//Spring layout for nd
		springLayout.putConstraint(SpringLayout.NORTH, nd, 0, SpringLayout.NORTH, gameDesc);
		springLayout.putConstraint(SpringLayout.WEST, nd, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, nd, -30, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nd, 125, SpringLayout.NORTH, nd);
		
		//Spring layout for rd
		springLayout.putConstraint(SpringLayout.NORTH, rd, 0, SpringLayout.NORTH, reqDesc);
		springLayout.putConstraint(SpringLayout.WEST, rd, 10, SpringLayout.EAST, reqDesc);
		springLayout.putConstraint(SpringLayout.EAST, rd, -30, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, rd, 125, SpringLayout.NORTH, rd);
		
		//Spring layout for reqDesc label
		springLayout.putConstraint(SpringLayout.NORTH, reqDesc, 15, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.WEST, reqDesc, 0, SpringLayout.WEST, reqName);
		
		//Spring layout for reqName label
		springLayout.putConstraint(SpringLayout.NORTH, reqName, 140, SpringLayout.SOUTH, gameDesc);
		springLayout.putConstraint(SpringLayout.WEST, reqName, 0, SpringLayout.WEST, gameDesc);
		
		//Spring layout for notAnIntegerError label
		springLayout.putConstraint(SpringLayout.NORTH, notAnIntegerError, 0, SpringLayout.SOUTH, voteButton);
		springLayout.putConstraint(SpringLayout.WEST, notAnIntegerError, -20, SpringLayout.EAST, voteButton);
		
		setLayout(springLayout);
	
		add(voteButton);
		add(submit);
		add(gameName);
		add(gameDesc);
		add(reqName);
		add(reqDesc);
		add(estimateLabel);
		add(estimateTextField);
		add(gameNameTextField);
		add(reqNameTextField);
		add(notAnIntegerError);
		add(nd);
		add(rd);
	}
	
	/**
	 * This function is used when a requirement is double clicked in one of the two requirement tables 
	 * and it sets the name and description fields to the selected requirement
	 * 
	 * @param reqToEstimate the requirement that is being estimated
	 */
	private void isValidEstimate(){
		if (estimateTextField.getText().length() > 0 && isInteger(estimateTextField.getText()) && !reqNameTextField.getText().isEmpty()){
			if (Integer.parseInt(estimateTextField.getText()) >= 0){
				voteButton.setEnabled(true);
				notAnIntegerError.setVisible(false);
			}
			else{
				voteButton.setEnabled(false);
				notAnIntegerError.setVisible(true);
			}
		}
		else{
			voteButton.setEnabled(false);
			notAnIntegerError.setVisible(true);
		}
	}
	
	//This function is used when a requirement is double clicked in one of the two requirement tables, and it sets the name and description fields to the 
	//selected requirement
	/**
	 * This function is used when a requirement is double clicked in one of the two requirement tables, and it sets the name and description fields to the
	 * selected requirement
	 * @param reqToEstimate the requirement to estimate
	 */
	public void chooseReq(Requirement reqToEstimate){
		currentReq = reqToEstimate;
		reqNameTextField.setText(currentReq.getName());
		reqDescTextArea.setText(currentReq.getDescription());
		int i = 0;
		for(i = 0; i < gameReqs.size(); i++){
			if (gameReqs.get(i) == currentReq.getId()){
				break;
			}
		}
		final int estimate = userEstimates.getVote().get(i);
		if (estimate > -1) {
			estimateTextField.setText(Integer.toString(estimate));
		} else {
			estimateTextField.setText("");
		}
	}
	

	/**
	 * This function will be used when the user submits an estimate for a requirement and it will notify GameRequirements to move the requirement from 
	 * to estimate table to the completed estimates table
	 * @param r the requirement to send
	 * @param estimate the estimate to be updated
	 */
	public void sendEstimatetoGameView(Requirement r, int estimate){
		gv.updateReqTables(r, estimate);
	}
	
	/**
	 * Helper function for checking if the estimate text box contains an integer
	 * @param s the string to be checking
	 * @return boolean true if integer, false if otherwise
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

	/**
	 * This function is called when the user estimates all of the requirements, it clears the name and description boxes
	 */
	public void clear() {
		reqNameTextField.setText("");
		reqDescTextArea.setText("");
		estimateTextField.setText("");
	}
	
	/**
	 * changes whether or not the user can submit vote
	 */
	public void checkCanSubmit(){
		boolean canSubmit = true;
		for (int estimate: userEstimates.getVote()){
			if (estimate < 0){
				canSubmit = false;
				break;
			}
		}
		submit.setEnabled(canSubmit);
	}
}
