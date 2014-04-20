package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.reqpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import java.awt.Label;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

/**
 * This controls adding a new requirement from the new game tab.
 * 
 * @author FFF8E7
 *@version $Revision: 1.0 $
 */
public class NewRequirementPanel extends JPanel {
	private Requirement currentRequirement;
	private JTextField nameField;
	private JTextArea descriptionField;
	private final JLabel nameLabel = new JLabel("Requirement Name: ");
	private final JLabel descriptionLabel = new JLabel("Requirement Description: ");
	private final JButton CreateRequirementButton = new JButton("Create Requirement");
	private final JButton cancelButton = new JButton("Cancel");
	private JComboBox<RequirementPriority> priorityComboBox = new JComboBox<RequirementPriority>();
	private JComboBox<RequirementType> typeComboBox = new JComboBox<RequirementType>();
	private NewGameDistributedPanel newGamePanel;
	private boolean isCreatable;
	
	/**
	 * Creates the tab for the new requirement to be specified and created
	 * @param btnClose
	 */
	public NewRequirementPanel(NewGameDistributedPanel ngdp){
		CreateRequirementButton.setEnabled(false);
		newGamePanel = ngdp;
		priorityComboBox = new JComboBox<RequirementPriority>(RequirementPriority.values());
		typeComboBox = new JComboBox<RequirementType>(RequirementType.values());
		panelSetup();
		currentRequirement= new Requirement();
		
	}
	
	/**
	 * Sets up the panel for the requirement to be created -
	 * 		creates name field
	 * 		creates description field
	 * 		places and sizes everything
	 */
	public void panelSetup() {
		nameField = new JTextField();
		JLabel lblPriority = new JLabel("Priority:");
		JLabel lblType = new JLabel("Type:");
		descriptionField = new JTextArea();
		descriptionField.setLineWrap(true);
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout constraints for cancelButton
		springLayout.putConstraint(SpringLayout.WEST, cancelButton, 29, SpringLayout.EAST, CreateRequirementButton);
		springLayout.putConstraint(SpringLayout.SOUTH, cancelButton, -38, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, cancelButton, -149, SpringLayout.EAST, this);
		
		//Spring layout constraints for CreateRequirementButton
		springLayout.putConstraint(SpringLayout.EAST, CreateRequirementButton, -286, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, CreateRequirementButton, -62, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, CreateRequirementButton, -39, SpringLayout.SOUTH, this);
		
		//Spring layout constraints for priorityComboBox
		springLayout.putConstraint(SpringLayout.NORTH, priorityComboBox, 43, SpringLayout.SOUTH, descriptionField);
		springLayout.putConstraint(SpringLayout.WEST, priorityComboBox, 5, SpringLayout.EAST, lblPriority);
		springLayout.putConstraint(SpringLayout.EAST, priorityComboBox, -350, SpringLayout.EAST, this);
		
		//Spring layout constraints for lblType
		springLayout.putConstraint(SpringLayout.WEST, lblType, 273, SpringLayout.EAST, lblPriority);
		springLayout.putConstraint(SpringLayout.NORTH, lblType, 46, SpringLayout.SOUTH, descriptionField);
		
		//Spring layout constraints for typeComboBox
		springLayout.putConstraint(SpringLayout.NORTH, typeComboBox, 284, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, typeComboBox, 6, SpringLayout.EAST, lblType);
		springLayout.putConstraint(SpringLayout.EAST, typeComboBox, -49, SpringLayout.EAST, this);
		
		//Spring layout constraints for descriptionField
		springLayout.putConstraint(SpringLayout.NORTH, descriptionField, 116, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionField, -44, SpringLayout.NORTH, typeComboBox);
		springLayout.putConstraint(SpringLayout.WEST, descriptionField, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, descriptionField, -45, SpringLayout.EAST, this);
		
		//Spring layout constraints for lblPriority
		springLayout.putConstraint(SpringLayout.NORTH, lblPriority, 45, SpringLayout.SOUTH, descriptionField);
		springLayout.putConstraint(SpringLayout.WEST, lblPriority, 53, SpringLayout.WEST, this);
		
		//Spring layout constraints for descriptionLabel
		springLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 53, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionLabel, -16, SpringLayout.NORTH, descriptionField);
		
		//Spring layout constraints for nameField
		springLayout.putConstraint(SpringLayout.WEST, nameField, 6, SpringLayout.EAST, nameLabel);
		springLayout.putConstraint(SpringLayout.EAST, nameField, -45, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, nameField, -3, SpringLayout.NORTH, nameLabel);
		
		//Spring layout constraints for the nameLabel
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 29, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nameLabel, 53, SpringLayout.WEST, this);
		
		setLayout(springLayout);
		add(cancelButton);
		add(lblPriority);
		add(lblType);
		add(nameField);
		add(nameLabel);
		add(descriptionLabel);
		add(descriptionField);
		add(CreateRequirementButton);
		add(priorityComboBox);
		add(typeComboBox);
		
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
				CreateRequirementButton.setEnabled(isCreatable);
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
		String stringName = nameField.getText();

		String stringDescription = this.descriptionField.getText();

		String stringIteration = "Backlog";
		

		RequirementStatus status = RequirementStatus.NEW;
		RequirementType type = (RequirementType) typeComboBox.getSelectedItem();
		RequirementPriority priority = (RequirementPriority) priorityComboBox.getSelectedItem();

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

}
	