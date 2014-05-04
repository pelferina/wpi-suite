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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class deals with instances of the User
 * 
 * @author FFF8E7
 * @version 6
 */
public class GetAllUsers {
	private static GetAllUsers instance = null;
	private static User[] users = null;
	private static int testFlag = 0;
	
	/**
	 * This constructor creates an instance if one does not already exist
	 * @return the instance of GetCurrentUser
	 */
	public static GetAllUsers getInstance(){
		if (instance == null){
			instance = new GetAllUsers();
		}
		return instance;
	}
	
	/**
	 * This function enables testing mode
	 * It is only called for JUnit tests
	 */
	public void enableTesting(){
		testFlag = 1;
	}
	/**
	 * This method returns the current user
	 * @return the current user as User
	 */
	public User[] getAllUsers(){
		if (testFlag == 1){
			User[] testUsers = {new User("admin", "admin", "1234", 27)};
			return testUsers;
		}
		if(users == null){
			try{
				if(Network.getInstance().getDefaultNetworkConfiguration() != null){
					sendRequest();
					return users;
				}
			}
			catch(RuntimeException exception){
				System.err.println("Exception thrown in GetAllUsers:" + exception);
			}
		}
		else{
			return users;
		}
		return null;
	}
	private void sendRequest(){
		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET); // GET == read
		request.addObserver(new GetAllUsersRequestObserver()); 
		// add an observer to process the response
		request.send(); // send the request// add an observer to process the response
	}
	public void setUsers(User[] users){
		GetAllUsers.users = users;
	}

}
