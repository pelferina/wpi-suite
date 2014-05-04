/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team FFF8E7
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user;



import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
* This observer handles responses to requests for current user
 * @author FFF8E7
 * @version 6
 */
public class GetAllUsersRequestObserver implements RequestObserver {
	
	private final GetAllUsers getUsers;
	
	public GetAllUsersRequestObserver() {
		getUsers = GetAllUsers.getInstance();
	}

	/**
	* Convert a json String to a list of user, because this method should have been implemented in User class.
	* @param json This method takes in a String json
	* @return User[] from json
	*/
	public User[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, User[].class);
	}
	
	/**
	* Parse the users out of the response body and pass them to the controller
	**/
	@Override
	public void responseSuccess(IRequest iReq) {
		final User[] userList = fromJsonArray(iReq.getResponse().getBody());
		getUsers.setUsers(userList);
	}

	
	/**
	* @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	*/
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}
	
	/**
	* placeholder for exception handling
	* TODO Add swanky error handling.
	* @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	*/
	@Override
	public void fail(IRequest iReq, Exception exception) {
		if(exception == null) System.err.println("FAILURE in GetAllUserRequestObserver! Maybe there's something wrong in server");
		exception.printStackTrace();
		getUsers.setUsers(null);
	}

}