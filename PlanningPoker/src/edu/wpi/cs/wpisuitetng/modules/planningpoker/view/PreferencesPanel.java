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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Panel to add an email address
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class PreferencesPanel extends JPanel {
	
	private final JTextField emailField = new JTextField();

	private JLabel userName;

	private final JLabel emailLabel = new JLabel("New Email: ");
	private final JLabel currentEmailLabel = new JLabel("");
	private final JLabel currentEmailNameLabel = new JLabel("Current Email:");
	private final JButton submitButton = new JButton ("Save");
	private final JCheckBox enableCheckBox = new JCheckBox("Enable E-mail Notification");
	private final JButton close;
	private EmailAddressModel eModel;
	
	/**
	 * Constructor to create panel with close button
	 * @\param btnClose button to close the panel
	 */
	public PreferencesPanel(JButton btnClose){
		setupPanel();
		currentEmailLabel.setText(emailField.getText());
		emailField.setText("");
		close = btnClose;
	}
	
	/**
	 * This method sets up the panel layout and design
	 */
	private void setupPanel(){
		
		final SpringLayout springLayout = new SpringLayout();
		
		final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
		userName = new JLabel ("Hi, " + currentUser.getName() + "!");
		userName.setFont (userName.getFont ().deriveFont (35.0f));

		// Swing layout for user greetings
		springLayout.putConstraint(SpringLayout.NORTH, userName, 100, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userName, 500, SpringLayout.WEST, this);
		
		// Swing layout for currentEmailNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, currentEmailNameLabel, 30, SpringLayout.SOUTH, userName);
		springLayout.putConstraint(SpringLayout.WEST, currentEmailNameLabel, 480, SpringLayout.WEST, this);
		// Swing layout for currentEmailLabel
		springLayout.putConstraint(SpringLayout.NORTH, currentEmailLabel, 0, SpringLayout.NORTH, currentEmailNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, currentEmailLabel, 10, SpringLayout.EAST, currentEmailNameLabel);
		// Swing layout for email label
		springLayout.putConstraint(SpringLayout.NORTH, emailLabel, 30, SpringLayout.SOUTH, currentEmailNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailLabel, 0, SpringLayout.WEST, currentEmailNameLabel);			
		// Swing layout for email Field
		springLayout.putConstraint(SpringLayout.NORTH, emailField, 0, SpringLayout.NORTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailField, 10, SpringLayout.EAST, emailLabel);
		springLayout.putConstraint(SpringLayout.EAST, emailField, 125, SpringLayout.WEST, emailField);
		// Swing layout for submit Button
		springLayout.putConstraint(SpringLayout.NORTH, submitButton, 30, SpringLayout.SOUTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, submitButton, -10, SpringLayout.WEST, emailLabel);
		// Swing layout for enableCheckBox
		springLayout.putConstraint(SpringLayout.NORTH, enableCheckBox, 0, SpringLayout.NORTH, submitButton);
		springLayout.putConstraint(springLayout.WEST, enableCheckBox, 10, SpringLayout.EAST, submitButton);
		
		setLayout(springLayout);
		
		add(currentEmailLabel);
		add(currentEmailNameLabel);
		add(userName);
		add(emailLabel);
		add(emailField);
		add(submitButton);
		add(enableCheckBox);
		enableCheckBox.addItemListener(new CheckBoxChangedListener(this));
		retrieveEmail();
		emailField.setText(eModel.getAddress());
		if(eModel.getEnable() == true){
			enableCheckBox.setSelected(true);
			makeEmailEnable();
		}else{
			enableCheckBox.setSelected(false);
			makeEmailDisable();
		}
		submitButton.addActionListener(new AddEmailAddressController(this));
	}
	
	private void retrieveEmail(){
		final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.GET); // GET == read
		request.addObserver(new GetEmailRequestObserver(this));
		request.send(); // send the request
		while(eModel == null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void makeEmailEnable(){
		this.submitButton.setEnabled(true);
		this.emailField.setEnabled(true);
	}
	public void makeEmailDisable(){
		this.submitButton.setEnabled(false);
		this.emailField.setEnabled(false);
	}
	public void setEmailModel(EmailAddressModel eModel){
		this.eModel = eModel;
	}
	public EmailAddressModel getEmailModel(){
		return this.eModel;
	}
	public String getEmailAddress(){
		return emailField.getText();
	}
}


class GetEmailRequestObserver implements RequestObserver {
	
	private final PreferencesPanel panel;
	
	/**
	 * This constructor populates the controller variable with the instance of GetEmailController
	 */
	public GetEmailRequestObserver(PreferencesPanel panel) {
		this.panel = panel;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		final EmailAddressModel[] emails = EmailAddressModel.fromJsonArray(iReq.getResponse().getBody());
		panel.setEmailModel(emails[0]);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/*
	 * placeholder for exception handling
	 * TODO Add swanky error handling.
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		if(exception == null) System.err.println("FAILURE in GetEmailRequestObserver");
		;//exception.printStackTrace();
	}

}

