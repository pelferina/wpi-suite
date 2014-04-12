package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	ArrayList<Requirement> reqCache;
	ArrayList<GameSession> gameCache;
	
	public RefreshManager() {
		gameController = GetGamesController.getInstance();
		reqController = GetRequirementsController.getInstance();
		
		reqCache = new ArrayList<Requirement>();
		gameCache =  new ArrayList<GameSession>();
		
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
		
		
		//System.out.println("RefreshManager polling for updates");
		if ( differentList(gameCache, GameModel.getInstance().getGames())){
			System.out.println(" GameController\'s reliant objects are out of date");
			gameController.refresh();
			
			
		}
		else if (differentList(reqCache, RequirementModel.getInstance().getRequirements())){
			System.out.println(" RequirementController\'s reliant objects are out of date");
			reqController.refresh();
			
			
		}	
		else{			
			// everything up to date!
			
		}
		
		gameCache = new ArrayList<GameSession>(GameModel.getInstance().getGames());
		reqCache = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		
	}
	
	//Assumes list are in the same order
	public boolean differentList(List<?> l1, List<?> l2) {
		System.out.println(" Diffing "+l1.toString()+"\n"+l2.toString());

		
		if (l1.size() != l2.size() )
			return true;
		
	    for (int i = 0; i < l1.size(); i++) 
	    	if ( ! l1.get(i).equals(l2.get(i) ))
	        	return true;
	    
	    return false;
	}

}
