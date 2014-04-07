package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.network.Network;

public class JTableModel extends AbstractTableModel {

	protected Object[][] Data; 	// Create new data array with the "X" position as each sessionData
								// and the "Y" as each Table spot
	protected int size =0;
    protected static final String[] COLUMN_NAMES = new String[] {"ID", "Name", "Deadline", "Owner", "Progress", "Status"};
    protected static final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, String.class, Calendar.class, String.class, String.class, String.class};
    
    public JTableModel(GameSession[] sessions){
    	
    	Data = new Object[COLUMN_NAMES.length][sessions.length];
    	size = sessions.length;
    	for (int i=0; i<sessions.length; i++){
    		Object[] curRow = {sessions[i].getGameID(), sessions[i].getGameName(),
    							sessions[i].getEndDate(), ""+sessions[i].getOwnerID(), 
    							"To Be Implemented", // Progress
    							sessions[i].getGameStatus()
    							};
    		
    		Data[i] = curRow;
    	}
    	
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
            	
            case 6: JButton button = new JButton(COLUMN_NAMES[columnIndex]);
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            ButtonAction();
                        }
                    });
                    return button;
            default: return "Error";
        }
    }

	protected Object ButtonAction() {
		// TODO Auto-generated method stub
		return null;
	}   
}