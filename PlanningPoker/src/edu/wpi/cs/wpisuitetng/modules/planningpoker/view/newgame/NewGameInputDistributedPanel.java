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
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is the window for the user to create a planning poker session
 *
 */
@SuppressWarnings("serial")
public class NewGameInputDistributedPanel extends AbsNewGameInputPanel {
	
	private int reqSelection; 
	private String[] yearString = {"2014", "2015","2016","2017","2018","2019","2020","2021","2022"}; //TODO
	private String[] monthString = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" }; //TODO
	private String[] ampmString = {"AM", "PM"};
	private Calendar currentDate;
	private int[] deckCards;
	private int[] defaultDeck = {1, 1, 2, 3, 5, 8, 13, -1};
	private int deadlineDay = 1;
	private int deadlineMonth = 1;
	private int deadlineYear = 2014;
	private final JLabel nameLabel = new JLabel("Game Name:");
	private final JLabel timeLabel = new JLabel ("Deadline Time:");
	//private final JLabel descriptionLabel = new JLabel("Game Description:");
	private final JLabel requirementLabel = new JLabel("Game Requirements");
	//private final JLabel userStoryLabel = new JLabel("User Story:");
	//private JButton addNewButton = new JButton("Add New");
	private final JLabel deadlineLabel = new JLabel("Deadline");
	private final JLabel deckLabel = new JLabel("Choose a deck");
	private JButton activateButton = new JButton("Activate Game");
	private JTextField nameTextField = new JTextField();
	private JComboBox<String> hourComboBox = new JComboBox<String>();
	private JComboBox<String> minuteComboBox = new JComboBox<String>();
	private JComboBox<String> ampmBox = new JComboBox<String>(ampmString);
	private int hourTime = -1;
	private int minuteTime = -1;
	private NewGameDistributedPanel newGameP;
	private JLabel yearLabel = new JLabel("Year: ");
	private JLabel monthLabel = new JLabel("Month: ");
	private JLabel dayLabel = new JLabel("Day: ");
	private JComboBox<String> yearBox = new JComboBox<String>(yearString);
	private JComboBox<String> monthBox = new JComboBox<String>(monthString);
	private JComboBox<Integer> dayBox = new JComboBox<Integer>();
	private JComboBox deckBox = new JComboBox();
	private List<Integer> selectionsMade = new ArrayList<Integer>();
	private List<Requirement> requirements;
	private final JButton addNewButton = new JButton("Create New");
	private boolean isAM = true;
	public boolean isNew = true;
	private boolean editMode = false;

	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp, GameSession gameSession)
	{
		this.editMode = true;
		init(ngdp);
		System.out.println("Editing Game: "+ gameSession.getGameName());
	}
	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp) {
		init(ngdp);
	}
	
	private void init(NewGameDistributedPanel ngdp)
	{
		currentDate = Calendar.getInstance();
		//selectedDeadline = Calendar.getInstance();
		//selectedDeadline.set(2014, 1, 1);
		newGameP = ngdp;
		//descriptionTextField.setColumns(10);
		setPanel();
		
		requirements = RequirementModel.getInstance().getRequirements();
		
		//initialize dayBox to 31 days (as in January)
		for (int i=0; i<31; i++){
			dayBox.addItem(i+1);
		}
		
		//Initialize hour and minute combo boxes
		hourComboBox.addItem("");
		minuteComboBox.addItem("");
		for (int j=0; j<12; j++){
			hourComboBox.addItem(j+1 + "");
		}
		
		for (int i=0; i<60; i++){
			if (i < 10){
				minuteComboBox.addItem("0" + i);
			}
			else{
				minuteComboBox.addItem("" + i);
			}
		}
		
		deckBox.addItem("Default Deck");
		
		//Adds a documentlistener to the name text field so that way if the text is changed, the pop-up will appear if 
		//the new game tab is closed.
		nameTextField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				newGameP.isNew = false;
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				newGameP.isNew = false;
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				newGameP.isNew = false;
			}
		});
		
		deckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deckBox.getSelectedIndex() == 0){
					deckCards = defaultDeck;
				}
				newGameP.isNew = false;
			}
		});
		
		//Sets isNew to false, and sets isAM to true if AM is selected, or false if PM is selected
		ampmBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (ampmBox.getSelectedIndex() == 0){
					isAM = true;
				}
				else {
					isAM = false;
				}
				newGameP.isNew = false;
			}
		});
		
		//Sets isNew to false, and sets minuteTime to the selected minute.
		minuteComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				minuteTime = minuteComboBox.getSelectedIndex() - 1;
				newGameP.isNew = false;
			}
		});
		
		//Sets isNew to false and sets hourtime to the hour selected. It is set to 0 if 12 is selected.
		hourComboBox.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e){
				int hourIndex = hourComboBox.getSelectedIndex();
				if (hourIndex != 12 && hourIndex != 0){
					hourTime = hourComboBox.getSelectedIndex();
				}
				else{
					hourTime = 0;
				}
				newGameP.isNew = false;
			}
		});
		
		//Sets isNew to false, deadlineYear to the selected year, and rebuilds what is in the dayBox if a leap year is deselected or selected
		//and the deadline month is February
		yearBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				newGameP.isNew = false;
				int year = yearBox.getSelectedIndex();
				if ((deadlineYear == 2016 || deadlineYear == 2020) && (year + 2014 != 2016 || year + 2014 != 2020)){
					dayBox.removeAllItems();
					for (int i=0; i<28; i++){
						dayBox.addItem(i+1);
					}
				}
				deadlineYear = year + 2014;
				if ((deadlineYear == 2016 || deadlineYear == 2020) && deadlineMonth == 2){
					dayBox.removeAllItems();
					for (int j=0; j<29; j++){
						dayBox.addItem(j+1);
					}
				}
			}
			
		});
		
		//Adds an action listener to the month selection combo box, which sets isNew to false and rebuilds what is inside the 
		//dayBox based on the month selected. Also sets deadlineMonth to the chosen month.
		monthBox.addActionListener(new ActionListener() {
			int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
			@Override
			public void actionPerformed(ActionEvent e) {
				newGameP.isNew = false;
				dayBox.removeAllItems();
				int days = monthBox.getSelectedIndex();
				for (int i=0; i<daysInMonth[days]; i++){
					dayBox.addItem(i+1);
				}	
				if ((deadlineYear == 2016 || deadlineYear == 2020) && days == 1){
					dayBox.addItem(29);
				}
				deadlineMonth = days + 1;
			}	
		});
		
		//Adds an action listener to the day of month selection combo box, and sets isNew to false, and changes the deadlineDay
		dayBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				newGameP.isNew = false;
				int day = dayBox.getSelectedIndex();
				deadlineDay = day + 1;
			}
			
		});
		
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newGameP.isNew = false;
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
					reqSelection = importWindow.currentSelectedReq.getId();
					/**TODO selections made persist when switching modules, but selectionsmade does nto persist*/
