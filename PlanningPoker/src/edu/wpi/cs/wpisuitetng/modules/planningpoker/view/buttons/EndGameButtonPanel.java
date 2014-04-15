/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

public class EndGameButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private JButton endGameButton = new JButton("<html>End<br />Game</html>");

	private ImageIcon endGameImg = null;
	
	/**
	 *  disables the end game button 
	 */
	public void setEndGameButtonInvisible() {
		endGameButton.setEnabled(false);
		endGameButton.setVisible(false);
	}
	
	/**
	 *  enables the end game button 
	 */
	public void setEndGameButtonVisible(){
		endGameButton.setVisible(true);
		endGameButton.setEnabled(true);
	}

	public EndGameButtonPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(150);
		
		endGameButton.setPreferredSize(new Dimension(150,30));	
		endGameButton.setVisible(false);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("endGame.png"));
		    endGameButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {}
		
		// the action listener for the Edit Games button
		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// check to see if any other tab is currently open
				// if (ViewEventController.getInstance().getMainView().getTabCount() == 1) {
				
					// toggle the editing overview table mode
//					ViewEventController.getInstance().toggleEditingTable(false);
//					// edits the Edit Button text based on whether in editing overview table mode or not
//					if (ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//						ViewEventController.getInstance().getOverviewTable().repaint();
//						setButtonToActivate();
////					}	
//					else {
						;
//					}
				}
			//}
		});
		
		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// toggle the editing overview table mode
//				ViewEventController.getInstance().toggleEditingTable(true);			
				;

			}
		});
		contentPanel.add(endGameButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
	}
	
}
