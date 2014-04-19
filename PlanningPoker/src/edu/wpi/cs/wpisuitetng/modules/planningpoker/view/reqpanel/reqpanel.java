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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;


public class reqpanel extends JPanel {
	private Requirement currentRequirement;
	private JTextField nameField;
	private JTextArea descriptionField;
	private final JLabel nameLabel = new JLabel("Requirement Name: ");
	private final JLabel descriptionLabel = new JLabel("Description: ");
	private final JButton CreateRequirementButton = new JButton("Create Requirement");
	private final JButton close;
	private JComboBox<RequirementPriority> priorityComboBox;
	private JComboBox<RequirementType> typeComboBox;
	
	/**
	 * Creates the tab for the new requirement to be specified and created
	 * @param btnClose
	 */
	public reqpanel(JButton btnClose){
		priorityComboBox = new JComboBox<RequirementPriority>(RequirementPriority.values());
		typeComboBox = new JComboBox<RequirementType>(RequirementType.values());
		panelSetup();
		close = btnClose;
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
		descriptionField = new JTextArea();
		descriptionField.setLineWrap(true);
		JLabel lblPriority = new JLabel("Priority");

		JLabel lblType = new JLabel("Type");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(46)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(nameLabel)
								.addComponent(descriptionLabel))
							.addGap(8)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(nameField)
								.addComponent(descriptionField, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
							.addGap(117)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(priorityComboBox, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPriority)
									.addGap(79)
									.addComponent(lblType))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(401)
							.addComponent(CreateRequirementButton)))
					.addContainerGap(350, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(descriptionLabel)
								.addComponent(descriptionField, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(93)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPriority)
								.addComponent(lblType))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(priorityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(160)
					.addComponent(CreateRequirementButton)
					.addContainerGap(368, Short.MAX_VALUE))
		);
		CreateRequirementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRequirement();
				close.doClick();
			}
		});
		setLayout(groupLayout);
		
		
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
	}
}
	
