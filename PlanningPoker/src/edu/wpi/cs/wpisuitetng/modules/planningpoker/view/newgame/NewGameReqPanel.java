/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

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
	private final JList reqList;
	private final JPanel listPanel = new JPanel();
	private final JLabel gameReqs = new JLabel("Requirements in the game");
	private List<Requirement> selected = new ArrayList<Requirement>();
	private List<Requirement> reqs = new ArrayList<Requirement>();
	private String[] columnName = {"Name", "Description"};
	
	public NewGameReqPanel()
	{
		reqs = RequirementModel.getInstance().getRequirements();
		listValue = new DefaultListModel<String>();
		reqList = new JList();
		setupPanel();
	}
	
	private void setupPanel()
	{
		//reqList.setListData(listValue);
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
