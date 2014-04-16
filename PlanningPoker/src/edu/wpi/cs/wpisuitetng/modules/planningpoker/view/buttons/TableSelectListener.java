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
	private JTable table;

	public TableSelectListener(JTable table){
		this.table = table;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    int row =  table.getSelectedRow();
	    if(row < 0){ // The table is not selected
	    	ViewEventController.getInstance().setEndGameButtonInvisible();
	    	return;
	    }
	    JTableModel model = (JTableModel)table.getModel();
		int ownerID = model.getOwnerID(row);
    	int gameID = model.getGameID(row);
    	GameStatus status = model.getGameStatus(row);
    	User currentUser = GetCurrentUser.getInstance().getCurrentUser();
    	if(currentUser.getIdNum() == ownerID && (status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS))){
    		ViewEventController.getInstance().setEndGameButtonVisible(gameID);
    	}else{
    		ViewEventController.getInstance().setEndGameButtonInvisible();
    	}
	}

}
