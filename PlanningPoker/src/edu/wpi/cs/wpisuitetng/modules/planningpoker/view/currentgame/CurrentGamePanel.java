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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;

/**
 * Description
 *
 * @author Xi Wen;Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings({"serial"})
public class CurrentGamePanel extends JPanel {
	
	private String[] testList = {"test1", "test2"};
	private JButton btnRefresh;
	private JList<String> List = new JList<String>(); 
	
	private final JList activeGameList;
	private final JList expiredGameList;
	private final GameModel allGameModel;
	private GameModel activeModel = new GameModel();
	private GameModel expiredModel = new GameModel();
	

	public CurrentGamePanel(GameModel gameModel) {
		// Construct the list box model
		allGameModel = gameModel;
		
		// Construct the components to be displayed
		activeGameList = new JList(activeModel);
		expiredGameList = new JList(expiredModel);
		
		// Change the font of the JList
		activeGameList.setFont(activeGameList.getFont().deriveFont(11));
		expiredGameList.setFont(expiredGameList.getFont().deriveFont(11));
		
		setPanel();
	}
	
	private void setPanel(){
		
		
		JScrollPane activeLstScrollPane = new JScrollPane(activeGameList);
		JScrollPane expiredLstScrollPane = new JScrollPane(expiredGameList);
        activeLstScrollPane.setPreferredSize(new Dimension(300,300));
        expiredLstScrollPane.setPreferredSize(new Dimension(300,300));

		
		btnRefresh = new JButton("Refresh");
		
		btnRefresh.addActionListener(new GetGamesController(allGameModel, expiredModel, activeModel));

		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
        add(activeLstScrollPane);
        add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
        add(expiredLstScrollPane);
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(btnRefresh);
		
	}
}
