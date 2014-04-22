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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;



import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is the window for the user to create a planning poker session
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class NewGameInputDistributedPanel extends JPanel {

	// TODO get rid of this, switch to GregorianCalendar data type
	private Calendar currentDate; 

	private NewGameDistributedPanel newGameP;

	/*
	 * Initializing optional Deadline
	 */
	private final JCheckBox deadlineCheckBox = new JCheckBox("Set Deadline");
	private final JLabel deadlineLabel = new JLabel("Deadline:");
	// Date Picker declarations
	private final UtilDateModel model = new UtilDateModel();
	private final JDatePanelImpl datePanel = new JDatePanelImpl(model);
	private final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

	private final JLabel deadlineTime = new JLabel ("Deadline Time:");
	private int hourTime;
	private int minuteTime; 
	private int deadlineDay;
	private int deadlineMonth;
	private int deadlineYear;
	private final JComboBox<String> deadlineHourComboBox = new JComboBox<String>();
	private final JComboBox<String> deadlineMinuteComboBox = new JComboBox<String>();
	private final JRadioButton AMButton = new JRadioButton("AM");
	private final JRadioButton PMButton = new JRadioButton("PM");
	private boolean isAM = true;

	/*
	 *  Initializing Requirement Selection	
	 */
	private final List<Integer> selectionsMade = new ArrayList<Integer>();

	/*
	 *  Initializing Optional Deck Selection 
	 */
	private final JCheckBox deckCheckBox = new JCheckBox("Use Deck");
	private final JComboBox<String> deckBox = new JComboBox<String>(); 

	/*
	 * Initializing name and description labels and text fields
	 */
	private final JLabel nameLabel = new JLabel("Game Name*:");
	private final JLabel descriptionLabel = new JLabel("Description:");
	private final JLabel deckLabel = new JLabel("Choose a deck:");
	private final JTextField nameTextField = new JTextField();
	private final JTextArea descriptionTextField = new JTextArea();

	/*
	 *  Initializing editMode variables	
	 */
	private boolean editMode = false;
	private GameSession currentGameSession = null;

	/*
	 * Initializing error handling
	 */
	private final JLabel hourError = new JLabel("Select an hour for deadline");
	private final JLabel minuteError = new JLabel("Select a minute for deadline");
	private final JLabel deadlineError = new JLabel("Can not have a deadline in the past");
	private final JLabel nameError = new JLabel("Enter a name for the game");
	private final JLabel reqError = new JLabel("Can not have a game with no requirements");

	/*
	 * Initializing Create/Update and Activate game buttons
	 */
	private final JButton saveGameButton;
	private final JButton activateGameButton = new JButton("Activate Game");

	/*
	 * Initializing Time Checker
	 */
	private Timer canActivateChecker;

	private boolean activate;


	/**
	 * Edit Tab constructor
	 * @param ngdp the new game distributed panel that it was added from
	 * @param gameSession the game to be edited
	 */
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp, GameSession gameSession)
	{
		currentGameSession = gameSession;
		editMode = true;
		saveGameButton = new JButton("Update Game");
		init(ngdp);
		if (gameSession.getGameStatus().equals(GameStatus.ACTIVE)){
			activateGameButton.setVisible(false);
		}
//		System.out.println("Editing Game: "+ gameSession.getGameName());
	}

	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param ngdp The NewGameDistributedPanel that it was added from
	 */
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp) {
		saveGameButton = new JButton("Create Game");
		init(ngdp);
	}

	private void setupButtonIcons()
	{
		try {
			Image img = ImageIO.read(getClass().getResource("create.png"));
			saveGameButton.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("activate.png"));
			activateGameButton.setIcon(new ImageIcon(img));		    
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Initializes the NewGameDistributedPanel
	 * @param ngdp the NewGameDistributedPanel to initialize
	 */
	private void init(NewGameDistributedPanel ngdp)
	{
		newGameP = ngdp;

		currentDate = Calendar.getInstance();


		setupButtonIcons();	

		saveGameButton.setEnabled(false);
		activateGameButton.setEnabled(false);

		setPanel();

		// Set initial save/activate game visibility		
		setSaveGameButtonVisibility(false);
		setActivateGameButtonVisibility(false);

		//Set deadline and deck to not be visible
		setDeadlineVisibility(false);	
		setDeckVisibility(false);

		// Initialize the error Messages		
		initializeErrorMessages();

		//Start timer to check if game can be activated
		startCanActivateCheckerTimer();		

		//This is run if the game is opened in edit mode
		if(editMode)
		{
			initializeEditMode();
		}
		//Display Deadline option only when checkbox is selected
		deadlineCheckBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setDeadlineVisibility(true);
					datePicker.revalidate();
					datePicker.repaint();
					if(!editMode)
					{
						// Initialize the deadline
						initializeDeadline();
					}
					setupDeadlineActionListeners();
				} else {
					setDeadlineVisibility(false);
				}
			}

		});

		//Display Deck selection only when checkbox is selected
		deckCheckBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// Initialize the deck
					initializeDeckComboBox();
					setDeckVisibility(true);		        
				} else {
					setDeckVisibility(false);
				}
			}	
		});

		//Adds a documentlistener to the name text field so that no name error can be handled, save button can be made visible  
		//the pop-up will appear if the new game tab is closed.
		nameTextField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				updateSave(e);
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				updateSave(e);
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				updateSave(e);
			}
			private void updateSave(DocumentEvent e) {
				newGameP.isNew = false;
				if (nameTextField.getText().length() != 0){
					nameError.setVisible(false);		
					setSaveGameButtonVisibility(true);
				}
				else
				{
					nameError.setVisible(true);		
					setSaveGameButtonVisibility(false);
				}
			}
		});

		//Adds a documentlistener to the description text area so that way if the text is changed in the description text area, the pop-up will 
		// appear is the new game tab is close
		descriptionTextField.getDocument().addDocumentListener(new DocumentListener(){
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

		//TODO: isNew? for deckbox
		//Adds an action listener to the deck box so that if the deck combo box to set isNew to false
		//if it is changed.		
		deckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (deckBox.getSelectedIndex() == 0){
				}
				newGameP.isNew = false;
			}
		});

		//This button is for saving a game, and to do that, only the name is required		
		saveGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveOrActivateGame();
				if(editMode)
				{
					newGameP.close.doClick();
				}
				else
				{
					newGameP.close.doClick();
				}

			}
		});

		//Checks to see if all the fields are properly filled, and then sends the game object to the database if done.
		activateGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				activate = true;
				saveOrActivateGame();
				canActivateChecker.stop();
				newGameP.close.doClick();
			}
		});
	}
	
	private void saveSelectedReqs(){
		selectionsMade.clear();
		final List<Requirement> reqsSelected = newGameP.getSelected();
		for (int i=0; i<reqsSelected.size(); i++){
			selectionsMade.add(reqsSelected.get(i).getId());
		}
	}

	private void saveOrActivateGame()
	{
		newGameP.isNew = true;

		initializeErrorMessages();

		final String name = nameTextField.getText();
		final String description = descriptionTextField.getText();

		@SuppressWarnings("deprecation")
		Date deadlineDate = new Date(deadlineYear - 1900, deadlineMonth , deadlineDay, hourTime, minuteTime);

		//If just saving the game, set deadline to null
		if(!deadlineCheckBox.isSelected())
		{
			deadlineDate = null;
		}
		saveSelectedReqs();
		final GameModel model = GameModel.getInstance();
		//If activating: Set game status to active and Send an activation email 
		if(!editMode)
		{
			GameSession newGame = new GameSession(name, description, 0 , GameModel.getInstance().getSize() + 1, deadlineDate, selectionsMade);
			if(activate == true)
			{
				newGame.setGameStatus(GameStatus.ACTIVE);
			}
			if (deckCheckBox.isSelected()){
				//TODO set correct deck
				newGame.setDeckId(0);
			}

			final AddGameController msgr = new AddGameController(model);
			msgr.sendGame(newGame);

		}
		else
		{
			currentGameSession.setGameName(name);
			currentGameSession.setGameDescription(description);
			currentGameSession.setGameReqs(selectionsMade);
			currentGameSession.setEndDate(deadlineDate);
			if(activate)
			{
				currentGameSession.setGameStatus(GameStatus.ACTIVE);
			}
			if (deckCheckBox.isSelected()){
				//TODO set correct deck
				currentGameSession.setDeckId(0);
			} else {
				currentGameSession.setDeckId(-1);
			}
			final UpdateGameController msgr = new UpdateGameController();
			//			System.out.println("Attempting to send game to database"); // TODO: also remove this
			msgr.sendGame(currentGameSession); //TODO: This is sending an error
		}		
	}
	/**
	 * This timer is used to check if the game can be activated.
	 * If it can be activated, which means that all the necessary fields are filled,
	 *  then the activate button will become enabled 
	 */
	void startCanActivateCheckerTimer()
	{
		//System.out.println("activating timer!");
		canActivateChecker = new Timer(100, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (!editMode){
					newGameP.isNew = areFieldsEmpty();
				}
				//Display Activate button if game can be activated
				if (canActivate()){
					initializeErrorMessages();
					activateGameButton.setEnabled(true);
				}
				else if (!canActivate()){
					activateGameButton.setEnabled(false);
				}
				// updates deadline time for Afternoons				
				if(deadlineCheckBox.isSelected())
				{
					hasDeadline();
				}
				//Display Update button if game has been changed in edit mode
				if(editMode && anythingChanged())
				{
					
					newGameP.isNew = false;
					saveGameButton.setEnabled(true);
				}
				else if(editMode && !anythingChanged())
				{
					newGameP.isNew = true;
					saveGameButton.setEnabled(false);
				}
			}
		});
		canActivateChecker.start();
	}
	
	/**
	 * 
	 * stops the canActivateChecker
	 *
	 */
	public void stopTimer(){
		canActivateChecker.stop();
	}

	/**
	 * Handles the error messages for requirements 
	 * @return true if requirements have been selected
	 */
	private boolean hasReqs()
	{
		//Displays the cannot have a game without requirements error if no requirements were chosen
		if (newGameP.getSelected().isEmpty()){
			reqError.setVisible(true);
			return false;
		}
		else
		{
			reqError.setVisible(false);
			return true;
		}
	}

	/**
	 *  Handles the error messages for deadline time
	 * @return true if invalid deadline is selected
	 */
	private boolean hasDeadline()
	{	
		//Get deadline date
		currentDate = Calendar.getInstance();
		setDeadlineDate();
		hourTime = getHour(deadlineHourComboBox.getSelectedIndex() + 1);
		minuteTime = deadlineMinuteComboBox.getSelectedIndex();
		final Calendar deadline = Calendar.getInstance();
		deadline.set(deadlineYear, deadlineMonth, deadlineDay, hourTime, minuteTime);		 
		if (deadline.after(currentDate)){
			deadlineError.setVisible(false);
			return true;
		}
		else {
			deadlineError.setVisible(true);
			return false;
		}
	}
	/**
	 *  Sets deadline(datePicker and time) to either visible or not
	 * @param isVisible
	 */
	private void setDeadlineVisibility(boolean isVisible)
	{
		deadlineTime.setVisible(isVisible);
		deadlineLabel.setVisible(isVisible);
		datePicker.setVisible(isVisible);
		deadlineHourComboBox.setVisible(isVisible);
		deadlineMinuteComboBox.setVisible(isVisible);
		AMButton.setVisible(isVisible);
		PMButton.setVisible(isVisible);
	}

	/** Sets deck to either visible or not
	 * @param isVisible
	 */
	private void setDeckVisibility(boolean isVisible)
	{
		deckLabel.setVisible(isVisible);
		deckBox.setVisible(isVisible);
	}

	/**
	 * Sets the save game button visibility
	 * @param isVisible
	 */
	private void setSaveGameButtonVisibility(boolean isVisible)
	{
		saveGameButton.setEnabled(isVisible);
	}

	/**
	 * Sets the Activate game button visibility
	 * @param isVisible
	 */
	private void setActivateGameButtonVisibility(boolean isVisible)
	{
		activateGameButton.setEnabled(isVisible);		
	}

	/**
	 * Initializes Error Messages to false
	 */
	private void initializeErrorMessages()
	{
		hourError.setVisible(false);
		minuteError.setVisible(false);
		deadlineError.setVisible(false);
		nameError.setVisible(false);
		reqError.setVisible(false);
	}

	/**
	 * Sets up deadline related action listeners
	 */
	private void setupDeadlineActionListeners()
	{
		//Sets isNew to false, and sets minuteTime to the selected minute.
		deadlineMinuteComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				minuteTime = deadlineMinuteComboBox.getSelectedIndex();
				newGameP.isNew = false;
				minuteError.setVisible(false);
			}

		});

		//Sets isNew to false and sets hourtime to the hour selected. It is set to 0 if 12 is selected.
		deadlineHourComboBox.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e){
				final int hourIndex = deadlineHourComboBox.getSelectedIndex();
				if (hourIndex != 11){
					hourTime = deadlineHourComboBox.getSelectedIndex() + 1;
				}
				else{
					hourTime = 0;
				}
				hourError.setVisible(false);
				newGameP.isNew = false;
			}
		});

		AMButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PMButton.setSelected(false);
				AMButton.setSelected(true);
				isAM = true;
			}
		});

		PMButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AMButton.setSelected(false);
				PMButton.setSelected(true);
				isAM = false;
			}
		});		

	}
	/**
	 * Initialize date picker, hour and minute combo boxes
	 */
	private void initializeDeadline()
	{
		currentDate = Calendar.getInstance();
		//Initialize date picker
		if (!datePicker.getModel().isSelected()){
			datePicker.getModel().setDate(currentDate.get(Calendar.YEAR), 
					currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
			datePicker.getModel().setSelected(true);
		}
		setDeadlineDate();

		setupDeadlineTime();

		//Default deadline hour and minute to a minute from the current time
		final SimpleDateFormat hourDateFormat = new SimpleDateFormat("hh");
		final SimpleDateFormat minuteDateFormat = new SimpleDateFormat("mm"); 
		final String hour = hourDateFormat.format(currentDate.getTime());
		final String minute = minuteDateFormat.format(currentDate.getTime());
		hourTime = Integer.parseInt(hour);
		minuteTime = Integer.parseInt(minute) + 1;
		deadlineHourComboBox.setSelectedIndex(hourTime-1);
		deadlineMinuteComboBox.setSelectedIndex(minuteTime);
		if (currentDate.get(Calendar.AM_PM) == Calendar.PM) {
			PMButton.setSelected(true);
			isAM = false;
		}        
		else
		{
			isAM = true;
			AMButton.setSelected(true);
		}
	}
	private void setDeadlineDate() {
		deadlineYear = datePicker.getModel().getYear();
		deadlineMonth = datePicker.getModel().getMonth();
		deadlineDay = datePicker.getModel().getDay();
	}
	private void setupDeadlineTime() {
		//Initialize Deadline Hour and Minute
		for (int j=0; j<12; j++){
			deadlineHourComboBox.addItem(j + 1 + "");
		}

		for (int i=0; i<60; i++){
			if (i < 10){
				deadlineMinuteComboBox.addItem("0" + i);
			}
			else{
				deadlineMinuteComboBox.addItem("" + i);
			}
		}
	}
	private void initializeDeckComboBox()
	{
		//Initializes the deck combo box
		deckBox.addItem("Default Deck");
	}

	private void initializeEditMode()
	{
		
		//Gets the deadline from the game
		if (currentGameSession.getEndDate() != null){

			deadlineCheckBox.setSelected(true);
			setDeadlineVisibility(true);

			final int year_index = currentGameSession.getEndDate().getYear() + 1900;

			final int month_index = currentGameSession.getEndDate().getMonth();

			final int day_index = currentGameSession.getEndDate().getDate();

			datePicker.getModel().setDate(year_index, month_index, day_index);
			datePicker.getModel().setSelected(true);

			setDeadlineDate();
			setupDeadlineActionListeners();
			setupDeadlineTime();
			//System.out.println("T:" + currentGameSession.getEndDate().getHours());			
			//	Sets the hour and minute combo boxes to the hour and minute in the game's deadline
			if (currentGameSession.getEndDate().getHours() > 11){
				deadlineHourComboBox.setSelectedIndex(currentGameSession.getEndDate().getHours() - 13);
				isAM = false;
				PMButton.setSelected(true);
				AMButton.setSelected(false);
			}
			else if (currentGameSession.getEndDate().getHours() == 0){
				deadlineHourComboBox.setSelectedIndex(11);
				AMButton.setSelected(true);
				isAM = true;
				PMButton.setSelected(false);
			}
			else {
				deadlineHourComboBox.setSelectedIndex(currentGameSession.getEndDate().getHours()-1);
				AMButton.setSelected(true);
				isAM = true;
				PMButton.setSelected(false);
			}
			deadlineMinuteComboBox.setSelectedIndex(currentGameSession.getEndDate().getMinutes());
		}
		else
		{
			initializeDeadline();
		}
		nameTextField.setText(currentGameSession.getGameName());
		descriptionTextField.setText(currentGameSession.getGameDescription());
		//set the selected deck
		if (currentGameSession.getDeckId() != -1){
			deckCheckBox.setSelected(true);
			initializeDeckComboBox();
			setDeckVisibility(true);
			//TODO set actual deck in combo box
		}
		//Puts the name of the game into the name text field, it can not be edited
		if(currentGameSession.getGameStatus() != GameStatus.DRAFT)
		{	
			nameTextField.setEditable(false);
			descriptionTextField.setEditable(false);	
		}
	}

	/**
	 * 
	 * Checks if the new game panel is empty and no requirements/deadline is selected
	 *
	 * @return boolean, true is empty, false otherwise
	 */
	private boolean areFieldsEmpty(){
		saveSelectedReqs();
		if (!nameTextField.getText().equals("")){
			return false;
		}
		if (!descriptionTextField.getText().equals("")){
			return false;
		}
		if (deadlineCheckBox.isSelected()){
			return false;
		}
		if (deckCheckBox.isSelected()){
			return false;
		}
		if (!selectionsMade.isEmpty()){
			return false;
		}
		return true;
	}
	
	private boolean anythingChanged() {
		saveSelectedReqs();

//		if(currentGameSession.getGameStatus() == GameStatus.DRAFT)
	//	{	
			// Check if the user has changed the name
			if (!(nameTextField.getText().equals(currentGameSession.getGameName()))){
				return true;}
			// Check if the user has changed the description
			if (!(descriptionTextField.getText().equals(currentGameSession.getGameDescription()))){
				return true;}
		//}
		// Check if the user has changed the deadline
		@SuppressWarnings("deprecation")

		final Date deadlineDate = new Date(deadlineYear - 1900, deadlineMonth, deadlineDay, getHour(deadlineHourComboBox.getSelectedIndex() + 1), minuteTime);
		if(deadlineCheckBox.isSelected() && !deadlineDate.equals(currentGameSession.getEndDate())){
			return true;
		}
		// Check if the user has changed the requirements
		if (!selectionsMade.containsAll(currentGameSession.getGameReqs())
				|| (selectionsMade.size() != currentGameSession.getGameReqs().size())){
			return true;
		}
		// Check if the user has changed the deck
		if ((currentGameSession.getDeckId() != -1 && !deckCheckBox.isSelected())
				|| (currentGameSession.getDeckId() == -1 && deckCheckBox.isSelected())){
			return true;
		}
		return false;
	}
	//TODO: Account for midnight	
	//Sets the hour based on the AM or PM combo box selection
	private int getHour(int hour){
		if (isAM){
			return hour;
		}
		else {
			return hour + 12;
		}
	}

	//This function checks to see if everything necessary for activating a game has been entered by the user.
	private boolean canActivate(){
		if (nameInputted() && descInputted() && hasReqs()){
			//Activate if deadline checkbox is not selected
			if(!deadlineCheckBox.isSelected()){
				return true;
			}
			//Activate if deadline is valid
			if(hasDeadline()){
				return true;
			}
		}
		return false;
	}

	//Returns true if the name text field has text
	private boolean nameInputted(){
		return (nameTextField.getText().length() > 0);
	}

	//Returns true if the description text field has text
	private boolean descInputted(){
		return  (descriptionTextField.getText().length() > 0);
	}

	/**
	 * a lot of Window Builder generated UI
	 * for setting the NewGameInputPage
	 */

	private void setPanel(){
		ButtonGroup AMPMgroup = new ButtonGroup();
		AMPMgroup.add(AMButton);
		AMPMgroup.add(PMButton);
		//		userList.setListData(listValue);
		final SpringLayout springLayout = new SpringLayout();

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
		descriptionTextField.setRows(3);
		descriptionTextField.setLineWrap(true);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionTextField, 10, SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextField, 0, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextField, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionTextField, -15, SpringLayout.NORTH, deadlineCheckBox);

		//Spring layout for the deadlineCheckBox
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineCheckBox, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineCheckBox, 0, SpringLayout.WEST, nameLabel);

		//Spring layout for the deckCheckBox
		springLayout.putConstraint(SpringLayout.SOUTH, deckCheckBox, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deckCheckBox, 25, SpringLayout.EAST, deadlineCheckBox);

		//Spring layout for the deckLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deckLabel, -230, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, deckLabel, 0, SpringLayout.NORTH, deckCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, deckLabel, 20, SpringLayout.EAST, deckCheckBox);		

		//Spring layout for the deckBox
		springLayout.putConstraint(SpringLayout.WEST, deckBox, -5, SpringLayout.EAST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, deckBox, 0, SpringLayout.SOUTH, deckLabel);

		//Spring layout for the deadlineLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -200, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);

		//Spring layout for the datePicker
		springLayout.putConstraint(SpringLayout.WEST, datePicker, 75, SpringLayout.WEST, deadlineLabel);
		springLayout.putConstraint(SpringLayout.NORTH, datePicker, 0, SpringLayout.NORTH, deadlineLabel);

		//Spring layout for the activateGameButton
		springLayout.putConstraint(SpringLayout.SOUTH, saveGameButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, saveGameButton, 180, SpringLayout.WEST, this);

		//Spring layout for the deadlineError
		springLayout.putConstraint(SpringLayout.WEST, deadlineError, 50, SpringLayout.WEST, saveGameButton);
		springLayout.putConstraint(SpringLayout.NORTH, deadlineError, -20, SpringLayout.NORTH, saveGameButton);
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

		//Spring layout for timeLabel
		springLayout.putConstraint(SpringLayout.NORTH, deadlineTime, 45, SpringLayout.SOUTH, deadlineLabel);
		springLayout.putConstraint(SpringLayout.WEST, deadlineTime, 0, SpringLayout.WEST, nameLabel);

		//Spring layout for deadlineHourComboBox
		springLayout.putConstraint(SpringLayout.NORTH, deadlineHourComboBox, 0, SpringLayout.NORTH, deadlineTime);
		springLayout.putConstraint(SpringLayout.WEST, deadlineHourComboBox, 5, SpringLayout.EAST, deadlineTime);

		//Spring layout for minuteComboBox
		springLayout.putConstraint(SpringLayout.NORTH, deadlineMinuteComboBox, 0, SpringLayout.NORTH, deadlineHourComboBox);
		springLayout.putConstraint(SpringLayout.WEST, deadlineMinuteComboBox, 40, SpringLayout.EAST, deadlineHourComboBox);

		//Spring layout for ampmButton
		springLayout.putConstraint(SpringLayout.NORTH, AMButton, 10, SpringLayout.SOUTH, deadlineHourComboBox);
		springLayout.putConstraint(SpringLayout.WEST, AMButton, 0, SpringLayout.WEST, deadlineHourComboBox);
		springLayout.putConstraint(SpringLayout.NORTH, PMButton, 10, SpringLayout.SOUTH, deadlineMinuteComboBox);
		springLayout.putConstraint(SpringLayout.WEST, PMButton, 0, SpringLayout.WEST, deadlineMinuteComboBox);

		//Spring layout for the saveGameButton
		springLayout.putConstraint(SpringLayout.SOUTH, saveGameButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, saveGameButton, 50, SpringLayout.WEST, this);

		//Spring layout for activateGameButton
		springLayout.putConstraint(SpringLayout.WEST, activateGameButton, 100, SpringLayout.EAST, saveGameButton);
		springLayout.putConstraint(SpringLayout.NORTH, activateGameButton, 0, SpringLayout.NORTH, saveGameButton);

		setLayout(springLayout);

		// Adds name and description labels and text fields
		add(nameLabel);
		add(nameTextField);
		add(descriptionLabel);
		add(descriptionTextField);

		//Adds time related components
		add(deadlineTime);
		add(deadlineHourComboBox);
		add(deadlineMinuteComboBox);
		add(AMButton);
		add(PMButton);

		// Adds label for Deadline and date related labels and boxes
		add(deadlineCheckBox);
		add(deadlineLabel);
		add(datePicker);

		// Adds deck GUI objects
		add(deckLabel);
		add(deckBox);
		add(deckCheckBox);

		// Adds buttons at the bottom end of the GUI
		add(saveGameButton);

		add(deadlineError);
		add(hourError);
		add(minuteError);
		add(nameError);
		add(reqError);
		add(activateGameButton);
	}
}
