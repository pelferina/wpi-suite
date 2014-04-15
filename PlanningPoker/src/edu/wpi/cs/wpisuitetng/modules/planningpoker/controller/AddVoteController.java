package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddVoteController {

	private final VoteModel model;

	public AddVoteController(VoteModel model) {
		this.model = model;
	}
	
	/** Sends a new vote obejct to the servah
	 * @param v the vote to be sent
	 */
	public void sendVote(Vote v){
		final Request request = Network.getInstance().makeRequest("planningpoker/vote", HttpMethod.PUT); // PUT == create
		request.setBody(v.toJSON()); // put the new session in the body of the request
		request.addObserver(new AddVoteRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	public VoteModel getModel() {
		return model;
	}

}
