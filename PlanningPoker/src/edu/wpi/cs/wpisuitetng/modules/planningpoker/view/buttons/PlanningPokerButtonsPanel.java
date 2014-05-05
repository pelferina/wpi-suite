/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Panel for buttons up top
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class PlanningPokerButtonsPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
	private final JButton createButton = new JButton("<html>Create<br />Game</html>");
	private final JButton optionsButton = new JButton("<html>Game<br />Options</html>");

	private final JPanel contentPanel = new JPanel();
	
	public PlanningPokerButtonsPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(330);
		
		createButton.setPreferredSize(new Dimension(150, 50));
		optionsButton.setPreferredSize(new Dimension(150, 50));

//		createButton.setHorizontalAlignment(SwingConstants.CENTER);
		try {
		    Image img = ImageIO.read(getClass().getResource("newgameimage.png"));
		    createButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("optionsimage.png"));
		    optionsButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in PlanningPokerButtonsPanel.");
		}
		
		// the action listener for the Create Requirement Button
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					ViewEventController.getInstance().createGame();
			//	}
			}
		});
		
		//action listener for the Create Iteration Button
		optionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					ViewEventController.getInstance().options();
				}
		//	}
		});
		
		contentPanel.add(createButton);
		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
		contentPanel.add(optionsButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
	}
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getCreateButton() {
		return createButton;
	}
	
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getOptionsButton() {
		return optionsButton;
	}
}
