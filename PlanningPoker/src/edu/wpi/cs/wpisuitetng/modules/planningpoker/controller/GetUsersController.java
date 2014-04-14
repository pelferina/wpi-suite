/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team FFF8E7
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the core users from the server.
 * 
 * @author Ruofan Ding
 * 
 */
public class GetUsersController {
	
	private static GetUsersController instance = null;
	
	public static GetUsersController getInstance(){
		if (instance == null){
			instance = new GetUsersController(new User[0]);
		}
		
		return instance;
	}

	protected User[] userList;

	public GetUsersController(User[] userList) {
		this.userList = userList;
	}
	
	public User[] getUsers(){
		return userList;
	}


	public void actionPerformed() {
		// Send a request to the core to save this message
		
<<<<<<< HEAD
		 int ii = 100000;
    	 int jj;
    	while(ii != 0){jj = ii*ii;ii=ii-1;}
		
		final Request request = Network.getInstance().makeRequest("core/user",
				HttpMethod.GET); // GET == read
		request.addObserver(new GetUsersRequestObserver(this)); // add an
																// observer to
																// process the
																// response
=======
		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET); // GET == read
		request.addObserver(new GetUsersRequestObserver()); 
		// add an observer to process the response
>>>>>>> cd1010f974bc3d98ced115fabcf23ecd13677661
		request.send(); // send the request
		
	}

	/**
	 * This method is called by the GetUsersRequestObserver
	 * 
	 * @param an array of users received from the server
	 */
	public void receivedUserList(User[] userList) {

		// Make sure the response was not null
		this.userList = userList;

	}
}