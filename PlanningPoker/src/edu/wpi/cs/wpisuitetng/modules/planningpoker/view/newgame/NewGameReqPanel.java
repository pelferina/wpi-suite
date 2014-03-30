/**
 * 
 */
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
public class NewGameReqPanel extends JPanel {

	DefaultListModel<String> listValue; //= {"Requirement One", "Requirement Two", "Requirement Three"};
	private final JList<String> reqList;// = new JList<String>();
	private List<Requirement> selected = new ArrayList<Requirement>();
	
	public NewGameReqPanel()
	{
		listValue = new DefaultListModel<String>();
		reqList = new JList<String>(listValue);
		setupPanel();
	}
	
	private void setupPanel()
	{
		//reqList.setListData(listValue);
		setLayout(new BorderLayout(0, 0));
		add(reqList);
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
