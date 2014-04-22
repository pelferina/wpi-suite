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
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;

/**
 * Button for ending a game
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class UserButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private final JButton userButton = new JButton();
	private ActionListener listener = null;
	private ImageIcon viewImg, voteImg;
	private Timer expireTimer = null;
	
	public UserButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(150);
		
		userButton.setPreferredSize(new Dimension(150,50));	
		userButton.setVisible(false);
		
		try {
			viewImg= new ImageIcon(ImageIO.read(getClass().getResource("view.png")));
		    voteImg = new ImageIcon(ImageIO.read(getClass().getResource("vote.png")));
		    
		} catch (IOException ex){
			System.out.println("IOException thrown in EndGameButtonPanel");
		}
		
		contentPanel.add(userButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeUserButtonInvisible() {
		userButton.setEnabled(false);
		userButton.setVisible(false);
	}

	/**
	 * Enables the vote game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeVoteGameButtonVisible(GameSession game){
		userButton.setVisible(true);
		userButton.setEnabled(true);
		userButton.setIcon(voteImg);
		userButton.setText("<html>Vote<br />Game</html>");
		
		
		if(listener != null){
			userButton.removeActionListener(listener);
		}
		listener = new ActivateGameActionListener(game);
		userButton.addActionListener(listener);
	}

	/**
	 * Enables the view game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeViewGameButtonVisible(GameSession game){
		userButton.setVisible(true);
		userButton.setEnabled(true);
		userButton.setIcon(viewImg);
		userButton.setText("<html>View<br />Game</html>");
		
		
		if(listener != null){
			userButton.removeActionListener(listener);
		}
		listener = new ArchiveGameActionListener(game);
		userButton.addActionListener(listener);
	}
	
}
