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


import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;




import javax.swing.SwingConstants;

import java.awt.Component;

import java.awt.Font;

/**
 * Panel for buttons up top
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class GreetingPanel extends ToolbarGroupView{
	
	// initialize the main view greeting panel
	private final JPanel contentPanel = new JPanel();
	private JLabel planningPokerImageLabel;
	
	public GreetingPanel(){
		super("");
		this.setPreferredWidth(330);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
//		userName = new JLabel ("<html><div align =\"center\"> <br/>Welcome to Planning Poker! </div></html>");
		try{
		    Image img = ImageIO.read(getClass().getResource("planningpoker.png"));
			planningPokerImageLabel = new JLabel(new ImageIcon(img));
		} catch(IOException ex) {
			System.out.println("IOException thrown in PlanningPokerButtonsPanel.");
		}
			
		planningPokerImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		planningPokerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		planningPokerImageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		
		planningPokerImageLabel.setFont (planningPokerImageLabel.getFont().deriveFont(20f));
		
		contentPanel.add(planningPokerImageLabel);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
		
		final JLabel lblWelcomeToPlanning = new JLabel("<html>Welcome to<br/> Planning Poker!</html>");
		lblWelcomeToPlanning.setFont(new Font("SWRomnt", Font.PLAIN, 15));
		contentPanel.add(lblWelcomeToPlanning);
		super.setContent(contentPanel);
	}	
}
