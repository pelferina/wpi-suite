/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;

/**
 * Panel to add an email address
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class AddEmailPanel extends JPanel {
	
	private final JTextField emailField = new JTextField();
	private final JLabel emailLabel = new JLabel("Email: ");
	private final JButton submitButton = new JButton ("Submit");
	private final JButton close;
	public boolean isNew = false;
	
	/**
	 * Constructor to create panel with close button
	 * @param btnClose button to close the panel
	 */
	public AddEmailPanel(JButton btnClose){
		setupPanel();
		close = btnClose;
	}
	
	/**
	 * This method sets up the panel layout and design
	 */
	private void setupPanel(){
		final SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, submitButton, 21, SpringLayout.SOUTH, emailField);
		springLayout.putConstraint(SpringLayout.WEST, submitButton, 189, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, emailField, -3, SpringLayout.NORTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailField, 6, SpringLayout.EAST, emailLabel);
		springLayout.putConstraint(SpringLayout.EAST, emailField, 285, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, emailLabel, 126, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, emailLabel, 136, SpringLayout.WEST, this);
		setLayout(springLayout);
		add(emailLabel);
		add(emailField);
		add(submitButton);
		
		submitButton.addActionListener(new AddEmailAddressController(emailField));
	}

}
