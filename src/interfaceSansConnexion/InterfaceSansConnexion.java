package interfaceSansConnexion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import org.jfree.chart.ChartPanel;

import capteur.Capteur;
import filesmanager.GestionnaireFichiersEnregistrement;
import traceur.Traceur;

public class InterfaceSansConnexion {
	
	private static JPanel sansConnexionPanel = new JPanel();
	private static GestionnaireFichiersEnregistrement gestionneur = new GestionnaireFichiersEnregistrement();
	private static Traceur traceur = new Traceur();
	private static JTree tree;
	private static ChartPanel trace;
	
	public static JPanel Panel(){
		
		sansConnexionPanel.setLayout(new GridBagLayout());
		
		// Graphe ///////////////////////////////////////
		trace = traceur.getChartPanel();
		JScrollPane scrollTrace = new JScrollPane(trace);
		scrollTrace.setPreferredSize( new Dimension(300, 100) );
		
		GridBagConstraints contraintsTrace = new GridBagConstraints();
		contraintsTrace.weightx = 5;
		contraintsTrace.weighty = 5;
		contraintsTrace.fill = GridBagConstraints.BOTH;
		sansConnexionPanel.add(scrollTrace, contraintsTrace);
		////////////////////////////////////////////////
		
		// Tree ///////////////////////////////////////
		tree = new JTree( new TreeModelFichiersEnregistrement(gestionneur) );
		tree.addTreeSelectionListener( new TreeSelectionEnregistrement(gestionneur, traceur) );
		JScrollPane scrollTree = new JScrollPane(tree);
		
		GridBagConstraints contraintsTree = new GridBagConstraints();
		contraintsTree.weightx = 1;
		sansConnexionPanel.add(scrollTree);
		////////////////////////////////////////////////
		
		return sansConnexionPanel;
	}
	
	public static void commencerEnregistrement( Object capteur ){
		if( capteur instanceof Capteur ){
			gestionneur.nouvelEnregistrement(capteur);
		}
	}
	
	public static void recupererDonnee( String identifiant, float valeur ){
		gestionneur.processObtentionValeurPourEnregistrement(identifiant, valeur);
		trace.getChart().fireChartChanged();
		trace.repaint();
	}
	
	public static void finirEnregistrement( Object capteur ){
		if( capteur instanceof Capteur ){
			gestionneur.stopEnregistrement((Capteur)capteur);
			tree.updateUI();
		}
	}
	
	public static void stopToutEnregistrement(){
		gestionneur.stopAll();
		tree.revalidate();
		tree.updateUI();
	}
	
	
}
