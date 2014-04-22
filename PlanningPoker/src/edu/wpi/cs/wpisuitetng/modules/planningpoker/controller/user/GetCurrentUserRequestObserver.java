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
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
* This observer handles responses to requests for current user
* @author Cosmic Latte
* @version $Revision: 1.0 $
*/
public class GetCurrentUserRequestObserver implements RequestObserver {
	
	private final GetCurrentUser getUser;
	
	public GetCurrentUserRequestObserver() {
		getUser = GetCurrentUser.getInstance();
	}

	
	/**
	* Parse the messages out of the response body and pass them to the controller
	**/
	@Override
	public void responseSuccess(IRequest iReq) {
		final User user = User.fromJSON(iReq.getResponse().getBody());
		getUser.setUser(user);
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
		if(exception == null) System.err.println("FAILURE in GetCurrentUserRequestObserver! Maybe there's something wrong in server");
		exception.printStackTrace();
		getUser.setUser(null);
	}

}