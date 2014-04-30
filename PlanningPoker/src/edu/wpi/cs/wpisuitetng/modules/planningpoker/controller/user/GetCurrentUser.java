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
public class GetCurrentUser {
	private static GetCurrentUser instance = null;
	private static User user = null;
	
	/**
	 * This constructor creates an instance if one does not already exist
	 * @return the instance of GetCurrentUser
	 */
	public static GetCurrentUser getInstance(){
		if (instance == null){
			instance = new GetCurrentUser();
		}
		return instance;
	}
	/**
	 * This method returns the current user
	 * @return the current user as User
	 */
	public User getCurrentUser(){
		if(user == null){
			try{
				if(Network.getInstance().getDefaultNetworkConfiguration() != null){
					sendRequest();
					return user;
				}	
			}
			catch(RuntimeException exception){
				// Session not yet created
				//System.err.println("Exception thrown in GetCurrentUser:" +exception);
			}
		}
		else{
			return user;
		}
		return null;
	}
	private void sendRequest(){
		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.PUT); 
		request.setBody("who am I?");
		request.addObserver(new GetCurrentUserRequestObserver()); 
		// add an observer to process the response
		request.send(); // send the request	
	}
	public void setUser(User user){
		GetCurrentUser.user = user;
	}
}
