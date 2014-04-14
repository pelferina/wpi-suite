package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;
import edu.wpi.cs.wpisuitetng.network.Network;

public class JTableModel extends AbstractTableModel {

	protected Object[][] Data; 	// Create new data array with the "X" position as each sessionData
								// and the "Y" as each Table spot
	protected Integer[] gameIDs;
	protected GetUsersController guc;
	protected User[] users;
	protected int size =0;
    protected static final String[] COLUMN_NAMES = new String[] {"Name", "Deadline", "Owner", "Progress", "Status"};
    protected static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, String.class, String.class, String.class};
    
    public JTableModel(ArrayList<GameSession> sessions){
    	this(sessions.toArray(new GameSession[0]));
    }

	public JTableModel(GameSession[] sessions){
		users = null;
		guc = GetUsersController.getInstance();
    	setUpTable(sessions);
    }
	
	private void setUpTable(GameSession[] sessions){
		
		Data = new Object[sessions.length][COLUMN_NAMES.length];
		gameIDs = new Integer[sessions.length];
    	size = sessions.length;
    	for (int i=0; i<sessions.length; i++){
    		Object[] curRow = {sessions[i].getGameName(),
    							sessions[i].getEndDate()!=null ? sessions[i].getEndDate().toString() : "No Deadline", 
    							getUserFromID(sessions[i].getOwnerID()), 
    							"To Be Implemented", // Progress
    							sessions[i].getGameStatus().name()
    							};
    		
    		Data[i] = curRow;
    		gameIDs[i] = sessions[i].getGameID();
    	}
	}
    
	private String getUserFromID(int userID){
		guc.actionPerformed();
		while (guc.getUsers() == null){
			try{
				Thread.sleep(10);
				System.out.println("Waiting for users");
			}
			catch(Exception e){
				
			}
		}
		users = guc.getUsers();
		for (User u : users){
			if (u.getIdNum() == userID){
				return u.getName();
			}
		}
		return "";
	}

    @Override public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override public int getRowCount() {
        return size;
    }

    @Override public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
       	if (rowIndex>=size)
    		return "";
    	
            /*Adding components*/
        switch (columnIndex) {
            case 0: 
            case 1:
            case 2: 
            case 3:
            case 4:
            case 5:
            	return Data[rowIndex][columnIndex];
            	
            default: return "Error";
        }
    }

	public void update(GameSession[] sessions){
		setUpTable(sessions);
	}
	public void update(ArrayList<GameSession> sessions){
		setUpTable(sessions.toArray(new GameSession[0]));
	}
	
	public int getIDFromRow(int row){
		return gameIDs[row];
	}
}