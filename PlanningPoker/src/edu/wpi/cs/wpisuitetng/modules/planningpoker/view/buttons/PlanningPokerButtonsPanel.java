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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Panel for buttons up top
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class PlanningPokerButtonsPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createButton = new JButton("<html>Create<br />Game</html>");
		private final JButton optionsButton = new JButton("<html>Game<br />Options</html>");

		private final JPanel contentPanel = new JPanel();
	
	public PlanningPokerButtonsPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(500);
		
	
		this.createButton.setHorizontalAlignment(SwingConstants.CENTER);
		try {
		    Image img = ImageIO.read(getClass().getResource("newgameimage.png"));
		    this.createButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("optionsimage.png"));
		    this.optionsButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {}
		
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
