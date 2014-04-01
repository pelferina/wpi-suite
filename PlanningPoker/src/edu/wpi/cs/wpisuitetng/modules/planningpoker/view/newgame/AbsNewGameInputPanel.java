package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class AbsNewGameInputPanel extends JPanel{
	protected JButton importButton = new JButton("Add");
	protected JButton removeButton = new JButton("Remove");

	public abstract void setGameName(String gameName);
}
