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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This is a model for email addresses. 
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings({"serial"})
public class EmailAddressModel extends AbstractModel {
	
	/** The list of messages on the board */
	private String address;
	private int userID;
	
	/**
	 * Constructs a new email model with a provided address
	 * Sets userID to -1 until on server side
	 * 
	 *  @param address This is the address to send the email to
	 */
	public EmailAddressModel(String address) {
		this.address = address;
		this.userID = -1;
	}

	/**
	 * Adds the given message to the board
	 * 
	 * @param address the email address to use
	 */
	public void updateEmail(String address) {
		this.address = address;
	}

	/**
	 * Retrieves the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * sets the address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Retrieves the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * sets the userID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Returns a JSON-encoded string representation of this message object
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, EmailAddressModel.class);
	}

	/**
	 * Returns an instance of EmailAddressMessage constructed using the given
	 * PostBoardMessage encoded as a JSON string.
	 * 
	 * @param json the json-encoded EmailModel to deserialize
	 * @return the EmailModel contained in the given JSON
	 */
	public static EmailAddressModel fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, EmailAddressModel.class);
	}
	

	/*
	 * The methods below are required by the model interface, however they
	 * do not need to be implemented for a basic model like EmailModel. 
	 */

	@Override
	public void save() {}

	@Override
	public void delete() {}

	@Override
	public Boolean identify(Object o) {return null;}
}
