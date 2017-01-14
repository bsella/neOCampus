package IHM;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
public class JTableRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        int selectedrow = table.getSelectedRow();
        if(selectedrow != -1) {
        	component.setForeground(Color.BLACK);
        }
        /**
         * Colorie les cellules en orange si le montant est inferieur au montant voulu
         */
        Object o = table.getValueAt(row, 3);
        if (o != null && component instanceof JLabel) {
            JLabel label = (JLabel) component;
            double valeur;
            try {
            	valeur = Double.parseDouble(label.getText());
            	// Valeur a changer avec pop-up et boutton Alerte
            	if (valeur < 500) {
                    Color clr = new Color(255, 226, 198);
                    component.setBackground(clr);
                    System.out.println("valeur" + valeur);
                }
			} catch (Exception e) {
				Color color = new Color(255, 255, 255);
		        component.setBackground(color);
			}
        }
        return component;
    }
}