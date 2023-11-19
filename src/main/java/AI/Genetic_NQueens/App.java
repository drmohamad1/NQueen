package AI.Genetic_NQueens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {

    	//	Genetics.Genetic(20, 100, 100);
		   Genetics.parallelGeneticInteractive(8, 5, 100, 100, 5, 2, 0.2, 0.1);
		//   Genetics.parallelGeneticIsolated(20, 5, 100, 100,0.2);
//System.exit(0);
//	    final XYSeries series = new XYSeries("Random Data");
//    	for(int i=10;i<100;i=i+10)
//    	{
//    		series.add(i, Genetics.average(Genetics.parallelGeneticInteractive(10, 10, 50, 100, i, 2, 1, 0.1)));
//    	}
//    	 final XYSeriesCollection data = new XYSeriesCollection(series);
//    	    final JFreeChart chart = ChartFactory.createXYLineChart(
//    	        "XY Series Demo",
//    	        "X", 
//    	        "Y", 
//    	        data,
//    	        PlotOrientation.VERTICAL,
//    	        true,
//    	        true,
//    	        false
//    	    );
//
//    	    final ChartPanel chartPanel = new ChartPanel(chart);
//    	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//    	    JFrame a=new JFrame();
//    	    a.add(chartPanel);
//    	    a.show();
//
//    	    
    	    

//    	int processors=Runtime.getRuntime().availableProcessors();
//    	System.out.println("processors="+processors);
//    	ArrayList<ArrayList<Integer>> population=new ArrayList<ArrayList<Integer>>();
//    	population=Genetics.randomPopulation(100, 500);
//    	for(int i=0;i<500;i++)
//    	{  
//    		System.out.println("generation "+i+":");
//    		population=Genetics.bestSelection(population,500);
//    		System.out.println("lenght= "+1/(Genetics.fitnessFunction(population.get(0))));
//    		java.util.Collections.shuffle(population);
//    		Genetics.crossover(population,1);
//    		Genetics.mutation(population, 0.1);
//    	}
//    	for(ArrayList<Integer> e:population)
//    	System.out.println(e+" "+1/Genetics.fitnessFunction(e));
    	    
    }
}
