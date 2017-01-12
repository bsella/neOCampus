package capteur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestionFichierPositionExterieur {
	
	private String fichierBatiment;
	private BufferedReader input;
	
	private ArrayList<String> listbatiment = new ArrayList<String>();
	private Map<String, ArrayList<String>> MapbatimentToetage = new HashMap<String,ArrayList<String>>();
	private Map<Integer, ArrayList<String>> Mapetagebatimenttosalle = new HashMap<Integer,ArrayList<String>>();
	
	/**
	 * Constructeur
	 * Parametre : le chemin d'acces du fichier contenant les batiments
	 */
	public GestionFichierPositionExterieur( String fichierBatiment ){
		this.fichierBatiment = fichierBatiment;
		
		try {
			
			this.input = new BufferedReader( new FileReader( fichierBatiment ) );
			
			try {
				this.interpretationFichier();
			} catch (IOException e) {
				System.out.println("Probleme d'interpretation du fichier");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e ) {
			System.out.println("le fichier " + this.fichierBatiment + " n'a pas peu etre ouvert");
			e.printStackTrace();
		}
		
	}
	
	private int hashCode( String batiment, String etage ){
		return (17 + etage.hashCode() ) * 13 + batiment.hashCode(); 
	}
	
	private boolean interpretationFichier() throws IOException{
		
		String ligne;
		String temp_batiment;
		String temp_etage;
		String[] decoupage_etage_salles;
		String[] decoupage_salle_salle;
		
		do {
			
			ligne = this.input.readLine();
			
			if( ligne != null && !ligne.equals("") ){
				temp_batiment = ligne;
				this.listbatiment.add(ligne);
				
				do {
					
					ligne = this.input.readLine();
					if( ligne != null && !ligne.equals("") ){
						decoupage_etage_salles = ligne.split(":");
						decoupage_salle_salle = decoupage_etage_salles[1].split(",");
						temp_etage = decoupage_etage_salles[0];
						if( this.MapbatimentToetage.containsKey(temp_batiment) ){
							this.MapbatimentToetage.get(temp_batiment).add(temp_etage);
						}else{
							this.MapbatimentToetage.put(temp_batiment, new ArrayList<String>());
							this.MapbatimentToetage.get(temp_batiment).add(temp_etage);
						}
						
						
						if( this.Mapetagebatimenttosalle.containsKey(this.hashCode(temp_batiment, temp_etage)) ){
							for( String elem : decoupage_salle_salle ){
								this.Mapetagebatimenttosalle.get(this.hashCode(temp_batiment, temp_etage)).add(elem);
							}
						}else{
							this.Mapetagebatimenttosalle.put(this.hashCode(temp_batiment, temp_etage), new ArrayList<String>());
							for( String elem : decoupage_salle_salle ){
								this.Mapetagebatimenttosalle.get(this.hashCode(temp_batiment, temp_etage)).add(elem);
							}
						}
					}
					
				} while (ligne != null && !ligne.equals(""));
				
			}
			
		} while (ligne != null);
		
		return true;
	}
	
	/**
	 *  String[] listeBatiment()
	 *  Permet d'obtenir la liste des batiments contenus dans le fichier
	 **/
	public String[] listeBatiment(){
		return this.listbatiment.toArray(new String[0]);
	}
	
	/**
	 *  String[] listeEtageFromBatiment( String batiment )
	 *  Permet d'obtenir la liste des étages que possède le batiment mis en paramètre
	 **/
	public String[] listeEtageFromBatiment( String batiment ){
		ArrayList<String> str = this.MapbatimentToetage.get(batiment);
		return str.toArray(new String[0]);
	}
	
	/**
	 *  String[] listeSalleFromEtageAndBatiment( String batiment, String etage )
	 *  Permet d'obtenir la liste des salles d'un batiment en fonction de son étage.
	 **/
	public String[] listeSalleFromEtageAndBatiment( String batiment, String etage ){
		return this.Mapetagebatimenttosalle.get( this.hashCode(batiment, etage) ).toArray(new String[0]);
	}
	

}
