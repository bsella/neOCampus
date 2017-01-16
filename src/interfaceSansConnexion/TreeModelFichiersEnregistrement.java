package interfaceSansConnexion;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;
import filesmanager.GestionnaireFichiersEnregistrement;

public class TreeModelFichiersEnregistrement extends DefaultTreeModel  {
	
	private static final long serialVersionUID = 1L;
	
	private GestionnaireFichiersEnregistrement gstFichiersEnregistrement;
	private List<CapteurInterieur> capteurInterieurs;
	private List<CapteurExterieur> capteurExterieurs;
		
	private static List<Batiment> batiments= new ArrayList<>();
	private static List<CapteurExterieur> capExt = new ArrayList<>();
	
	private static DefaultMutableTreeNode root =new DefaultMutableTreeNode("Capteurs");
	
	public TreeModelFichiersEnregistrement( GestionnaireFichiersEnregistrement gstFichiersEnregistrement ) {
		super(root);
		this.gstFichiersEnregistrement = gstFichiersEnregistrement;
		capteurExterieurs = gstFichiersEnregistrement.getCapteurExterieur();
		capteurInterieurs = gstFichiersEnregistrement.getCapteurInterieur();
		for( CapteurInterieur capteur : capteurInterieurs ){
			add(capteur);
		}
		for( CapteurExterieur capteur : capteurExterieurs ){
			add(capteur);
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
			if(index>=((Batiment)parent).getEtages().size())
				return null;
			return ((Batiment)parent).getEtages().get(index);
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
		//
		if( parent instanceof Capteur ){
			return gstFichiersEnregistrement.getFichierEnregistrement((Capteur)parent).getDates().get(index);
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
			return ((Batiment)parent).getEtages()==null?0:((Batiment) parent).getEtages().size();
		if(parent instanceof Etage)
			return ((Etage)parent).getSalles().size();
		if(parent instanceof Salle)
			return ((Salle)parent).getCapteurs().size();
		if( parent instanceof Capteur ){
			return gstFichiersEnregistrement.getFichierEnregistrement((Capteur)parent).getDates().size();
		}
		return 0;
	}
	public boolean isLeaf(Object node){
		if(node instanceof String){
			if(node.equals("Interieurs") || node.equals("Exterieurs"))
				return false;
		}
		return getChildCount(node)==0;
    }

	@Override
	public int getIndexOfChild(Object pere, Object fils) {

		return 0;
	}

	@Override
	public Object getRoot(){
		return "Capteurs";
	}

	private static <T> T getSame(T t, List<T> list){
		for(T tt : list){
			if(tt.equals(t))
				return tt;
		}
		return t;
	}
	public static void add(Capteur c){
		if(c instanceof CapteurInterieur){
			CapteurInterieur ci= (CapteurInterieur)c;
			Batiment b =getSame(ci.getBatiment(),batiments);
			Etage e;
			Salle s;
			if(!batiments.contains(b)){
				batiments.add(b);
				e=ci.getEtage();
				b.addEtage(e);
				s= ci.getSalle();
				e.addSalle(s);
			}else{
				e=getSame(ci.getEtage(),b.getEtages());
				if(!b.getEtages().contains(e)){
					b.addEtage(e);
					s= ci.getSalle();
					e.addSalle(s);
				}else{
					s=getSame(ci.getSalle(),e.getSalles());
					if(!e.getSalles().contains(s))
						e.addSalle(s);
				}
			}
			s.addCapteur(ci);
		}else{
			CapteurExterieur ce=(CapteurExterieur)c;
			ce= getSame(ce, capExt);		
			if(!capExt.contains(ce)){
				capExt.add(ce);
			}
		}
	}

}
