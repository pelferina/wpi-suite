/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

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
		final ActionListener gameCheck = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateGames();
					}
				}

				catch(RuntimeException exception){
					//System.err.println(exception.getMessage());
				}
			}
		};
		//Create action listener for timer
		final ActionListener reqCheck = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						updateRequirements();
					}
				}

				catch(RuntimeException exception){
					//System.err.println(exception.getMessage());
				}
			}
		};
		
		// Timer will update RefreshManager every 2 seconds
		Timer g = new Timer(2000, gameCheck);
		Timer r = new Timer(2000, reqCheck);
		g.start();
		r.start();
	}
	
	private void updateGames()
	{
		//Make a request to the database
		gameController.actionPerformed(null);
	
		if ( differentList(gameCache, GameModel.getInstance().getGames())){
			gameController.refresh();
			gameCache = new ArrayList<GameSession>(GameModel.getInstance().getGames());
		}
	}
	
	private void updateRequirements()
	{
		//Make a request to the database
		reqController.actionPerformed(null);	

		
		if (differentList(reqCache, RequirementModel.getInstance().getRequirements())){
			reqController.refresh();
			reqCache = new ArrayList<Requirement>(RequirementModel.getInstance().getRequirements());
		}	
	}
	
	
	//Assumes list are in the same order
	public boolean differentList(List<?> l1, List<?> l2) {
		if (l1.size() != l2.size() )
			return true;
		
	    for (int i = 0; i < l1.size(); i++) 
	    	if ( ! l1.get(i).equals(l2.get(i) ))
	        	return true;
	    
	    return false;
	}

}
