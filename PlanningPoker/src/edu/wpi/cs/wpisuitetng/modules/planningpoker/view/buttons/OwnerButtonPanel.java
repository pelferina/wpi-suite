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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Button for ending a game
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class OwnerButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private final JButton ownerButton = new JButton();
	private final JLabel ownerLabel = new JLabel("Owner Options");
	JButton editButton = new JButton("<html>Edit<br />Game</html>");
	private ActionListener listener = null;
	private ImageIcon endImg, activateImg, archiveImg, editImg;
	private Timer expireTimer = null;
	
	public OwnerButtonPanel(){
		super("");
		getContent().setBounds(1, 0, 10, 10);
		contentPanel.setBounds(2, 0, 0, 0);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(300);
		
		try {
		    endImg= new ImageIcon(ImageIO.read(getClass().getResource("cancel.png")));
		    activateImg = new ImageIcon(ImageIO.read(getClass().getResource("activate.png")));
		    archiveImg =new ImageIcon( ImageIO.read(getClass().getResource("archive.png")));
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in EndGameButtonPanel");
		}
		
		//setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPanel.setOpaque(false);
		editButton.setBounds(11, 19, 140, 57);
		editButton.setVisible(false);
		editButton.setIcon(editImg);
		ownerButton.setBounds(149, 19, 140, 57);
//		ownerButton.setPreferredSize(new Dimension(150,50));	
		ownerButton.setVisible(false);
		ownerButton.setHorizontalAlignment(SwingConstants.CENTER);
		ownerLabel.setBounds(123, 0, 93, 14);
		ownerLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		ownerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(getContent());
		setLayout(null);
		add(contentPanel);
		add(ownerLabel);
		add(editButton);
		add(ownerButton);
		
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
		ownerButton.setIcon(endImg);
		ownerButton.setText("<html>End<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new EndGameActionListener(game);
		ownerButton.addActionListener(listener);
		if (game.getEndDate()!=null)
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));

	}
	/**
	 * Enables the activate game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeActivateGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ActivateGameActionListener(game);
		ownerButton.addActionListener(listener);
		
		if (game.getEndDate()!=null)
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));
	}
	
	/**
	 * Disable the activate game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeActivateGameButtonDisable(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(false);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ActivateGameActionListener(game);
		ownerButton.addActionListener(listener);
		
		if (game.getEndDate()!=null)
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));
	}
	/**
	 * Disable the activate game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeActivateGameButtonDisable(){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(false);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
	}

	/**
	 * Enables the archive game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeArchiveGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(archiveImg);
		ownerButton.setText("<html>Archive<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ArchiveGameActionListener(game);
		ownerButton.addActionListener(listener);
	}
	
	private void expireThisButtonIn(int expireTime) {
		if (expireTimer!=null)
			expireTimer.stop();
		
		ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ownerButton.removeActionListener(listener);
				makeActivateGameButtonDisable();
				System.out.println("Expired");
			}
			
		}; // make it expire 		
		
		expireTimer = new Timer(expireTime, al);
		expireTimer.setRepeats(false);
		expireTimer.start();
		System.out.println("Expiring in "+expireTime);
	}
	
	/**
	 * This method sets the button to read "activate"
	 *
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeEditGameButtonVisible(int gameID){
		editButton.setVisible(true);
		editButton.setEnabled(true);
		if(listener != null){
			editButton.removeActionListener(listener);
		}
		listener = new EditGameActionListener(gameID);
		editButton.addActionListener(listener);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeEditGameButtonInvisible() {
		editButton.setEnabled(false);
		editButton.setVisible(false);
	}
}
