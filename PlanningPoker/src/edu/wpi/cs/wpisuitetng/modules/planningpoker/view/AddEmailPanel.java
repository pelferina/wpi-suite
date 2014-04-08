package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class AddEmailPanel extends JPanel {
	
	private JTextField emailField = new JTextField();
	private JLabel emailLabel = new JLabel("Email: ");
	private JButton submitButton = new JButton ("Submit");
	
	public AddEmailPanel(){
		setupPanel();
	}
	
	private void setupPanel(){
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, submitButton, 21, SpringLayout.SOUTH, emailField);
		springLayout.putConstraint(SpringLayout.WEST, submitButton, 189, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, emailField, -3, SpringLayout.NORTH, emailLabel);
		springLayout.putConstraint(SpringLayout.WEST, emailField, 6, SpringLayout.EAST, emailLabel);
		springLayout.putConstraint(SpringLayout.EAST, emailField, 285, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, emailLabel, 126, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, emailLabel, 136, SpringLayout.WEST, this);
		setLayout(springLayout);
		add(emailLabel);
		add(emailField);
		add(submitButton);
	}

}
