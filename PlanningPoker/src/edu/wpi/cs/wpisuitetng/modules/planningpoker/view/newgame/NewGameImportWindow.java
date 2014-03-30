/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JWindow;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;



/**
 * @author Anthony Dresser
 * @version March 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameImportWindow {
	Requirement currentSelectedReq;
	JDialog importWindow;
	private JRootPane mainPanel = new JRootPane();
	private JButton finishButton = new JButton("Finish");
	private String[] listValue;
	private final JList<String> reqList = new JList<String>();
	private final List<Requirement> requirements;
	
	/**
	 * constructor for newgameimportwindow
	 * @param requirements
	 * @param parentFrame
	 */
	public NewGameImportWindow(List<Requirement> theRequirements, Window parentFrame)
	{
		importWindow = new JDialog((Frame) parentFrame, "Import", true);
		this.requirements = theRequirements;
		listValue = new String[requirements.size()];
		
		//Get the string names for the import window JList
		for (int i = 0; i < this.requirements.size(); i++) 
		{
			Requirement req = this.requirements.get(i);
			listValue[i] = req.getName();
		}
		finishButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int tempIndex = reqList.getSelectedIndex();
				if (tempIndex!=-1)
					currentSelectedReq = requirements.get(tempIndex);
				importWindow.dispose();
			}
		});
		setupPanel();
	}
	/**
	 * This sets up the panel
	 * called once to set up, don't call again pls
	 */
	private void setupPanel()
	{
		importWindow.getContentPane().add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, finishButton, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, finishButton, -25, SpringLayout.SOUTH, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		reqList.setListData(listValue);
		mainPanel.add(finishButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, reqList, 62, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, reqList, -78, SpringLayout.EAST, mainPanel);
		mainPanel.add(reqList);
		
		importWindow.setSize(450, 300);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		importWindow.setLocation(dim.width/2-importWindow.getSize().width/2, dim.height/2-importWindow.getSize().height/2);
		
		importWindow.setVisible(true);
	}
	
}
