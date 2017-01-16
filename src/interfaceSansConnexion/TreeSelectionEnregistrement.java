package interfaceSansConnexion;

import java.util.Date;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import capteur.Capteur;
import filesmanager.FichierEnregistrement;
import filesmanager.GestionnaireFichiersEnregistrement;
import traceur.Traceur;

public class TreeSelectionEnregistrement implements TreeSelectionListener {
	
	private TreePath path;
	private GestionnaireFichiersEnregistrement gst;
	private Traceur traceur;
	
	public TreeSelectionEnregistrement(GestionnaireFichiersEnregistrement gst, Traceur traceur){
		this.gst = gst;
		this.traceur = traceur;
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		path = event.getPath();
		
		FichierEnregistrement fichier = getFichierEnregistrementFromPath();
		Date date = getDateFromPath();
		if( date != null && fichier != null ){
			traceur.DatasetFromFichierEnregistrement(fichier, date);
			//traceur.TimeSeriesFromFichierEnregistrement(fichier, date);
		}
	}
	
	private FichierEnregistrement getFichierEnregistrementFromPath(){
		if( path.getLastPathComponent() instanceof Date ){
			
			int count = path.getPathCount();
			Capteur capteur = (Capteur)path.getPathComponent(count - 2);			
			return gst.getFichierEnregistrement(capteur);
		}
		return null;
	}
	
	private Date getDateFromPath(){
		if( path.getLastPathComponent() instanceof Date ){
			
			int count = path.getPathCount();
			Date date = (Date)path.getPathComponent(count - 1);
			
			return date;
		}
		return null;
	}

}
