package IHM;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import capteur.Batiment;
import capteur.CapteurExterieur;
import capteur.CapteurInterieur;
import capteur.TypeCapInter;


public class CapteurTreeModel extends DefaultTreeModel{
	
	class Etage{
		int etage;
		Batiment bat;
		List<Salle> salles= new ArrayList<>();
		Etage(int e, Batiment b){
			etage=e;
			bat=b;
		}
		int get(){
			return etage;
		}
		Batiment getBatiment(){
			return bat;
		}
		public String toString(){
			if(etage<0) return "étage "+etage;
			if(etage==0) return "RDC";
			if(etage==1) return "1er étage";
			return Integer.toString(etage)+ "eme étage";
		}
		void addSalle(Salle s){
			salles.add(s);
		}
		void removeSalle(Salle s){
			salles.remove(s);
		}
		boolean listVide(){
			return salles.isEmpty();
		}
		List<Salle> getSalles(){
			return salles;
		}
	}
	class Salle{
		String salle;
		Etage etage;
		List<Capteur> capteurs= new ArrayList<>();
		Salle(Etage e, String s){
			salle=s;
			etage=e;
		}
		String get(){
			return salle;
		}
		Etage getEtage(){
			return etage;
		}
		public String toString(){
			return salle;
		}
		void addCapteur(Capteur c){
			capteurs.add(c);
		}
		void removeCapteur(Capteur c){
			capteurs.remove(c);
		}
		boolean listVide(){
			return capteurs.isEmpty();
		}
		List<Capteur> getCapteurs(){
			return capteurs;
		}
	}
	class Capteur{
		String ID;
		String position;
		Salle salle;
		TypeCapInter type;
		Capteur(String id, String p,Salle s, TypeCapInter t){
			ID=id;
			position=p;
			salle=s;
			type= t;
		}
		String get(){
			return ID;
		}
		String getPos(){
			return position;
		}
		Salle getSalle(){
			return salle;
		}
		public String toString(){
			return ID+"("+position+")";
		}
	}
	private static final long serialVersionUID = 1L;
	List<Batiment> batiments= new ArrayList<>();
	Map<Batiment, List<Etage>> batimentEtage= new HashMap<>();
	List<String> capExt = new ArrayList<>();
	
	public CapteurTreeModel(TreeNode root) {
		super(root);
	}
	
	public Object getRoot(){
		return "Capteurs";
	}
	<T> T getSame(T t, List<T> list){
		for(T tt : list){
			if(tt.toString().equals(t.toString()))
				return tt;
		}
		return t;
	}
	public void add(CapteurInterieur ci){
		Batiment b = ci.getEmplacement().getBatiment();
		Etage e= new Etage(ci.getEmplacement().getEtage(),b);
		Salle s= new Salle(e, ci.getEmplacement().getSalle());
		b=getSame(b,batiments);
		if(!batiments.contains(b)){
			List<Etage> eList= new ArrayList<>();
			eList.add(e);
			batiments.add(b);
			batimentEtage.put(b, eList);
			e.addSalle(s);
		}else{
			e=getSame(e, batimentEtage.get(b));
			if(!batimentEtage.get(b).contains(e)){
				List<Etage> eList= batimentEtage.get(b);
				eList.add(e);
				batimentEtage.put(b, eList);
				e.addSalle(s);
			}else{
				s=getSame(s, e.getSalles());
				if(!e.getSalles().contains(s))
					e.addSalle(s);
			}
		}
		s.addCapteur(new Capteur(ci.getID(),ci.getEmplacement().getDescriptif(),s,ci.getType()));
	}
	public void remove(CapteurInterieur ci){
		Batiment b = ci.getEmplacement().getBatiment();
		Etage e= new Etage(ci.getEmplacement().getEtage(),b);
		Salle s= new Salle(e, ci.getEmplacement().getSalle());
		Capteur c= new Capteur(ci.getID(),ci.getEmplacement().getDescriptif(),s,ci.getType());
		s.removeCapteur(c);
		if(s.listVide()){
			e.removeSalle(s);
			if(e.listVide()){
				batimentEtage.remove(e);
				if(batimentEtage.isEmpty()){
					batiments.remove(b);
				}
			}
		}
	}
	public void add(CapteurExterieur ce){
		if(!capExt.contains(ce)){
			capExt.add(ce.toString());
			Collections.sort(capExt);
		}
	}

	public Object getChild(Object parent, int index) {
		if(parent instanceof String)
			switch((String)parent){
				case "Capteurs":
					switch(index){
						case 0: return "Interieurs";
						case 1: return "Exterieurs";
						default: return null;
					}
				case "Interieurs":
					if(index>=batiments.size())
						return null;
					return batiments.get(index);
				case "Exterieurs":
					if(index>=capExt.size())
						return null;
					return capExt.get(index);
			}
		if(parent instanceof Batiment){
			if(index>=batimentEtage.get(parent).size())
				return null;
			return batimentEtage.get(parent).get(index);
		}
		if(parent instanceof Etage){
			if(index>=((Etage)parent).getSalles().size())
				return null;
			return ((Etage)parent).getSalles().get(index);
		}
		if(parent instanceof Salle){
			if(index>=((Salle)parent).getCapteurs().size())
				return null;
			return ((Salle)parent).getCapteurs().get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if(parent instanceof String)
			switch((String)parent){
				case "Capteurs": return 2;
				case "Interieurs": return batiments.size();
				case "Exterieurs": return capExt.size();
			}
		if(parent instanceof Batiment)
			return batimentEtage.get(parent)==null?0:batimentEtage.get(parent).size();
		if(parent instanceof Etage)
			return ((Etage)parent).getSalles().size();
		if(parent instanceof Salle)
			return ((Salle)parent).getCapteurs().size();
		return 0;
	}
	public boolean isLeaf(Object node) {
		return getChildCount(node)==0;
    }

}
