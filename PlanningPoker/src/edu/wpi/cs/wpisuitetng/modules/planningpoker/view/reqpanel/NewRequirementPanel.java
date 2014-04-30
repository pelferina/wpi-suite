/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.reqpanel;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.GuiStandards;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;

/**
 * This controls adding a new requirement from the new game tab.
 * 
 * @author FFF8E7
 * @version 6
 */
public class NewRequirementPanel extends JPanel {
	private final Requirement currentRequirement;
	private JTextField nameField = new JTextField();
	private final JTextArea descriptionField = new JTextArea();
	private final JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
	private final JLabel nameLabel = new JLabel("Requirement Name: ");
	private final JLabel descriptionLabel = new JLabel("Requirement Description: ");
	private final JButton CreateRequirementButton = new JButton("Create Requirement");
	private final JButton cancelButton = new JButton("Cancel");
	private JComboBox<RequirementPriority> priorityComboBox = new JComboBox<RequirementPriority>();
	private JComboBox<RequirementType> typeComboBox = new JComboBox<RequirementType>();
	private final NewGameDistributedPanel newGamePanel;
	private boolean isCreatable;
	private final JLabel reqError = new JLabel("Can not have a requirement with no name or description");
	
	/**
	 * Constructor for NewRequirementPanel
	 * @param ngdp The NewGameDistributedPanel to add this to
	 */
	public NewRequirementPanel(NewGameDistributedPanel ngdp){
		try{
			Image img = ImageIO.read(getClass().getResource("cancel.png"));
			cancelButton.setIcon(new ImageIcon(img));
			img = ImageIO.read(getClass().getResource("save.png"));
			CreateRequirementButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		CreateRequirementButton.setEnabled(false);
		reqError.setVisible(true);
		newGamePanel = ngdp;
		priorityComboBox = new JComboBox<RequirementPriority>(RequirementPriority.values());
		typeComboBox = new JComboBox<RequirementType>(RequirementType.values());
		panelSetup();
		currentRequirement = new Requirement();
		
	}
	
	/**
	 * Sets up the panel for the requirement to be created -
	 * 		creates name field
	 * 		creates description field
	 * 		places and sizes everything
	 */
	public void panelSetup() {
		//Add padding
		descriptionField.setBorder(BorderFactory.createCompoundBorder(
				descriptionField.getBorder(), 
		        BorderFactory.createEmptyBorder(GuiStandards.TEXT_AREA_MARGINS.getValue(), 
		        		GuiStandards.TEXT_AREA_MARGINS.getValue(), 
		        		GuiStandards.TEXT_AREA_MARGINS.getValue(),
		        		GuiStandards.TEXT_AREA_MARGINS.getValue())));
		descriptionField.setWrapStyleWord(true);
		
		nameField.setMargin(new Insets(0, GuiStandards.TEXT_BOX_MARGIN.getValue(), 0, 0));
		
		final JLabel lblPriority = new JLabel("Priority:");
		final JLabel lblType = new JLabel("Type:");
		descriptionField.setLineWrap(true);
		final SpringLayout springLayout = new SpringLayout();
		
		//Spring layout constraints for cancelButton
		springLayout.putConstraint(SpringLayout.SOUTH, cancelButton, -GuiStandards.BOTTOM_MARGIN.getValue(), SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, cancelButton, 0, SpringLayout.EAST, descriptionScrollPane);
		
		//Spring layout constraints for CreateRequirementButton
		springLayout.putConstraint(SpringLayout.EAST, CreateRequirementButton, -GuiStandards.BUTTON_OFFSET.getValue(), SpringLayout.WEST, cancelButton);
		springLayout.putConstraint(SpringLayout.NORTH, CreateRequirementButton, 0, SpringLayout.NORTH, cancelButton);
		
		//Spring layout constraints for priorityComboBox
		springLayout.putConstraint(SpringLayout.NORTH, priorityComboBox, 43, SpringLayout.SOUTH, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, priorityComboBox, 5, SpringLayout.EAST, lblPriority);
		springLayout.putConstraint(SpringLayout.EAST, priorityComboBox, 250, SpringLayout.WEST, priorityComboBox);
		
		//Spring layout constraints for lblType
		springLayout.putConstraint(SpringLayout.EAST, lblType, -10, SpringLayout.WEST, typeComboBox);
		springLayout.putConstraint(SpringLayout.NORTH, lblType, 46, SpringLayout.SOUTH, descriptionScrollPane);
		
		//Spring layout constraints for typeComboBox
		springLayout.putConstraint(SpringLayout.NORTH, typeComboBox, 284, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, typeComboBox, -250, SpringLayout.EAST, typeComboBox);
		springLayout.putConstraint(SpringLayout.EAST, typeComboBox, 0, SpringLayout.EAST, descriptionScrollPane);
		
		//Spring layout constraints for descriptionScrollPane
		springLayout.putConstraint(SpringLayout.NORTH, descriptionScrollPane, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.SOUTH, descriptionLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionScrollPane, -44, SpringLayout.NORTH, typeComboBox);
		springLayout.putConstraint(SpringLayout.WEST, descriptionScrollPane, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionScrollPane, -GuiStandards.RIGHT_MARGIN.getValue(), SpringLayout.EAST, this);
		
		//Spring layout constraints for lblPriority
		springLayout.putConstraint(SpringLayout.NORTH, lblPriority, 45, SpringLayout.SOUTH, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.WEST, lblPriority, 0, SpringLayout.WEST, descriptionScrollPane);
		
		//Spring layout constraints for descriptionLabel
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 30, SpringLayout.NORTH, nameLabel);
		
		//Spring layout constraints for nameField
		springLayout.putConstraint(SpringLayout.WEST, nameField, 6, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameField, 0, SpringLayout.EAST, descriptionScrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, nameField, -GuiStandards.TEXT_HIEGHT_OFFSET.getValue(), SpringLayout.NORTH, nameLabel);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, nameField, 0, SpringLayout.VERTICAL_CENTER, nameLabel);
		
