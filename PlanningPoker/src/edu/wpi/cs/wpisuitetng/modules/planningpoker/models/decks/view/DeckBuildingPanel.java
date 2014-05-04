/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.JTextFieldLimit;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;

/**
 * The DeckBuildingPanel class
 * @author Cosmic Latte
 * @version 6
 */
@SuppressWarnings({"serial"})
public class DeckBuildingPanel extends JPanel {

	private boolean isSingleSelection = true;
	private final JButton btnAddCard = new JButton("Add Card");
	private final JButton btnRmvSelected = new JButton("Remove Selected");
	private final JButton btnRmvAll = new JButton("Remove all");
	private final JButton btnSave = new JButton("Save deck");
	private final JButton btnDelete = new JButton("Delete deck");
	private final JButton btnCancel = new JButton("Cancel deck");
	private final JRadioButton btnSingleSelection = new JRadioButton("Single Selection");
	private final JRadioButton btnMultipleSelection = new JRadioButton("Multiple Selection");
	private final ButtonGroup selectionGroup = new ButtonGroup();
	private final JComboBox<String> comboBoxDeckList = new JComboBox<String>();
	private final JLabel lblDeckName = new JLabel("Deck Name:");
	private final JLabel lblDecks = new JLabel("Decks:");
	private final JLabel errLabel = new JLabel("");
	private final JLabel lblAdd = new JLabel("Card Value:");
	private final JLabel lblSelection = new JLabel("Selection Mode:");
	private final JTextField nameField = new JTextField();
	private final JTextField numberField = new JTextField();
	private final SpringLayout springLayout = new SpringLayout();
	private String newDeckName;
	private final List<Integer> newDeckCards = new ArrayList<Integer>();
	private List<Integer> cardsToBeRemoved = new ArrayList<Integer>();
	private final JPanel cardPanel = new JPanel();
	private final JScrollPane cardArea = new JScrollPane(cardPanel);
	private final NewGameDistributedPanel newGameDistributed;
//	private final JLabel notAnIntegerError = new JLabel ("Please enter an non-negative integer for card value!");
//	private final JLabel duplicateNameError = new JLabel ("This deck name already exists!");
//	private final JLabel noCardError = new JLabel ("You need to have at least one card in the deck!");

