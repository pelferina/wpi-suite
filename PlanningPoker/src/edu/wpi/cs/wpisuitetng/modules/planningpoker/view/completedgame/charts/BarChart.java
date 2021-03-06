/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame.charts;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * The BarChar class, extends JScrollPane
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class BarChart extends JScrollPane {
private static GameSession theGame = new GameSession("", "", -1, -1, null, null);
private final ChartPanel barChart;
private static int theReq = -1;

		/**
		 * Constructor for NewBarChartPanel.
		 * @param gs The game session to create a chart of
		 * @param aReq the requirement iq to chart
		 */
		public BarChart(GameSession gs, int aReq){
			theGame = gs;
			theReq = aReq;
			final JPanel panel = new JPanel(new BorderLayout());
			barChart = createPanel();
			barChart.setPreferredSize(super.getSize());
			panel.add(barChart, BorderLayout.CENTER);
			
			this.setViewportView(panel);
		}
		
		/**
		
		 * @return the data set depending on the type of data called for either status or iteration */
		private static DefaultCategoryDataset dataSetup() {
			final List<Vote> votes = theGame.getVotes();
			final DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			final int numUsers = votes.size();
			//Get each vote
			for(int i=0; i < numUsers; i++){
				dataSet.addValue(votes.get(i).getVote().get(theReq), "Estimate", GetUsersController.getInstance().getUserFromID(votes.get(i).getUID()));
			}
			return dataSet;

		}

		/**
		 * @param dataset the data to be shown by the chart
		 * @param title the title of the chart(either status or iteration)
		
		 * @return the created bar graph */
		private static JFreeChart createChart(CategoryDataset dataset){
		    
		    final JFreeChart chart = ChartFactory.createBarChart("Estimates for " + RequirementModel.getInstance().getRequirement(theReq).getName(), "User", "Estimate", dataset, PlotOrientation.VERTICAL, false, true, false);

		    final String fontName = "Lucida Sans";

		    final StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();

		    theme.setTitlePaint( Color.decode( "#4572a7" ) );
		    theme.setExtraLargeFont( new Font(fontName, Font.PLAIN, 16) ); //title
		    theme.setLargeFont( new Font(fontName, Font.BOLD, 15)); //axis-title
		    theme.setRegularFont( new Font(fontName, Font.PLAIN, 11));
		    theme.setRangeGridlinePaint( Color.decode("#C0C0C0"));
		    theme.setPlotBackgroundPaint( Color.white );
		    theme.setChartBackgroundPaint( Color.white );
		    theme.setGridBandPaint( Color.red );
		    theme.setAxisOffset( new RectangleInsets(0, 0, 0, 0) );
		    theme.setBarPainter(new StandardBarPainter());
		    theme.setAxisLabelPaint( Color.decode("#666666")  );
		    theme.apply( chart );
		    chart.getCategoryPlot().setOutlineVisible( false );
		    chart.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
		    chart.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
		    chart.getCategoryPlot().setRangeGridlineStroke( new BasicStroke() );
		    chart.getCategoryPlot().getRangeAxis().setTickLabelPaint( Color.decode("#666666") );
		    chart.getCategoryPlot().getDomainAxis().setTickLabelPaint( Color.decode("#666666") );
		    chart.setTextAntiAlias( true );
		    chart.setAntiAlias( true );
		    chart.getCategoryPlot().getRenderer().setSeriesPaint( 0, Color.decode( "#4572a7" ));
		    final BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
		    rend.setShadowVisible( true );
		    rend.setShadowXOffset( 2 );
		    rend.setShadowYOffset( 0 );
		    rend.setShadowPaint( Color.decode( "#C0C0C0"));
		    rend.setMaximumBarWidth( 0.1);
		    
		    return chart;
		}
		
		/**		
		
		 * @return the created bar graph **/
		public static ChartPanel createPanel() {
			final JFreeChart chart = createChart(dataSetup());
			final ChartPanel aPanel = new ChartPanel(chart);
			aPanel.setDomainZoomable(false);
			aPanel.setRangeZoomable(false);
			
			return aPanel;
		}
		
//		/**
//		 * Method paintComponent.
//		 * @param g Graphics
//		 */
//		@Override
//		public void paintComponent(Graphics g){
//			barChart.setChart(createChart(setData()));
//			barChart.setPreferredSize(super.getSize());
//			super.paintComponent(g);
//		}
		
		/**
		 * This function updates the chart information
		 * @param aGame the game session of the chart
		 * @param aReq the requirement id to display
		 */
		public void updateChart(GameSession aGame, int aReq)
		{
			theGame = aGame;
			theReq = aReq;
			barChart.setChart(createChart(dataSetup()));
			barChart.setDomainZoomable(false);
			barChart.setRangeZoomable(false);
		}
}
