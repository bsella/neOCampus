package IHM;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.emplacement.CapteurInterieur;

public class TableauCapteurModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	List<Double> data= new ArrayList<>();
	List<Capteur> capListAll= new ArrayList<>();
	List<CapteurInterieur> capListInt= new ArrayList<>();
	List<CapteurExterieur> capListExt= new ArrayList<>();
	List<Alerte> listeAlertes = new ArrayList<>();
	
	public List<Alerte> getListeAlertes() {
		return listeAlertes;
	}
	public void setListeAlertes(List<Alerte> listeAlertes) {
		this.listeAlertes = listeAlertes;
	}
	private boolean contains(Capteur cap){
		for(Capteur c : capListAll){
			if(c.equals(cap))
				return true;
		}
		return false;
	}
	@Override
	public int getRowCount(){
		return capListInt.size()+capListExt.size();
	}

	@Override
	public int getColumnCount(){
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<capListInt.size()){
			CapteurInterieur ci= capListInt.get(rowIndex);
			switch(columnIndex){
				case 0: return ci.getID();
				case 1: return ci.getType();
				case 2: return ci.getBatiment().toString()+", "+ ci.getEtage().toString()+", salle :"+ci.getSalle().toString()+", "+ci.getPosition();
				case 3: return getValue(ci)==null?"":getValue(ci);
			}
		}else{
			CapteurExterieur ce= capListExt.get(rowIndex-capListInt.size());
			switch(columnIndex){
				case 0: return ce.getID();
				case 1: return ce.getType();
				case 2: return ce.getGPS();
				case 3: return getValue(ce)==null?"":getValue(ce);
			}
		}
		return null;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex){
		return false;
	}

	@Override
	public String getColumnName(int colIndex){
		switch(colIndex){
			case 0: return "ID";
			case 1: return "Type";
			case 2: return "Emplacement";
			case 3: return "Donnée";
		}
		return null;
	}
	
	public void add(Capteur c){
		if(!contains(c)){
			capListAll.add(c);
			data.add(null);
		}
	}
	public void remove(String id){
		Capteur c= stringToCapteur(id);
		if(c!=null){
			data.remove(capListAll.indexOf(c));
			capListAll.remove(c);			
			if(c instanceof CapteurInterieur)
				capListInt.remove(c);
			else
				capListExt.remove(c);
		}
		fireTableDataChanged();
	}
	public Capteur stringToCapteur(String id){
		for(Capteur c : capListAll){
			if(c.getID().equals(id))
				return c;
		}
		return null;
	}
	public void changeVal(Capteur c, double val){
		if(contains(c)){
			data.set(capListAll.indexOf(c),val);
			fireTableDataChanged();
		}
	}
	public Double getValue(Capteur c){
		if(contains(c))
			return data.get(capListAll.indexOf(c));
		return null;
	}
	private void clear(){
		capListInt.clear();
		capListExt.clear();
	}
	public void show(List<Capteur> list){
		clear();
		for(Capteur c : list){
			if(c instanceof CapteurInterieur){
				if(contains(c))
					capListInt.add((CapteurInterieur)c);
			}else{
				if(contains(c))
					capListExt.add((CapteurExterieur)c);				
			}
		}
		fireTableDataChanged();
	}
}
