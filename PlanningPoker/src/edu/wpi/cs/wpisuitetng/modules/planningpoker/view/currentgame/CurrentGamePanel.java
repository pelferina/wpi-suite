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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Description
 *
 * @author Ruofan Ding
 * @version Apr 1st
 */
@SuppressWarnings({"serial"})
public class CurrentGamePanel extends JPanel {

	private JButton btnRefresh;
	private JButton btnEdit;
	private JList<String> List = new JList<String>(); 
	private final JList activeGameList;
	private final GameModel allGameModel;
	private GameModel activeModel = GameModel.getInstance();


	public CurrentGamePanel(GameModel gameModel) {
		// Construct the list box model
		allGameModel = gameModel;
		activeGameList = new JList(allGameModel);
		// Change the font of the JList
		activeGameList.setFont(activeGameList.getFont().deriveFont(11));

		setPanel();
	}
	private void setPanel(){
		SpringLayout springLayout = new SpringLayout();
		JScrollPane activeLstScrollPane = new JScrollPane(activeGameList);
		
		activeLstScrollPane.setPreferredSize(new Dimension(1000,300));
		btnEdit = new JButton("Edit");
		btnRefresh = new JButton("Refresh");
		springLayout.putConstraint(SpringLayout.SOUTH, btnEdit, 30, SpringLayout.SOUTH, btnRefresh);
		btnEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().createNewGameTab(true);
			}	
		});
		btnRefresh.addActionListener(new GetGamesController(allGameModel));
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(activeLstScrollPane);
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(btnRefresh);
		add(btnEdit);
	}

}



