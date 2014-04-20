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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * A panel for playing a game session
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class PlayDeckGameTest extends JPanel{

	private final List<Integer> gameReqs;
	private final JLabel reqName = new JLabel("Requirement Name:");
	private final JLabel reqDesc = new JLabel("Requirement Description:");
	private final JLabel estimateLabel = new JLabel("Input Estimate");
	private final JTextField estimateTextField = new JTextField();
	private final JTextField reqNameTextField = new JTextField();
	private final JTextArea reqDescTextArea = new JTextArea();
	private final JButton submit = new JButton("Submit All Estimates");
	private final JButton voteButton = new JButton("Vote");
	private Vote userEstimates;
	private Requirement currentReq;
	private GameSession currentGame;
	private int deckId;
	private List<Integer> gameCardList = new ArrayList<Integer>();
	private Integer votesSoFarInt = 0;
	private JLabel votesSoFarNameLabel = new JLabel("Estimate: ");
	private JLabel votesSoFarLabel = new JLabel("0");
	//List of buttons associated with the cards. First element -> lowest card val
	private final List<GameCard> cardButtons = new ArrayList<GameCard>();
	
	/**
	 * Constructor for a PlayGame panel
	 * @param gameToPlay the game session that is being played
	 * @param agv the active game view
	 */
	public PlayDeckGameTest(GameSession gameToPlay){
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
		//reqNameTextField.setText(currentReq.getName());
		//reqDescTextArea.setText(currentReq.getDescription());
		if (gameToPlay.getVotes().size() > 0){
			estimateTextField.setText(Integer.toString(gameToPlay.getVotes().get(0).getVote().get(currentReq.getId())));
		}
		reqNameTextField.setEditable(false);
		reqDescTextArea.setEditable(false);
		//TODO add item listener to game cards;
		for (final GameCard card: cardButtons){
			card.addItemListener (new ItemListener() {
				public void itemStateChanged ( ItemEvent ie ) {
					if (card.isSelected()) {
						votesSoFarInt += card.getValue();
						votesSoFarLabel.setText (votesSoFarInt.toString()) ;
					} else {
						votesSoFarInt -= card.getValue();
						votesSoFarLabel.setText (votesSoFarInt.toString()) ;
					}
				}
			});
		}
		
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
				}
			}
			
		});
		
		
		//setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(reqName)
						.addComponent(reqDesc)
						.addComponent(cardButtons.get(0))
						.addComponent(cardButtons.get(4))
						.addComponent(votesSoFarNameLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(reqNameTextField)
						.addComponent(reqDescTextArea)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(cardButtons.get(1))
										.addComponent(cardButtons.get(5)))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(cardButtons.get(2))
										.addComponent(cardButtons.get(6)))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(cardButtons.get(3))
										.addComponent(cardButtons.get(7))))
						.addGroup(layout.createSequentialGroup()
								.addComponent(votesSoFarLabel)
								.addComponent(voteButton)
								.addComponent(submit))).addComponent(voteButton)
								.addComponent(submit)
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(reqName)
						.addComponent(reqNameTextField))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(reqDesc)
						.addComponent(reqDescTextArea))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cardButtons.get(0))
						.addComponent(cardButtons.get(1))
						.addComponent(cardButtons.get(2))
						.addComponent(cardButtons.get(3)))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cardButtons.get(4))
						.addComponent(cardButtons.get(5))
						.addComponent(cardButtons.get(6))
						.addComponent(cardButtons.get(7)))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(votesSoFarNameLabel)
						.addComponent(votesSoFarLabel)
						.addComponent(voteButton)
						.addComponent(submit))
		);
		/*
		
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
	*/
		
		add(reqName);
		add(reqNameTextField);
		add(reqDesc);
		//add(estimateLabel);
		//add(estimateTextField);
		add(reqDescTextArea);
		addButtons();
		add(votesSoFarNameLabel);
		add(votesSoFarLabel);
		add(voteButton);
		add(submit);
	}
	
	/**
	 * This function cycles through available cards and generate the appropriate buttons
	 */
	private void generateButtons(){

		final Iterator<Integer> cardIterator = gameCardList.iterator();
		final Iterator<GameCard> buttonIterator = cardButtons.iterator();

		System.out.println(gameCardList);
		
		
		//This loop will cycle through the deck's values, and create buttons for them.
		//The buttons will have the value as its text
		while(cardIterator.hasNext()){
			cardButtons.add(new GameCard(cardIterator.next()));
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
			estimateTextField.setText(Integer.toString(estimate));
		} else {
			estimateTextField.setText("");
		}
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
