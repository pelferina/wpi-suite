/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.icons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * @author justinhess
 * @version $Revision: 1.0 $
 */
public class NeedsVoteIcon implements Icon {
	
	private int width;
	private int height;
	
	public NeedsVoteIcon(){
		width = 5;
		height = 5;
	}
	
	/**
	 * Method getIconHeight.
	
	
	 * @return int * @see javax.swing.Icon#getIconHeight() */
	@Override
	public int getIconHeight() {
		return height;
	}

	/**
	 * Method getIconWidth.
	
	 * @return int * @see javax.swing.Icon#getIconWidth() */
	@Override
	public int getIconWidth() {
		return width;
	}

	/**
	 * Method paintIcon.
	 * @param c Component
	 * @param g Graphics
	 * @param x int
	 * @param y int
	
	 * @see javax.swing.Icon#paintIcon(Component, Graphics, int, int) */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(new Color(169,46,34));
		g.fillOval(x, y, width, height);
	}

}
