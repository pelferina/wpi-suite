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

import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A panel for playing a game session
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class PlayGame extends JPanel implements Refreshable{

	protected final List<Integer> gameReqs;
	protected final JLabel gameName = new JLabel("Game Name:");
	protected final JLabel gameDesc = new JLabel("Game Description:");
	protected final JLabel reqName = new JLabel("Requirement Name:");
	protected final JLabel reqDesc = new JLabel("Requirement Description:");
	protected final JLabel estimateLabel = new JLabel("Input Estimate:");
	protected final JLabel votesSoFarNameLabel = new JLabel("Estimate: "); // Used by play deck game
	protected final JLabel gameEnded = new JLabel("The Game Has Ended.");
	protected final JLabel gameModified = new JLabel("The game has been modified. Please reopen it.");
	protected final JLabel notAnIntegerError = new JLabel("Estimate must be a positive integer");
	protected final JLabel voteConfirmation = new JLabel("Vote submitted!");
	protected JLabel deadlineLabel = new JLabel("Game ends on:");
	protected final JTextField estimateTextField = new JTextField();
	protected final JTextField gameNameTextField = new JTextField();
	protected final JTextField reqNameTextField = new JTextField();
	protected final JTextArea gameDescTextArea = new JTextArea();
	protected final JTextArea reqDescTextArea = new JTextArea();
	protected final JScrollPane gameDescScroll = new JScrollPane(gameDescTextArea);
	protected final JScrollPane reqDescScroll = new JScrollPane(reqDescTextArea);
	protected final JButton submit = new JButton("Submit");
	protected final JButton voteButton = new JButton("Vote");
	protected Vote userEstimates;
	protected Requirement currentReq;
	protected final GameView gv;
	protected GameSession currentGame;
	protected boolean hasVoted = false;
	protected TimerTask setFocus;
	protected TimerTask voteConfirm;
	protected Timer setFocusTimer;
	protected final SpringLayout springLayout = new SpringLayout();
	protected ActionListener voteActionListener;
	private boolean hasModified = false;

	/**
	 * Constructor for a PlayGame panel
	 * @param gameToPlay the game session that is being played
	 * @param agv the active game view
	 */
	public PlayGame(GameSession gameToPlay, GameView agv){
		if (!gameToPlay.getDeadlineString().equals("No deadline")){
			deadlineLabel.setText("Game ends at: " + gameToPlay.getDeadlineString());
		}
		else {
			deadlineLabel.setText("This game has no deadline");
		}
		voteConfirmation.setVisible(false);
		GetGamesController.getInstance().addRefreshable(this);
		gameEnded.setVisible(false);
		gameModified.setVisible(false);

		setFocus = new TimerTask(){

			@Override
			public void run() {
				estimateTextField.requestFocusInWindow();
				getRootPane().setDefaultButton(voteButton);
			}

		};

		setFocusTimer = new Timer();
		setFocusTimer.schedule(setFocus, 250);

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
			estimateTextField.requestFocusInWindow();
		}
		gameDescTextArea.setText(currentGame.getGameDescription());

		// set not editable
		gameNameTextField.setEditable(false);
		reqNameTextField.setEditable(false);
		gameDescTextArea.setEditable(false);
		reqDescTextArea.setEditable(false);

		// set word wrap
		gameDescTextArea.setLineWrap(true);
		gameDescTextArea.setWrapStyleWord(true);
		reqDescTextArea.setLineWrap(true);
		reqDescTextArea.setWrapStyleWord(true);

		// set colors
		gameNameTextField.setBackground(Color.WHITE);
		gameNameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		reqNameTextField.setBackground(Color.WHITE);
		reqNameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
		notAnIntegerError.setForeground(Color.red);

		//Add padding
		gameDescTextArea.setBorder(BorderFactory.createCompoundBorder(
				gameDescTextArea.getBorder(), 
				BorderFactory.createEmptyBorder(GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue())));

		reqDescTextArea.setBorder(BorderFactory.createCompoundBorder(
				reqDescTextArea.getBorder(), 
				BorderFactory.createEmptyBorder(GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue(), 
						GuiStandards.TEXT_AREA_MARGINS.getValue())));

		gameNameTextField.setBorder(BorderFactory.createCompoundBorder(
				gameNameTextField.getBorder(), 
				BorderFactory.createEmptyBorder(0, GuiStandards.TEXT_BOX_MARGIN.getValue(), 0, 0)));

		reqNameTextField.setBorder(BorderFactory.createCompoundBorder(
				reqNameTextField.getBorder(), 
				BorderFactory.createEmptyBorder(0, GuiStandards.TEXT_BOX_MARGIN.getValue(), 0, 0)));

		//This document listener will enable the submit button when something is inputted into the estimate text field
		estimateTextField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				checkValidEstimate();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				checkValidEstimate();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				checkValidEstimate();
			}
		});

		//Observer for the vote button. It will save the vote client side, the submit button will handle sending it to the database.

		voteActionListener = new ActionListener(){

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
				estimateTextField.requestFocusInWindow();
				gv.isNew = false;
				notAnIntegerError.setVisible(false);
			}

		};
		voteButton.addActionListener(voteActionListener);

		//adds the action listener for controlling the submit button
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				currentGame = GameModel.getInstance().getGame(currentGame.getGameID());
				if (currentGame.getGameStatus() == GameStatus.COMPLETED || currentGame.getGameStatus() == GameStatus.ARCHIVED)
				{
					submit.setText("Game Ended");
					submit.setEnabled(false);
				}
				else
				{
					final AddVoteController msgr = new AddVoteController(VoteModel.getInstance());
					msgr.sendVote(userEstimates);
					gv.isNew = true;
					ViewEventController.getInstance().getMain().remove(gv);
				}
			}
		});


		//Spring layout placement for gameName label
		springLayout.putConstraint(SpringLayout.NORTH, gameName, GuiStandards.TOP_MARGIN.getValue(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, gameName, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);

		//Spring layout placement for gameDesc label
		springLayout.putConstraint(SpringLayout.NORTH, gameDesc, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, gameNameTextField);
		springLayout.putConstraint(SpringLayout.WEST, gameDesc, 0, SpringLayout.WEST, gameName);

		//Spring layout placement for vote button
		springLayout.putConstraint(SpringLayout.SOUTH, voteButton, 0, SpringLayout.SOUTH, submit);
		springLayout.putConstraint(SpringLayout.EAST, voteButton, -10, SpringLayout.WEST, submit);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, -100, SpringLayout.EAST, voteButton);

		//Spring layout placement for submit button
		springLayout.putConstraint(SpringLayout.SOUTH, submit, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, submit, 0, SpringLayout.EAST, reqDescScroll);

		//Spring layout placement for estimateTextField
		springLayout.putConstraint(SpringLayout.NORTH, estimateTextField, 0, SpringLayout.NORTH, voteButton);
		springLayout.putConstraint(SpringLayout.SOUTH, estimateTextField, 0, SpringLayout.SOUTH, voteButton);
		springLayout.putConstraint(SpringLayout.EAST, estimateTextField, -10, SpringLayout.WEST, voteButton);
		springLayout.putConstraint(SpringLayout.WEST, estimateTextField, -60, SpringLayout.EAST, estimateTextField);

		//Spring layout for placement of gameNameTextField
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextField, 0, SpringLayout.WEST, gameName);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextField, 0, SpringLayout.EAST, gameDescScroll);
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextField, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, gameName);

		//Spring layout for placement of reqNameTextField
		springLayout.putConstraint(SpringLayout.WEST, reqNameTextField, 0, SpringLayout.WEST, reqName);
		springLayout.putConstraint(SpringLayout.EAST, reqNameTextField, 0, SpringLayout.EAST, gameDescScroll);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameTextField, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, reqName);

		//Spring layout for estimateLabel
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, estimateLabel, 0, SpringLayout.VERTICAL_CENTER, estimateTextField);
		springLayout.putConstraint(SpringLayout.EAST, estimateLabel, -5, SpringLayout.WEST, estimateTextField);

		//Spring layout for gameDescScroll
		springLayout.putConstraint(SpringLayout.NORTH, gameDescScroll, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, gameDesc);
		springLayout.putConstraint(SpringLayout.WEST, gameDescScroll, 0, SpringLayout.WEST, gameDesc);
		springLayout.putConstraint(SpringLayout.EAST, gameDescScroll, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, gameDescScroll, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, reqName);
		
		//Spring layout for reqDescScroll
		springLayout.putConstraint(SpringLayout.NORTH, reqDescScroll, 10, SpringLayout.SOUTH, reqDesc);
		springLayout.putConstraint(SpringLayout.WEST, reqDescScroll, 0, SpringLayout.WEST, reqDesc);
		springLayout.putConstraint(SpringLayout.EAST, reqDescScroll, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, reqDescScroll, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, notAnIntegerError);

		//Spring layout for reqDesc label
		springLayout.putConstraint(SpringLayout.NORTH, reqDesc, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, reqNameTextField);
		springLayout.putConstraint(SpringLayout.WEST, reqDesc, 0, SpringLayout.WEST, reqName);

		//Spring layout for reqName label
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, reqName, -20, SpringLayout.VERTICAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.WEST, reqName, 0, SpringLayout.WEST, gameDesc);

		//Spring layout for notAnIntegerError label
		springLayout.putConstraint(SpringLayout.SOUTH, notAnIntegerError, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, submit);
		springLayout.putConstraint(SpringLayout.EAST, notAnIntegerError, 0, SpringLayout.EAST, submit);

		//Spring layout for GameEnded label
		springLayout.putConstraint(SpringLayout.NORTH, gameEnded, 0, SpringLayout.SOUTH, notAnIntegerError);
		springLayout.putConstraint(SpringLayout.EAST, gameEnded, 0, SpringLayout.EAST, notAnIntegerError);

		//Spring layout for GameEnded label
		springLayout.putConstraint(SpringLayout.NORTH, gameModified, 0, SpringLayout.SOUTH, notAnIntegerError);
		springLayout.putConstraint(SpringLayout.EAST, gameModified, 0, SpringLayout.EAST, notAnIntegerError);
		
		
		//Spring layout for voteConfirmation label
		springLayout.putConstraint(SpringLayout.SOUTH, voteConfirmation, 10, SpringLayout.NORTH, voteButton);
		springLayout.putConstraint(SpringLayout.WEST, voteConfirmation, 0, SpringLayout.WEST, voteButton);

		//Spring layout for deadlineLabel
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, deadlineLabel, 0, SpringLayout.VERTICAL_CENTER, notAnIntegerError);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);

		setLayout(springLayout);

		add(voteConfirmation);
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
		add(gameEnded);
		add(gameModified);
		add(gameDescScroll);
		add(reqDescScroll);
		add(deadlineLabel);
	}

	/**
	 * This function is used when a requirement is double clicked in one of the two requirement tables 
	 * and it sets the name and description fields to the selected requirement
	 */
	protected void checkValidEstimate(){
		if (!estimateTextField.getText().isEmpty()) {
			if (isInteger(estimateTextField.getText())) {
				if (Integer.parseInt(estimateTextField.getText()) >= 0){
					voteButton.setEnabled(true);
					notAnIntegerError.setVisible(false);
				}
				else{
					voteButton.setEnabled(false);
					notAnIntegerError.setVisible(true);
				}

			}
			else {
				voteButton.setEnabled(false);
				notAnIntegerError.setVisible(true);
			}
		}
		else {
			voteButton.setEnabled(false);
			notAnIntegerError.setVisible(false);
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
		estimateTextField.requestFocusInWindow();
		voteButton.setEnabled(false);
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
		boolean canSubmit = false;
		//TODO Change to hasChanged
		for (int estimate: userEstimates.getVote()){
			if (estimate > 0){
				canSubmit = true;
				break;
			}
		}

		if (currentGame.getGameStatus() == GameStatus.COMPLETED || currentGame.getGameStatus() == GameStatus.ARCHIVED)
		{
			clear();
			submit.setText("Game Ended");
			canSubmit = false;
			submit.setVisible(false);
			voteButton.setVisible(false);
			estimateTextField.setVisible(false);
			notAnIntegerError.setVisible(false);
			gameEnded.setVisible(true);
			estimateTextField.setText("");

		}
		submit.setEnabled(canSubmit);
	}

	@Override
	public void refreshRequirements(){
	}

	@Override
	public void refreshGames() {
		if(hasModified){
			return;
		}
		final GameSession refreshedGame = GameModel.getInstance().getGame(currentGame.getGameID());
		
		if (refreshedGame.getGameStatus().equals(GameStatus.ACTIVE) &&
				(!refreshedGame.getGameReqs().equals(currentGame.getGameReqs())
				|| !refreshedGame.getDeadlineString().equals(currentGame.getDeadlineString())
				|| refreshedGame.getDeckId() != currentGame.getDeckId())) {
			gameModified.setVisible(true);
			hasModified = true;
			hideSubmit();
			currentGame = refreshedGame;
			return;
		}

		currentGame = refreshedGame;
		if (currentGame.getGameStatus() == GameStatus.COMPLETED || currentGame.getGameStatus() == GameStatus.ARCHIVED)
		{
			hideSubmit();
			gameEnded.setVisible(true);
		}
	}

	private void hideSubmit(){
		clear();
		submit.setText("Game has been modified");
		submit.setVisible(false);
		voteButton.setVisible(false);
		estimateTextField.setVisible(false);
		estimateLabel.setVisible(false);
		notAnIntegerError.setVisible(false);

		estimateTextField.setText("");
		votesSoFarNameLabel.setVisible(false);
		deadlineLabel.setVisible(false);
	}
	
	@Override
	public void refreshDecks() {
		// TODO Auto-generated method stub

	}


	public JTextField getEstimateTextField() {
		return estimateTextField;
	}

	public JTextField getGameNameTextField() {
		return gameNameTextField;
	}

	public JTextField getReqNameTextField() {
		return reqNameTextField;
	}

	public JTextArea getGameDescTextArea() {
		return gameDescTextArea;
	}

	public JTextArea getReqDescTextArea() {
		return reqDescTextArea;
	}

	public JButton getSubmit() {
		return submit;
	}

	public JButton getVoteButton() {
		return voteButton;
	}

}
