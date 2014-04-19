/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * Initial panel for creating a new game
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class NewGameInitialPanel extends JPanel{
	private final JLabel gameNameLabel = new JLabel("Game Name:");
	private final JTextField gameNameInput = new JTextField();
	private final JRadioButton liveButton = new JRadioButton("Live");
	private final JRadioButton distributedButton = new JRadioButton("Distributed");
	private final ButtonGroup gameType  = new ButtonGroup();
	
	public NewGameInitialPanel(){
		setPanel();

		gameNameLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				gameNameLabel.setText("");
			}
		});
	}
	
	private void setPanel(){
		gameType.add(liveButton);
		gameType.add(distributedButton);
		
		final SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, gameNameLabel, -30, SpringLayout.NORTH, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, gameNameInput, -3, SpringLayout.NORTH, gameNameLabel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, gameNameInput, 0, SpringLayout.WEST, distributedButton);
		sl_mainPanel.putConstraint(SpringLayout.EAST, gameNameInput, -104, SpringLayout.EAST, this);
		sl_mainPanel.putConstraint(SpringLayout.EAST, gameNameLabel, 0, SpringLayout.EAST, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, liveButton, -133, SpringLayout.SOUTH, this);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, distributedButton, 0, SpringLayout.NORTH, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, distributedButton, 23, SpringLayout.EAST, liveButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, liveButton, 151, SpringLayout.WEST, this);
		setLayout(sl_mainPanel);
		
		add(gameNameLabel);
		add(gameNameInput);
		add(liveButton);
		add(distributedButton);
	}
	
	public JTextField getTxtNewGame() {
		return gameNameInput;
	}
	
	/**
	 * This method returns either 1 or 2, for live or distributed game.
	 * @return returns the type of game as an integer
	 */
	public int getGameType(){
		if (liveButton.isSelected()){
			return 1; // flag for live game
		}
		return 2; // flag for distributed game
	}
	
	// Added by Ruofan
	public String getGameName(){
		return gameNameInput.getText();
	}

}