	/** 
	 * Constructor for a DeckPanel panel
	 * @param ngdp The NewGameDistributedPanel to be based off of
	 */
	public DeckBuildingPanel(NewGameDistributedPanel ngdp){
		setupButtonIcons();
		
		// Set up for Radio buttons that will determine deck selection mode
		selectionGroup.add(btnSingleSelection);
		selectionGroup.add(btnMultipleSelection);
		btnSingleSelection.setSelected(true);
		btnMultipleSelection.setSelected(false);
		newGameDistributed = ngdp;
		
		// Sets a consistent font for all buttons
		final Font size = new Font(btnSave.getFont().getName(), btnSave.getFont().getStyle(), 10);
		
		btnSave.setFont(size);
		btnSave.setSize(80, 20);
		btnSave.setEnabled(false);
		btnDelete.setFont(size);
		btnDelete.setSize(80, 20);
		btnAddCard.setFont(size);
		btnAddCard.setSize(80, 20);
		btnAddCard.setEnabled(false);
		btnRmvSelected.setFont(size);
		btnRmvSelected.setSize(80, 20);
		btnRmvAll.setFont(size);
		btnRmvAll.setSize(80, 20);
		btnCancel.setFont(size);
		btnCancel.setSize(80, 20);
		errLabel.setVisible(false);
		//cardPanel.setMinimumSize(new Dimension(200, 13));
		cardArea.setMinimumSize(new Dimension(200, 135));

		nameField.setDocument(new JTextFieldLimit(20));
		numberField.setDocument(new JTextFieldLimit(3));
		
		// Sets up cardArea
		cardArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		//This document listener will enable the submit button when something is inputed into the estimate text field
		numberField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				isValidCard();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				isValidCard();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				isValidCard();
			}
		});
		
		//This document listener will perform actions accordingly when the test in nameField is changed
		nameField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				isValidDeckName();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				isValidDeckName();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				isValidDeckName();
			}
		});
		
		// All listeners and their functions
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newDeckName = nameField.getText();

				final Deck newDeck = new Deck (newDeckName, newDeckCards);
				newDeck.setIsSingleSelection(isSingleSelection);

				AddDeckController.getInstance().addDeck(newDeck);
				System.out.println("added Deck " + newDeckName + 
						"; Id = " + newDeck.getId() + 
						"; with cards: " + newDeckCards.toString() + 
						"; with singleSelection set to:" + newDeck.getIsSingleSelection());
				System.out.println("Current DeckModel size is " + DeckModel.getInstance().getSize());
				nameField.setText("");
				
				// Clears lists
				newDeckCards.clear();
				cardsToBeRemoved.clear();
				
				// Clears panel
				cardPanel.removeAll();
				cardPanel.revalidate();
				cardPanel.repaint();
				
				// Resets Selection Modes
				isSingleSelection = true;
				btnSingleSelection.setSelected(true);
				btnMultipleSelection.setSelected(false);
				
				newGameDistributed.closeDeck();
			}
		});
		
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				nameField.setText("");
				
				// Clears lists
				newDeckCards.clear();
				cardsToBeRemoved.clear();
				
				// Clears panel
				cardPanel.removeAll();
				cardPanel.revalidate();
				cardPanel.repaint();
				
				// Resets Selection Modes
				isSingleSelection = true;
				btnSingleSelection.setSelected(true);
				btnMultipleSelection.setSelected(false);
				
				newGameDistributed.closeDeck();
			}
		});
		
		btnAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final GameCard card;
				final int cardNumber;
				try {
					cardNumber = Integer.parseInt(numberField.getText());
					card = new GameCard(cardNumber);
					card.setCancelCard(true);
					// Sets listener for the  new card
					card.addItemListener (new ItemListener(){
						public void itemStateChanged ( ItemEvent ie) {
							if (card.isSelected()){
								cardsToBeRemoved.add(card.getValue()); // If card is selected, adds its value to the list of cards to be removed
								Collections.sort(cardsToBeRemoved);
							} else {
								cardsToBeRemoved.remove(card.getValue()); // If unselected, remove from list
							}
						}
					});
					
					// Adds card to panel
					cardPanel.add(card);
					cardPanel.revalidate();
		
					// Stores new card value to the list
					newDeckCards.add(cardNumber);
					
					// GUI calls
					btnSave.setEnabled(true);
					numberField.setText("");
					resetPanel();
					
					// Outputs console msgs
					System.out.println("Added card " + cardNumber);
					System.out.println("Current card list is: " + newDeckCards.toString());
				} catch (NumberFormatException err) {
					System.err.println("Incorrect use of gameCard constructor: param not a number");
					errLabel.setText(numberField.getText() + " is not a valid non-negative integer!");
					errLabel.setVisible(true);
				} 
				isValidDeckName();
			}
		});
		
		btnRmvSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final List<Integer> tempDeckCards = new ArrayList<Integer>(newDeckCards);
				
				for(int valueToRemove: cardsToBeRemoved){
					for(int cardValue: tempDeckCards){
						if(cardValue == valueToRemove){
							newDeckCards.remove(newDeckCards.indexOf(valueToRemove));
							break;
						}
					}
				}
				
				// Resets panel and list
				resetPanel();
				cardsToBeRemoved.clear();
				
				// Checks the amount of cards left and sets the save button to false if none is found
				if(newDeckCards.isEmpty()) btnSave.setEnabled(false);
				
				// Outputs console messages
				System.out.println("Removed cards from deck");
				System.out.println("Current card list is: " + newDeckCards.toString());
				
				//display error message
				if (newDeckCards.isEmpty()){
					errLabel.setText("Need to have at least one card in a deck.");
					errLabel.setVisible(true);
				}
				
			}
		});
		
		btnRmvAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Clears lists
				newDeckCards.clear();
				cardsToBeRemoved.clear();
				// Clears panel
				cardPanel.removeAll();
				cardPanel.revalidate();
				cardPanel.repaint();
				isValidCard();

				// Sets button status to false because there are no more cards on the new deck
				btnSave.setEnabled(false);
				
				// Outputs console messages
				System.out.println("Cleared current deck");
				System.out.println("Current card list is: " + newDeckCards.toString());

				//display error message
				if (newDeckCards.isEmpty()){
					errLabel.setText("Need to have at least one card in a deck.");
					errLabel.setVisible(true);
				}
			}
		});
		
		btnSingleSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSingleSelection.setSelected(true);
				btnMultipleSelection.setSelected(false);
				isSingleSelection = true;
				
			}
		});
		
		btnMultipleSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnMultipleSelection.setSelected(true);
				btnSingleSelection.setSelected(false);
				isSingleSelection = false;
				
			}
		});
		
		//Spring layout for lblDecks
		springLayout.putConstraint(SpringLayout.NORTH, lblDecks, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblDecks, 20, SpringLayout.WEST, this);
		
		//Spring layout for comboBoxDeckList
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, comboBoxDeckList, 0, SpringLayout.VERTICAL_CENTER, lblDecks);
		springLayout.putConstraint(SpringLayout.WEST, comboBoxDeckList, 10, SpringLayout.EAST, lblDecks);
		springLayout.putConstraint(SpringLayout.EAST, comboBoxDeckList, 100, SpringLayout.WEST, comboBoxDeckList);

		//Spring layout for lblDeckName
		springLayout.putConstraint(SpringLayout.NORTH, lblDeckName, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblDeckName, 20, SpringLayout.WEST, this);
	
		//Spring layout for textField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameField, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.EAST, lblDeckName);
		springLayout.putConstraint(SpringLayout.EAST, nameField, 175, SpringLayout.WEST, nameField);
		
		//Spring layout for btnCancel
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -GuiStandards.BOTTOM_MARGIN.getValue(), SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
	
		//Spring layout for btnDelete
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnDelete, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, btnSave);
	
		//Spring layout for cardArea
		springLayout.putConstraint(SpringLayout.NORTH, cardArea, 10, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.SOUTH, cardArea, 140, SpringLayout.NORTH, cardArea);
		springLayout.putConstraint(SpringLayout.WEST, cardArea, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, cardArea, -20, SpringLayout.EAST, this);

		//Spring layout for lblAdd
		springLayout.putConstraint(SpringLayout.NORTH, lblAdd, 10, SpringLayout.SOUTH, cardArea);
		springLayout.putConstraint(SpringLayout.WEST, lblAdd, 0, SpringLayout.WEST, cardArea);
		
		//Spring layout for numberField
		springLayout.putConstraint(SpringLayout.NORTH, numberField, 0, SpringLayout.NORTH, lblAdd);
		springLayout.putConstraint(SpringLayout.WEST, numberField, 10, SpringLayout.EAST, lblAdd);
		springLayout.putConstraint(SpringLayout.EAST, numberField, 40, SpringLayout.WEST, numberField);
		
		//Spring layout for btnAddCard
		springLayout.putConstraint(SpringLayout.NORTH, btnAddCard, 10, SpringLayout.SOUTH, lblAdd);
		springLayout.putConstraint(SpringLayout.WEST, btnAddCard, 0, SpringLayout.WEST, lblAdd);
		springLayout.putConstraint(SpringLayout.EAST, btnAddCard, 0, SpringLayout.EAST, numberField);

		//SpringLayout for lblSelection
		springLayout.putConstraint(SpringLayout.NORTH, lblSelection, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, btnAddCard);
		springLayout.putConstraint(SpringLayout.WEST, lblSelection, 0, SpringLayout.WEST, btnAddCard);
		
		//SpringLayout for selectionGroup
		springLayout.putConstraint(SpringLayout.NORTH, btnSingleSelection, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, lblSelection);
		springLayout.putConstraint(SpringLayout.WEST, btnSingleSelection, 0, SpringLayout.WEST, lblSelection);
		springLayout.putConstraint(SpringLayout.NORTH, btnMultipleSelection, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, btnSingleSelection);
		springLayout.putConstraint(SpringLayout.WEST, btnMultipleSelection, 0, SpringLayout.WEST, btnSingleSelection);
		
		//Spring layout for btnRmvSelected
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvSelected, 10, SpringLayout.SOUTH, cardArea);
		springLayout.putConstraint(SpringLayout.EAST, btnRmvSelected, 0, SpringLayout.EAST, cardArea);
		
		//Spring layout for btnRmvAll
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvAll, 10, SpringLayout.SOUTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvAll, 0, SpringLayout.WEST, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.EAST, btnRmvAll, 0, SpringLayout.EAST, btnRmvSelected);
		
		//Spring layout for btnSave
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -GuiStandards.BUTTON_OFFSET.getValue(), SpringLayout.WEST, btnCancel);
		
		setLayout(springLayout);
		
		add(lblDeckName);
		add(nameField);
		add(btnSave);
		add(cardArea);
		add(lblAdd);
		add(numberField);
		add(btnAddCard);
		add(lblSelection);
		add(btnSingleSelection);
		add(btnMultipleSelection);
		add(btnRmvAll);
		add(btnRmvSelected);
		add(btnCancel);
		add(errLabel);
	}
	private void setupButtonIcons()
	{
		try{
			Image img = ImageIO.read(getClass().getResource("cancel.png"));
			btnCancel.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("save.png"));
			btnSave.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	private void resetPanel(){
		// Sorts list
		Collections.sort(newDeckCards);
		
		// Clears panel
		cardPanel.removeAll();
		cardPanel.revalidate();
		cardPanel.repaint();
		
		// Adds sorted list
		for(final int cardValue: newDeckCards){
			final GameCard card = new GameCard(cardValue);
			card.setCancelCard(true);
			cardPanel.add(card);
			
			// Sets listener for the card
			card.addItemListener (new ItemListener(){
				public void itemStateChanged ( ItemEvent ie) {
					if (card.isSelected()){
						cardsToBeRemoved.add(card.getValue()); // If card is selected, adds its value to the list of cards to be removed
						Collections.sort(cardsToBeRemoved);
					} else {
						cardsToBeRemoved.remove(Integer.valueOf(card.getValue())); // If unselected, remove from list
					}
				}
			});
		}
	}
	
	/**
	 * Checks if the inputed deck name is valid
	 */
	private void isValidDeckName(){
		String name = nameField.getText();
		name = trim(name);
		if (name.length() != 0 && !DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
			errLabel.setVisible(false);
			btnSave.setEnabled(true);
			isValidDeck();
		}
		else {
			btnSave.setEnabled(false);
			if (DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
				errLabel.setText("Duplicate deck name.");
				errLabel.setVisible(true);
			}
			else {
				errLabel.setText("Deck must have a name.");
				errLabel.setVisible(true);
			}
		}
	}
	
	/**
	 * checks if deck is valid
	 */
	private void isValidDeck() {
		if (!newDeckCards.isEmpty()){
			btnSave.setEnabled(true);
			errLabel.setVisible(false);
			System.out.println("newDeckName is " + newDeckName);
		}
		else {
			btnSave.setEnabled(false);
			errLabel.setText("Need to have at least one card in a deck.");
			errLabel.setVisible(true);
		}
	}
	
	/**
	 *checks if the inputed card number is valid and perform actions accordingly
	 */
	private void isValidCard(){
		if (numberField.getText().length() > 0 && isInteger(numberField.getText()) && !nameField.getText().isEmpty()){
			if (Integer.parseInt(numberField.getText()) >= 0){
				btnAddCard.setEnabled(true);
				if (!DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
					btnSave.setEnabled(true);
				}
				else {
					btnSave.setEnabled(false);
				}
				//TODO notAnIntegerError.setVisible(false);
			}
			else{
				btnAddCard.setEnabled(false);
				//TODO notAnIntegerError.setVisible(true);
			}
		}
		boolean needsName = false;
		boolean allValid = true;
		
		//Name field checking
		if(!nameField.getText().isEmpty()){
			btnSave.setEnabled(true);
			errLabel.setVisible(false);
		}else{
			errLabel.setText("Deck must have a name.");
			errLabel.setVisible(true);
			needsName = true;
			allValid = false;
		}
		
		//Number field checking
		if(numberField.getText().length() > 0 && isInteger(numberField.getText()) && Integer.parseInt(numberField.getText()) >= 0){
			btnAddCard.setEnabled(true);
			errLabel.setVisible(false);
		}else if(needsName){
			btnAddCard.setEnabled(false);
			errLabel.setText("Deck must have name and card must be a non-negative integer.");
			errLabel.setVisible(true);
		}else{
			btnAddCard.setEnabled(false);
			errLabel.setText("Card must be a non-negative integer.");
			errLabel.setVisible(true);
		}
		
		//Checks that a card has been added to deck
		if (newDeckCards.isEmpty()){
			allValid = false;
		}
		
		btnSave.setEnabled(allValid);
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
	 * Trims a string by removing excess whitespace
	 * @param aString The string to be trimmed
	 * @return The newly trimmed string
	 */
	public String trim(String aString) {
		int len = aString.length();
		int st = 0;

		while ((st < len) && Character.isWhitespace(aString.charAt(st))) {
			st++;
		}
		while ((st < len) && Character.isWhitespace(aString.charAt(len - 1))) {
			len--;
		}
		return ((st > 0) || (len < aString.length())) ? aString.substring(st, len) : aString;
	}


	/**
	 * This function sets the focus on the name field.
	 */
	public void focusOnName() {
		nameField.requestFocusInWindow();
		getRootPane().setDefaultButton(btnAddCard);
	}
	
	public boolean isSingleSelection() {
		return isSingleSelection;
	}
	public JButton getBtnAddCard() {
		return btnAddCard;
	}
	public JButton getBtnRmvSelected() {
		return btnRmvSelected;
	}
	public JButton getBtnRmvAll() {
		return btnRmvAll;
	}
	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public JTextField getNameField() {
		return nameField;
	}
	public JTextField getNumberField() {
		return numberField;
	}
	public List<Integer> getNewDeckCards() {
		return newDeckCards;
	}
	
	public List<Integer> getCardsToBeRemoved() {
		return cardsToBeRemoved;
	}
	public void setCardsToBeRemoved(List<Integer> cardsToBeRemoved) {
		this.cardsToBeRemoved = cardsToBeRemoved;
	}
}
