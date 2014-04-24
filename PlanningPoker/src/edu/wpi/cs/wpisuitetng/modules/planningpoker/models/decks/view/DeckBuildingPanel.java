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

import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings({"serial"})
public class DeckBuildingPanel extends JPanel {
	private JTextField textField;
	SpringLayout springLayout = new SpringLayout();
	JLabel lblDeckName = new JLabel("Deck Name:");
	JButton btnAddCard = new JButton("Add Card");
	JButton btnRmvSelected = new JButton("Remove Selected");
	JButton btnRmvAll = new JButton("Remove all");
	
	/** Constructor for a DeckPanel panel
	 */
	public DeckBuildingPanel(){
		
		textField = new JTextField();
		textField.setColumns(10);
		
		Font size = new Font(btnRmvSelected.getFont().getName(), btnRmvSelected.getFont().getStyle(), 10);
		btnRmvSelected.setFont(size);
		btnRmvSelected.setSize(80, 20);
		btnRmvAll.setFont(size);
		btnRmvAll.setSize(80, 20);
		
		btnAddCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		//Spring layout for textField
		springLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.EAST, lblDeckName);
		
		//Spring layout for lblDeckName
		springLayout.putConstraint(SpringLayout.NORTH, lblDeckName, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblDeckName, 20, SpringLayout.WEST, this);
	
		//Spring layout for btnAddCard
		springLayout.putConstraint(SpringLayout.NORTH, btnAddCard, 0, SpringLayout.NORTH, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, btnAddCard, 30, SpringLayout.EAST, textField);
		
		//Spring layout for btnRmvSelected
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvSelected, 40, SpringLayout.SOUTH, lblDeckName);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvSelected, 0, SpringLayout.WEST, lblDeckName);
		
		//Spring layout for btnRmvAll
		springLayout.putConstraint(SpringLayout.NORTH, btnRmvAll, 5, SpringLayout.SOUTH, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.WEST, btnRmvAll, 0, SpringLayout.WEST, btnRmvSelected);
		springLayout.putConstraint(SpringLayout.EAST, btnRmvAll, 0, SpringLayout.EAST, btnRmvSelected);
		
		setLayout(springLayout);
		
		add(textField);
		add(lblDeckName);
		add(btnAddCard);
		add(btnRmvAll);
		add(btnRmvSelected);
	}
}









