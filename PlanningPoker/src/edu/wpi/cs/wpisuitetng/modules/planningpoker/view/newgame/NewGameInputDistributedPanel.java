/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Cosmic Latte
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import net.sourceforge.jdatepicker.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is the window for the user to create a planning poker session
 *
 */
@SuppressWarnings("serial")
public class NewGameInputDistributedPanel extends JPanel {
private Calendar currentDate; // TODO get rid of this, switch to GregorianCalendar data type
	
	private NewGameDistributedPanel newGameP;
	
	private int hourTime = -1;
	private int minuteTime = -1; 
	private int deadlineDay = 1;
	private int deadlineMonth = 1;
	private int deadlineYear = 2014;
	private int[] deckCards;
	private int[] defaultDeck = {1, 1, 2, 3, 5, 8, 13, -1};
	private boolean isAM = true;
	public boolean isNew = true;
	private final String[] yearString = {"2014", "2015","2016","2017","2018","2019","2020","2021","2022"}; 
	private final String[] monthString = {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" }; 
	private final String[] ampmString = {"AM", "PM"};
	private List<Integer> selectionsMade = new ArrayList<Integer>();
	
	private JButton saveButton;
	private final JButton addNewButton = new JButton("Create New");
	private JComboBox deckBox = new JComboBox(); // Implement Decks
	private JComboBox<Integer> dayBox = new JComboBox<Integer>();
	private JComboBox<String> hourComboBox = new JComboBox<String>();
	private JComboBox<String> minuteComboBox = new JComboBox<String>();
	private JComboBox<String> ampmBox = new JComboBox<String>(ampmString);
	private JComboBox<String> yearBox = new JComboBox<String>(yearString);
	private JComboBox<String> monthBox = new JComboBox<String>(monthString);
	private final JLabel nameLabel = new JLabel("Game Name*:");
	private final JLabel timeLabel = new JLabel ("Deadline Time:");
	private final JLabel descriptionLabel = new JLabel("Description:");
	private final JLabel deadlineLabel = new JLabel("Deadline:");
	private final JLabel deckLabel = new JLabel("Chosen deck:");
	private JLabel yearLabel = new JLabel("Year: ");
	private JLabel monthLabel = new JLabel("Month: ");
	private JLabel dayLabel = new JLabel("Day: ");
	private JTextField nameTextField = new JTextField();
	private JTextArea descTextArea = new JTextArea();
	private boolean editMode = false;
	private GameSession gameSession = null;
	private final JLabel hourError = new JLabel("Select an hour for deadline");
	private final JLabel minuteError = new JLabel("Select a minute for deadline");
	private final JLabel deadlineError = new JLabel("Can not have a deadline in the past");
	private final JLabel nameError = new JLabel("Enter a name for the game");
	private final JLabel reqError = new JLabel("Can not have a game with no requirements");
	private final JButton activateButton = new JButton("Activate Game");
	Timer canActivateChecker;
	
	// Date Picker declarations
	private JPanel frame = new JPanel();
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel = new JDatePanelImpl(model);
	private JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	
	/**
	 * 
	 * @param ngdp, the new game distributed panel that it was added from
	 * @param gameSession, the game to be edited
	 */
	//Edit Tab constructor
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp, GameSession gameSession)
	{
		this.gameSession = gameSession;
		this.editMode = true;
		saveButton = new JButton("Update Game");
		init(ngdp);
		System.out.println("Editing Game: "+ gameSession.getGameName());
	}
	
	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp) {
		saveButton = new JButton("Create Game");
		init(ngdp);
	}
	
	//Initializes the NewGameDistributedPanel
	
	private void init(NewGameDistributedPanel ngdp)
	{
		// This timer is used to check if the game can be activated. If it can be activated, which means that all the necessary fields are filled,
		// then the activate button will become enabled
		canActivateChecker = new Timer(100, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (canActivate()){
					activateButton.setEnabled(true);
				}
				else {
					activateButton.setEnabled(false);
				}
			}
		});
		canActivateChecker.start();
		saveButton.setEnabled(false);
		activateButton.setEnabled(false);
		currentDate = Calendar.getInstance();

		newGameP = ngdp;

		setPanel();
				
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
		
		//Initializes the deck combo box
		deckBox.addItem("No Deck");
		deckBox.addItem("Default Deck");
		
		//This is run if the game is opened in edit mode
		if(editMode)
		{
			DateFormat dayFormat = new SimpleDateFormat("dd");
			DateFormat monthFormat = new SimpleDateFormat("mm");
			
			DateFormat yearFormat = new SimpleDateFormat("yy");

			//Gets the deadline from the game
			if (gameSession.getEndDate() != null){
				int year_index = gameSession.getEndDate().getYear();
			
				int day_index = gameSession.getEndDate().getMonth() - 1;
			
				int month_index = gameSession.getEndDate().getDay();
				datePicker.getModel().setDate(year_index, month_index, day_index);
			
			//Sets the hour and minute combo boxes to the hour and minute in the game's deadline
			
				if (gameSession.getEndDate().getHours() > 11){
					hourComboBox.setSelectedIndex(gameSession.getEndDate().getHours() - 12);
				}
				else if (gameSession.getEndDate().getHours() == 0){
					hourComboBox.setSelectedIndex(12);
				}
				else {
					hourComboBox.setSelectedIndex(gameSession.getEndDate().getHours());
				}
				minuteComboBox.setSelectedIndex(gameSession.getEndDate().getMinutes()+1);
			}
			//Puts the name of the game into the name text field, it can not be edited
			
			nameTextField.setText(gameSession.getGameName());
			nameTextField.setEditable(false);
			descTextArea.setText(gameSession.getGameDescription());
			
		}
		//Adds a documentlistener to the name text field so that way if the text is changed, the pop-up will appear if 
		//the new game tab is closed.
		nameTextField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				newGameP.isNew = false;
				if (nameTextField.getText().length() != 0){
					saveButton.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				newGameP.isNew = false;
				if (nameTextField.getText().length() != 0){
					saveButton.setEnabled(true);
				}
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				newGameP.isNew = false;
				if (nameTextField.getText().length() != 0){
					saveButton.setEnabled(true);
				}
			}
		});

		//Adds a documentlistener to the description text area so that way if the text is changed in the description text area, the pop-up will 
		// appear is the new game tab is close
				descTextArea.getDocument().addDocumentListener(new DocumentListener(){
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

		//Adds an action listener to the deck box so that if the deck combo box to set isNew to false
		//if it is changed.
				
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

	//This button is for saving a game, and to do that, only the name is required
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = nameTextField.getText();
				GameModel model = GameModel.getInstance();
				GameSession newGame = new GameSession(name, new String(), 0, model.getSize() + 1, new Date(), new ArrayList<Integer>()); 
				AddGameController msgr = new AddGameController(model);
				msgr.sendGame(newGame);	
			}
		});
		
	//Checks to see if all the fields are properly filled, and then sends the game object to the database if done.

		activateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				hourError.setVisible(false);
				minuteError.setVisible(false);
				deadlineError.setVisible(false);
				nameError.setVisible(false);
				reqError.setVisible(false);
				String name = nameTextField.getText();
				String description = descTextArea.getText();
				Calendar selectedDeadline = Calendar.getInstance();
				currentDate = Calendar.getInstance();
				if (datePicker.getModel().isSelected() == false){
					datePicker.getModel().setDate(currentDate.get(Calendar.YEAR), 
							currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH) - 1);
				}
				currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 1);
				deadlineYear = datePicker.getModel().getYear();
				deadlineMonth = datePicker.getModel().getMonth() + 1;
				deadlineDay = datePicker.getModel().getDay();
				selectedDeadline.set(deadlineYear, deadlineMonth, deadlineDay, getHour(hourTime), minuteTime);
				List<Requirement> reqsSelected = newGameP.getSelected();
				for (int i=0; i<reqsSelected.size(); i++){
					selectionsMade.add(reqsSelected.get(i).getId());
				}
				//Displays the hour error game if no hour was chosen
				if (hourComboBox.getSelectedIndex() == 0){
					hourError.setVisible(true);
				}
				//Displays the minute error game if no minute was chosen
				else if (minuteComboBox.getSelectedIndex() == 0){
					minuteError.setVisible(true);
				}
				//Displays deadline in the past error if the chosen deadline is before the current date
				else if(!selectedDeadline.after(currentDate)){
					deadlineError.setVisible(true);
				}
				//Displays the cannot have a game without requirements error if no requirements were chosen
				else if (selectionsMade.isEmpty()){
					reqError.setVisible(true);
				}
				//Displays the no name inputed error
				else if (name.length() < 1){
					nameError.setVisible(true);
				}
				else if (description.length() < 1){
					nameError.setVisible(true);
				}
				//Sends the game to the database
				else{
					newGameP.isNew = true;
					Date deadlineDate = new Date(deadlineYear - 1900, deadlineMonth - 1, deadlineDay, getHour(hourTime), minuteTime);
					GameSession newGame = new GameSession(name, description, 0 , GameModel.getInstance().getSize()+1, deadlineDate, selectionsMade); 
					GameModel model = GameModel.getInstance();
					AddGameController msgr = new AddGameController(model);
					msgr.sendGame(newGame);	
					final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.PUT); // PUT == create
					request.setBody("endGame" + newGame.getGameName());
					request.send(); // send the request
					JOptionPane gameCreated = new JOptionPane("Game Created and Activated");
					JOptionPane.showMessageDialog(gameCreated, "Game has been created and activated", "Game created", JOptionPane.INFORMATION_MESSAGE);
					newGameP.close.doClick();
				}
			}
		});
	}
	
	//Sets the hour based on the AM or PM combo box selection
	
	private int getHour(int hour){
		if (isAM == true){
			return hour;
		}
		else {
			return hour + 12;
		}
	}
	
	//This function checks to see if everything necessary for activating a game has been entered by the user.
	private boolean canActivate(){
		if (nameInputted() && descInputted() && hourSelected() && minuteSelected() && hasReqs() && hasDeadline()){
			return true;
		}
		return false;
	}
	
	//Returns true if the name text field has text
	private boolean nameInputted(){
		if (nameTextField.getText().length() > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns true if the description text field has text
	private boolean descInputted(){
		if (descTextArea.getText().length() > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns true if an hour has been selected
	private boolean hourSelected(){
		if (hourComboBox.getSelectedIndex() != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns true if a minute has been selected
	private boolean minuteSelected(){
		if (minuteComboBox.getSelectedIndex() != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Returns true if requirements have been added to the game
	private boolean hasReqs(){
		List<Requirement> reqsSelected = newGameP.getSelected();
		if (reqsSelected.size() > 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	//Returns true if a deadline is in the future
	private boolean hasDeadline(){
		Calendar currentDate = Calendar.getInstance();
		int year = datePicker.getModel().getYear();
		int month = datePicker.getModel().getMonth();
		int day = datePicker.getModel().getDay();
		if (!hourSelected() && !minuteSelected()){
			return false;
		}
		int hour = getHour(hourComboBox.getSelectedIndex());
		int minute = minuteComboBox.getSelectedIndex();
		Calendar deadline = Calendar.getInstance();
		deadline.set(year, month, day, hour, minute);
		if (deadline.after(currentDate)){
			return true;
		}
		else {
			return false;
		}
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
		springLayout.putConstraint(SpringLayout.WEST, nameTextField, 100, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameTextField, 200, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		//Spring layout for the descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 30, SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the descTextArea
		descTextArea.setRows(3);
		descTextArea.setLineWrap(true);
		springLayout.putConstraint(SpringLayout.NORTH, descTextArea, 10, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descTextArea, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descTextArea, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descTextArea, -15, SpringLayout.NORTH, deadlineLabel);
		
		//Spring layout for the deadlineLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -250, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 30, SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the descTextArea
		descTextArea.setRows(3);
		descTextArea.setLineWrap(true);
		springLayout.putConstraint(SpringLayout.NORTH, descTextArea, 10, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descTextArea, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descTextArea, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descTextArea, -15, SpringLayout.NORTH, deadlineLabel);
		
		//Spring layout for the deadlineLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -250, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the activateButton
		springLayout.putConstraint(SpringLayout.SOUTH, saveButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, saveButton, 180, SpringLayout.WEST, this);
		
		//Spring layout for the deadlineError
		springLayout.putConstraint(SpringLayout.WEST, deadlineError, 0, SpringLayout.WEST, saveButton);
		springLayout.putConstraint(SpringLayout.NORTH, deadlineError, -20, SpringLayout.NORTH, saveButton);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		deadlineError.setVisible(false);
		
		//Spring layout for the minuteError
		springLayout.putConstraint(SpringLayout.WEST, minuteError, 0, SpringLayout.WEST, deadlineError);
		springLayout.putConstraint(SpringLayout.NORTH, minuteError, 0, SpringLayout.NORTH, deadlineError);
		minuteError.setVisible(false);
		
		//Spring layout for the hourError
		springLayout.putConstraint(SpringLayout.WEST, hourError, 0, SpringLayout.WEST, deadlineError);
		springLayout.putConstraint(SpringLayout.NORTH, hourError, 0, SpringLayout.NORTH, deadlineError);
		hourError.setVisible(false);
		
		//Spring layout for the nameError
		springLayout.putConstraint(SpringLayout.WEST, nameError, 0, SpringLayout.WEST, deadlineError);
		springLayout.putConstraint(SpringLayout.NORTH, nameError, 0, SpringLayout.NORTH, deadlineError);
		nameError.setVisible(false);
		
		//Spring layout for the reqError
		springLayout.putConstraint(SpringLayout.WEST, reqError, 0, SpringLayout.WEST, deadlineError);
		springLayout.putConstraint(SpringLayout.NORTH, reqError, 0, SpringLayout.NORTH, deadlineError);
		reqError.setVisible(false);
		
		//Spring layout for the yearLabel
		springLayout.putConstraint(SpringLayout.WEST, yearLabel, 0, SpringLayout.WEST, deadlineLabel);
		springLayout.putConstraint(SpringLayout.NORTH, yearLabel, 18, SpringLayout.SOUTH, deadlineLabel);
		
		//Spring layout for the monthLabel
		springLayout.putConstraint(SpringLayout.WEST, monthLabel, 0, SpringLayout.WEST, yearLabel);
		springLayout.putConstraint(SpringLayout.NORTH, monthLabel, 18, SpringLayout.SOUTH, yearLabel);
		
		//Spring layout for the dayLabel
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

		//Spring layout for the datePicker
		springLayout.putConstraint(SpringLayout.WEST, datePicker, 150, SpringLayout.WEST, deadlineLabel);
		springLayout.putConstraint(SpringLayout.NORTH, datePicker, 0, SpringLayout.NORTH, deadlineLabel);
		
		//Spring layout for timeLabel
    	springLayout.putConstraint(SpringLayout.NORTH, timeLabel, 25, SpringLayout.SOUTH, deadlineLabel);
        springLayout.putConstraint(SpringLayout.WEST, timeLabel, 0, SpringLayout.WEST, nameLabel);
		    
		//Spring layout for hourComboBox
        springLayout.putConstraint(SpringLayout.NORTH, hourComboBox, 0, SpringLayout.NORTH, timeLabel);
        springLayout.putConstraint(SpringLayout.WEST, hourComboBox, 150, SpringLayout.WEST, timeLabel);
        
        //Spring layout for minuteComboBox
        springLayout.putConstraint(SpringLayout.NORTH, minuteComboBox, 0, SpringLayout.NORTH, hourComboBox);
        springLayout.putConstraint(SpringLayout.WEST, minuteComboBox, 10, SpringLayout.EAST, hourComboBox);
        
        //Spring layout for ampmBox
        springLayout.putConstraint(SpringLayout.NORTH, ampmBox, 0, SpringLayout.NORTH, minuteComboBox);
        springLayout.putConstraint(SpringLayout.WEST, ampmBox, 10, SpringLayout.EAST, minuteComboBox);
		
		//Spring layout for the deckLabel
		springLayout.putConstraint(SpringLayout.NORTH, deckLabel, 25, SpringLayout.SOUTH, timeLabel);
		springLayout.putConstraint(SpringLayout.WEST, deckLabel, 0, SpringLayout.WEST, nameLabel);		
		
		//Spring layout for the deckBox
		springLayout.putConstraint(SpringLayout.WEST, deckBox, 150, SpringLayout.WEST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, deckBox, 0, SpringLayout.SOUTH, deckLabel);
		
		//Spring layout for the saveButton
		springLayout.putConstraint(SpringLayout.SOUTH, saveButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, saveButton, 180, SpringLayout.WEST, this);
		
		//Spring layout for activateButton
		springLayout.putConstraint(SpringLayout.WEST, activateButton, 50, SpringLayout.EAST, saveButton);
		springLayout.putConstraint(SpringLayout.NORTH, activateButton, 0, SpringLayout.NORTH, saveButton);
		
		setLayout(springLayout);
	
		// Adds name and description labels and text fields
		add(nameLabel);
		add(nameTextField);
		add(descriptionLabel);
		add(descTextArea);
		
		//Adds time related components
		add(timeLabel);
		add(hourComboBox);
		add(minuteComboBox);
		add(ampmBox);
		
		// Adds label for Deadline and date related labels and boxes
		add(deadlineLabel);
		add(datePicker);
		
		// Adds deck GUI objects
		add(deckLabel);
		add(deckBox);
		
		// Adds buttons at the bottom end of the GUI
		add(saveButton);
		
		add(deadlineError);
		add(hourError);
		add(minuteError);
		add(nameError);
		add(reqError);
		add(activateButton);
	}

}
