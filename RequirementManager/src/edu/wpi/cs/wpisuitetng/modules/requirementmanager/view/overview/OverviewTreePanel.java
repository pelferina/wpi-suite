/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;


/**
 * @author justinhess
 * @version $Revision: 1.0 $
 */
public class OverviewTreePanel extends JScrollPane implements MouseListener, TreeSelectionListener{

	private JTree tree;
	/**
	 * Sets up the left hand panel of the overview
	 */
	public OverviewTreePanel()
	{
        this.setViewportView(tree);
        ViewEventController.getInstance().setOverviewTree(this);
		this.refresh();  
	}
	
	/**
	 * Method valueChanged.
	
	
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(TreeSelectionEvent) *//*
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		
	}*/
	
	/**
	 * This will wipe out the current tree and rebuild it
	 */
	public void refresh(){
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(/*ConfigManager.getConfig().getProjectName()*/ "Iteration Tree"); //makes a starting node
		List<Iteration> iterations = sortIterations(IterationModel.getInstance().getIterations()); //retreive the list of all iterations
		for(int i=0; i<iterations.size(); i++){

			DefaultMutableTreeNode newIterNode = new DefaultMutableTreeNode(iterations.get(i)); //make a new iteration node to add
			List<Requirement> requirements = sortRequirements(iterations.get(i).getRequirements()); //gets the list of requirements that is associated with the iteration

			//check to see if there are any requirements for the iteration
			if(requirements.size() > 0){
				addRequirementsToTree(requirements, newIterNode);
			}

			top.add(newIterNode); //add the iteration's node to the top node
		}

        tree = new JTree(top); //create the tree with the top node as the top
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //tell it that it can only select one thing at a time
        tree.setToggleClickCount(0);
 
        tree.setCellRenderer(new CustomTreeCellRenderer()); //set to custom cell renderer so that icons make sense
        tree.addMouseListener(this); //add a listener to check for clicking
        tree.addTreeSelectionListener(this);
        
        tree.setTransferHandler(new IterationTransferHandler());
        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON);
        
        this.setViewportView(tree); //make panel display the tree
        
        ViewEventController.getInstance().setOverviewTree(this); //update the ViewEventControler so it contains the right tree

	}
	
	/**
	 * Method addRequirementsToTree.
	 * @param requirements List<Requirement>
	 * @param parentNode DefaultMutableTreeNode
	 */
	private void addRequirementsToTree(List<Requirement> requirements, DefaultMutableTreeNode parentNode) {
		
		for (int j = 0; j < requirements.size(); j++) {
			DefaultMutableTreeNode newReqNode = new DefaultMutableTreeNode(requirements.get(j));
			List<Requirement> children = sortRequirements(requirements.get(j).getChildren());
//			if((requirements.get(j).getParentID() != -1) && (parentNode.getUserObject() instanceof Iteration)){
//				System.out.println("breaking for " + requirements.get(j).getName());
//				continue;
//			}
			if(children.size() > 0)
			{
				addRequirementsToTree(children, newReqNode);
			}
			parentNode.add(newReqNode);
		}
	}

	/**
	 * Method mouseClicked.
	 * @param e MouseEvent
	
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent) */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (e.getClickCount() == 2)
		{
			TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
			if(treePath != null)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(node != null)
				{
					if(node.getUserObject() instanceof Iteration)
					{
						ViewEventController.getInstance().editIteration((Iteration)node.getUserObject());
					}
					if(node.getUserObject() instanceof Requirement)
					{
						ViewEventController.getInstance().editRequirement((Requirement)node.getUserObject());
					}
				}
			}
		}
/*
		    if (SwingUtilities.isRightMouseButton(e)) {

		        int row = tree.getClosestRowForLocation(e.getX(), e.getY());
		        tree.setSelectionRow(row);
		        String label = "popup: ";
		        JPopupMenu popup = new JPopupMenu();
				popup.add(new JMenuItem(label));
				popup.show(tree, x, y);
		    }
		    */
	}

	/**
	 * Method mousePressed.
	 * @param e MouseEvent
	
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent) */
	@Override
	public void mousePressed(MouseEvent e) {		
	}

	/**
	 * Method mouseReleased.
	 * @param e MouseEvent
	
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent) */
	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	/**
	 * Method mouseEntered.
	 * @param e MouseEvent
	
	 * @see java.awt.event.MouseListener#mouseEntered(MouseEvent) */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Method mouseExited.
	 * @param e MouseEvent
	
	 * @see java.awt.event.MouseListener#mouseExited(MouseEvent) */
	@Override
	public void mouseExited(MouseEvent e) {		
	}
	/**
	
	 * @return the tree */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Method valueChanged.
	 * @param e TreeSelectionEvent
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(node != null)
		{
			if(node.getUserObject() instanceof Iteration)
			{	
				ViewEventController.getInstance().getIterationOverview().highlight((Iteration)node.getUserObject());
			}
			else
			{
				ViewEventController.getInstance().getIterationOverview().highlight(IterationModel.getInstance().getBacklog());
			}
		}
	}

	/**
	 * Method selectIteration.
	 * @param iteration Iteration
	 */
	public void selectIteration(Iteration iteration) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
		Enumeration<DefaultMutableTreeNode> e = root.breadthFirstEnumeration();
		
		DefaultMutableTreeNode foundNode = null;
		while(e.hasMoreElements())
		{
			DefaultMutableTreeNode node = e.nextElement();
			if(node.getUserObject() == iteration)
			{
				foundNode = node;
				break;
			}
		}
		
		if(foundNode != null) 
		{
			TreePath path = new TreePath(foundNode.getPath());
			tree.setSelectionPath(path);
			tree.scrollPathToVisible(path);
		}
	}

	/**
	 * @param list the list of iterations to be sorted
	 * @return the same list sorted by start date
	 */
	public List<Iteration> sortIterations(List<Iteration> list) {
		
		Collections.sort(list, new IterationComparator());

		return list;
	}
	
	/**
	 * @param list requirements to be sorted
	 * @return the same list sorted by name
	 * 
	 */
	public List<Requirement> sortRequirements(List<Requirement> list) {
		
		Collections.sort(list, new RequirementComparator());

		return list;
	}
}

/**
 * @author Kevin
 * sorts the Iterations by date
 *
 */
class IterationComparator implements Comparator<Iteration> {
    public int compare(Iteration I1, Iteration I2) {
       if(I1.getStart() == null) 
    	   return -1;
       if(I2.getStart() == null)
    	   return 1;
       return I1.getStart().getDate().compareTo(I2.getStart().getDate());
    }
}

/**
 * @author Kevin
 * sorts Requirements by name
 *
 */
class RequirementComparator implements Comparator<Requirement>{
	public int compare(Requirement R1, Requirement R2){
		return R1.getName().compareTo(R2.getName());
	}
}
