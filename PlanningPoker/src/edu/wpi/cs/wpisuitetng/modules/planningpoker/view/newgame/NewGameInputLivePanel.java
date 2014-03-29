/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.*;

/**
 * This is the window for the user to create a planning poker session
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameInputLivePanel extends AbsNewGameInputPanel {
	
	private String reqSelection;
	private JButton importButton = new JButton("Import");
	private final JLabel nameLabel = new JLabel("Name:");
	private final JLabel descriptionLabel = new JLabel("Description:");
	private final JLabel userStoryLabel = new JLabel("User Story:");
	private JButton addNewButton = new JButton("Add New");
	private final JLabel timeLabel = new JLabel("Time");
	private final JLabel hourLabel = new JLabel ("Hours:");
	private final JLabel minuteLabel = new JLabel("Minutes:");
	private final JLabel userLabel = new JLabel("Users");
	private JButton addButton = new JButton("Add");
	private JButton deleteButton = new JButton("Delete");
	private JButton backButton  = new JButton("Back");
	private JButton nextButton = new JButton("Next");
	private String[] listValue = {"Here's a Requirement", "Here's Another"};
	private final JList<String> userList = new JList<String>();
	private JTextField nameTextField = new JTextField();
	private final JTextField userStoryTextField = new JTextField();
	private final JTextField hourTextField = new JTextField();
	private final JTextField minutesTextField = new JTextField();
	private final JTextField descriptionTextField = new JTextField();
	private AbsNewGamePanel newGameP;
	
	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputLivePanel(AbsNewGamePanel nglp) {
		newGameP = nglp;
		descriptionTextField.setColumns(10);
		setPanel();
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GetRequirementsController.getInstance().retrieveRequirements();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				List<Requirement> requirements = RequirementModel.getInstance().getRequirements();
				
				JPanel panel = new JPanel();
				Window parentWindow = SwingUtilities.windowForComponent(panel); 
				// or pass 'this' if you are inside the panel
				Frame parentFrame = null;
				if (parentWindow instanceof Frame) {
				    parentFrame = (Frame)parentWindow;
				}
				NewGameImportWindow importWindow = new NewGameImportWindow(requirements, parentFrame);
				reqSelection = importWindow.currentSelectedReq;
				newGameP.updatePanels(reqSelection);
			}
		});
	}
	/**
	 * a lot of Window Builder generated UI
	 * for setting the NewGameInputPage
	 */
	private void setPanel(){
		userList.setListData(listValue);
		SpringLayout springLayout = new SpringLayout();
				
		//Spring layout for the nameLabel
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 34, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the nameTextField
		springLayout.putConstraint(SpringLayout.WEST, nameTextField, 43, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameTextField, -40, SpringLayout.WEST, importButton);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		
		//Spring layout for the descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 18, SpringLayout.SOUTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the descriptionTextField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, descriptionTextField, 0, SpringLayout.VERTICAL_CENTER, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextField, 0, SpringLayout.WEST, nameTextField);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextField, -23, SpringLayout.EAST, this);
		
		//Spring layout for the importButton
		springLayout.putConstraint(SpringLayout.NORTH, importButton, -5, SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, importButton, -23, SpringLayout.EAST, this);
		
		//Spring layout for the userStoryLabel
		springLayout.putConstraint(SpringLayout.NORTH, userStoryLabel, 18, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, userStoryLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the UserStoryTextField
		springLayout.putConstraint(SpringLayout.NORTH, userStoryTextField, 8, SpringLayout.SOUTH, descriptionTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, userStoryTextField, -308, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userStoryTextField, 0, SpringLayout.WEST, descriptionTextField);
		springLayout.putConstraint(SpringLayout.EAST, userStoryTextField, -23, SpringLayout.EAST, this);
		
		//Spring layout for the addNewButton
		springLayout.putConstraint(SpringLayout.WEST, addNewButton, 0, SpringLayout.WEST, userStoryTextField);
		springLayout.putConstraint(SpringLayout.NORTH, addNewButton, 6, SpringLayout.SOUTH, userStoryTextField);
		
		//Spring layout for the timeLabel
		springLayout.putConstraint(SpringLayout.SOUTH, timeLabel, -237, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, timeLabel, 0, SpringLayout.WEST, userStoryLabel);
		
		//Spring layout for the hourLabel
		springLayout.putConstraint(SpringLayout.WEST, hourLabel, 0, SpringLayout.WEST, timeLabel);
		springLayout.putConstraint(SpringLayout.NORTH, hourLabel, 18, SpringLayout.SOUTH, timeLabel);
		
		//Spring layout for the hourTextField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, hourTextField, 0, SpringLayout.VERTICAL_CENTER, hourLabel);
		springLayout.putConstraint(SpringLayout.WEST, hourTextField, 0, SpringLayout.WEST, userStoryTextField);
		springLayout.putConstraint(SpringLayout.EAST, hourTextField, 40, SpringLayout.WEST, hourTextField);
		
		//Spring layout for the minuteLabel
		springLayout.putConstraint(SpringLayout.WEST, minuteLabel, 0, SpringLayout.WEST, hourLabel);
		springLayout.putConstraint(SpringLayout.NORTH, minuteLabel, 18, SpringLayout.SOUTH, hourLabel);
		
		//Spring layout for the minutesTextField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, minutesTextField, 0, SpringLayout.VERTICAL_CENTER, minuteLabel);
		springLayout.putConstraint(SpringLayout.WEST, minutesTextField, 0, SpringLayout.WEST, hourTextField);
		springLayout.putConstraint(SpringLayout.EAST, minutesTextField, 40, SpringLayout.WEST, minutesTextField);
		
		//Spring layout for the userLabel
		springLayout.putConstraint(SpringLayout.NORTH, userLabel, 18, SpringLayout.SOUTH, minuteLabel);
		springLayout.putConstraint(SpringLayout.WEST, userLabel, 0, SpringLayout.WEST, minuteLabel);
		
		//Spring layout for the userList
		springLayout.putConstraint(SpringLayout.NORTH, userList, 0, SpringLayout.NORTH, userLabel);
		springLayout.putConstraint(SpringLayout.WEST, userList, 6, SpringLayout.EAST, userLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, userList, -30, SpringLayout.NORTH, backButton);
		springLayout.putConstraint(SpringLayout.EAST, userList, 120, SpringLayout.WEST, userList);
		
		//Spring layout for the addButton
		springLayout.putConstraint(SpringLayout.WEST, addButton, 10, SpringLayout.EAST, userList);
		springLayout.putConstraint(SpringLayout.EAST, addButton, 0, SpringLayout.EAST, deleteButton);
		springLayout.putConstraint(SpringLayout.NORTH, addButton, 10, SpringLayout.NORTH, userList);
		
		//Spring layout for the deleteButton
		springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, -10, SpringLayout.SOUTH, userList);
		springLayout.putConstraint(SpringLayout.WEST, deleteButton, 0, SpringLayout.WEST, addButton);
		
		//Spring layout for the backButton
		springLayout.putConstraint(SpringLayout.WEST, backButton, 23, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this);
		
		//Spring layout for the nextButton
		springLayout.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, backButton);
		springLayout.putConstraint(SpringLayout.EAST, nextButton, -23, SpringLayout.EAST, this);
		
		setLayout(springLayout);
		
		nameTextField.setColumns(10);
		userStoryTextField.setColumns(10);
		minutesTextField.setColumns(10);
		hourTextField.setColumns(10);
		
		add(importButton);
		add(nameLabel);
		add(descriptionLabel);
		add(userStoryLabel);
		add(addNewButton);
		add(timeLabel);
		add(hourLabel);
		add(minuteLabel);
		add(userLabel);
		add(addButton);
		add(deleteButton);
		add(backButton);
		add(nextButton);
		add(userList);
		add(nameTextField);
		add(userStoryTextField);
		add(hourTextField);
		add(minutesTextField);
		add(descriptionTextField);
	}
}
