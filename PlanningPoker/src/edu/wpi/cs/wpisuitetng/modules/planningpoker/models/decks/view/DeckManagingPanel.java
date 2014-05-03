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
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;

/**
 * The DeckBuildingPanel class
 * @author Cosmic Latte
 * @version 6
 */
@SuppressWarnings({"serial"})
public class DeckManagingPanel extends JPanel implements Refreshable{

	private boolean isSingleSelection = true; 
	private boolean newDeckFlag = false;
	private boolean changeFlag = false;
	
	private JButton btnAddCard = new JButton("Add Card");
	private JButton btnRmvSelected = new JButton("Remove Selected");
	private JButton btnRmvAll = new JButton("Remove all");
	private JButton btnSave = new JButton("Save deck");
	private JButton btnDelete = new JButton("Delete deck");
	private JButton btnCancel = new JButton("Cancel");
	private JButton btnClose = new JButton("Close");
	private JRadioButton btnSingleSelection = new JRadioButton("Single Selection");
	private JRadioButton btnMultipleSelection = new JRadioButton("Multiple Selection");
	private ButtonGroup selectionGroup = new ButtonGroup();
	private JComboBox<String> decksComboBox = new JComboBox<String>();
	private JLabel lblDeckName = new JLabel("Deck Name:");
	private JLabel lblDecks = new JLabel("Decks:");
	private JLabel errLabel = new JLabel("");
	private JLabel lblAdd = new JLabel("Card Value:");
	private JLabel lblSelection = new JLabel("Selection Mode:");
	private JTextField nameField = new JTextField();
	private JTextField numberField = new JTextField();
	private SpringLayout springLayout = new SpringLayout();
	private String newDeckName;
	private List<Integer> newDeckCards = new ArrayList<Integer>();
	private List<Integer> cardsToBeRemoved = new ArrayList<Integer>();
	private List<Integer> currentDeck = new ArrayList<Integer>();
	private List<Deck> decks = new ArrayList<Deck>(DeckModel.getInstance().getDecks());
	private JPanel cardPanel = new JPanel();
	private JScrollPane cardArea = new JScrollPane(cardPanel);
	private NewGameDistributedPanel newGameDistributed;
//	private final JLabel notAnIntegerError = new JLabel ("Please enter an non-negative integer for card value!");
//	private final JLabel duplicateNameError = new JLabel ("This deck name already exists!");
//	private final JLabel noCardError = new JLabel ("You need to have at least one card in the deck!");
	private int selectedDeckIndex;

	/** Constructor for a DeckPanel panel
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
		btnAddCard.setFont(size);
		btnAddCard.setSize(80, 20);
		btnAddCard.setEnabled(false);
		btnRmvSelected.setFont(size);
		btnRmvSelected.setSize(80, 20);
		btnRmvAll.setFont(size);
		btnRmvAll.setSize(80, 20);
		btnCancel.setFont(size);
		btnCancel.setSize(80,20);
		errLabel.setVisible(false);
		//cardPanel.setMinimumSize(new Dimension(200, 13));
		cardArea.setMinimumSize(new Dimension(200, 135));
		
		// Sets up cardArea
		cardArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
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
							changeFlag = false;
							if(selectedDeckIndex == 0){
								newDeckFlag = true;
								
								//Clear lists
								newDeckCards.clear();
								cardsToBeRemoved.clear();
								currentDeck.clear();
								
								//GUI calls
								nameField.setText("");
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
								currentDeck = DeckModel.getInstance().getDeck(selectedDeckIndex).getCards();
								
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
								
								// GUI calls
								btnAddCard.setEnabled(true);
								btnRmvSelected.setEnabled(true);
								btnRmvAll.setEnabled(true);
								btnSave.setEnabled(true);
								btnDelete.setEnabled(true);
								nameField.setText(decksComboBox.getItemAt(selectedDeckIndex));
								numberField.setText("");
								resetPanel();
							}
						}
					}
				});
		
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newDeckName = nameField.getText();
				Deck newDeck = new Deck (newDeckName, newDeckCards); //Updated deck with new name and new cards
				newDeck.setSingleSelection(isSingleSelection); //Updates selection mode
				if(newDeckFlag){ // Store new deck
					AddDeckController.getInstance().addDeck(newDeck); 
				} else { // Update selected deck
					AddDeckController.getInstance().addDeck(newDeck);//TODO MODIFY THIS TO UPDATE A DECKK INSTANCE
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
				changeFlag = true;
				btnSave.setEnabled(true);
				btnRmvSelected.setEnabled(true);
				btnRmvAll.setEnabled(true);
				final GameCard card;
				int cardNumber;
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
					errLabel.setText(numberField.getText()+ " is not a valid non-negative integer!");
					errLabel.setVisible(true);
				} 
				validateAll();
			}
		});
		
		btnRmvSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				changeFlag = true;
				btnSave.setEnabled(true);
				List<Integer> tempDeckCards = new ArrayList<Integer>(newDeckCards);
				
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
				if(newDeckCards.isEmpty()){
					btnSave.setEnabled(false);
					btnRmvAll.setEnabled(false);
					btnRmvSelected.setEnabled(false);
				}
				
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
				changeFlag = true;
				btnSave.setEnabled(true);
				btnSingleSelection.setSelected(true);
				btnMultipleSelection.setSelected(false);
				isSingleSelection = true;
				
			}
		});
		
		btnMultipleSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFlag = true;
				btnSave.setEnabled(true);
				btnMultipleSelection.setSelected(true);
				btnSingleSelection.setSelected(false);
				isSingleSelection = false;
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Pop up message asking to confirm deletion of a deck
				// TODO deletion of a deck from database
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
		springLayout.putConstraint(SpringLayout.EAST, nameField, 100, SpringLayout.WEST, nameField);
		
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
		springLayout.putConstraint(SpringLayout.EAST, numberField, 100, SpringLayout.WEST, numberField);
		
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
	
	
	
	private void setupDecks(){
		decksComboBox.removeAllItems();
		decksComboBox.addItem("Create New Deck ...");
		addDecksToDeckComboBox();
	}
	
	/**
	 * Add all available deck selections to the combo box
	 *
	 */
	private void addDecksToDeckComboBox()
	{
		decks = new ArrayList<Deck>(DeckModel.getInstance().getDecks());
		for (Deck d: decks){
			if(!d.getName().equals("Default Deck"))
				decksComboBox.addItem(d.getName());
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
		if (!(nameField.getText().length() > 0)){
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

	public void setFocusOnName() {
		nameField.requestFocusInWindow();
		getRootPane().setDefaultButton(btnAddCard);
	}

	@Override
	public void refreshRequirements() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshGames() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshDecks() {
		if (decks.size() != DeckModel.getInstance().getDecks().size()){
			setupDecks();
		}
	}
}