		//Spring layout constraints for the nameLabel
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, GuiStandards.TOP_MARGIN.getValue(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, GuiStandards.DIVIDER_MARGIN.getValue(), SpringLayout.WEST, this);
		
		// spring layout for req error
		springLayout.putConstraint(SpringLayout.EAST, reqError, 0, SpringLayout.EAST, cancelButton);
		springLayout.putConstraint(SpringLayout.NORTH, reqError, -20, SpringLayout.NORTH, cancelButton);
		reqError.setVisible(false);
		
		
		setLayout(springLayout);
		add(cancelButton);
		add(lblPriority);
		add(lblType);
		add(nameField);
		add(nameLabel);
		add(descriptionLabel);
		add(descriptionScrollPane);
		add(CreateRequirementButton);
		add(priorityComboBox);
		add(typeComboBox);
		add(reqError);
		/**
		 * Action listener for the createRequirementButton
		 */
		CreateRequirementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRequirement();
				newGamePanel.closeReq();
			}
		});
		
		/**
		 * Action listener for the cancelButton
		 */
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGamePanel.closeReq();
			}
		});
		/**
		 * Document listener for the nameField
		 */
		nameField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				checkInput(e);
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				checkInput(e);
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				checkInput(e);
			}
			
			public void checkInput(DocumentEvent e){
				isCreatable = (!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty());
				if (isCreatable){
					CreateRequirementButton.setEnabled(isCreatable);
					getRootPane().setDefaultButton(CreateRequirementButton);
				}
				reqError.setVisible(!isCreatable);
			}
		});
		/**
		 * Document listener for the discriptionField
		 */
		descriptionField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				checkInput(e);
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				checkInput(e);
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				checkInput(e);
			}
			
			public void checkInput(DocumentEvent e){
				isCreatable = (!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty());
				CreateRequirementButton.setEnabled(isCreatable);
				reqError.setVisible(!isCreatable);
			}
		});
		
	}
	
	/**
	 * Adds the new requirement to the requirement manager
	 */
	private void updateRequirement(){
		currentRequirement.setId(RequirementModel.getInstance().getNextID());
		currentRequirement.setWasCreated(true);		
		// Extract the name, release number, and description from the GUI fields
		final String stringName = nameField.getText();

		final String stringDescription = descriptionField.getText();

		final String stringIteration = "Backlog";
		

		final RequirementStatus status = RequirementStatus.NEW;
		final RequirementType type = (RequirementType) typeComboBox.getSelectedItem();
		final RequirementPriority priority = (RequirementPriority) priorityComboBox.getSelectedItem();

		currentRequirement.setName(stringName);

		currentRequirement.setDescription(stringDescription);
		currentRequirement.setStatus(status);
		currentRequirement.setPriority(priority);

		currentRequirement.setIteration(stringIteration);
		currentRequirement.setType(type);
		RequirementModel.getInstance().addRequirement(currentRequirement);
		UpdateRequirementController.getInstance().updateRequirement(
				currentRequirement);

		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();
		newGamePanel.sendCreatedReq(currentRequirement);
	}
	/**
	 * This function clears all fields so that creating multiple requirments won't have the same fields
	 */
	public void clearFields() {
		nameField.setText("");
		descriptionField.setText("");
		priorityComboBox.setSelectedIndex(0);
		typeComboBox.setSelectedIndex(0);
	}
	
	public String getName(){
		return nameField.getText();
	}
	public String getDescription(){
		return descriptionField.getText();
	}
	public JButton getCreateRequirementButton(){
		return CreateRequirementButton;
	}
	public JLabel getReqError(){
		return reqError;
	}

	public void setFocusOnName() {
		nameField.requestFocusInWindow();
	}
}
	
