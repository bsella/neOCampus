package IHM;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

 
public class JTableRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1) {
        	component.setForeground(Color.BLACK);
        	component.setBackground(Color.WHITE);
        }

        TableauCapteurModel model = (TableauCapteurModel) table.getModel();
        value = model.getValueAt(row, 3);
        String ID = (String) model.getValueAt(row, 0);
        System.out.println("Value : " + value + " ID : " + ID);
        int valAlerte = -1;
        double valTableau = -1;
        if (model.listeAlertes.size() > 0) {
        	for (int i = 0 ; i < model.listeAlertes.size() ; i++) {
        		if (ID.equals(model.listeAlertes.get(i).getIDCapteur())) {
        			valAlerte = model.listeAlertes.get(i).getValAlerte();
        		}
        	}
           	if (value instanceof Double) {
           		valTableau = (double) value;
           	} else {
           		valTableau = valAlerte;	
           	}
           	if (valTableau < valAlerte ) {
           		component.setBackground(Color.RED);
           	} else {
           		component.setBackground(Color.WHITE);
           	}
        }
        return component;
    }
}