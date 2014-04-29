/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.util.Rotation;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;

/**
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class Histogram extends JPanel {
	private static String title;
	private ChartPanel chart;
	private static GameSession aGame;
	private static int theReq;

	/**
	 * @param title
	 *            the title of the pie chart which determines if we are
	 *            displaying based on status, iteration, or users assigned to.
	 */
	public Histogram(String title, GameSession gs, int reqIndex) {   
		aGame = gs;
		theReq = reqIndex;
	    chart = createPanel();	
	}

	/**
	 * @param dataset
	 *            the data to be displayed by the pie chart
	 * @param title
	 *            the title of the chart
	
	 * @return the pie chart to be displayed */
//	private static JFreeChart createChart(HistogramDataset dataset, String title) {
//
//	    JFreeChart chart = ChartFactory.createHistogram(
//	              title, 
//	              null, 
//	              null, 
//	              dataset, 
//	              PlotOrientation.VERTICAL, 
//	              true, 
//	              false, 
//	              false
//	          );
//
//	    chart.setBackgroundPaint(new Color(230,230,230));
//	    XYPlot aPlot = (XYPlot)chart.getPlot();
//	    aPlot.setForegroundAlpha(0.7F);
//	    aPlot.setBackgroundPaint(Color.WHITE);
//	    aPlot.setDomainGridlinePaint(new Color(150,150,150));
//	    aPlot.setRangeGridlinePaint(new Color(150,150,150));
//	    XYBarRenderer renderer = (XYBarRenderer)aPlot.getRenderer();
//	    renderer.setShadowVisible(false);
//	    renderer.setBarPainter(new StandardXYBarPainter()); 
//	    renderer.setDrawBarOutline(false);
//	    return chart;
//	}
	
	private static JFreeChart createChart(DefaultCategoryDataset dataset, String title) {
	    
	    JFreeChart chart = ChartFactory.createBarChart("Estimates for "+aGame.getGameName(), "User", "Estimate", dataset, PlotOrientation.VERTICAL, true, false, false);

	    chart.setBackgroundPaint(new Color(230,230,230));
//	    XYPlot aPlot = (XYPlot)chart.getPlot();
//	    aPlot.setForegroundAlpha(0.7F);
//	    aPlot.setBackgroundPaint(Color.WHITE);
//	    aPlot.setDomainGridlinePaint(new Color(150,150,150));
//	    aPlot.setRangeGridlinePaint(new Color(150,150,150));
//	    XYBarRenderer renderer = (XYBarRenderer)aPlot.getRenderer();
//	    renderer.setShadowVisible(false);
//	    renderer.setBarPainter(new StandardXYBarPainter()); 
//	    renderer.setDrawBarOutline(false);
	    return chart;
	}
	
	private static DefaultCategoryDataset setData() {
		List<Vote> votes = aGame.getVotes();
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		int numUsers = votes.size();
		double[] data = new double[numUsers];
		double max = 0;
		
		//Get each vote
		for(int i=0; i < numUsers; i++){
			dataSet.addValue(votes.get(i).getVote().get(theReq), "Estimate", Integer.toString(votes.get(i).getUID()));
		}
		return dataSet;

	}

	/**
	 * Creates the piechart panel
	
	 * @return the piechart panel */
	public static ChartPanel createPanel() {
		JFreeChart chart = createChart(setData(), title);
		return new ChartPanel(chart);
	}
	
	public static ChartPanel getPanel()
	{
		return createPanel();
	}

}