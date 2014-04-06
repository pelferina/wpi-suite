/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * adding the contents of the message text field to the model as a new
 * message.
 * 
 * @author Chris Casola
 *
 */
public class AddEmailAddressController implements ActionListener {
	private final JPanel view;
	
	/**
	 * Construct an AddEmailAddressController for the given model, view pair
	 * @param model the model containing the messages
	 * @param view the view where the user enters new messages
	 */
	public AddEmailAddressController(JPanel view) {
		this.view = view;
	}
	
	/**
	 * Construct an AddEmailAddressController for the given model, view pair
	 * @param model the model containing the messages
	 * @param view the view where the user enters new messages
	 */
	public AddEmailAddressController() {
		this.view = null;
	}

	/* *
	 * TODO implement once ui has been finalized
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (view == null)
		{
			return;
		}
		
//		// Get the text that was entered
//		String address = view.getEmail().getText();
//		
//		// Make sure there is text
//		if (message.length() > 0) {
//			// Clear the text field
//			view.getTxtNewMessage().setText("");
//			
//			// Send a request to the core to save this message
//			final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.PUT); // PUT == create
//			request.setBody(new EmailModel(address).toJSON()); // put the new message in the body of the request
//			request.addObserver(new AddMessageRequestObserver(this)); // add an observer to process the response
//			request.send(); // send the request
//		}
	}

	public void sendEmail(String address)
	{
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.PUT); // PUT == create
		request.setBody(new EmailAddressModel(address).toJSON()); // put the new message in the body of the request
		request.addObserver(new AddEmailAddressObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
}
