/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.DeckCard;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameCard;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private List<GameCard> cardList = new ArrayList<GameCard>();
	
	/** Constructor for a DeckPanel panel
	 */
	public DeckBuildingPanel(){
		
		
		// Sets a consistent font for all buttons
		Font size = new Font(btnSave.getFont().getName(), btnSave.getFont().getStyle(), 10);
		
		btnSave.setFont(size);
		btnSave.setSize(80, 20);
		btnDelete.setFont(size);
		btnDelete.setSize(80, 20);
		btnAddCard.setFont(size);
		btnAddCard.setSize(80, 20);
		btnRmvSelected.setFont(size);
		btnRmvSelected.setSize(80, 20);
		btnRmvAll.setFont(size);
		btnRmvAll.setSize(80, 20);
		
		// Sets up cardArea
		cardArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	
		// All listeners and their functions
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// TODO: Action corresponding to this
			}
		});
		
		btnAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameCard card;
				try {
					card = new GameCard(Integer.parseInt(numberField.getText()));
				}catch(NumberFormatException e){
					System.err.println("Incorrect use of gameCard constructor: param not a number");
					errLabel.setText(numberField.getText()+ " is not a valid non-negative integer!");
				} 
				cardPanel.add(card);
				cardPanel.revalidate();
				//TODO: Sort cards by value
			}
		});
		
		btnRmvSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
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
}










