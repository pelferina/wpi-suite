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

import javax.swing.JPanel;



import java.awt.Font;







import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * The DeckBuildingPanel class
 * @author Cosmic Latte
 * @version 6
 */
@SuppressWarnings({"serial"})
public class DeckBuildingPanel extends JPanel {

	private final JButton btnAddCard = new JButton("Add Card");
	private final JButton btnRmvSelected = new JButton("Remove Selected");
	private final JButton btnRmvAll = new JButton("Remove all");
	private final JButton btnSave = new JButton("Save deck");
	private final JButton btnDelete = new JButton("Delete deck");
	private final JComboBox<String> comboBoxDeckList = new JComboBox<String>();
	private final JLabel lblDeckName = new JLabel("Deck Name:");
	private final JLabel lblDecks = new JLabel("Decks:");
	private final JPanel cardPanel = new JPanel();
	private final JScrollPane cardArea = new JScrollPane(cardPanel);
	private final JTextField textField = new JTextField();
	private final SpringLayout springLayout = new SpringLayout();
	
	/** Constructor for a DeckPanel panel
	 */
	public DeckBuildingPanel(){
		
		// Sets up Combo Box for decks
		setUpDeckList();
		
		// Sets a consistent font for all buttons
		final Font size = new Font(btnSave.getFont().getName(), btnSave.getFont().getStyle(), 10);
		
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
				// TODO: Action corresponding to this
			}
		});
		
		btnRmvSelected.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// TODO: Action corresponding to this
			}
		});
		
		btnRmvAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// TODO: Action corresponding to this
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
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, textField, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.EAST, lblDeckName);
		springLayout.putConstraint(SpringLayout.EAST, textField, 100, SpringLayout.WEST, textField);
		
		//Spring layout for btnSave
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnSave, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -20, SpringLayout.EAST, this);
	
		//Spring layout for btnDelete
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnDelete, 0, SpringLayout.VERTICAL_CENTER, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, btnSave);
	
		//Spring layout for cardArea
		springLayout.putConstraint(SpringLayout.NORTH, cardArea, 10, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, cardArea, 130, SpringLayout.NORTH, cardArea);
		springLayout.putConstraint(SpringLayout.WEST, cardArea, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, cardArea, -20, SpringLayout.EAST, this);

		//Spring layout for btnRmvSelected
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvSelected, 10, SpringLayout.SOUTH, cardArea);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnRmvSelected, 0, SpringLayout.HORIZONTAL_CENTER, this);
		
		//Spring layout for btnAddCard
		springLayout.putConstraint(SpringLayout.NORTH, btnAddCard, 0, SpringLayout.NORTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.EAST, btnAddCard, -10, SpringLayout.WEST, btnRmvSelected);
		
		//Spring layout for btnRmvAll
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvAll, 0, SpringLayout.NORTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvAll, 10, SpringLayout.EAST, btnRmvSelected);
		
		setLayout(springLayout);
		
		add(lblDeckName);
		add(textField);
		add(btnSave);
		add(cardArea);
		add(btnAddCard);
		add(btnRmvAll);
		add(btnRmvSelected);
	}
	
	private void setUpDeckList(){
		//TODO: Populate Combo Box with all existing Deck Names
	}
	
}










