package IHM;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class JTableRender extends DefaultTableCellRenderer {

	private boolean inAlerte(double val, List<Alerte> listA){
		Alerte plusProcheAGauche=null;
		Alerte plusProcheADroite=null;
		for(Alerte a : listA){
			if(plusProcheAGauche==null){
				if(a.getValAlerte()<val)
					plusProcheAGauche=a;
			}else{
				if(a.getValAlerte()<val && a.getValAlerte()>plusProcheAGauche.getValAlerte())
					plusProcheAGauche=a;
			}
			if(plusProcheADroite==null){
				if(a.getValAlerte()>val)
					plusProcheADroite=a;
			}else{
				if(a.getValAlerte()>val && a.getValAlerte()<plusProcheADroite.getValAlerte())
					plusProcheADroite=a;
			}
		}
		if(plusProcheADroite== null){
			if(plusProcheAGauche==null)
				return false;
			return plusProcheAGauche.isSuperieur();
		}else{
			if(plusProcheAGauche==null)
				return !plusProcheADroite.isSuperieur();
			return plusProcheAGauche.isSuperieur() || !plusProcheADroite.isSuperieur();
		}
	}
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		component.setBackground(Color.WHITE);
		component.setForeground(Color.BLACK);
		
		int selectedRow = table.getSelectedRow();
		if(selectedRow != -1){
			component.setForeground(Color.BLACK);
			component.setBackground(Color.WHITE);
		}

		TableauCapteurModel model = (TableauCapteurModel) table.getModel();
		value = model.getValueAt(row, 3);
		String ID = (String)model.getValueAt(row, 0);
		List<Alerte> listA =model.getAlertes(ID);
		if(value instanceof Double){
			if(inAlerte((double)value, listA))
				component.setBackground(Color.RED);					
		}
		return component;
    }
}
