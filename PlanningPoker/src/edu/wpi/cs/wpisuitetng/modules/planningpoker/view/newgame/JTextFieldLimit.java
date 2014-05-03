/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Cosmic Latte
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
/**
 * The JTextFieldLimit class extends PlainDocument
 * @author FFF8E7
 * @version 6
 */
public class JTextFieldLimit extends PlainDocument {
  private final int limit;
  /**
   * Constructor for limit
   * @param limit The new limit
   */
  public JTextFieldLimit(int limit) {
    this.limit = limit;
  }

  /**
   * Constructor for limit
   * @param limit The new limit
   * @param upper UNUSED COMPLETELY
   */
  JTextFieldLimit(int limit, boolean upper) {
    this.limit = limit;
  }

  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    if (str == null){
      return;
    }

    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str, attr);
    }
  }
}