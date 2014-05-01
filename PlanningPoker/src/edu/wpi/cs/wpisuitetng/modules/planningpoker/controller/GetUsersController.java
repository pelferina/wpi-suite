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




import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the core users from the server.
 *
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class GetUsersController {
	
	private static GetUsersController instance = null;
	
	/**
	 * This method returns the instance of GetUsersController, or creates one if one does not exist
	 * 
	 * @return the instance of GetUsersController
	 */
	public static GetUsersController getInstance(){
		if (instance == null){
			instance = new GetUsersController(new User[0]);
		}
		
		return instance;
	}

	protected User[] userList;

	/**
	 * This constructor populates the userList with the given input
	 * @param userList the UserList[] to control 
	 */
	public GetUsersController(User[] userList) {
		this.userList = userList;
	}
	
	/**
	 * This getter returns userList as a User[]
	 * @return userList as a User[]
	 */
	public User[] getUsers(){
		return userList;
	}

	public String getUserFromName(String userName){
		for (User u : userList){
			if (u.getUsername().equals(userName)){
				return u.getName();
			}
		}
		return "";
	}

	/**
	 * This method is called to send a request to the GetUsersRequestObserver
	 */
	public void actionPerformed() {
		// Send a request to the core to save this message
		
		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET); // GET == read
		request.addObserver(new GetUsersRequestObserver()); 
		// add an observer to process the response
		request.send(); // send the request
		
	}

	/**
	 * This method is called by the GetUsersRequestObserver
	 * 
	 * @param userList an array of users received from the server
	 */
	public void receivedUserList(User[] userList) {

		// Make sure the response was not null
		this.userList = userList;

	}
}