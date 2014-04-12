package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
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
	
	public RefreshManager() {
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
		//Make a request to the database
		gameController.actionPerformed(null);
		reqController.actionPerformed(null);	
		
		
		System.out.println("RefreshManager checking for updates");
		if ( differentList(gameCache, GameModel.getInstance().getGames())){
			System.out.println("Difference found in game cache");
			gameController.refresh();
			
			
		}
		else if (differentList(reqCache, RequirementModel.getInstance().getRequirements())){
			System.out.println("Difference found in req cache");
			reqController.refresh();
			
			
		}	
		else{
			System.out.println("Cache up to date");
			
			
		}
		
		gameCache = GameModel.getInstance().getGames();
		reqCache = RequirementModel.getInstance().getRequirements();
		
	}
	
	//Assumes list are in the same order
	private boolean differentList(List<?> l1, List<?> l2) {
		if (l1.size() != l2.size() )
			return true;
		
	    for (int i = 0; i < l1.size(); i++) 
	    	if ( ! l1.get(i).equals(l2.get(i) ))
	        	return true;
	    
	    return false;
	}

}
