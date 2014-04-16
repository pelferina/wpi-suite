/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
 * @author fff8e7
 *
 */
public class PlayGame extends JPanel{

	private List<Integer> gameReqs;
	private final JLabel reqName = new JLabel("Requirement Name:");
	private final JLabel reqDesc = new JLabel("Requirement Description:");
	private final JLabel estimateLabel = new JLabel("Input Estimate");
	private JTextField estimateTextField = new JTextField();
	private JTextField reqNameTextField = new JTextField();
	private JTextArea reqDescTextArea = new JTextArea();
	private final JButton submit = new JButton("Submit All Estimates");
	private final JButton voteButton = new JButton("Vote");
	private Vote userEstimates;
	private Requirement currentReq;
	private GameView gv;
	private GameSession currentGame;
	
	public PlayGame(GameSession gameToPlay, GameView agv){
		currentGame = gameToPlay;
		this.gameReqs = currentGame.getGameReqs();
		ArrayList<Integer> estimates = new ArrayList<Integer>();
		System.out.println(gameReqs.size());
		for (int i = 0; i < gameReqs.size(); i++){
			estimates.add(-1);
		}
		userEstimates = new Vote(estimates, currentGame.getGameID());
		for (Vote v: gameToPlay.getVotes()){
			if (v.getUID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum()){
				userEstimates = v;
			}
		}
		submit.setEnabled(false);
		this.gv = agv;
		List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
		
		//Finds the requirement that is first in the to estimate table. The play game screen will default to displaying the first requirement
		//in the estimates pending table
		for (Requirement r: allReqs){
			if (r.getId() == gameReqs.get(0)){
				currentReq = r;
				break;
			}
		}
		
		//Sets the description and name text fields to the first requirement in the to estimate table
		reqNameTextField.setText(currentReq.getName());
		reqDescTextArea.setText(currentReq.getDescription());
		if (gameToPlay.getVotes().size() > 0){
			estimateTextField.setText(Integer.toString(gameToPlay.getVotes().get(0).getVote().get(currentReq.getId())));
		}
		reqNameTextField.setEditable(false);
		reqDescTextArea.setEditable(false);
		
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
				int estimate = Integer.parseInt(estimateTextField.getText());
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
					sendEstimatetoGameView(currentReq);
				}
			}
			
		});
		
		//adds the action listener for controlling the submit button
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int option = JOptionPane.showOptionDialog(gv, "Do you wish to submit your current votes?", "Save Votes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (option == 0){
					AddVoteController msgr = new AddVoteController(VoteModel.getInstance());
					msgr.sendVote(userEstimates);
					ViewEventController.getInstance().getMain().remove(gv);
				}
			}
		});
		
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout placement for vote button
		springLayout.putConstraint(SpringLayout.NORTH, voteButton, 6, SpringLayout.SOUTH, estimateTextField);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, 132, SpringLayout.WEST, this);		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, voteButton, 0, springLayout.HORIZONTAL_CENTER, estimateTextField);
		
		//Spring layout placement for submit button
		springLayout.putConstraint(SpringLayout.NORTH, submit, 30, SpringLayout.SOUTH, voteButton);
		springLayout.putConstraint(SpringLayout.WEST, submit, 0, SpringLayout.WEST, voteButton);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, submit, 0, springLayout.HORIZONTAL_CENTER, voteButton);
		
		//Spring layout placement for estimateTextField
		springLayout.putConstraint(SpringLayout.NORTH, estimateTextField, 6, SpringLayout.SOUTH, estimateLabel);
		springLayout.putConstraint(SpringLayout.WEST, estimateTextField, 0, SpringLayout.WEST, estimateLabel);
		springLayout.putConstraint(SpringLayout.EAST, estimateTextField, 0, SpringLayout.EAST, estimateLabel);
		
		//Spring layout for placement of reqNameTextField
		springLayout.putConstraint(SpringLayout.WEST, reqNameTextField, 32, SpringLayout.EAST, reqName);
		springLayout.putConstraint(SpringLayout.EAST, reqNameTextField, -116, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameTextField, -3, SpringLayout.NORTH, reqName);
		
		//Spring layout for estimateLabel
		springLayout.putConstraint(SpringLayout.NORTH, estimateLabel, 47, SpringLayout.SOUTH, reqDescTextArea);
		springLayout.putConstraint(SpringLayout.WEST, estimateLabel, 155, SpringLayout.WEST, this);
		
		//Spring layout for reqDescTextArea
		springLayout.putConstraint(SpringLayout.NORTH, reqDescTextArea, 0, SpringLayout.NORTH, reqDesc);
		springLayout.putConstraint(SpringLayout.WEST, reqDescTextArea, 6, SpringLayout.EAST, reqDesc);
		springLayout.putConstraint(SpringLayout.SOUTH, reqDescTextArea, 113, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.EAST, reqDescTextArea, 438, SpringLayout.WEST, this);
		
		//Spring layout for reqDesc label
		springLayout.putConstraint(SpringLayout.NORTH, reqDesc, 16, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.WEST, reqDesc, 0, SpringLayout.WEST, reqName);
		
		//Spring layout for reqName label
		springLayout.putConstraint(SpringLayout.NORTH, reqName, 35, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqName, 25, SpringLayout.WEST, this);
		setLayout(springLayout);
	
		add(voteButton);
		add(submit);
		add(reqName);
		add(reqDesc);
		add(estimateLabel);
		add(estimateTextField);
		add(reqNameTextField);
		add(reqDescTextArea);
	}
	
	//This function checks to make sure an inputed estimate is valid, i.e is not blank and is an integer
	//then enables the vote button
	private void isValidEstimate(){
		if (estimateTextField.getText().length() > 0 && isInteger(estimateTextField.getText())){
			voteButton.setEnabled(true);
		}
		else{
			voteButton.setEnabled(false);
		}
	}
	
	//This function is used when a requirement is double clicked in one of the two requirement tables, and it sets the name and description fields to the 
	//selected requirement
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
		int estimate = userEstimates.getVote().get(i);
		if (estimate > -1) {
			estimateTextField.setText(Integer.toString(estimate));
		} else {
			estimateTextField.setText("");
		}
	}
	
	//This function will be used when the user submits an estimate for a requirement, and it will notify GameRequirements to move the requirement from
	//to estimate table to the completed estimates table
	public void sendEstimatetoGameView(Requirement r){
		gv.updateReqTables(r);
	}
	
	//Helper function for checking if the estimate text box contains an integer
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}

	//This function is called when the user estimates all of the requirements, it clears the name and description boxes
	public void clear() {
		reqNameTextField.setText("");
		reqDescTextArea.setText("");
		estimateTextField.setText("");
	}
	
	//checks to see if the user has voted on all the requirements in a game, then enables the submit button
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
