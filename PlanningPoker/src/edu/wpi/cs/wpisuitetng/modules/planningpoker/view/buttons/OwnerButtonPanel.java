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
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;

/**
 * Button for ending a game
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class OwnerButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private final JButton ownerButton = new JButton();
	private ActionListener listener = null;
	private Image endImg, activateImg, archiveImg;
	
	public OwnerButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(150);
		
		ownerButton.setPreferredSize(new Dimension(150,30));	
		ownerButton.setVisible(false);
		
		try {
		    endImg= ImageIO.read(getClass().getResource("cancel.png"));
		    activateImg = ImageIO.read(getClass().getResource("cancel.png"));
		    archiveImg = ImageIO.read(getClass().getResource("cancel.png"));
		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in EndGameButtonPanel");
		}
		
		contentPanel.add(ownerButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeOwnerButtonInvisible() {
		ownerButton.setEnabled(false);
		ownerButton.setVisible(false);
	}
	
	/**
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeEndGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(new ImageIcon(endImg));
		ownerButton.setText("<html>End<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new EndGameActionListener(game);
		ownerButton.addActionListener(listener);
	}
	/**
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeActivateGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(new ImageIcon(endImg));
		ownerButton.setText("<html>Activate<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new EndGameActionListener(game);
		ownerButton.addActionListener(listener);
	}
	/**
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeArchiveGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(new ImageIcon(endImg));
		ownerButton.setText("<html>Archive<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new EndGameActionListener(game);
		ownerButton.addActionListener(listener);
	}
}
