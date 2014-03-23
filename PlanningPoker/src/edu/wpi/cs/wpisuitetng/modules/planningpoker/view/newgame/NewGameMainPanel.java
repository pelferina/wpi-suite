/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameMainPanel extends JPanel{
	//private JPanel mainPanel;
	private JLabel gameNameLabel;
	private JTextField gameNameInput;
	private JRadioButton liveButton;
	private JRadioButton distributedButton;
	private ButtonGroup gameType;
	private JButton next;
	
	public NewGameMainPanel(){
		//mainPanel = new JPanel();
		gameNameLabel = new JLabel("Game Name: ");
		gameNameInput = new JTextField();
		liveButton = new JRadioButton("Live");
		distributedButton = new JRadioButton("Distributed");
		gameType = new ButtonGroup();
		gameType.add(liveButton);
		gameType.add(distributedButton);
		next = new JButton("Next");
		setPanel();
	}
	
	private void setPanel(){
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, gameNameLabel, -30, SpringLayout.NORTH, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, gameNameInput, -3, SpringLayout.NORTH, gameNameLabel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, gameNameInput, 0, SpringLayout.WEST, distributedButton);
		sl_mainPanel.putConstraint(SpringLayout.EAST, gameNameInput, -104, SpringLayout.EAST, this);
		sl_mainPanel.putConstraint(SpringLayout.EAST, gameNameLabel, 0, SpringLayout.EAST, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, next, 41, SpringLayout.SOUTH, distributedButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, next, 197, SpringLayout.WEST, this);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, liveButton, -133, SpringLayout.SOUTH, this);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, distributedButton, 0, SpringLayout.NORTH, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, distributedButton, 23, SpringLayout.EAST, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, liveButton, 151, SpringLayout.WEST, this);
		setLayout(sl_mainPanel);
		add(gameNameLabel);
		add(gameNameInput);
		add(liveButton);
		add(distributedButton);
		add(next);
	}
	

}
