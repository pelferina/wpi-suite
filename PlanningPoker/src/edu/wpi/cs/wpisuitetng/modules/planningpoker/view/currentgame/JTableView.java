/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;

/**
 * The view for the table of game sessions
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class JTableView extends JScrollPane {
	private JTable table;
	public JTableView(){
		GameSession[] blank = {};
		table = new JTable(new JTableModel(blank));
		setViewportView(table);
		add(table);
	}
	/**
	 * Constructor to create the table view with an array of sessions
	 * @param sessions
	 */
	public JTableView(GameSession[] sessions) {
		table = new JTable(new JTableModel(sessions));
		setViewportView(table);
		add(table);
	}

}
