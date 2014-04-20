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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * A panel for playing a game session
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class PlayDeckGame extends JPanel{

	private final List<Integer> gameReqs;
	private final JLabel gameName = new JLabel("Game Name:");
	private final JLabel gameDesc = new JLabel("Game Description:");
	private final JLabel reqName = new JLabel("Requirement Name:");
	private final JLabel reqDesc = new JLabel("Requirement Description:");
	private final JTextField gameNameTextField = new JTextField();
	private final JTextField reqNameTextField = new JTextField();
	private final JTextArea gameDescTextArea = new JTextArea();
	private final JTextArea reqDescTextArea = new JTextArea();
	private final JPanel deckAreaPanel = new JPanel();
	private final JScrollPane deckArea = new JScrollPane(deckAreaPanel);
	private final JScrollPane nd = new JScrollPane(gameDescTextArea);
	private final JScrollPane rd = new JScrollPane(reqDescTextArea);
	private final JButton submit = new JButton("Submit All Estimates");
	private final JButton voteButton = new JButton("Vote");
	private Vote userEstimates;
	private Requirement currentReq;
	private GameView gv;
	private GameSession currentGame;
	private int deckId;
	private List<Integer> gameCardList = new ArrayList<Integer>();
	private int votesSoFarInt = 0;
	private JLabel votesSoFarNameLabel = new JLabel("Estimate: ");
	private JLabel votesSoFarLabel = new JLabel("0");
	//List of buttons associated with the cards. First element -> lowest card val
	private final List<GameCard> cardButtons = new ArrayList<GameCard>();
	
	/**
	 * Constructor for a PlayGame panel
	 * @param gameToPlay the game session that is being played
	 * @param agv the active game view
	 */
	public PlayDeckGame(GameSession gameToPlay, GameView agv){
		currentGame = gameToPlay;
		gameReqs = currentGame.getGameReqs();
		deckId = currentGame.getDeckId();
		deckId = 0;
		gameCardList = DeckModel.getInstance().getDeck(deckId).getCards();
		generateButtons();
		final ArrayList<Integer> estimates = new ArrayList<Integer>();
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
		reqNameTextField.setText(currentReq.getName());
		gameDescTextArea.setText(currentGame.getGameDescription());
		reqDescTextArea.setText(currentReq.getDescription());
		gameNameTextField.setEditable(false);
		reqNameTextField.setEditable(false);
		gameDescTextArea.setEditable(false);
		reqDescTextArea.setEditable(false);
		gameDescTextArea.setLineWrap(true);
		gameDescTextArea.setWrapStyleWord(true);
		reqDescTextArea.setLineWrap(true);
		reqDescTextArea.setWrapStyleWord(true);
		
		//TODO add item listener to game cards;
		for (final GameCard card: cardButtons){
			card.addItemListener (new ItemListener() {
				public void itemStateChanged ( ItemEvent ie ) {
					if (card.isSelected()) {
						votesSoFarInt += card.getValue();
						votesSoFarLabel.setText (new Integer(votesSoFarInt).toString()) ;
					} else {
						votesSoFarInt -= card.getValue();
						votesSoFarLabel.setText (new Integer(votesSoFarInt).toString()) ;
					}
				}
			});
		}
		
		//Observer for the vote button. It will save the vote client side, the submit button will handle sending it to the database.
		
		voteButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				//final int estimate = Integer.parseInt(estimateTextField.getText());
				if (votesSoFarInt < 0){
					//TODO error message
					return;
				}
				else{
					for(int i = 0; i < gameReqs.size(); i++){
						if (gameReqs.get(i) == currentReq.getId()){
							ArrayList<Integer> votes = (ArrayList<Integer>) userEstimates.getVote();
							votes.set(i, votesSoFarInt);
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
		//set layout
		final SpringLayout springLayout = new SpringLayout();
		
		//Spring layout placement for gameName label
		springLayout.putConstraint(SpringLayout.NORTH, gameName, 15, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, 30, SpringLayout.WEST, this);
		
		//Spring layout placement for gameDesc label
		springLayout.putConstraint(SpringLayout.NORTH, gameDesc, 15, SpringLayout.SOUTH, gameName);
		springLayout.putConstraint(SpringLayout.WEST, gameDesc, 0, SpringLayout.WEST, gameName);
		
		//Spring layout placement for vote button
		springLayout.putConstraint(SpringLayout.NORTH, voteButton, 0, SpringLayout.NORTH, votesSoFarLabel);
		springLayout.putConstraint(SpringLayout.WEST, voteButton, 30, SpringLayout.EAST, votesSoFarLabel);
		
		//Spring layout placement for submit button
		springLayout.putConstraint(SpringLayout.SOUTH, submit, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, submit, -10, SpringLayout.EAST, this);
		
		//Spring layout for placement of gameNameTextField
		springLayout.putConstraint(SpringLayout.WEST, gameNameTextField, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, gameNameTextField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, gameNameTextField, 0, SpringLayout.NORTH, gameName);
		
		//Spring layout for placement of reqNameTextField
		springLayout.putConstraint(SpringLayout.WEST, reqNameTextField, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, reqNameTextField, 600, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameTextField, 0, SpringLayout.NORTH, reqName);
		
		//Spring layout for nd
		springLayout.putConstraint(SpringLayout.NORTH, nd, 0, SpringLayout.NORTH, gameDesc);
		springLayout.putConstraint(SpringLayout.WEST, nd, 0, SpringLayout.WEST, rd);
		springLayout.putConstraint(SpringLayout.EAST, nd, -30, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nd, 75, SpringLayout.NORTH, nd);
		
		//Spring layout for rd
		springLayout.putConstraint(SpringLayout.NORTH, rd, 0, SpringLayout.NORTH, reqDesc);
		springLayout.putConstraint(SpringLayout.WEST, rd, 10, SpringLayout.EAST, reqDesc);
		springLayout.putConstraint(SpringLayout.EAST, rd, -30, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, rd, 75, SpringLayout.NORTH, rd);
		
		//Spring layout for reqDesc label
		springLayout.putConstraint(SpringLayout.NORTH, reqDesc, 15, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.WEST, reqDesc, 0, SpringLayout.WEST, reqName);
		
		//Spring layout for reqName label
		springLayout.putConstraint(SpringLayout.NORTH, reqName, 90, SpringLayout.SOUTH, gameDesc);
		springLayout.putConstraint(SpringLayout.WEST, reqName, 0, SpringLayout.WEST, gameDesc);
		
		//Spring layout for deckArea
		springLayout.putConstraint(SpringLayout.NORTH, deckArea, 15, SpringLayout.SOUTH, rd);
		springLayout.putConstraint(SpringLayout.WEST, deckArea, 15, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, deckArea, -15, SpringLayout.EAST, this);
		//springLayout.putConstraint(SpringLayout.SOUTH, deckArea, -15, SpringLayout.NORTH, submit);
		
		//Spring layout for votesSoFarNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, votesSoFarNameLabel, 15, SpringLayout.SOUTH, deckArea);
		springLayout.putConstraint(SpringLayout.WEST, votesSoFarNameLabel, 0, SpringLayout.WEST, reqDesc);
		
		//Spring layout for votesSoFarLabel
		springLayout.putConstraint(SpringLayout.NORTH, votesSoFarLabel, 0, SpringLayout.NORTH, votesSoFarNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, votesSoFarLabel, 5, SpringLayout.EAST, votesSoFarNameLabel);
		
		setLayout(springLayout);
		
		add(voteButton);
		add(submit);
		add(gameName);
		add(gameDesc);
		add(reqName);
		add(reqDesc);
		add(gameNameTextField);
		add(reqNameTextField);
		add(nd);
		add(rd);
		add(deckArea);
		add(votesSoFarNameLabel);
		add(votesSoFarLabel);
	}
	
	/**
	 * This function cycles through available cards and generate the appropriate buttons
	 */
	private void generateButtons(){

		final Iterator<Integer> cardIterator = gameCardList.iterator();
		final Iterator<GameCard> btnIterator = cardButtons.iterator(); 

		System.out.println(gameCardList);
		
		
		//This loop will cycle through the deck's values, and create buttons for them.
		//The buttons will have the value as its text
		while(cardIterator.hasNext()){
			cardButtons.add(new GameCard(cardIterator.next()));
		}
		
		//Loops through all the cards, and adds it to the scroll pane's panel
		for(GameCard i: cardButtons){
			deckAreaPanel.add(i);
		}
		
		System.out.println("CI:"+cardButtons);
		System.out.println("v2:"+gameCardList);
		//This loop will cycle through all of the buttons that have been created and display them
		
	}
	/**
	 * This function cycles through available cards and add the appropriate buttons
	 */
	private void addButtons(){
		for(int i=0; i < cardButtons.size(); i++)
		{
		   GameCard c = cardButtons.get(i);
		   add(c);
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
			votesSoFarLabel.setText(Integer.toString(estimate));
		} else {
			votesSoFarLabel.setText("");
		}
	}
	

	/**
	 * This function will be used when the user submits an estimate for a requirement and it will notify GameRequirements to move the requirement from 
	 * to estimate table to the completed estimates table
	 * @param r the requirement to send
	 */
	public void sendEstimatetoGameView(Requirement r){
		gv.updateReqTables(r);
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
