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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameLivePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {

	private NewGameMainPanel newGame = new NewGameMainPanel();
	private final JPanel currentGame = new JPanel();
	private final JPanel pastGames = new JPanel();
	
	public MainView() {
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		addTab("New Game", newGame);
		addTab("Current Game", currentGame);
		addTab("Past Game", pastGames);
	}

}
