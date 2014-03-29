package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.JSplitPane;

public abstract class AbsNewGamePanel extends JSplitPane{
	NewGameReqPanel newGameReqPanel;
	AbsNewGameInputPanel newGameInputPanel;

	
	public void updatePanels(String requirement){
		newGameReqPanel.addReq(requirement);
	}

}
