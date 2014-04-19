/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Submit button by
 * adding the contents of the message text field to the model as a new
 * message.
 * 
 * @author Cosmic Latte
 * @version $Requirement: 1.0 $
 */
public class AddEmailAddressController implements ActionListener {
	private final JTextField view;
	
	/**
	 * Construct an AddEmailAddressController for the given model, view pair
	 * 
	 * @param view the view where the user enters new messages
	 */
	public AddEmailAddressController(JTextField view) {
		this.view = view;
	}
	
	/**
	 * Construct an AddEmailAddressController for the given model, view pair
	 * @param model the model containing the messages
	 * @param view the view where the user enters new messages
	 */
	public AddEmailAddressController() {
		view = null;
	}

	/* *
	 * TODO implement once ui has been finalized
	 * This method is called when the user clicks the Submit button
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println("Submit been pressed");
		if (view == null)
		{
			return;
		}
		
		saveEmail(view.getText());
	}

	/**
	 * This method creates a request to send and email, and adds an observer
	 * @param address The address to send to
	 */
	public void saveEmail(String address)
	{
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.PUT); // PUT == create
		//request.setBody(new EmailAddressModel(address).toJSON()); // put the new message in the body of the request
		request.setBody(address);
		request.addObserver(new AddEmailAddressObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
}
