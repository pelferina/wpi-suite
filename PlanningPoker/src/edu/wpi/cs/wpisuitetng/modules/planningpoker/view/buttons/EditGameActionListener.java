package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class EditGameActionListener implements ActionListener{
	int gameID;
	public EditGameActionListener(int gameID){
		this.gameID = gameID;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		List<GameSession> games = GameModel.getInstance().getGames();
		for(GameSession g: games){
			if(g.getGameID() == gameID){
				ViewEventController.getInstance().editGameTab(g);
			}
		}
		
	}
	
}