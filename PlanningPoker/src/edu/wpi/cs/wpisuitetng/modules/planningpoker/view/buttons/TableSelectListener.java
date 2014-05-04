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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.JTableModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * This is a table listener for determining when a table is selected
 * 
 * @author FFF8E7
 * @version 6
 */
public class TableSelectListener implements ListSelectionListener{
	private final JTable table;

	/**
	 * Constructor for TableSelectListener
	 * @param table the table to listen to
	 */
	public TableSelectListener(JTable table){
		this.table = table;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    int row =  table.getSelectedRow();
	    if(row < 0){ // The table is not selected
	    	/*
	    	ViewEventController.getInstance().makeOwnerButtonInvisible();
	    	ViewEventController.getInstance().makeUserButtonInvisible();
	    	ViewEventController.getInstance().makeEditGameButtonInVisible();
	    	*/
	    	ViewEventController.getInstance().removeButtons();
	    	return;
	    }
	    row = table.convertRowIndexToModel(row);
	    final JTableModel model = (JTableModel)table.getModel();
	    final GameSession gameSelected = model.getGame(row);
	    
	    /*
		final int ownerID = model.getOwnerID(row);
    	final int gameID = model.getGameID(row);
    	final GameStatus status = gameSelected.getGameStatus();
    	final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
    	*/
	    
	    
    	ViewEventController.getInstance().removeButtons();
    	ViewEventController.getInstance().changeButton(gameSelected);
    	
    	/*
    	final boolean hasCategory = false;
    	if(currentUser.getIdNum() == ownerID){
    		if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS)){
    			ViewEventController.getInstance().makeEndGameButtonVisible(gameSelected);
    			//hasCategory = true;
    		}else if(status.equals(GameStatus.DRAFT)){
    			if(isValid(gameSelected)){
    				ViewEventController.getInstance().makeActivateGameButtonVisible(gameSelected);
    			}else{
    				System.out.println("disable the button");
    				ViewEventController.getInstance().makeActivateGameButtonDisable(gameSelected);
    			}
    			//hasCategory = true;
    		}else if(status.equals(GameStatus.COMPLETED)){
    			ViewEventController.getInstance().makeArchiveGameButtonVisible(gameSelected);
    			//hasCategory = true;
    		}
    		
    		if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.DRAFT)){
    			ViewEventController.getInstance().makeEditGameButtonVisible(gameSelected);
    		}else{
    			ViewEventController.getInstance().makeEditGameButtonInVisible();
    		}
    	}else{
    		ViewEventController.getInstance().makeOwnerButtonInvisible();
    		ViewEventController.getInstance().makeEditGameButtonInVisible();
    	}
    	
    	if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS)){
			ViewEventController.getInstance().makeVoteGameButtonVisible(gameSelected);
		}else if(status.equals(GameStatus.ARCHIVED) || status.equals(GameStatus.COMPLETED)){
			ViewEventController.getInstance().makeViewGameButtonVisible(gameSelected);
		}else {
			ViewEventController.getInstance().makeUserButtonInvisible();
		}
		*/
	}
	/**
	 * Checks if the GameSession is valid
	 * @param gs the GameSession to check
	 * @return true if valid, false otherwise
	 */
	public boolean isValid(GameSession gs) { // if all neccessary fields are filled out, returns true
		if (gs.getGameName().length() > 0 && gs.getGameReqs().size() > 0){
			if (gs.getEndDate() == null || gs.getEndDate().getTime() > System.currentTimeMillis()){
				return true;
			}
		}
		return false;
	}

}
