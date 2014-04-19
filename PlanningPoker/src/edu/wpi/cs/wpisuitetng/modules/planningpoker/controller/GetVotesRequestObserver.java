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
