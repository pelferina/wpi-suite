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
	private String username;
	private boolean enable;
	
	/**
	 * Constructs a new email model with a provided address
	 * Sets userID to -1 until on server side
	 * 
	 *  @param address This is the address to send the email to
	 */
	public EmailAddressModel(String address, String username, boolean enable) {
		this.address = address;
		this.username = username;
		this.enable = enable;
	}

	/**
	 * Retrieves the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Retrieves the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * REtrieves the enable
	 * @return
	 */
	public boolean getEnable(){
		return this.enable;
	}

	/**
	 * sets the address
	 * @param address a string address to be set
	 * @return EmailAddressModel, the email address that is being set
	 */
	public EmailAddressModel setAddress(String address) {
		this.address = address;
		return this;
	}



	/**
	 * sets the username
	 * @param username the user name to be set, String
	 * @return EmailAddressModel, the EmailAddressModel that the user is being set for
	 */
	public EmailAddressModel setUsername(String username) {
		this.username = username;
		return this;
	}
	/**
	 * set the sending email enable or disable
	 * @param value
	 * @return EmailAddressModel, the EmailAddressModel that the user is being set for
	 */
	public EmailAddressModel setEnable(boolean value){
		this.enable = value;
		return this;
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
	/**
	 * Returns an array of EmailAddressModel parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of GameSession
	 * @return an array of GameSession deserialzied from the given json string
	 */
	public static EmailAddressModel[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, EmailAddressModel[].class);
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
	public Boolean identify(Object o) {
		return null;
	}
}
