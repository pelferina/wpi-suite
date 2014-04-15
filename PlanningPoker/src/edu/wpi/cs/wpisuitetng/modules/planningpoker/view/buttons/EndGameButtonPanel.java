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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * Panel for ending a game
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class EndGameButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	final private JButton endGameButton = new JButton("<html>End<br />Game</html>");
	
	public EndGameButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(150);
		
		endGameButton.setPreferredSize(new Dimension(150,30));	
		endGameButton.setVisible(false);
		
		try {
		    final Image img = ImageIO.read(getClass().getResource("endGame.png"));
		    endGameButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			System.out.println("IOException thrown in EndGameButtonPanel");
		}
		
		contentPanel.add(endGameButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void setEndGameButtonInvisible() {
		endGameButton.setEnabled(false);
		endGameButton.setVisible(false);
	}
	
	/**
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void setEndGameButtonVisible(int gameID){
		endGameButton.setVisible(true);
		endGameButton.setEnabled(true);
		endGameButton.addActionListener(new EndGameActionListener(gameID));
	}
}
