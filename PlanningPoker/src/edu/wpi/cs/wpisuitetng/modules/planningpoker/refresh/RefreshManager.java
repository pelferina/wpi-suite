package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;



/*
 * Refresh Class
 * 	all controllers
 * 		cache for each controller
 * 		methods to diff cache and local model
 * 
 * Every Controller
 * 	List of refreshable panels
 *	Add Refreshable Method
 *	Remove Refreshable
 *
 * Each Panel
 * 	Refresh Method
 * 
 * Refreshable Interface
 *
 * 
 */




public class RefreshManager {
	
	GetGamesController gameController;
	GetRequirementsController reqController;
	List<Requirement> reqCache;
	List<GameSession> gameCache;
	
	private RefreshManager() {
		gameController = GetGamesController.getInstance();
		reqController = GetRequirementsController.getInstance();
		
		reqCache = RequirementModel.getInstance().getRequirements();
		gameCache = GameModel.getInstance().getGames();
		
		//Create action listener for timer
		final ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateAllControllers();
					}
				}

				catch(RuntimeException exception){
					System.err.println(exception.getMessage());
				}
			}
		};
		
		// Timer will update RefreshManager every 2 seconds
		Timer t = new Timer(2000, actionListener);
		t.start();
	}
	
	private void updateAllControllers()
	{
		gameController.actionPerformed(null);
		reqController.actionPerformed(null);	
		
		diffRequirements();
		
		
		gameCache = GameModel.getInstance().getGames();
		reqCache = RequirementModel.getInstance().getRequirements();
		
	}
	
	/**
	 * 
	 * @return true if changes have been made to the requirement model, false otherwise
	 */
	private boolean diffRequirements()
	{
		if (reqCache == null)
			return false;
		if (reqCache.size() != RequirementModel );
	}
	


}
