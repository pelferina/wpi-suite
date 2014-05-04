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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * The EditGameActionListener that implements ActionListener
 * @author fff8e7
 * @version 6
 */
public class EditGameActionListener implements ActionListener{
	GameSession game;
	/**
	 * This listener watches for game edit requests
	 * @param gameSelected The selected GameSession
	 */
	public EditGameActionListener(GameSession gameSelected){
		game = gameSelected;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ViewEventController.getInstance().editGameTab(game);
	}
	
}