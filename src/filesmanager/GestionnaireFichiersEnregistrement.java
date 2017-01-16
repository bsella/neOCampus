package filesmanager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.emplacement.CapteurInterieur;
import interfaceSansConnexion.TreeModelFichiersEnregistrement;


public class GestionnaireFichiersEnregistrement {
	
	private static final String chemin = "./res/records/"; 
	
	private List<FichierEnregistrement> fichiersDisponibles = new ArrayList<FichierEnregistrement>();
	private Map<String, FichierEnregistrement> fichierEnCoursTraitement = new HashMap<String, FichierEnregistrement>();
	
	// TODO ESSAI POUR TREE
	private List<CapteurInterieur> capteurInterieursPossedantEnregistrement = new ArrayList<CapteurInterieur>();
	private List<CapteurExterieur> capteurExterieursPossedantEnregistrement = new ArrayList<CapteurExterieur>();
	//
	
	/*
	 * Constructeur
	 */
	public GestionnaireFichiersEnregistrement(){
		processFichierDisponible();
	}
	
	/*
	 * processFichierDisponible
	 * procedure qui permet de recuperer les fichiers d'enregistrement present dans le dossier ./res/records/
	 */
	private void processFichierDisponible(){
		File dossier = new File( chemin );
		if( !dossier.exists() ) dossier.mkdirs();
		
		try {
			
			FichierEnregistrement enrg;
			ObjectInputStream ObjReader;
			File[] fichiers = dossier.listFiles();
			
			for( File fichier : fichiers ){
				FileInputStream input = new FileInputStream(fichier);
				ObjReader = new ObjectInputStream(input);
				enrg = (FichierEnregistrement)ObjReader.readObject();
				fichiersDisponibles.add( enrg );
				//TODO ESSAI POUR TREE
				Object capteur = enrg.getCapteur();
				if( capteur instanceof CapteurInterieur ){
					capteurInterieursPossedantEnregistrement.add((CapteurInterieur)capteur);
				}else{
					capteurExterieursPossedantEnregistrement.add((CapteurExterieur)capteur);
				}
				//
				ObjReader.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public FichierEnregistrement getFichierEnregistrement( Capteur capteur ){
		return rechercheFichierExiste(capteur);
	}
	
	//TODO TEST
	public FichierEnregistrement getFichierEnregistrement( int index ){
		return fichiersDisponibles.get(index);
	}
	
	/*
	 * nouvelEnregistrement
	 * Permet de demarrer un nouvel enregistrement
	 * Le parametre d'entrée doit etre une instance valide d'un capteur interieur ou exterieur
	 */
	public void nouvelEnregistrement( Object o ){
		
		if( o instanceof Capteur ){
			
			FichierEnregistrement fichier;
			if( (fichier = rechercheFichierExiste( (Capteur) o )) == null){
				fichier = new FichierEnregistrement(o);
			}
			fichier.setDateActuelle( new Date() );
			fichierEnCoursTraitement.put(fichier.getIdentifiant(), fichier);
		}
		
	}
	
	/*
	 * rechercheFichierExiste
	 * Retourne le fichier d'enregistrement d'un capteur si il existe daja
	 * Le parametre d'entrée doit etre une instance valide d'un capteur interieur ou exterieur
	 */
	private FichierEnregistrement rechercheFichierExiste( Capteur capteur  ){
		for( FichierEnregistrement fichier : fichiersDisponibles ){
			if( capteur.getID().equals(fichier.getIdentifiant()) ) return fichier;
		}
		return null;
	}
	
	/*
	 * StopEnregistrement
	 * Arrete l'enregistrement d'un capteur, l'enregistrement sera automatiqument inscrit dans un fichier
	 * apres cet appel de fonction.
	 * Le parametre d'entrée doit etre une instance valide d'un capteur interieur ou exterieur
	 */
	public void stopEnregistrement( Capteur capteur ){
		
		if( fichierEnCoursTraitement.containsKey(capteur.getID()) ){
			FichierEnregistrement fichier = fichierEnCoursTraitement.remove(capteur.getID());
			processEcritureFichier(fichier);
			if( !fichiersDisponibles.contains(fichier) ){
				fichiersDisponibles.add(fichier);
				if( capteur instanceof CapteurInterieur ){
					capteurInterieursPossedantEnregistrement.add((CapteurInterieur)capteur);
				}else{
					capteurExterieursPossedantEnregistrement.add((CapteurExterieur)capteur);
				}
			}
		}
	}
	
	
	public void stopAll(){
		 Set<String> capteurs = fichierEnCoursTraitement.keySet();
		 Iterator<String> it = capteurs.iterator();
		for( ;it.hasNext();  ){
			String id = it.next();
			if( fichierEnCoursTraitement.containsKey(id) ){
				
				FichierEnregistrement fichier = fichierEnCoursTraitement.get(id);
				processEcritureFichier(fichier);
				it.remove();
				if( !fichiersDisponibles.contains(fichier) ){
					fichiersDisponibles.add(fichier);
					Capteur capteur = fichier.getCapteur();
					TreeModelFichiersEnregistrement.add(capteur);
					if( capteur instanceof CapteurInterieur ){
						capteurInterieursPossedantEnregistrement.add((CapteurInterieur)capteur);
					}else{
						capteurExterieursPossedantEnregistrement.add((CapteurExterieur)capteur);
					}
				}
			}
		}
	}

	
	/*
	 * processEcritureFichier
	 * procedure d'ecriture du fichier 
	 */
	private void processEcritureFichier( FichierEnregistrement fichier ){
		File file = new File( chemin + fichier.getIdentifiant() );
		
		try {
			
			FileOutputStream output = new FileOutputStream(file);
			ObjectOutputStream objetOut = new ObjectOutputStream(output);
			objetOut.writeObject(fichier);
			objetOut.flush();
			objetOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	public void processObtentionValeurPourEnregistrement( String identifiant, float valeur ){
		if( fichierEnCoursTraitement.containsKey(identifiant) ){
			FichierEnregistrement fichier = fichierEnCoursTraitement.get(identifiant);
			fichier.addDonnee( fichier.getDateActuelle() , valeur);
		}
	}
	
	public List<CapteurExterieur> getCapteurExterieur(){
		return capteurExterieursPossedantEnregistrement;
	}
	
	public List<CapteurInterieur> getCapteurInterieur(){
		return capteurInterieursPossedantEnregistrement;
	}

}
