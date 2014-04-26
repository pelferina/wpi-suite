/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view;

import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings({"serial"})
public class DeckBuildingPanel extends JPanel {

	private JButton btnAddCard = new JButton("Add Card");
	private JButton btnRmvSelected = new JButton("Remove Selected");
	private JButton btnRmvAll = new JButton("Remove all");
	private JButton btnSave = new JButton("Save deck");
	private JButton btnDelete = new JButton("Delete deck");
	private JComboBox<String> comboBoxDeckList = new JComboBox<String>();
	private JLabel lblDeckName = new JLabel("Deck Name:");
	private JLabel lblDecks = new JLabel("Decks:");
	private JLabel errLabel = new JLabel("");
	private JPanel cardPanel = new JPanel();
	private JScrollPane cardArea = new JScrollPane(cardPanel);
	private JTextField nameField = new JTextField();
	private JTextField numberField = new JTextField();
	private SpringLayout springLayout = new SpringLayout();
	private String newDeckName;
	private List<Integer> newDeckCards = new ArrayList<Integer>();
	private List<GameCard> cardList = new ArrayList<GameCard>();

	/** Constructor for a DeckPanel panel
	 */
	public DeckBuildingPanel(){
		
		
		// Sets a consistent font for all buttons
		Font size = new Font(btnSave.getFont().getName(), btnSave.getFont().getStyle(), 10);
		
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
		
		// Sets up cardArea
		cardArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
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
				// TODO: Action corresponding to this
				newDeckName = nameField.getText();
				DeckModel.getInstance().addDeck(new Deck (newDeckName, newDeckCards));
				System.out.println("added Deck " + newDeckName + " with cards: " + newDeckCards.toString());
				System.out.println("Current DeckModel size is " + DeckModel.getInstance().getSize());
				nameField.setText("");
				newDeckCards.clear();
				//TODO close tab
			}
		});
		
		btnAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameCard card;
				int cardNumber;
				try {
					cardNumber = Integer.parseInt(numberField.getText());
					card = new GameCard(cardNumber);
					cardPanel.add(card);
					cardPanel.revalidate();
		
					// Stores new card value to the list
					newDeckCards.add(cardNumber);
					btnSave.setEnabled(true);
					numberField.setText("");
					resetPanel();
					System.out.println("Added card " + cardNumber);
					System.out.println("Current card list is: " + newDeckCards.toString());
				} catch (NumberFormatException err) {
					System.err.println("Incorrect use of gameCard constructor: param not a number");
					errLabel.setText(numberField.getText()+ " is not a valid non-negative integer!");
				} 
				
			}
		});
		
		btnRmvSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// TODO: Action corresponding to this
			}
		});
		
		btnRmvAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Clears lists
				newDeckCards.clear();
				cardList.clear();
				
				// Clears panel
				cardPanel.removeAll();
				cardPanel.revalidate();
				cardPanel.repaint();
			}
		});
		
		comboBoxDeckList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// TODO: Action corresponding to this
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
		springLayout.putConstraint(SpringLayout.EAST, nameField, 100, SpringLayout.WEST, nameField);
		
		//Spring layout for btnSave
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnSave, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -20, SpringLayout.EAST, this);
	
		//Spring layout for btnDelete
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnDelete, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, btnSave);
	
		//Spring layout for cardArea
		springLayout.putConstraint(SpringLayout.NORTH, cardArea, 10, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.SOUTH, cardArea, 140, SpringLayout.NORTH, cardArea);
		springLayout.putConstraint(SpringLayout.WEST, cardArea, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, cardArea, -20, SpringLayout.EAST, this);

		//Spring layout for btnRmvSelected
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvSelected, 10, SpringLayout.SOUTH, cardArea);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnRmvSelected, 0, SpringLayout.HORIZONTAL_CENTER, this);
		
		//Spring layout for btnAddCard
		springLayout.putConstraint(SpringLayout.NORTH, btnAddCard, 0, SpringLayout.NORTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.EAST, btnAddCard, -10, SpringLayout.WEST, btnRmvSelected);
		
		//Spring layout for numberField
		springLayout.putConstraint(SpringLayout.NORTH, numberField, 0, SpringLayout.NORTH, btnAddCard);
		springLayout.putConstraint(SpringLayout.EAST, numberField, -10, SpringLayout.WEST, btnAddCard);
		springLayout.putConstraint(SpringLayout.WEST, numberField, -100, SpringLayout.EAST, numberField);
		//Spring layout for btnRmvAll
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvAll, 0, SpringLayout.NORTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvAll, 10, SpringLayout.EAST, btnRmvSelected);
		
		setLayout(springLayout);
		
		add(lblDeckName);
		add(nameField);
		add(btnSave);
		add(cardArea);
		add(btnAddCard);
		add(btnRmvAll);
		add(btnRmvSelected);
		add(numberField);
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
			GameCard card = new GameCard(cardValue);
			cardPanel.add(card);
		}
	}
	
	/**
	 * Checks if the inputed deck name is valid
	 */
	private void isValidDeckName(){
		if (nameField.getText().length() > 0 && !DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
			if (!newDeckCards.isEmpty()){
				btnSave.setEnabled(true);
				System.out.println("newDeckName is " + newDeckName);
			}
			else {
				btnSave.setEnabled(false);
			}
		}
		else {
			btnSave.setEnabled(false);
			if (DeckModel.getInstance().isDuplicateDeck(nameField.getText())){
				//TODO: add errMSG: Duplicate Deck Name
			}
			else {
				//TODO: add errMSG: Invalid Deck Name
			}
		}
	}
	
	
	/**
	 *checks if the inputed card number is valid and perform actions accordingly
	 */
	private void isValidCard(){
		if (numberField.getText().length() > 0 && isInteger(numberField.getText()) && !nameField.getText().isEmpty()){
			if (Integer.parseInt(numberField.getText()) >= 0){
				btnAddCard.setEnabled(true);
				btnSave.setEnabled(true);
				//TODO notAnIntegerError.setVisible(false);
			}
			else{
				btnAddCard.setEnabled(false);
				//TODO notAnIntegerError.setVisible(true);
			}
		}
		else{
			btnAddCard.setEnabled(false);
			//TODO notAnIntegerError.setVisible(true);
		}
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
}










