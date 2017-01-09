package IHM;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import capteur.CapteurInterieur;

public class TableauCapteurIntModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	List<CapteurInterieur> capList= new ArrayList<>();
	List<Double> data= new ArrayList<>();

	boolean contains(CapteurInterieur ci){
		for(CapteurInterieur c : capList){
			if(c.equals(ci))
				return true;
		}
		return false;
	}
	@Override
	public int getRowCount(){
		return capList.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CapteurInterieur ci= capList.get(rowIndex);
		switch(columnIndex){
			case 0: return ci.getID();
			case 1: return ci.getType();
			case 2: return ci.getEmplacement().getBatiment();
			case 3: return ci.getEmplacement().getEtage();
			case 4: return ci.getEmplacement().getSalle();
			case 5: return ci.getEmplacement().getDescriptif();
			case 6: return data.get(rowIndex)==null?"NULL":data.get(rowIndex);
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
			case 2: return "Batiment";
			case 3: return "Etage";
			case 4: return "Salle";
			case 5: return "Position";
			case 6: return "Donnée";
		}
		return null;
	}
	
	public void add(CapteurInterieur ci, double val){
		if(!contains(ci)){
			capList.add(ci);
			data.add(val);
		}
	}
	public void changeVal(CapteurInterieur ci, double val){
		if(contains(ci)){
			data.set(capList.indexOf(ci),val);
		}
	}
}
