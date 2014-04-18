package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

public class VoteData extends JPanel{

	private final JLabel reqNameLabel = new JLabel("Requirement Name:");
	private final JLabel reqDescriptionLabel = new JLabel("Requirement Description:");
	private final JLabel meanLabel = new JLabel("Mean:");
	private final JLabel medianLabel = new JLabel("Median:");
	private final JLabel estimatesLabel = new JLabel("Estimates:");
	private JTextField reqNameText = new JTextField();
	private JTextField meanTextField = new JTextField();
	private JTextField medianTextField = new JTextField();
	private JTextArea descriptionTextArea = new JTextArea();
	private JScrollPane estimatesPane;
	private JTable estimatesTable;
	private GameSession completedGame;
	private CompleteView completeView;
	private List<Integer> gameReqIDs;
	private List<Requirement> gameReqs;
	private Requirement currentReq;
	private int	reqIndex;
	
	public VoteData(GameSession gs, CompleteView cv){
		completeView = cv;
		completedGame = gs;
		gameReqs = cv.getGameRequirements();
		currentReq = gameReqs.get(0);
		reqIndex = 0;
		
		//Sets the name and description text fields to the first requirements
		reqNameText.setText(currentReq.getName());
		reqNameText.setEnabled(false);
		descriptionTextArea.setText(currentReq.getDescription());
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setEnabled(false);
		gs.calculateStats();
		
		//Sets the statistic text fields to the stats of the first requirement in the game, and disables user edits
		meanTextField.setText(Float.toString(gs.getMean().get(reqIndex)));
		meanTextField.setEnabled(false);
		medianTextField.setText(Float.toString(gs.getMedian().get(reqIndex)));
		medianTextField.setEnabled(false);
		init();
	}
	
	private void init(){
		
		estimatesTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		estimatesTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"User Name", "Estimate"}));
		DefaultTableModel estimatesModel = (DefaultTableModel) estimatesTable.getModel();
		
		int i = 0;
		for (Vote v: completedGame.getVotes()){
			estimatesModel.setValueAt(v.getUID(), i, 0);
			estimatesModel.setValueAt(v.getVote().get(reqIndex), i, 1);
			i++;
		}
		
		estimatesPane = new JScrollPane(estimatesTable);
		estimatesPane.setViewportView(estimatesTable);
		
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout constraints for estimatesPane
		springLayout.putConstraint(SpringLayout.NORTH, estimatesPane, 6, SpringLayout.SOUTH, estimatesLabel);
		springLayout.putConstraint(SpringLayout.WEST, estimatesPane, 0, SpringLayout.WEST, estimatesLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesPane, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, estimatesPane, 0, SpringLayout.EAST, descriptionTextArea);
		
		//Spring layout constraints for estimatesLabel
		springLayout.putConstraint(SpringLayout.NORTH, estimatesLabel, 6, SpringLayout.SOUTH, medianTextField);
		springLayout.putConstraint(SpringLayout.WEST, estimatesLabel, 0, SpringLayout.WEST, medianLabel);
		
		//Spring layout constraints for medianTextField
		springLayout.putConstraint(SpringLayout.NORTH, medianTextField, 0, SpringLayout.NORTH, meanTextField);
		springLayout.putConstraint(SpringLayout.WEST, medianTextField, 6, SpringLayout.EAST, medianLabel);
		springLayout.putConstraint(SpringLayout.EAST, medianTextField, 79, SpringLayout.EAST, medianLabel);
		
		//Spring layout constraints for reqNameText
		springLayout.putConstraint(SpringLayout.NORTH, reqNameText, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqNameText, 6, SpringLayout.EAST, reqNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, reqNameText, -154, SpringLayout.EAST, this);
		
		//Spring layout constraints for descriptionTextArea
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextArea, 37, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextArea, -61, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionTextArea, 83, SpringLayout.SOUTH, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionTextArea, 6, SpringLayout.SOUTH, reqDescriptionLabel);
		
		//Spring layout constraints for medianLabel
		springLayout.putConstraint(SpringLayout.NORTH, medianLabel, 3, SpringLayout.NORTH, meanTextField);
		springLayout.putConstraint(SpringLayout.WEST, medianLabel, 80, SpringLayout.EAST, meanTextField);
		
		//Spring layout constraints for meanTextField
		springLayout.putConstraint(SpringLayout.NORTH, meanTextField, -3, SpringLayout.NORTH, meanLabel);
		springLayout.putConstraint(SpringLayout.WEST, meanTextField, -61, SpringLayout.EAST, reqNameLabel);
		springLayout.putConstraint(SpringLayout.EAST, meanTextField, 12, SpringLayout.EAST, reqNameLabel);
		
		//Spring layout constraints for meanLabel
		springLayout.putConstraint(SpringLayout.NORTH, meanLabel, 26, SpringLayout.SOUTH, descriptionTextArea);
		springLayout.putConstraint(SpringLayout.WEST, meanLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		
		//Spring layout constraints for reqNameLabel
		springLayout.putConstraint(SpringLayout.EAST, reqNameLabel, -328, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameLabel, 10, SpringLayout.NORTH, this);
		
		//Spring layout constraints for reqDescriptionLabel
		springLayout.putConstraint(SpringLayout.WEST, reqDescriptionLabel, 27, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqDescriptionLabel, 6, SpringLayout.SOUTH, reqNameText);
		
		setLayout(springLayout);
		
		add(estimatesPane);
		add(descriptionTextArea);
		add(medianTextField);
		add(meanTextField);
		add(reqNameText);
		add(estimatesLabel);
		add(reqDescriptionLabel);
		add(medianLabel);
		add(meanLabel);
		add(reqNameLabel);
		
		
		
	}
	
	
}
