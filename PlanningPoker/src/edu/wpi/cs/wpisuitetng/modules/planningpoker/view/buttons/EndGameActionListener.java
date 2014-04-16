package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class EndGameActionListener implements ActionListener{
	int gameID;
	public EndGameActionListener(int gameID){
		this.gameID = gameID;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		List<GameSession> games = GameModel.getInstance().getGames();
		for(GameSession g: games){
			if(g.getGameID() == gameID){
				g.setGameStatus(GameStatus.COMPLETED);
				final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // POST == UPDATE
				request.setBody(g.toJSON()); // put the new session in the body of the request
				request.addObserver(new UpdateGameRequestObserver()); // add an observer to process the response
				request.send(); // send the request
			}
		}
		
	}
	
}