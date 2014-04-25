/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * Creates a card for the game voting
 * @author fff8e7
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class GameCard extends JToggleButton{
	
	private Integer value;
	private BufferedImage cardSelectedImg;
	private BufferedImage cardUnselectedImg;
	private ImageIcon cardSelectedIcon;
	private ImageIcon cardUnselectedIcon;
	
	/**
	 * Constructor to create the card with the given value
	 * @param value
	 */
	public GameCard(Integer value){
		this.value = value;
		try {
			cardUnselectedImg  = ImageIO.read(getClass().getResource("planningpokercardunselected.png"));
			cardSelectedImg = ImageIO.read(getClass().getResource("planningpokercardselected.png"));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		String cardText = value.toString();
		cardUnselectedIcon = new ImageIcon(cardUnselectedImg);
		cardSelectedIcon = new ImageIcon(cardSelectedImg); 
		this.setText(cardText);
		this.setIcon(cardUnselectedIcon);
		this.setSelected(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setContentAreaFilled(false);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setSelectedIcon(cardSelectedIcon);
	}
	
	/**
	 * Getter for value
	 * @return value
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * Getter for the card image
	 * @return The appropriate image
	 */
	public BufferedImage getImg()
	{
		if(this.isSelected()) return cardSelectedImg;
		else return cardUnselectedImg;
	}

}
