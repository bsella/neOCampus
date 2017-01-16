package traceur;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.emplacement.CapteurInterieur;
import filesmanager.FichierEnregistrement;


public class Traceur {
	
	private JFreeChart chart;
	private ChartPanel chartpanel;
	private XYSeriesCollection dataset;
	protected TimeSeriesCollection datasetTime;
	private String titreTrace = "titre";

	public Traceur(){
		
		dataset = createEmptyDataset();
		datasetTime = createEmptyTimeSeries();
		
		//chart = createGraphe(dataset);
		chart = createTimeSeriesChart(dataset);
		chartpanel = new ChartPanel(chart);
		
	}
	
	private XYSeriesCollection createEmptyDataset(){
		XYSeries series = new XYSeries("Empty");
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		dataset.addSeries(series);
		return dataset;
	}
	
	private TimeSeriesCollection createEmptyTimeSeries(){
		TimeSeries series = new TimeSeries(TimeUnit.SECONDS);
		TimeSeriesCollection timeSeries = new TimeSeriesCollection();
		timeSeries.addSeries(series);
		return timeSeries;
	}
	
	public void DatasetFromFichierEnregistrement( FichierEnregistrement fichier, Date date ){
		
		Capteur capteur = fichier.getCapteur();

		int frequence = capteur.getFrec();
		this.titreTrace = capteur.getID();
		
		this.chart.setTitle(titreTrace);
		XYPlot plot = (XYPlot)this.chart.getPlot();
		plot.getDomainAxis().setAttributedLabel("Temps");
		if(capteur instanceof CapteurInterieur)
			plot.getRangeAxis().setAttributedLabel(((CapteurInterieur)capteur).getType().toString());
		else
			plot.getRangeAxis().setAttributedLabel(((CapteurExterieur)capteur).getType().toString());
			
		
		List<Float> donnees = fichier.getData(date);
		
		XYSeries series = new XYSeries("Simple");
		for( int i = 0; i<donnees.size(); i++ ){
			series.add(i * frequence, donnees.get(i));
		}		
		dataset.removeAllSeries();
		dataset.addSeries(series);
	}
	
	public void TimeSeriesFromFichierEnregistrement( FichierEnregistrement fichier, Date date ){
		Capteur capteur = fichier.getCapteur();

		this.titreTrace = capteur.getID();
		
		String unitee = capteur.getUDM();
		
		XYPlot plot = (XYPlot)this.chart.getPlot();
		plot.getDomainAxis().setAutoRange(true);
		plot.getDomainAxis().setFixedAutoRange(1000);
		plot.getDomainAxis().setAutoRangeMinimumSize(2);
		plot.getDomainAxis().setAttributedLabel( unitee );
		plot.getRenderer().setSeriesPaint(0, Color.black);
		//plot.getRangeAxis().setRange(capteur.getIMin(), capteur.getIMax());
		
		List<Float> donnees = fichier.getData(date);
		TimeSeries series = new TimeSeries("Time series");
		Second current = new Second(date);
		for( int i = 0; i<donnees.size(); i++  ){
			series.add( current, i * Math.cos( Math.toRadians(i) ) );
			//for( int j = 0; j<frequence; j++ ){
				current = (Second)current.next();
			//}
		}
		
	}
	
	/*
	 * createGraphe
	 * Renvoie une XYLineChart liée avec dataset
	 */
	private JFreeChart createGraphe( XYDataset dataset ){
		return ChartFactory.createXYLineChart(
				titreTrace,
				"temps",
				"valeurs",
				dataset,
				PlotOrientation.VERTICAL,
				false,
				true,
				false);
	}
	
	/*
	 * createTimeSeriesChart
	 * Renvoie une TimeSeriesChart liée avec dataset
	 */
	private JFreeChart createTimeSeriesChart( XYDataset dataset ){
		return ChartFactory.createTimeSeriesChart(
				titreTrace,
				"temps",
				"valeurs",
				dataset,
				true,
				true,
				false);
	}
	
	public ChartPanel getChartPanel(){
		return chartpanel;
	}

}
