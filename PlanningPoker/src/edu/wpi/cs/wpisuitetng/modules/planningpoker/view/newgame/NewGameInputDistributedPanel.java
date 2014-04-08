/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.BorderLayout;
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
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
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
public class NewGameInputDistributedPanel extends AbsNewGameInputPanel {
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
	
	private final JButton activateButton = new JButton("Activate Game");
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
	private final JLabel deckLabel = new JLabel("Choosen deck:");
	private JLabel yearLabel = new JLabel("Year: ");
	private JLabel monthLabel = new JLabel("Month: ");
	private JLabel dayLabel = new JLabel("Day: ");
	private JTextField nameTextField = new JTextField();
	private JTextArea descTextArea = new JTextArea();
	private boolean editMode = false;
	private final JLabel hourError = new JLabel("Select an hour for deadline");
	private final JLabel minuteError = new JLabel("Select a minute for deadline");
	private final JLabel deadlineError = new JLabel("Can not have a deadline in the past");
	private final JLabel nameError = new JLabel("Enter a name for the game");
	private final JLabel reqError = new JLabel("Can not have a game with no requirements");

	// Date Picker declarations
	private JPanel frame = new JPanel();
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel = new JDatePanelImpl(model);
	private JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	
	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputDistributedPanel(NewGameDistributedPanel ngdp, boolean editMode) {
		this.editMode = editMode;
		currentDate = Calendar.getInstance();
		newGameP = ngdp;
		//descriptionTextField.setColumns(10);
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
		
		//Adds a documentlistener to the description text area so that way if the text is changed, the pop-up will 
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
				//Checks to see if all the fields are properly filled, and then sends the game object to the database if done.
		
		activateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				hourError.setVisible(false);
				minuteError.setVisible(false);
				deadlineError.setVisible(false);
				nameError.setVisible(false);
				reqError.setVisible(false);
				String name = nameTextField.getText();
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
				if (hourComboBox.getSelectedIndex() == 0){
					hourError.setVisible(true);
				}
				else if (minuteComboBox.getSelectedIndex() == 0){
					minuteError.setVisible(true);
				}
				else if(!selectedDeadline.after(currentDate)){
					deadlineError.setVisible(true);
				}
				else if (selectionsMade.isEmpty()){
					reqError.setVisible(true);
				}
				else if (name.length() < 1){
					nameError.setVisible(true);
				}
				else{
					newGameP.isNew = true;
					System.out.println("Valid date selected an-d requirements were selected");
					Date deadlineDate = new Date(deadlineYear, deadlineMonth, deadlineDay, getHour(hourTime), minuteTime);
					GameSession newGame = new GameSession(name, 0 , GameModel.getInstance().getSize()+1, deadlineDate, selectionsMade); 
					GameModel model = GameModel.getInstance();
					AddGameController msgr = new AddGameController(model);
					msgr.sendMessage(newGame);	
					System.out.println(selectedDeadline.get(Calendar.MONTH) + "/" + selectedDeadline.get(Calendar.DAY_OF_MONTH) + "/" + selectedDeadline.get(Calendar.YEAR));
					System.out.println(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH) + "/" + currentDate.get(Calendar.YEAR));
					JOptionPane gameCreated = new JOptionPane("Game Created and Activated");
					JOptionPane.showMessageDialog(gameCreated, "Game has been created and activated", "Game created", JOptionPane.INFORMATION_MESSAGE);
					newGameP.close.doClick();
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
		springLayout.putConstraint(SpringLayout.WEST, nameTextField, 100, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameTextField, 200, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		//Spring layout for the descriptionLabel
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 30, SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the descTextArea
		descTextArea.setRows(3);
		descTextArea.setLineWrap(true);
		springLayout.putConstraint(SpringLayout.NORTH, descTextArea, 0, SpringLayout.NORTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descTextArea, 100, SpringLayout.WEST, descriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, descTextArea, 400, SpringLayout.EAST, descriptionLabel);
		
		//Spring layout for the deadlineLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -250, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);
		
		//Spring layout for the activateButton
		springLayout.putConstraint(SpringLayout.SOUTH, activateButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, activateButton, 180, SpringLayout.WEST, this);
		
		//Spring layout for the deadlineError
		springLayout.putConstraint(SpringLayout.WEST, deadlineError, 0, SpringLayout.WEST, activateButton);
		springLayout.putConstraint(SpringLayout.NORTH, deadlineError, -20, SpringLayout.NORTH, activateButton);
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
		
		//Spring layout for the activateButton
		springLayout.putConstraint(SpringLayout.SOUTH, activateButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, activateButton, 180, SpringLayout.WEST, this);
		
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
		add(activateButton);
		
		add(deadlineError);
		add(hourError);
		add(minuteError);
		add(nameError);
		add(reqError);
	}
	
	//Added by Ruofan.
	public void setGameName(String gameName){
		nameTextField.setText(gameName);
	}
}
