package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public abstract class AbsNewGamePanel extends JSplitPane{
	NewGameReqPanel newGameReqPanel;
	AbsNewGameInputPanel newGameInputPanel;

	
	public void updatePanels(Requirement requirement){
		newGameReqPanel.addReq(requirement);
	}
	
	public Requirement getSelectedRequirement(){
		return newGameReqPanel.removeSelected();
	}

}
