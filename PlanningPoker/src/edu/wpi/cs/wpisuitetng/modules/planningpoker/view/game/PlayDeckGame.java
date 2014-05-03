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





import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import javax.swing.SpringLayout;





import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;



import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;




/**
 * A panel for playing a game session
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class PlayDeckGame extends PlayGame implements Refreshable{

	private final JPanel deckAreaPanel = new JPanel();
	private final JScrollPane deckArea = new JScrollPane(deckAreaPanel);
	private final int deckId;
	private List<Integer> gameCardList = new ArrayList<Integer>();
	private int votesSoFarInt = -1;
	
	private final JLabel votesSoFarLabel = new JLabel("0");

	//List of buttons associated with the cards. First element -> lowest card val
	private final List<GameCard> cardButtons = new ArrayList<GameCard>();

	private boolean isDeckSingleSelection;

	/**
	 * Constructor for a PlayGame panel
	 * @param gameToPlay the game session that is being played
	 * @param agv the active game view
	 */
	public PlayDeckGame(GameSession gameToPlay, GameView agv){
		super(gameToPlay, agv);

		deckId = currentGame.getDeckId();
		gameCardList = DeckModel.getInstance().getDeck(deckId).getCards();
		isDeckSingleSelection = DeckModel.getInstance().getDeck(deckId).isSingleSelection();
		generateButtons();

		deckArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		for (final GameCard card: cardButtons){


			card.addItemListener (new ItemListener() {
				public void itemStateChanged ( ItemEvent ie ) {
					if (!isAnyCardSelected()) {
						votesSoFarInt = -1;
						voteButton.setEnabled(false);
						votesSoFarLabel.setVisible(false);
					}
					else if(!isDeckSingleSelection){ // MULTIPLE SELECTION
						if (votesSoFarInt == -1) {
							votesSoFarInt = 0;
							votesSoFarLabel.setVisible(true);
						}
						if (card.isSelected()) {
							votesSoFarInt += card.getValue();
							votesSoFarLabel.setText (Integer.toString(votesSoFarInt)) ;
						} else {
							votesSoFarInt -= card.getValue();
							votesSoFarLabel.setText (Integer.toString(votesSoFarInt)) ;
						}
						voteButton.setEnabled(true);
					} else { // SINGLE SELECTION
						if (card.isSelected()) {
							votesSoFarInt = card.getValue();
							votesSoFarLabel.setVisible(true);
							unselectOtherCards(card);
							voteButton.setEnabled(true);
							votesSoFarLabel.setText (Integer.toString(votesSoFarInt)) ;
						}
					}
				} 
			});

		}

		//Observer for the vote button. It will save the vote client side, the submit button will handle sending it to the database.

		voteButton.removeActionListener(voteActionListener);
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
					//System.out.println(userEstimates.getVote());
					sendEstimatetoGameView(currentReq, votesSoFarInt);
				}
				gv.isNew = false;
				unselectAll();
			}

		});

		springLayout.putConstraint(SpringLayout.SOUTH, reqDescScroll, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, deckArea);

		//Spring layout for deckArea
		springLayout.putConstraint(SpringLayout.WEST, deckArea, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, deckArea, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, deckArea, -GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.NORTH, notAnIntegerError);

		//Spring layout for votesSoFarNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, votesSoFarNameLabel, 15, SpringLayout.SOUTH, deckArea);
		springLayout.putConstraint(SpringLayout.WEST, votesSoFarNameLabel, 0, SpringLayout.WEST, deckArea);

		//Spring layout for votesSoFarLabel
		springLayout.putConstraint(SpringLayout.NORTH, votesSoFarLabel, 0, SpringLayout.NORTH, votesSoFarNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, votesSoFarLabel, 5, SpringLayout.EAST, votesSoFarNameLabel);

		setLayout(springLayout);

		votesSoFarLabel.setVisible(false);

		add(deckArea);
		add(votesSoFarNameLabel);
		add(votesSoFarLabel);

		super.estimateLabel.setVisible(false);
		super.estimateTextField.setVisible(false);

	}

	/**
	 * Deselects all cards except for the one passed in
	 * @param selectedCard The card to keep
	 */
	protected void unselectOtherCards(GameCard selectedCard) {
		boolean selectedFlag = false; // If loop keeps a card selected, this flag will prevent selecting other cards with the same value
		for(GameCard card: cardButtons){
			if(card == selectedCard && !selectedFlag) selectedFlag = true; // if current card is the same as the given card, skip the unselection
			else card.setSelected(false); // else, unselect it
		}

	}

	/**
	 * This function cycles through available cards and generate the appropriate buttons
	 */
	private void generateButtons(){

		final Iterator<Integer> cardIterator = gameCardList.iterator();

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
	 * This function cycles through available cards and unselect all of them
	 */
	public void unselectAll(){
		System.out.println("Unselecting All Cards");
		for(int i=0; i < cardButtons.size(); i++)
		{
			GameCard c = cardButtons.get(i);
			c.setSelected(false);
		}
		voteButton.setEnabled(false);
	}

	/**
	 * Check if any cards are selected
	 * @return true if at least one card is selected
	 */
	private boolean isAnyCardSelected() {
		for (GameCard aCard: cardButtons) {
			if (aCard.isSelected()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void refreshDecks() {
		// TODO Auto-generated method stub

	}
}
