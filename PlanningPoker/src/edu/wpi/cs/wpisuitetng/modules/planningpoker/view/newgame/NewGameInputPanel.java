/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameInputPanel extends JPanel {
	
	private JButton importButton = new JButton("Import");
	private final JLabel nameLabel = new JLabel("Name:");
	private JTextField nameInput = new JTextField();
	private final JLabel descriptionLabel = new JLabel("Description:");
	private JTextField descriptionInput = new JTextField();
	private final JLabel userStoryLabel = new JLabel("User Story:");
	private JTextField userStoryInput = new JTextField();
	private JButton addNewButton = new JButton("Add New");
	private final JLabel timeLabel = new JLabel("Time:");
	private final JLabel hourLabel = new JLabel ("Hr.");
	private JTextField hourInput = new JTextField();
	private final JLabel minuteLabel = new JLabel("Min.");
	private JTextField minuteInput = new JTextField();
	private final JLabel userLabel = new JLabel("Users:");
	private JButton addButton = new JButton("Add");
	private JButton deleteButton = new JButton("Delete");
	private JButton backButton  = new JButton("Back");
	private JButton nextButton = new JButton("Next");
	
	public NewGameInputPanel() {
		setPanel();
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				NewGameImportWindow importWindow = new NewGameImportWindow();
			}
		});
	}
	
	private void setPanel(){
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, minuteInput, -3, SpringLayout.NORTH, minuteLabel);
		springLayout.putConstraint(SpringLayout.WEST, minuteInput, 0, SpringLayout.WEST, nameInput);
		springLayout.putConstraint(SpringLayout.EAST, minuteInput, 0, SpringLayout.EAST, nameInput);
		springLayout.putConstraint(SpringLayout.NORTH, hourInput, -3, SpringLayout.NORTH, hourLabel);
		springLayout.putConstraint(SpringLayout.WEST, hourInput, 0, SpringLayout.WEST, nameInput);
		springLayout.putConstraint(SpringLayout.EAST, hourInput, 0, SpringLayout.EAST, nameInput);
		springLayout.putConstraint(SpringLayout.EAST, userStoryInput, 0, SpringLayout.EAST, nameInput);
		springLayout.putConstraint(SpringLayout.WEST, userStoryInput, 67, SpringLayout.EAST, userStoryLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionInput, 6, SpringLayout.SOUTH, nameInput);
		springLayout.putConstraint(SpringLayout.WEST, descriptionInput, 63, SpringLayout.EAST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionInput, -300, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, nameInput, 0, SpringLayout.EAST, descriptionInput);
		springLayout.putConstraint(SpringLayout.NORTH, userStoryInput, -3, SpringLayout.NORTH, userStoryLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, nextButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userStoryLabel, 35, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, backButton, 0, SpringLayout.NORTH, nextButton);
		springLayout.putConstraint(SpringLayout.EAST, backButton, -47, SpringLayout.WEST, nextButton);
		springLayout.putConstraint(SpringLayout.EAST, nextButton, 0, SpringLayout.EAST, importButton);
		springLayout.putConstraint(SpringLayout.NORTH, minuteLabel, 24, SpringLayout.SOUTH, hourLabel);
		springLayout.putConstraint(SpringLayout.NORTH, hourLabel, 18, SpringLayout.SOUTH, timeLabel);
		springLayout.putConstraint(SpringLayout.NORTH, addNewButton, 26, SpringLayout.SOUTH, userStoryLabel);
		springLayout.putConstraint(SpringLayout.NORTH, userStoryLabel, 19, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 34, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, nameInput, -3, SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, nameInput, 160, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, userLabel, 27, SpringLayout.SOUTH, minuteLabel);
		springLayout.putConstraint(SpringLayout.WEST, userLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, minuteLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, hourLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, timeLabel, 33, SpringLayout.SOUTH, addNewButton);
		springLayout.putConstraint(SpringLayout.WEST, timeLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, addNewButton, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 15, SpringLayout.SOUTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.NORTH, importButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, importButton, -23, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 37, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, deleteButton, 184, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, addButton, 184, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, addButton, -6, SpringLayout.NORTH, deleteButton);
		springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, -43, SpringLayout.SOUTH, this);
		setLayout(springLayout);
		add(importButton);
		add(nameLabel);
		add(nameInput);
		add(descriptionLabel);
		add(descriptionInput);
		add(userStoryLabel);
		add(userStoryInput);
		add(addNewButton);
		add(timeLabel);
		add(hourLabel);
		add(hourInput);
		add(minuteLabel);
		add(minuteInput);
		add(userLabel);
		add(addButton);
		add(deleteButton);
		add(backButton);
		add(nextButton);
	}

}
