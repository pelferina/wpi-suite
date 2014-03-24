/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SpringLayout;

/**
 * @author Anthony Dresser
 * @version March 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameImportWindow extends JWindow {
	private JPanel mainPanel = new JPanel();
	private JButton finishButton = new JButton("Finish");
	String[] listValue = {"Here's a Requirement", "Here's Another"};
	private final JList<String> reqList = new JList<String>();
	
	public NewGameImportWindow()
	{
		setupPanel();
		finishButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
	}
	
	private void setupPanel()
	{
		getContentPane().add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.WEST, finishButton, 178, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, finishButton, -25, SpringLayout.SOUTH, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		reqList.setListData(listValue);
		mainPanel.add(finishButton);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, reqList, 42, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, reqList, 62, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, reqList, -44, SpringLayout.NORTH, finishButton);
		sl_mainPanel.putConstraint(SpringLayout.EAST, reqList, -78, SpringLayout.EAST, mainPanel);
		mainPanel.add(reqList);
	}
}
