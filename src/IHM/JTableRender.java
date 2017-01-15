package IHM;

import java.awt.Color;
import java.awt.Component;
import java.util.zip.Inflater;

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
        value = model.getValueAt(row, column);
        System.out.println("Value : " + value);
        int valAlerte = -1;
        double valTableau = -1;
        if (model.listeAlertes.size() > 0) {
           	valAlerte = model.listeAlertes.get(0).getValAlerte();
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