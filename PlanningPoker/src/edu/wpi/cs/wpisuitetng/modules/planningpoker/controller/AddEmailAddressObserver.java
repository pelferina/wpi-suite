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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add an email.
 * 
 * @author Chris Casola
 *
 */
public class AddEmailAddressObserver implements RequestObserver {
	
	private final AddEmailAddressController controller;
	
	public AddEmailAddressObserver(AddEmailAddressController controller) {
		this.controller = controller;
	}
	
	/**
	 * Implementable later if functionality is required.
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		return;
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add an email failed");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a email failed");
	}

}
