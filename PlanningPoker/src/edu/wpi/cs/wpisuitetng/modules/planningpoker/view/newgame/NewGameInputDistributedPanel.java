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
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
//import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.*;

/**
 * This is the window for the user to create a planning poker session
 *
 */
@SuppressWarnings("serial")
public class NewGameInputDistributedPanel extends AbsNewGameInputPanel {
	
	private Requirement reqSelection; 
	private String[] yearString = {"2014", "2015","2016","2017","2018","2019","2020","2021","2022","2030"}; //TODO
	private String[] monthString = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" }; //TODO
	private Calendar selectedDeadline;
	private Calendar currentDate;
	private int[] deckCards;
	private int[] defaultDeck = {1, 1, 2, 3, 5, 8, 13, -1};
	private final JLabel nameLabel = new JLabel("Name:");
	private final JLabel descriptionLabel = new JLabel("Description:");
	private final JLabel userStoryLabel = new JLabel("User Story:");
	private JButton addNewButton = new JButton("Add New");
	private final JLabel deadlineLabel = new JLabel("Deadline");
	private final JLabel deckLabel = new JLabel("Choose a deck");
	private JButton backButton  = new JButton("Back");
	private JButton nextButton = new JButton("Next");
	private JTextField nameTextField = new JTextField();
	private final JTextArea userStoryTextArea = new JTextArea();
	private final JTextField descriptionTextField = new JTextField();
	private AbsNewGamePanel newGameP;
	private JLabel yearLabel = new JLabel("Year: ");
	private JLabel monthLabel = new JLabel("Month: ");
	private JLabel dayLabel = new JLabel("Day: ");
	private JComboBox<String> yearBox = new JComboBox<String>(yearString);
	private JComboBox<String> monthBox = new JComboBox<String>(monthString);
	private JComboBox<Integer> dayBox = new JComboBox<Integer>();
	private JComboBox deckBox = new JComboBox();
	private List<Requirement> selectionsMade = new ArrayList<Requirement>();
	private List<Requirement> requirements;

	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputDistributedPanel(AbsNewGamePanel nglp) {
		currentDate = Calendar.getInstance();
		selectedDeadline = Calendar.getInstance();
		selectedDeadline.set(2014, 1, 1);
		newGameP = nglp;
		descriptionTextField.setColumns(10);
		setPanel();
		
		requirements = RequirementModel.getInstance().getRequirements();
		
		//initialize dayBox to 31 days (as in January)
		for (int i=0; i<31; i++){
			dayBox.addItem(i+1);
		}	
		deckBox.addItem("Default Deck");
		
		deckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deckBox.getSelectedIndex() == 0){
					deckCards = defaultDeck;
				}
			}
		});
		
		yearBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int year = yearBox.getSelectedIndex();
				selectedDeadline.set(Calendar.YEAR, year + 2014);
			}
			
		});
		
		monthBox.addActionListener(new ActionListener() {
			int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
			@Override
			public void actionPerformed(ActionEvent e) {
				dayBox.removeAllItems();
				int days = monthBox.getSelectedIndex();
				for (int i=0; i<daysInMonth[days]; i++){
					dayBox.addItem(i+1);
				}	
				selectedDeadline.set(Calendar.MONTH, days + 1);
			}	
		});
		
		dayBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int day = dayBox.getSelectedIndex();
				selectedDeadline.set(Calendar.DAY_OF_MONTH, day + 1);
			}
			
		});
		
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				JPanel panel = new JPanel();
				Window parentWindow = SwingUtilities.windowForComponent(panel); 
				// or pass 'this' if you are inside the panel
				Frame parentFrame = null;
				if (parentWindow instanceof Frame) {
				    parentFrame = (Frame)parentWindow;
				}
				NewGameImportWindow importWindow = new NewGameImportWindow(requirements, parentFrame);
				if (importWindow.currentSelectedReq !=null && !selectionsMade.contains(importWindow.currentSelectedReq))
				{
					reqSelection = importWindow.currentSelectedReq;
					/**TODO selections made persist when switching modules, but selectionsmade does nto persist*/
					newGameP.updatePanels(reqSelection);
					selectionsMade.add(reqSelection);
					requirements.remove(reqSelection);
				}

			}
		});
		
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Requirement removed = newGameP.getSelectedRequirement();
				if( removed != null){
					selectionsMade.remove(removed);
					requirements.add(removed);
				}
			}
		});
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentDate = Calendar.getInstance();
				if(!selectedDeadline.after(currentDate)){
					System.out.println("Cannot have a deadline in the past");
					JOptionPane deadlineError = new JOptionPane("Deadline error");
					JOptionPane.showMessageDialog(deadlineError, "Can not have a deadline in the past", "Deadline error", JOptionPane.ERROR_MESSAGE);
				}
				else if (selectionsMade.isEmpty()){
					System.out.println("Can not have a game with no requirements");
					JOptionPane deadlineError = new JOptionPane("No requirements selected");
					JOptionPane.showMessageDialog(deadlineError, "Can not have a game with no requirements", "No requirements selected", JOptionPane.ERROR_MESSAGE);
				}
				else{
					System.out.println("Valid date selected");
				}
			}
		});
	}
	/**
	 * a lot of Window Builder generated UI
	 * for setting the NewGameInputPage
	 */
	private void setPanel(){
//		userList.setListData(listValue);
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
		
		//Spring layout for the removeButton
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, -30, SpringLayout.NORTH, importButton);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, 0, SpringLayout.EAST, this);
		
		//Spring layout for the userStoryLabel
		springLayout.putConstraint(SpringLayout.NORTH, userStoryLabel, 18, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, userStoryLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the UserStoryTextField
		springLayout.putConstraint(SpringLayout.NORTH, userStoryTextArea, 8, SpringLayout.SOUTH, descriptionTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, userStoryTextArea, -308, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userStoryTextArea, 0, SpringLayout.WEST, descriptionTextField);
		springLayout.putConstraint(SpringLayout.EAST, userStoryTextArea, -23, SpringLayout.EAST, this);
		userStoryTextArea.setLineWrap(true);
		
		//Spring layout for the addNewButton
		springLayout.putConstraint(SpringLayout.WEST, addNewButton, 0, SpringLayout.WEST, userStoryTextArea);
		springLayout.putConstraint(SpringLayout.NORTH, addNewButton, 6, SpringLayout.SOUTH, userStoryTextArea);
		
		//Spring layout for the timeLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -237, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, userStoryLabel);
		
//		//Spring layout for the userList
//		springLayout.putConstraint(SpringLayout.NORTH, userList, 0, SpringLayout.NORTH, userLabel);
//		springLayout.putConstraint(SpringLayout.WEST, userList, 6, SpringLayout.EAST, userLabel);
//		springLayout.putConstraint(SpringLayout.SOUTH, userList, -30, SpringLayout.NORTH, backButton);
//		springLayout.putConstraint(SpringLayout.EAST, userList, 120, SpringLayout.WEST, userList);
//		
//		//Spring layout for the addButton
//		springLayout.putConstraint(SpringLayout.WEST, addButton, 10, SpringLayout.EAST, userList);
//		springLayout.putConstraint(SpringLayout.EAST, addButton, 0, SpringLayout.EAST, deleteButton);
//		springLayout.putConstraint(SpringLayout.NORTH, addButton, 10, SpringLayout.NORTH, userList);
//		
//		//Spring layout for the deleteButton
//		springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, -10, SpringLayout.SOUTH, userList);
//		springLayout.putConstraint(SpringLayout.WEST, deleteButton, 0, SpringLayout.WEST, addButton);
		
		//Spring layout for the backButton
		springLayout.putConstraint(SpringLayout.WEST, backButton, 23, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this);
		
		//Spring layout for the nextButton
		springLayout.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, backButton);
		springLayout.putConstraint(SpringLayout.EAST, nextButton, -23, SpringLayout.EAST, this);
		
		//Spring layout for the yearLabel
		springLayout.putConstraint(SpringLayout.WEST, yearLabel, 0, SpringLayout.WEST, deadlineLabel);
		springLayout.putConstraint(SpringLayout.NORTH, yearLabel, 18, SpringLayout.SOUTH, deadlineLabel);
		
		//Spring layout for the monthLabel
		springLayout.putConstraint(SpringLayout.WEST, monthLabel, 0, SpringLayout.WEST, yearLabel);
		springLayout.putConstraint(SpringLayout.NORTH, monthLabel, 18, SpringLayout.SOUTH, yearLabel);
		
		//Spring layout for the monthLabel
		springLayout.putConstraint(SpringLayout.WEST, dayLabel, 0, SpringLayout.WEST, monthLabel);
		springLayout.putConstraint(SpringLayout.NORTH, dayLabel, 18, SpringLayout.SOUTH, monthLabel);
		
		//Spring layout for the yearBox
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, yearBox, 0, SpringLayout.VERTICAL_CENTER, yearLabel);
		springLayout.putConstraint(SpringLayout.WEST, yearBox, 0, SpringLayout.WEST, userStoryTextArea);
		springLayout.putConstraint(SpringLayout.EAST, yearBox, 100, SpringLayout.WEST, yearBox);
		
		//Spring layout for the monthBox
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, monthBox, 0, SpringLayout.VERTICAL_CENTER, monthLabel);
		springLayout.putConstraint(SpringLayout.WEST, monthBox, 0, SpringLayout.WEST, yearBox);
		springLayout.putConstraint(SpringLayout.EAST, monthBox, 100, SpringLayout.WEST, monthBox);
		
		//Spring layout for the dayBox
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, dayBox, 0, SpringLayout.VERTICAL_CENTER, dayLabel);
		springLayout.putConstraint(SpringLayout.WEST, dayBox, 0, SpringLayout.WEST, monthBox);
		springLayout.putConstraint(SpringLayout.EAST, dayBox, 100, SpringLayout.WEST, dayBox);
		
		//Spring layout for the deckBox
		springLayout.putConstraint(SpringLayout.EAST, deckBox, 150, SpringLayout.EAST, yearBox);
		springLayout.putConstraint(SpringLayout.SOUTH, deckBox, 0, SpringLayout.SOUTH, yearBox);
		
		//Spring layout for the deckLabel
		springLayout.putConstraint(SpringLayout.EAST, deckLabel, 250, SpringLayout.EAST, deadlineLabel);
		springLayout.putConstraint(SpringLayout.NORTH, deckLabel, 0, SpringLayout.NORTH, deadlineLabel);		
		
		setLayout(springLayout);
		
		nameTextField.setColumns(10);
		userStoryTextArea.setColumns(10);

		
		add(importButton);
		add(removeButton);
		add(nameLabel);
		add(descriptionLabel);
		add(userStoryLabel);
		add(addNewButton);
		add(deadlineLabel);
//		add(userLabel);
//		add(addButton);
//		add(deleteButton);
		add(backButton);
		add(nextButton);
//		add(userList);
		add(nameTextField);
		add(userStoryTextArea);
		add(descriptionTextField);
		add(yearBox);
		add(monthBox);
		add(dayBox);
		add(yearLabel);
		add(monthLabel);
		add(dayLabel);
		add(deckLabel);
		add(deckBox);
	}
	
}
