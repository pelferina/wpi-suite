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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class EndGameButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private JButton endGameButton = new JButton("<html>End<br />Game</html>");
	
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

