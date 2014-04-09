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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
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
	private Calendar currentDate;
	private int[] deckCards;
	private int[] defaultDeck = {1, 1, 2, 3, 5, 8, 13, -1};
	private int deadlineDay = 1;
	private int deadlineMonth = 1;
	private int deadlineYear = 2014;
	private final JLabel nameLabel = new JLabel("Game Name:");
	private final JLabel requirementLabel = new JLabel("Game Requirements");
	private final JLabel deadlineLabel = new JLabel("Deadline");
	private final JLabel deckLabel = new JLabel("Choose a deck");
	private JButton activateButton = new JButton("Activate Game");
	private JTextField nameTextField = new JTextField();
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
	private final JButton addNewButton = new JButton("Create New");

	/**
	 * The constructor for the NewGameInputPanel
	 * has void parameters
	 * @param nglp, The NewGameLivePanel that it was added from
	 */
	public NewGameInputDistributedPanel(AbsNewGamePanel nglp, String name) {
		currentDate = Calendar.getInstance();
		newGameP = nglp;
		nameTextField.setText(name);
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
				deadlineYear = year + 2014;
				System.out.println(deadlineYear);
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
				deadlineMonth = days + 1;
				System.out.println(deadlineMonth);
			}	
		});
		
		dayBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				int day = dayBox.getSelectedIndex();
				deadlineDay = day + 1;
				System.out.println(deadlineDay);
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
		
		activateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentDate = Calendar.getInstance();
				currentDate.set(Calendar.MONTH, currentDate.get(Calendar.MONTH) + 1);
				String name = nameTextField.getText();
				Calendar selectedDeadline = Calendar.getInstance();
				selectedDeadline.set(deadlineYear, deadlineMonth, deadlineDay);
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
				else if (name.length() < 1){
					JOptionPane deadlineError = new JOptionPane("No name inputted");
					JOptionPane.showMessageDialog(deadlineError, "Please input a name for the game", "Name error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					System.out.println("Valid date selected and requirements were selected");
					GameSession newGame = new GameSession(name, 0 , -1, selectedDeadline, selectionsMade); 
					GameModel model = new GameModel();
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
	private void setPanel(){
		SpringLayout springLayout = new SpringLayout();
				
		//Spring layout for the nameLabel
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 34, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 23, SpringLayout.WEST, this);
		
		//Spring layout for the nameTextField
		springLayout.putConstraint(SpringLayout.WEST, nameTextField, 43, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameTextField, 200, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameTextField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		//Spring layout for the timeLabel
		springLayout.putConstraint(SpringLayout.SOUTH, deadlineLabel, -237, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, deadlineLabel, 0, SpringLayout.WEST, nameLabel);
		
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
		
		setLayout(springLayout);
		
		nameTextField.setColumns(10);

		
		add(importButton);
		add(removeButton);
		add(nameLabel);
		add(deadlineLabel);
		add(activateButton);
		add(addNewButton);
		add(nameTextField);
		add(yearBox);
		add(monthBox);
		add(dayBox);
		add(yearLabel);
		add(monthLabel);
		add(dayLabel);
		add(deckLabel);
		add(deckBox);
		add(requirementLabel);
	}
	
	//Added by Ruofan.
	public void setGameName(String gameName){
		nameTextField.setText(gameName);
	}
}
