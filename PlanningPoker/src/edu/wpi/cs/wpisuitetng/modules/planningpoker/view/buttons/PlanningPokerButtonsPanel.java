/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * @author justinhess
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class PlanningPokerButtonsPanel extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createButton = new JButton("<html>Create<br />Game</html>");
		
		private final JPanel contentPanel = new JPanel();
	
	public PlanningPokerButtonsPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
	
		this.createButton.setHorizontalAlignment(SwingConstants.CENTER);
		try {
		    Image img = ImageIO.read(getClass().getResource("new_game.png"));
		    this.createButton.setIcon(new ImageIcon(img));
		    
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
		
	
			
		contentPanel.add(createButton);
		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
	}
	/**
	 * Method getCreateButton.
	
	 * @return JButton */
	public JButton getCreateButton() {
		return createButton;
	}	
}
