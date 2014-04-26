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

import java.awt.event.ActionListener;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;







/**
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
@SuppressWarnings("serial")
public class EditButtonsPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	JButton editButton = new JButton("<html>Edit<br />Game</html>");
	final JButton createCancelButton = new JButton("<html>Cancel<br />Games</html>");
	private ActionListener listener = null;
	private ImageIcon editImg = null;
	
	/**
	 *  disables the Edit Games/ActivateGames button 
	 */
	public void disableCreateEditButton() {
		editButton.setEnabled(false);
	}
	
	/**
	 *  enables the Edit Games/ActivateGames button 
	 */
	public void enableCreateEditButton() {
		editButton.setEnabled(true);
	}

	public EditButtonsPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(300);
		
		editButton.setPreferredSize(new Dimension(150,50));	
		
		try {
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in EditButtonsPanel.");
		}
		editButton.setIcon(editImg);
		editButton.setVisible(false);
		
		contentPanel.add(editButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getEditButton.
	 * @return JButton 
	 * 
	 */
	public JButton getEditButton() {
		return editButton;
	}

	/**
	 * This method sets the button to read "activate"
	 *
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void setEditGameButtonVisible(int gameID){
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
	public void setEditGameButtonInvisible() {
		editButton.setEnabled(false);
		editButton.setVisible(false);
	}

}
