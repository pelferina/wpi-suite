package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

@SuppressWarnings("serial")
public class RTableModel extends AbstractTableModel {

	protected Object[][] Data; 	// Create new data array with the "X" position as each sessionData
								// and the "Y" as each Table spot
	protected Integer[] reqIDs;
	protected Requirement[] requirements;
	protected GameSession game;
	protected int size = 0;
    protected static final String[] COLUMN_NAMES = new String[] {"Name", "Desc", "Avg. Vote", "Median"};
    protected static final Class<?>[] COLUMN_TYPES = new Class<?>[] {String.class, String.class, Integer.class, Integer.class};

	public RTableModel(GameSession gs){
    	setUpTable(gs);
    }
	
	private void setUpTable(GameSession gs){
		game = gs;
		game.calculateStats();
		Data = new Object[gs.getGameReqs().size()][COLUMN_NAMES.length];
		requirements = new Requirement[gs.getGameReqs().size()];
		System.out.println(RequirementModel.getInstance().getRequirements());
		int j=0;
		for (Integer i : gs.getGameReqs()){
			
			requirements[j] = RequirementModel.getInstance().getRequirement(i);	
			System.out.println(i);
			j++;
		}
		reqIDs = new Integer[requirements.length];
    	size = requirements.length;
    	for (int i=0; i<requirements.length; i++){
    		Object[] curRow = {requirements[i].getName(),
								requirements[i].getDescription(), 
    							gs.getMean().get(i),
    							gs.getMedian().get(i)
    							};
    		Data[i] = curRow;
    		reqIDs[i] = requirements[i].getId();
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
            	return Data[rowIndex][columnIndex];
            	
            default: return "Error";
        }
    }

	public void update(GameSession gs){
		setUpTable(gs);
	}
	
}