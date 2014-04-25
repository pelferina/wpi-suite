/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class GetVotesRequestObserver implements RequestObserver {

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		GetVoteController.getInstance().receivedGames(Vote.fromJsonArray(iReq.getResponse().getBody()));
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Error Retrieving Votes");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		System.err.println("Error Retrieving Votes");
		if(exception != null) {
			exception.printStackTrace();
		}
	}

}
