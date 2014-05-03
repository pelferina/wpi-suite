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




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;



import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Panel to add an email address
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class PreferencesPanel extends JPanel {
	
	private final JTextField emailField = new JTextField();

	private JLabel userName;

	private final JLabel emailLabel = new JLabel("New Email: ");
	private final JLabel currentEmailLabel = new JLabel("");
	private final JLabel currentEmailNameLabel = new JLabel("Current Email:");
	private final JButton submitButton = new JButton ("Save");
	private final JButton deckButton = new JButton ("Manage Decks");
	private final JCheckBox enableCheckBox = new JCheckBox("Enable E-mail Notification");
	private final JButton close;
	private EmailAddressModel eModel;
	
	/**
	 * Constructor to create panel with close button
	 * @param btnClose button to close the panel
	 */
	public PreferencesPanel(JButton btnClose){
		setupPanel();
		if(emailField.getText().length() == 0){
			currentEmailLabel.setText("none");
		}
		else currentEmailLabel.setText(emailField.getText());
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
		springLayout.putConstraint(SpringLayout.NORTH, userName, GuiStandards.TOP_MARGIN.getValue(), SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, userName, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.WEST, this);
		
		// Swing layout for currentEmailNameLabel
		springLayout.putConstraint(SpringLayout.NORTH, currentEmailNameLabel, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, userName);
		springLayout.putConstraint(SpringLayout.WEST, currentEmailNameLabel, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.WEST, this);
		// Swing layout for currentEmailLabel
		springLayout.putConstraint(SpringLayout.NORTH, currentEmailLabel, 0, SpringLayout.NORTH, currentEmailNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, currentEmailLabel, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.EAST, currentEmailNameLabel);
		// Swing layout for email label
		springLayout.putConstraint(SpringLayout.NORTH, emailLabel, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, currentEmailNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailLabel, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.WEST, this);			
		// Swing layout for email Field
		springLayout.putConstraint(SpringLayout.NORTH, emailField, 0, SpringLayout.NORTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailField, GuiStandards.LABEL_TEXT_OFFSET.getValue(), SpringLayout.EAST, emailLabel);
		springLayout.putConstraint(SpringLayout.EAST, emailField, 0, SpringLayout.EAST, enableCheckBox);
		// Swing layout for submit Button
		springLayout.putConstraint(SpringLayout.NORTH, submitButton, GuiStandards.NEXT_LABEL_OFFSET.getValue(), SpringLayout.SOUTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, submitButton, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.WEST, this);
		// Swing layout for enableCheckBox
		springLayout.putConstraint(SpringLayout.NORTH, enableCheckBox, 0, SpringLayout.NORTH, submitButton);
		springLayout.putConstraint(springLayout.WEST, enableCheckBox, GuiStandards.LEFT_MARGIN.getValue(), SpringLayout.EAST, submitButton);
		// Swing layout for deckButton
		springLayout.putConstraint(SpringLayout.NORTH, deckButton, 30, SpringLayout.SOUTH, enableCheckBox);
		springLayout.putConstraint(SpringLayout.WEST, deckButton, 0, SpringLayout.WEST, enableCheckBox);
		
		setLayout(springLayout);
		
		add(currentEmailLabel);
		add(currentEmailNameLabel);
		add(userName);
		add(emailLabel);
		add(deckButton);
		
		emailField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkValid();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkValid();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkValid();

			}
			
			private void checkValid(){
				if(emailField.getText().toUpperCase().matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}")){
					submitButton.setEnabled(true);
				}else{
					submitButton.setEnabled(false);
				}
			}
			
		});
		add(emailField);
		add(submitButton);
		add(enableCheckBox);
		enableCheckBox.addItemListener(new CheckBoxChangedListener(this));
		retrieveEmail();
		emailField.setText(eModel.getAddress());
		if(eModel.getEnable()){
			enableCheckBox.setSelected(true);
			makeEmailEnable();
		}else{
			enableCheckBox.setSelected(false);
			makeEmailDisable();
		}
		submitButton.addActionListener(new AddEmailAddressController(this));
		

		//Adds a new deck management tab when clicked
		deckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Opens a new deck management tab
				ViewEventController.getInstance().deckManagementTab();
			}
			//	}
		});
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
	/**
	 * Enables Email Capabilities
	 */
	public void makeEmailEnable(){
		submitButton.setEnabled(true);
		emailField.setEnabled(true);
		emailField.setOpaque(true);
	}
	/**
	 * Disables Email Capabilities
	 */
	public void makeEmailDisable(){
		submitButton.setEnabled(false);
		emailField.setEnabled(false);
		emailField.setOpaque(false);
	}
	public void setEmailModel(EmailAddressModel eModel){
		this.eModel = eModel;
	}
	public EmailAddressModel getEmailModel(){
		return eModel;
	}
	public String getEmailAddress(){
		return emailField.getText();
	}
	
	/**
	 * This updates the emailFields
	 */
	public void updateDisplay(){
		emailField.setText("");
		currentEmailLabel.setText(eModel.getAddress());
	}
}

/**
 * EmailRequestObserver class
 * @author Cosmic Latte
 * @version 6
 */
class GetEmailRequestObserver implements RequestObserver {
	
	private final PreferencesPanel panel;
	
	/**
	 * This constructor populates the controller variable with the instance of GetEmailController
	 * @param panel The panel to look at
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
	}

}

