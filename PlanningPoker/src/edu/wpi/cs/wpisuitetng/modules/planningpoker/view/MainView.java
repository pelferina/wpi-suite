/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.CurrentGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameDistributedPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {

	private NewGameDistributedPanel newGame;
	private final JPanel currentGame;
	private final JPanel pastGames;
	
	public MainView(GameModel gameModel, boolean hasNewGame) {
		newGame = new NewGameDistributedPanel(gameModel);
		currentGame = new CurrentGamePanel(gameModel);
		pastGames = new JPanel();
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		if (hasNewGame == true){
			addTab("New Game", newGame);
		}
		addTab("Current Game", currentGame);
		addTab("Past Game", pastGames);
	}

}
