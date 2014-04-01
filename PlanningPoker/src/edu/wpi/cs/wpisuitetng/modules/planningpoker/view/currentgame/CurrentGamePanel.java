/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;

/**
 * Description
 *
 * @author Ruofan Ding
 * @version Apr 1st
 */
@SuppressWarnings({"serial"})
public class CurrentGamePanel extends JPanel {

	private JButton btnRefresh;
	private JList<String> List = new JList<String>(); 
	private final JList activeGameList;
	private final GameModel allGameModel;
	private GameModel activeModel = new GameModel();


	public CurrentGamePanel(GameModel gameModel) {
		// Construct the list box model
		allGameModel = gameModel;
		activeGameList = new JList(allGameModel);
		// Change the font of the JList
		activeGameList.setFont(activeGameList.getFont().deriveFont(11));

		setPanel();
	}
	private void setPanel(){
		JScrollPane activeLstScrollPane = new JScrollPane(activeGameList);

		activeLstScrollPane.setPreferredSize(new Dimension(300,300));

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new GetGamesController(allGameModel));
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(activeLstScrollPane);
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(btnRefresh);
	}

}



