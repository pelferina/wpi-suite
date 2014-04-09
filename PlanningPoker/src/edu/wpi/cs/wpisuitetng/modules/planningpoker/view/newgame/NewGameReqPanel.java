/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * This class shows the requirements that are currently in the game
 * @author Anthony Dresser
 * @version March 23, 2014
 */
@SuppressWarnings("serial")
public class NewGameReqPanel extends JTabbedPane {

	DefaultListModel<String> listValue;
	private final JList<String> reqList;
	private final JPanel listPanel = new JPanel();
	private final JLabel gameReqs = new JLabel("Requirements in the game");
	private List<Requirement> selected = new ArrayList<Requirement>();
	
	public NewGameReqPanel()
	{
		listValue = new DefaultListModel<String>();
		reqList = new JList<String>(listValue);
		setupPanel();
	}
	
	private void setupPanel()
	{
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		listPanel.add(reqList);
		addTab("Game Requirements", listPanel);
	}
	/**
	 * Takes a requirement and adds it to its reqList
	 * @param req
	 */
	public void addReq(Requirement req){
		selected.add(req);
		listValue.addElement(req.getName());
	}
	
	public Requirement removeSelected(){
		int tempIndex = reqList.getSelectedIndex();
		if (tempIndex == -1)
			return null;
		Requirement tempReq = selected.get(tempIndex);
		listValue.remove(tempIndex);
		selected.remove(tempIndex);
		return tempReq;
		
	}
}
