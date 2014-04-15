/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Anthony
 *
 */
public class GameCard {
	
	int value;
	BufferedImage img;
	
	public GameCard(int value){
		this.value = value;
		try {
			img  = ImageIO.read(getClass().getResource("planningpokercard.png"));
		} catch (IOException ex) {}
	}
	
	public BufferedImage getImg()
	{
		return img;
	}

}
