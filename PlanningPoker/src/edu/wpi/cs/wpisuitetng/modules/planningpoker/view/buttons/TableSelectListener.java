/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class TableSelectListener implements ListSelectionListener{
	private final JTable table;

	public TableSelectListener(JTable table){
		this.table = table;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    final int row =  table.getSelectedRow();
	    if(row < 0){ // The table is not selected
	    	ViewEventController.getInstance().setEndGameButtonInvisible();
	    	return;
	    }
	    final JTableModel model = (JTableModel)table.getModel();
		final int ownerID = model.getOwnerID(row);
    	final int gameID = model.getGameID(row);
    	final GameStatus status = model.getGameStatus(row);
    	final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
    	if(currentUser.getIdNum() == ownerID && (status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS))){
    		ViewEventController.getInstance().setEndGameButtonVisible(gameID);
    	}else{
    		ViewEventController.getInstance().setEndGameButtonInvisible();
    	}
	}

}
