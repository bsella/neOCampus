package IHM;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import capteur.CapteurExterieur;

public class TableauCapteurExtModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	List<CapteurExterieur> capList= new ArrayList<>();
	List<Double> data= new ArrayList<>();

	boolean contains(CapteurExterieur ci){
		for(CapteurExterieur c : capList){
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
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CapteurExterieur ce= capList.get(rowIndex);
		switch(columnIndex){
			case 0: return ce.getID();
			case 1: return ce.getType();
			case 2: return ce.getGPS().getLatitude();
			case 3: return ce.getGPS().getLongitude();
			case 4: return data.get(rowIndex)==null?"NULL":data.get(rowIndex);
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
			case 2: return "Latitude";
			case 3: return "Longitude";
			case 4: return "Donnée";
		}
		return null;
	}
	
	public void add(CapteurExterieur ce){
		if(!contains(ce)){
			capList.add(ce);
			data.add(0.0);
		}
	}
	public void changeVal(CapteurExterieur ce, double val){
		if(contains(ce)){
			data.set(capList.indexOf(ce),val);
		}
	}
}
