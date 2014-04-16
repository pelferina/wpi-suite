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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Creates a card for the game voting
 * @author Anthony
 * @version $Revision: 1.0 $
 */
public class GameCard {
	
	int value;
	BufferedImage img;
	
	/**
	 * Constructor to create the card with the given value
	 * @param value
	 */
	public GameCard(int value){
		this.value = value;
		try {
			img  = ImageIO.read(getClass().getResource("planningpokercard.png"));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public BufferedImage getImg()
	{
		return img;
	}

}
