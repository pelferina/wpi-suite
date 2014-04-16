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
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Panel that displays the current game
 *
 * @author Ruofan Ding
 * @version $Revision: 1.0 $
 */
@SuppressWarnings({"serial"})
public class CurrentGamePanel extends JPanel {

	private JButton btnRefresh;
	private JButton btnEdit;
	private final JList activeGameList;
	private List<GameSession> listOfGames = new ArrayList<GameSession>();
	public CurrentGamePanel() {
		// Construct the list box model
		activeGameList = new JList( GameModel.getInstance());
		listOfGames = GameModel.getInstance().getGames();
		// Change the font of the JList
		activeGameList.setFont(activeGameList.getFont().deriveFont(11));

		setPanel();
	}
	/**
	 * This method sets the layout and designs the panel
	 */
	private void setPanel(){
		final SpringLayout springLayout = new SpringLayout();
		final JScrollPane activeLstScrollPane = new JScrollPane(activeGameList);
		
		activeLstScrollPane.setPreferredSize(new Dimension(1000,300));
		btnEdit = new JButton("Edit");
		btnRefresh = new JButton("Refresh");
		springLayout.putConstraint(SpringLayout.SOUTH, btnEdit, 30, SpringLayout.SOUTH, btnRefresh);
		btnEdit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				final int tempIndex = activeGameList.getSelectedIndex();
				if (tempIndex == -1)
					return;
				final GameSession tempGame = listOfGames.get(tempIndex);		
				ViewEventController.getInstance().editGameTab(tempGame);
			}	
		});
		btnRefresh.addActionListener(GetGamesController.getInstance());
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(activeLstScrollPane);
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(btnRefresh);
		add(btnEdit);
	}

}



