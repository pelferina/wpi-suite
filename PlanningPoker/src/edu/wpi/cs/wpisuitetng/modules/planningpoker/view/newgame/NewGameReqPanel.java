/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import java.awt.BorderLayout;

/**
 * @author Anthony Dresser
 * @version March 23, 2014
 */
@SuppressWarnings("serial")
public class NewGameReqPanel extends JPanel {

	String[] listValue = {"Requirement One", "Requirement Two", "Requirement Three"};
	private final JList<String> reqList = new JList<String>();

	public NewGameReqPanel()
	{
		setupPanel();
	}
	
	private void setupPanel()
	{
		reqList.setListData(listValue);
		setLayout(new BorderLayout(0, 0));
		add(reqList);
	}
}
