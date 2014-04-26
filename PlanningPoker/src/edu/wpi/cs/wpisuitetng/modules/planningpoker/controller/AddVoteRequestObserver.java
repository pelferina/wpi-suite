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

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer watches over requests from the AddVoteController
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public class AddVoteRequestObserver implements RequestObserver {
	private final AddVoteController controller;

	/**
	 * This constructor creates the AddVoteRequestObserverobject
	 * @param addVoteController The addVoteController object to observe
	 */
	public AddVoteRequestObserver(AddVoteController addVoteController) {
		controller = addVoteController;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
