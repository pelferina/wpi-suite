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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameMainPanel extends JPanel{
	private JButton nextInitialButton = new JButton("Next");
	public NewGameInitialPanel initialPanel = new NewGameInitialPanel();
	private NewGameLivePanel livePanel;
	private NewGameDistributedPanel distributedPanel;
	private final GameModel lstGameModel;

	public NewGameMainPanel(GameModel gameModel){
		livePanel = new NewGameLivePanel(gameModel);

//		distributedPanel = new NewGameDistributedPanel(); //TODO check whether it should be in edit mode or not
		setupInitialPanel();
		nextInitialButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				removeAll();
				GetRequirementsController.getInstance().retrieveRequirements(); /**TODO this is sketchy as **** - Rafi Haynes*/
				 
				//Edited by R
				String gameName = initialPanel.getGameName();
				if (initialPanel.getGameType() == 1)
					setupLivePanel(gameName);
				else
					setupDistributedPanel(gameName);
				
			}
		});
		
		lstGameModel = gameModel;
		nextInitialButton.addActionListener(new AddGameController(lstGameModel, this));
		
	}

	private void setupInitialPanel(){
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout for nextButton
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nextInitialButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nextInitialButton, -10, SpringLayout.SOUTH, this);
		
		//Spring layout for initialPanel
		springLayout.putConstraint(SpringLayout.NORTH, initialPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, initialPanel, 47, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, initialPanel, -6, SpringLayout.NORTH, nextInitialButton);
		springLayout.putConstraint(SpringLayout.EAST, initialPanel, -50, SpringLayout.EAST, this);
		
		setLayout(springLayout);
		
		add(nextInitialButton);
		add(initialPanel);
	}
	
	private void setupLivePanel(String gameName)
	{
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout for livePanel
		springLayout.putConstraint(SpringLayout.NORTH, livePanel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, livePanel, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, livePanel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, livePanel, 0, SpringLayout.EAST, this);
		
		setLayout(springLayout);
		
		livePanel.setGameName(gameName);
		add(livePanel);
	}
	
	private void setupDistributedPanel(String gameName) {
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout for livePanel
		springLayout.putConstraint(SpringLayout.NORTH, distributedPanel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, distributedPanel, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, distributedPanel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, distributedPanel, 0, SpringLayout.EAST, this);
		
		setLayout(springLayout);
		
		distributedPanel.setGameName(gameName);
		add(distributedPanel);
	}
}
