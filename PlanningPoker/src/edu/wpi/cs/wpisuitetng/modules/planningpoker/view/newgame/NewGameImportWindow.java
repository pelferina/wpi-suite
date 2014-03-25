/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;



/**
 * @author Anthony Dresser
 * @version March 24, 2014
 */
@SuppressWarnings("serial")
public class NewGameImportWindow extends JFrame {
	private JPanel mainPanel = new JPanel();
	private JButton finishButton = new JButton("Finish");
	private String[] listValue;
	private final JList<String> reqList = new JList<String>();
	private final List<Requirement> requirements;
	
	public NewGameImportWindow(List<Requirement> requirements)
	{
		this.requirements = requirements;
		listValue = new String[requirements.size()];
		for (int i = 0; i < this.requirements.size(); i++) 
		{
			Requirement req = this.requirements.get(i);
			listValue[i] = req.getName();
		}
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
		sl_mainPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, finishButton, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, finishButton, -25, SpringLayout.SOUTH, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		reqList.setListData(listValue);
		mainPanel.add(finishButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, reqList, 62, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, reqList, -78, SpringLayout.EAST, mainPanel);
		mainPanel.add(reqList);
		
		setSize(450, 300);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		
		setVisible(true);
	}
}
