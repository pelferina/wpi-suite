/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 * 
 *
 */
public class UpdateGameController {
	
	private final GameModel model;
	//TODO Remove depreciated methods once GUI is updated for new data definitions
	/**
	 * Construct an UpdateMessageController for the given model
	 * @param model the model containing the messages
	 */
	public UpdateGameController(GameModel model){
		this.model = model;
	}
	public void sendMessage(GameSession ToSend){
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // PUT == create
		request.setBody(ToSend.toJSON()); // put the new session in the body of the request
		request.addObserver(new UpdateGameRequestObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	/**
	 * When the new message is received back from the server, add it to the local model.
	 * @param message
	 */
	public void addMessageToModel(GameSession game) {
		model.addGame(game);
	}

}
