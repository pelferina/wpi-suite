/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * 
 * @author fff8e7
 * @version $Revision: 1.0 $
 */
public class EditGameActionListener implements ActionListener{
	int gameID;
	/**
	 * This listener watches for game edit requests
	 * @param gameID the integer game id to edit
	 */
	public EditGameActionListener(int gameID){
		this.gameID = gameID;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		final List<GameSession> games = GameModel.getInstance().getGames();
		for(GameSession g: games){
			if(g.getGameID() == gameID){
				ViewEventController.getInstance().editGameTab(g);
			}
		}
		
	}
	
}