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

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * Creates a card for the game voting
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class GameCard extends JToggleButton{
	
	private final Integer value;
	private BufferedImage cardSelectedImg;
	private BufferedImage cardUnselectedImg; // $codepro.audit.disable variableShouldBeFinal
	private ImageIcon cardSelectedIcon;
	private final ImageIcon cardUnselectedIcon;
	
	private boolean isCancelCard;
	
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
		final String cardText = value.toString();
		final int length = cardText.length();
		final int size = 30 - length * 2;
		final Font buttonFont = new Font("Arial", Font.BOLD, size);
		cardUnselectedIcon = new ImageIcon(cardUnselectedImg);
		cardSelectedIcon = new ImageIcon(cardSelectedImg); 
		this.setText(cardText);
		this.setFont(buttonFont);
		this.setIcon(cardUnselectedIcon);
		this.setSelected(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setContentAreaFilled(false);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.CENTER);
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
	/**
	 * Changes the image of the card to the Cancel icon
	 * @param b sets to cancel icon if true, back to default otherwise
	 */
	public void setCancelCard(boolean b){
		isCancelCard = b;
		
		if(b){
			try {
				cardSelectedImg = ImageIO.read(getClass().getResource("planningpokercardcancel.png"));
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
			cardSelectedIcon = new ImageIcon(cardSelectedImg);
			this.setSelectedIcon(cardSelectedIcon);
			
		}else{
			try {
				cardSelectedImg = ImageIO.read(getClass().getResource("planningpokercardselected.png"));
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
			cardSelectedIcon = new ImageIcon(cardSelectedImg);
			this.setSelectedIcon(cardSelectedIcon);
		}
	}
	
	/**
	 * Determines if the card is the cancel card type
	 * @return true if it is a cancel card, false otherwise
	 */
	public boolean isCancelCard(){
		return isCancelCard;
	}

}
