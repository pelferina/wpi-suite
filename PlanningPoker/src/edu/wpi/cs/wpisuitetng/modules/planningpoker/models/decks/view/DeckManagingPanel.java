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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.JTextFieldLimit;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;

/**
 * The DeckBuildingPanel class
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings({"serial"})
public class DeckManagingPanel extends JPanel implements Refreshable{

	private boolean isSingleSelection = true; 
	private boolean newDeckFlag = true;

	private final JButton btnAddCard = new JButton("Add Card");
	private final JButton btnRmvSelected = new JButton("Remove Selected");
	private final JButton btnRmvAll = new JButton("Remove all");
	private final JButton btnSave = new JButton("Save deck");
	private final JButton btnDelete = new JButton("Delete deck");
	private final JButton btnCancel = new JButton("Cancel");
	private JButton btnClose = new JButton("Close");
	private final JRadioButton btnSingleSelection = new JRadioButton("Single Selection");
	private final JRadioButton btnMultipleSelection = new JRadioButton("Multiple Selection");
	private final ButtonGroup selectionGroup = new ButtonGroup();
	private final JComboBox<String> decksComboBox = new JComboBox<String>();
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
	private final List<Integer> cardsToBeRemoved = new ArrayList<Integer>();
	private List<Integer> currentDeck = new ArrayList<Integer>();
	private final List<Integer> deckIDs = new ArrayList<Integer>();
	private List<Deck> decks = new ArrayList<Deck>(DeckModel.getInstance().getDecks());
	private final JPanel cardPanel = new JPanel();
	private final JScrollPane cardArea = new JScrollPane(cardPanel);
	private NewGameDistributedPanel newGameDistributed;
	//	private final JLabel notAnIntegerError = new JLabel ("Please enter an non-negative integer for card value!");
	//	private final JLabel duplicateNameError = new JLabel ("This deck name already exists!");
	//	private final JLabel noCardError = new JLabel ("You need to have at least one card in the deck!");
	private int selectedDeckIndex;

	/** Constructor for a DeckPanel panel
	 * @param close Button to close this panel
	 */
	public DeckManagingPanel(JButton close){
		btnClose = close;
		setup();
	}

	private void setup(){
		GetDecksController.getInstance().addRefreshable(this);
		// Sets up elements
		setupButtonIcons();
		setupDecks();
		setupDetails();
		setupListeners();
		setupLayout();
	}

	/**
	 * This function sets up individual variable settings, fonts, and sizes.
	 */
	private void setupDetails(){


		// Set up for Radio buttons that will determine deck selection mode
		selectionGroup.add(btnSingleSelection);
		selectionGroup.add(btnMultipleSelection);
		btnSingleSelection.setSelected(true);
		btnMultipleSelection.setSelected(false);

		// Sets a consistent font for all buttons
		final Font size = new Font(btnSave.getFont().getName(), btnSave.getFont().getStyle(), 10);

		btnSave.setFont(size);
		btnSave.setSize(80, 20);
		btnSave.setEnabled(false);
		btnDelete.setFont(size);
		btnDelete.setSize(80, 20);
		btnDelete.setEnabled(false);
		btnAddCard.setFont(size);
		btnAddCard.setSize(80, 20);
		btnAddCard.setEnabled(false);
		btnRmvSelected.setFont(size);
		btnRmvSelected.setSize(80, 20);
		btnRmvSelected.setEnabled(false);
		btnRmvAll.setFont(size);
		btnRmvAll.setSize(80, 20);
		btnRmvAll.setEnabled(false);
		btnCancel.setFont(size);
		btnCancel.setSize(80, 20);
		errLabel.setVisible(false);
		//cardPanel.setMinimumSize(new Dimension(200, 13));
		cardArea.setMinimumSize(new Dimension(200, 135));

		nameField.setDocument(new JTextFieldLimit(20));
		numberField.setDocument(new JTextFieldLimit(3));
		
		// Sets up cardArea
		cardArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

	}
	/**
	 * This sets up listeners for components
	 */
	private void setupListeners(){
		//This document listener will enable the submit button when something is inputed into the estimate text field
		numberField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				validateAll();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				validateAll();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				validateAll();
			}
		});

		//This document listener will perform actions accordingly when the test in nameField is changed
		nameField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				validateAll();
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				validateAll();
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				validateAll();
			}
		});

		// All listeners and their functions
		decksComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					selectedDeckIndex = decksComboBox.getSelectedIndex();
					if(selectedDeckIndex == 0){
						newDeckFlag = true;

						//Clear lists
						newDeckCards.clear();
						cardsToBeRemoved.clear();
						currentDeck.clear();

						//GUI calls
						nameField.setText("");
						nameField.setEnabled(true);
						btnSave.setEnabled(false);
						btnAddCard.setEnabled(true);
						btnRmvSelected.setEnabled(false);
						btnRmvAll.setEnabled(false);
						btnDelete.setEnabled(false);
						cardPanel.revalidate();
						resetPanel();

					} else {
						newDeckFlag = false;

						//Clear lists
						newDeckCards.clear();
						cardsToBeRemoved.clear();
						currentDeck.clear();

						// Gets deck cards
						currentDeck = DeckModel.getInstance().getDeck(deckIDs.get(selectedDeckIndex)).getCards();

						// Populate pane with cards of the corresponding deck
						for(int value: currentDeck){
							final GameCard card = new GameCard(value);
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
							newDeckCards.add(value);
						}
						// Get Selection Mode
						if(DeckModel.getInstance().getDeck(deckIDs.get(selectedDeckIndex)).getIsSingleSelection()){
							isSingleSelection = true;
							btnSingleSelection.setSelected(true);
							btnMultipleSelection.setSelected(false);
						} else {
							isSingleSelection = false;
							btnSingleSelection.setSelected(false);
							btnMultipleSelection.setSelected(true);
						}
						
						
						// GUI calls
						nameField.setEnabled(true);
						nameField.setText(decksComboBox.getItemAt(selectedDeckIndex));
						btnAddCard.setEnabled(true);
						btnRmvSelected.setEnabled(true);
						btnRmvAll.setEnabled(true);
						btnSave.setEnabled(true);
						btnDelete.setEnabled(true);
						numberField.setText("");
						resetPanel();
					}
				}
			}
		});

		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newDeckName = nameField.getText();
				final Deck newDeck = new Deck (newDeckName, newDeckCards); //Updated deck with new name and new cards
				newDeck.setIsSingleSelection(isSingleSelection); //Updates selection mode
				System.out.println("new deck cards: " + newDeckCards);
				if(newDeckFlag){ // Store new deck
					System.out.println("storing new deck");
					AddDeckController.getInstance().addDeck(newDeck); 
					setupDecks();
				} else { // Update selected deck
					System.out.println("Editing deck");
					final Deck thisDeck = DeckModel.getInstance().getDeck(deckIDs.get(selectedDeckIndex));
					thisDeck.setIsDeleted(true); // delete old version
					UpdateDeckController.getInstance().updateDeck(thisDeck);

					newDeck.setId(-1); // indicate that it needs new id
					AddDeckController.getInstance().addDeck(newDeck); // add new version to the list
					setupDecks();
				}

				// Clears nameField
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

				// Moves selection back to initial index
				decksComboBox.setSelectedIndex(0);
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
					btnRmvSelected.setEnabled(true);
					btnRmvAll.setEnabled(true);
					numberField.setText("");
					resetPanel();

				} catch (NumberFormatException err) {
					System.err.println("Incorrect use of gameCard constructor: param not a number");
					errLabel.setText(numberField.getText() + " is not a valid non-negative integer!");
					errLabel.setVisible(true);
				} 
				validateAll();
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
				btnSave.setEnabled(true);

				// Checks the amount of cards left and sets the save button to false if none is found
				if(newDeckCards.isEmpty()){
					btnSave.setEnabled(false);
					btnRmvAll.setEnabled(false);
					btnRmvSelected.setEnabled(false);
				}

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
				validateAll();

				// Sets button status to false because there are no more cards on the new deck
				btnSave.setEnabled(false);
				btnRmvSelected.setEnabled(false);
				btnRmvAll.setEnabled(false);

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
				btnSave.setEnabled(true);
				btnSingleSelection.setSelected(true);
				btnMultipleSelection.setSelected(false);
				isSingleSelection = true;

			}
		});

		btnMultipleSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSave.setEnabled(true);
				btnMultipleSelection.setSelected(true);
				btnSingleSelection.setSelected(false);
				isSingleSelection = false;

			}
		});

		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO popup message
				final Deck thisDeck = DeckModel.getInstance().getDecks().get(deckIDs.get(selectedDeckIndex));
				thisDeck.setIsDeleted(true);
				UpdateDeckController.getInstance().updateDeck(thisDeck);

				// Clears lists
				newDeckCards.clear();
				cardsToBeRemoved.clear();

				// Clears panel
				cardPanel.removeAll();
				cardPanel.revalidate();
				cardPanel.repaint();
				validateAll();

				// Sets button status to false because there are no more cards on the new deck
				btnSave.setEnabled(false);
				btnAddCard.setEnabled(false);
				btnRmvSelected.setEnabled(false);
				btnRmvAll.setEnabled(false);
				btnDelete.setEnabled(false);
				nameField.setEnabled(false);

				// Changes combo box selection to the first deck of the list (new deck)
				decksComboBox.setSelectedIndex(0);
				setupDecks();

				// TODO temp msg that prints out the deck was deleted
			}
		});

		btnClose.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO close tab
			}
		});
	}
	private void setupLayout(){
		//Spring layout for lblDecks
		springLayout.putConstraint(SpringLayout.NORTH, lblDecks, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblDecks, 20, SpringLayout.WEST, this);

		//Spring layout for decksComboBox
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, decksComboBox, 0, SpringLayout.VERTICAL_CENTER, lblDecks);
		springLayout.putConstraint(SpringLayout.WEST, decksComboBox, 10, SpringLayout.EAST, lblDecks);
		springLayout.putConstraint(SpringLayout.EAST, decksComboBox, 150, SpringLayout.WEST, decksComboBox);

		//Spring layout for lblDeckName
		springLayout.putConstraint(SpringLayout.NORTH, lblDeckName, 30, SpringLayout.NORTH, lblDecks);
		springLayout.putConstraint(SpringLayout.WEST, lblDeckName, 0, SpringLayout.WEST, lblDecks);

		//Spring layout for textField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameField, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, nameField, 10, SpringLayout.EAST, lblDeckName);
		springLayout.putConstraint(SpringLayout.EAST, nameField, 175, SpringLayout.WEST, nameField);

		//Spring layout for btnSave
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -20, SpringLayout.EAST, this);

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
		springLayout.putConstraint(SpringLayout.NORTH, lblSelection, 0, SpringLayout.NORTH, numberField);
		springLayout.putConstraint(SpringLayout.WEST, lblSelection, 10, SpringLayout.EAST, numberField);

		//SpringLayout for selectionGroup
		springLayout.putConstraint(SpringLayout.NORTH, btnSingleSelection, 10, SpringLayout.SOUTH, lblSelection);
		springLayout.putConstraint(SpringLayout.WEST, btnSingleSelection, 0, SpringLayout.WEST, lblSelection);
		springLayout.putConstraint(SpringLayout.NORTH, btnMultipleSelection, 5, SpringLayout.SOUTH, btnSingleSelection);
		springLayout.putConstraint(SpringLayout.WEST, btnMultipleSelection, 0, SpringLayout.WEST, btnSingleSelection);

		//Spring layout for btnRmvSelected
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvSelected, 0, SpringLayout.NORTH, numberField);
		springLayout.putConstraint(SpringLayout.EAST, btnRmvSelected, 0, SpringLayout.EAST, cardArea);

		//Spring layout for btnRmvAll
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnRmvAll, 0, SpringLayout.VERTICAL_CENTER, btnAddCard);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvAll, 0, SpringLayout.WEST, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.EAST, btnRmvAll, 0, SpringLayout.EAST, btnRmvSelected);

		//Spring layout for btnDelete
		springLayout.putConstraint(SpringLayout.SOUTH, btnDelete, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 20, SpringLayout.WEST, this);

		//Spring layout for btnClose
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnClose, -20, SpringLayout.EAST, this);

		setLayout(springLayout);

		add(lblDecks);
		add(decksComboBox);
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
		add(btnRmvSelected);
		add(btnRmvAll);
		add(btnDelete);
		add(btnClose);
	}


	/**
	 * Sets up the deck list by first clearing, adding a new deck instance and by populating the list with another function
	 */
	private void setupDecks(){
		decksComboBox.removeAllItems();
		deckIDs.clear();
		deckIDs.add(0);
		decksComboBox.addItem("Create New Deck ...");
		addDecksToDeckComboBox();
	}

	/**
	 * Add all available deck selections to the combo box
	 */
	private void addDecksToDeckComboBox()
	{
		decks = new ArrayList<Deck>(DeckModel.getInstance().getDecks());
		for (Deck d: decks){
			// Ignores default deck and deleted decks
			if(d.getId() != 0 && !d.getIsDeleted()){
				decksComboBox.addItem(d.getName());
				deckIDs.add(d.getId());
			}
		}
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
	 * This function validates all of the inputs and sets buttons accordingly.
	 */
	private void validateAll(){
		btnAddCard.setEnabled(true);
		btnSave.setEnabled(true);
		validateDeckName();
		validateNotEmpty();
		validateCard();
	}

	/**
	 * Checks if the inputed deck name is valid
	 */
	private void validateDeckName(){
		String name = nameField.getText();
		name = trim(name);
		if (name.length() == 0 || DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
			btnSave.setEnabled(false);
			errLabel.setText("Invalid deck name.");
			errLabel.setVisible(true);
		}
	}

	/**
	 *checks if the inputed card number is valid and perform actions accordingly
	 */
	private void validateCard(){
		boolean valid = false;

		if (numberField.getText().length() > 0 && isInteger(numberField.getText()) && !nameField.getText().isEmpty()){
			if (Integer.parseInt(numberField.getText()) >= 0){
				valid = true;
			}
		}
		btnAddCard.setEnabled(valid);
	}

	private void validateNotEmpty(){
		//Checks that a card has been added to deck
		if (newDeckCards.isEmpty()){
			btnSave.setEnabled(false);
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
	 * sets the focus to the name field
	 */
	public void focusOnName() {
		nameField.requestFocusInWindow();
		getRootPane().setDefaultButton(btnAddCard);
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


	@Override
	public void refreshRequirements() {}

	@Override
	public void refreshGames() {}

	/**
	 * Function called by Refreshable periodically
	 * refreshDecks will update the deck list in decksComboBox whenever changes are observed
	 */
	@Override
	public void refreshDecks() {
		if (decks.size() != DeckModel.getInstance().getDecks().size()){
			setupDecks();
		}
	}
}
