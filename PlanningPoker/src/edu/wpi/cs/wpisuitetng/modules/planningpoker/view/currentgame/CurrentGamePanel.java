/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameInputLivePanel;

import javax.swing.SpringLayout;

/**
 * Description
 *
 * @author Xi Wen;Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings({"serial"})
public class CurrentGamePanel extends JPanel {
	
	private String[] testList = {"test1", "test2"};
	private JList<String> List = new JList<String>(); 
	
	private final JList gameList;
	private final GameModel lstGameModel;
	

	public CurrentGamePanel(GameModel gameModel) {
		// Construct the list box model
		lstGameModel = gameModel;
		// Construct the components to be displayed
		gameList = new JList(lstGameModel);
		// Change the font of the JList
		gameList.setFont(gameList.getFont().deriveFont(11));
		setPanel();
		// Put the listbox in a scroll pane
//		JScrollPane lstScrollPane = new JScrollPane(gameList);
//		lstScrollPane.setPreferredSize(new Dimension(500,400));
//		add(lstScrollPane);
	}
	
	private void setPanel(){
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, List, 44, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, List, 64, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, List, 256, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, List, 386, SpringLayout.WEST, this);
		setLayout(springLayout);
		List.setListData(testList);
		add(List);
	}
}
