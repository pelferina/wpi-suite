/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import java.awt.BorderLayout;

/**
 * This class shows the requirements that are currently in the game
 * @author Anthony Dresser
 * @version March 23, 2014
 */
@SuppressWarnings("serial")
public class NewGameReqPanel extends JPanel {

	DefaultListModel<String> listValue; //= {"Requirement One", "Requirement Two", "Requirement Three"};
	private final JList<String> reqList;// = new JList<String>();
	
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
	public void addReq(String req){
		listValue.addElement(req);
	}
}