//					newGameP.updatePanels(importWindow.currentSelectedReq);
					selectionsMade.add(reqSelection);
					requirements.remove(importWindow.currentSelectedReq);
				}

			}
		});
		
		removeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newGameP.isNew = false;
				Requirement removed = newGameP.getSelectedRequirement();
				if( removed != null){
					selectionsMade.remove(removed.getId());
					requirements.add(removed);
				}
			}
		});
		
		//Checks to see if all the fields are properly filled, and then sends the game object to the database if done.
		
		activateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameTextField.getText();
				Calendar selectedDeadline = Calendar.getInstance();
				currentDate = Calendar.getInstance();
				currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 1);
				selectedDeadline.set(deadlineYear, deadlineMonth, deadlineDay, getHour(hourTime), minuteTime);
				if (hourComboBox.getSelectedIndex() == 0){
					System.out.println("Please choose an hour for deadline");
				}
				else if (minuteComboBox.getSelectedIndex() == 0){
					System.out.println("Please choose a minute for deadline");
				}
				else if(!selectedDeadline.after(currentDate)){
					System.out.println("Cannot have a deadline in the past");
					JOptionPane deadlineError = new JOptionPane("Deadline error");
					JOptionPane.showMessageDialog(deadlineError, "Can not have a deadline in the past", "Deadline error", JOptionPane.ERROR_MESSAGE);
				}
				else if (selectionsMade.isEmpty()){
					System.out.println("Can not have a game with no requirements");
					JOptionPane deadlineError = new JOptionPane("No requirements selected");
					JOptionPane.showMessageDialog(deadlineError, "Can not have a game with no requirements", "No requirements selected", JOptionPane.ERROR_MESSAGE);
				}
				else if (name.length() < 1){
					JOptionPane deadlineError = new JOptionPane("No name inputted");
					JOptionPane.showMessageDialog(deadlineError, "Please input a name for the game", "Name error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					newGameP.isNew = true;
					System.out.println("Valid date selected and requirements were selected");
					Date deadlineDate = new Date(deadlineYear, deadlineMonth, deadlineDay, getHour(hourTime), minuteTime);
					GameSession newGame = new GameSession(name, 0 , GameModel.getInstance().getSize()+1, deadlineDate, selectionsMade); 
					GameModel model = GameModel.getInstance();
					AddGameController msgr = new AddGameController(model);
					msgr.sendMessage(newGame);	
					System.out.println(selectedDeadline.get(Calendar.MONTH) + "/" + selectedDeadline.get(Calendar.DAY_OF_MONTH) + "/" + selectedDeadline.get(Calendar.YEAR));
					System.out.println(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentDate.get(Calendar.YEAR));
					JOptionPane gameCreated = new JOptionPane("Game Created and Activated");
					JOptionPane.showMessageDialog(gameCreated, "Game has been created and activated", "Game created", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
	/**
	 * a lot of Window Builder generated UI
	 * for setting the NewGameInputPage
	 */
	
	private int getHour(int hour){
		if (isAM == true){
			return hour;
		}
		else {
			return hour + 12;
		}
	}
	
	private void setPanel(){
//		userList.setListData(listValue);
		SpringLayout springLayout = new SpringLayout();
				
		//Spring layout for the nameLabel
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 34, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the nameTextField
		springLayout.putConstraint(SpringLayout.WEST, nameTextField, 43, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameTextField, 200, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		
		/*Spring layout for the descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 18, SpringLayout.SOUTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the descriptionTextField
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, descriptionTextField, 0, SpringLayout.VERTICAL_CENTER, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextField, 0, SpringLayout.WEST, nameTextField);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextField, -23, SpringLayout.EAST, this);
		*/
		/*Spring layout for the userStoryLabel
		springLayout.putConstraint(SpringLayout.NORTH, userStoryLabel, 18, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, userStoryLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the UserStoryTextField
		springLayout.putConstraint(SpringLayout.NORTH, userStoryTextArea, 8, SpringLayout.SOUTH, descriptionTextField);
		springLayout.putConstraint(SpringLayout.SOUTH, userStoryTextArea, -308, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userStoryTextArea, 0, SpringLayout.WEST, descriptionTextField);
		springLayout.putConstraint(SpringLayout.EAST, userStoryTextArea, -23, SpringLayout.EAST, this);
		userStoryTextArea.setLineWrap(true);
		*/
		
		
		/*Spring layout for the addNewButton
		springLayout.putConstraint(SpringLayout.WEST, addNewButton, 0, SpringLayout.WEST, userStoryTextArea);
		springLayout.putConstraint(SpringLayout.NORTH, addNewButton, 6, SpringLayout.SOUTH, userStoryTextArea);
		*/
		
		//Spring layout for the timeLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -237, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);
		
		
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
		
		/*Spring layout for the backButton
		springLayout.putConstraint(SpringLayout.WEST, backButton, 23, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, backButton, -10, SpringLayout.SOUTH, this);
		*/
		
		//Spring layout for the activateButton
		springLayout.putConstraint(SpringLayout.SOUTH, activateButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, activateButton, 180, SpringLayout.WEST, this);
		
		//Spring layout for the requirementLabel
		springLayout.putConstraint(SpringLayout.WEST, requirementLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, requirementLabel, 50, SpringLayout.SOUTH, nameTextField);
		
		//Spring layout for the importButton
		springLayout.putConstraint(SpringLayout.SOUTH, importButton, 50, SpringLayout.SOUTH, requirementLabel);
		springLayout.putConstraint(SpringLayout.WEST, importButton, 0, SpringLayout.WEST, requirementLabel);
				
		//Spring layout for the addNewButton
		springLayout.putConstraint(SpringLayout.NORTH, addNewButton, 0, SpringLayout.NORTH, importButton);
		springLayout.putConstraint(SpringLayout.EAST, addNewButton, 150, SpringLayout.EAST, importButton);
		
		//Spring layout for the removeButton
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 0, SpringLayout.NORTH, importButton);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, 300, SpringLayout.EAST, importButton);
		
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
		springLayout.putConstraint(SpringLayout.WEST, yearBox, 50, SpringLayout.WEST, yearLabel);
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
		
		//Spring layout for timeLabel
		springLayout.putConstraint(SpringLayout.SOUTH, timeLabel, 50, SpringLayout.SOUTH, dayLabel);
		springLayout.putConstraint(SpringLayout.WEST, timeLabel, 0, SpringLayout.WEST, dayLabel);
		
		//Spring layout for hourComboBox
		springLayout.putConstraint(SpringLayout.SOUTH, hourComboBox, 0, SpringLayout.SOUTH, timeLabel);
		springLayout.putConstraint(SpringLayout.WEST, hourComboBox, 10, SpringLayout.EAST, timeLabel);
		
		//Spring layout for minuteComboBox
		springLayout.putConstraint(SpringLayout.NORTH, minuteComboBox, 0, SpringLayout.NORTH, hourComboBox);
		springLayout.putConstraint(SpringLayout.WEST, minuteComboBox, 10, SpringLayout.EAST, hourComboBox);
		
		//Spring layout for ampmBox
		springLayout.putConstraint(SpringLayout.NORTH, ampmBox, 0, SpringLayout.NORTH, minuteComboBox);
		springLayout.putConstraint(SpringLayout.WEST, ampmBox, 10, SpringLayout.EAST, minuteComboBox);
		
		
		
		setLayout(springLayout);
		
		nameTextField.setColumns(10);
		//userStoryTextArea.setColumns(10);

		add(importButton);
		add(removeButton);
		add(nameLabel);
		//add(descriptionLabel);
		//add(userStoryLabel);
		//add(addNewButton);
		add(deadlineLabel);
//		add(userLabel);
//		add(addButton);
//		add(deleteButton);
		//add(backButton);
		add(activateButton);
		add(addNewButton);
//		add(userList);
		add(nameTextField);
		//add(userStoryTextArea);
		//add(descriptionTextField);
		add(yearBox);
		add(monthBox);
		add(dayBox);
		add(yearLabel);
		add(monthLabel);
		add(dayLabel);
		add(deckLabel);
		add(deckBox);
		add(requirementLabel);
		add(timeLabel);
		add(hourComboBox);
		add(minuteComboBox);
		add(ampmBox);
	}
	
	//Added by Ruofan.
	public void setGameName(String gameName){
		nameTextField.setText(gameName);
	}
}
