package IHM;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import capteur.Capteur;
import capteur.TypeCapInter;
import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;
import interfaceSansConnexion.InterfaceSansConnexion;

public class InterfaceVisu extends JFrame {
	private static final long serialVersionUID = 1L;
	private ServeurIHM sIHM;
	boolean connected=false;

	private boolean validIP(String ip){
	    try{
	        if(ip == null || ip.isEmpty()) return false;
	        String[] parts = ip.split("\\.");
	        if (parts.length != 4) return false;
	        for(String s : parts){
	            int i = Integer.parseInt(s);
	            if((i<0) || (i>255)) return false;
	        }
	        if(ip.endsWith(".")) return false;
	        return true;
	    }catch (NumberFormatException nfe){
	        return false;
	    }
	}
	
	int getIndex(String[] ss, String s){
		for(int i=0; i<ss.length; i++){
			if(ss[i].equals(s))
				return i;
		}
		return -1;
	}
	
	public InterfaceVisu(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tp= new JTabbedPane();
		Container connexion = new Container();
		Container data = new Container();
		Container alerte = new Container();
		tp.addTab("Connexion", connexion);
		// A suprrimer quand on doit rendre le projet
		tp.addTab("Data", data);
		tp.addTab("Alerte", alerte);
		tp.add("Graphique", InterfaceSansConnexion.Panel());
		// A supprimer quand on doit rendre le projet
		GridBagLayout gbl = new GridBagLayout();
		data.setLayout(gbl);
		
		
		TableauCapteurModel dm= new TableauCapteurModel();
		JTable t = new JTable(dm);
		t.setDefaultRenderer(Object.class, new JTableRender());
		JScrollPane dataTable= new JScrollPane(t);
		CapteurInterieur test1= new CapteurInterieur("test1" , new Salle(new Etage(2, new Batiment("U3", 0, 1)), "103"), "testt", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test2= new CapteurInterieur("test2" , new Salle(new Etage(2, new Batiment("U3", 0, 1)), "103"), "test", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test3= new CapteurInterieur("test3" , new Salle(new Etage(1, new Batiment("U2", 0, 1)), "104"), "test", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test4= new CapteurInterieur("test4" , new Salle(new Etage(2, new Batiment("U2", 0, 1)), "104"), "test", TypeCapInter.EAU_CHAUDE);
		
		DefaultListModel<Capteur> capteurListModel= new DefaultListModel<>();
		JList<Capteur> list=new JList<>(capteurListModel);
		JScrollPane listScroll=new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(600,100));
		
		DefaultMutableTreeNode root =new DefaultMutableTreeNode("Capteurs");
		CapteurTreeModel ctm= new CapteurTreeModel(root);
		JTree capteurTree=new JTree(ctm);
		ctm.add(test1);
		ctm.add(test2);
		ctm.add(test3);
		ctm.add(test4);
		ctm.remove("test4");
		ctm.remove("test3");
		dm.add(test1);
		dm.add(test2);
		dm.add(test3);
		dm.add(test4);
		
		GridBagLayout gblConnextion = new GridBagLayout();
		connexion.setLayout(gblConnextion);
		GridBagConstraints c= new GridBagConstraints();
		
		JTextField textID =new JTextField(20);
		JTextField textIp =new JTextField(15);
		JTextField textPort =new JTextField(5);
		JButton connectB =new JButton("Connexion au Serveur");
		JButton deconnectB =new JButton("Déconnexion");
		
		deconnectB.setEnabled(false);
		c.weightx=1;c.weighty=1;
		c.gridx=0;c.gridy=0;
		c.fill=GridBagConstraints.HORIZONTAL;
		connexion.add(new JLabel("Identifiant :"),c);
		c.gridy++;
		connexion.add(new JLabel("Addresse du serveur :"),c);
		c.gridy++;
		connexion.add(new JLabel("Numéro de port :"),c);
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		connexion.add(connectB,c);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridy=0;
		c.gridx=1;
		connexion.add(textID,c);
		c.gridy++;
		connexion.add(textIp,c);
		c.gridy++;
		connexion.add(textPort,c);
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		connexion.add(deconnectB,c);
		
		JScrollPane scrollTree= new JScrollPane(capteurTree);
		c= new GridBagConstraints();
		c.weightx=5;
		c.weighty=5;
		c.fill=GridBagConstraints.BOTH;
		data.add(dataTable,c);
		c.weightx=1;
		data.add(scrollTree,c);
		c.gridy=1;
		c.weightx=5;
		c.weighty=1;
		data.add(listScroll,c);
		JButton inscB= new JButton("S'inscrire");
		JButton desinscB= new JButton("Se désinscrire");
		JPanel boutons= new JPanel(new GridLayout(2, 1));
		boutons.add(inscB);
		boutons.add(desinscB);
		inscB.setEnabled(false);
		desinscB.setEnabled(false);
		c.weightx=1;
		data.add(boutons,c);
		
		/* Tab Alerte */
		GridBagLayout gblAlerte = new GridBagLayout();
		alerte.setLayout(gblAlerte);
		JButton buttonValider = new JButton("Valider alerte");
		JButton buttonSupprimer = new JButton("Supprimer alerte");
		JSpinner spinnerValAlerte = new JSpinner();
		JRadioButton inferieurR = new JRadioButton("inférieur à la valeur");
		JRadioButton superieurR = new JRadioButton("supérieur à la valeur");
		
		JTable tAlerte = new JTable(dm);
		tAlerte.setDefaultRenderer(Object.class, new JTableRender());
		JScrollPane dataTableCapAlerte= new JScrollPane(tAlerte);
		
		DefaultTableModel modelAlerte = new DefaultTableModel(new Object[]{"ID", "Type Capteur","ValeurAlerte", "Alertes"},0);
		JTable tableauAlerte = new JTable(modelAlerte);

		List<Alerte> listeAlertes = new ArrayList<>();
		dm.setListeAlertes(listeAlertes);
		JScrollPane dataTabAlertes = new JScrollPane(tableauAlerte);
		JPanel boutonsRadioAlerte = new JPanel(new GridLayout(2,2));
		inferieurR.setSelected(true);
		boutonsRadioAlerte.add(inferieurR);
		boutonsRadioAlerte.add(superieurR);
		boutonsRadioAlerte.add(buttonValider);
		boutonsRadioAlerte.add(buttonSupprimer);
		//position grille
		c.gridx=0; c.gridy=0;
		//nombre de case occupé
		//ratio des cases
		c.weightx=1;
		c.fill=GridBagConstraints.BOTH;
		alerte.add(new JLabel("Liste des Capteurs :"), c);
		c.gridy++;
		alerte.add(new JLabel("Liste des Alertes :"), c);
		c.gridy++;
		alerte.add(new JLabel("Valeur de l'alerte :"), c);
		c.weightx=4;
		c.gridx=1; c.gridy=0;
		alerte.add(dataTableCapAlerte, c);
		c.gridy++;
		alerte.add(dataTabAlertes, c);
		c.gridy++;
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=1;
		alerte.add(spinnerValAlerte, c);
		c.gridy++;
		c.fill=GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridwidth=2;
		alerte.add(boutonsRadioAlerte, c);
		
		inferieurR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inferieurR.isSelected()) {
					superieurR.setSelected(false);
				} else {
					superieurR.setSelected(true);
				}
				
			}
		});
		
		superieurR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (superieurR.isSelected()) {
					inferieurR.setSelected(false);
				} else {
					inferieurR.setSelected(true);
				}
				
			}
		});
		
		buttonValider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tAlerte.getSelectedRow();
		        if(selectedRow != -1) {
		        	String ID = (String) dm.getValueAt(selectedRow, 0);
		        	int niveauAlerte = (int) spinnerValAlerte.getValue();
		        	boolean sens;
		        	if (inferieurR.isSelected()) {
		        		sens = true;
		        	} else {
		        		sens = false;
		        	}
		        	/* Verif */
		        	System.out.println("ID du capteur pour l'alerte" + ID);
		        	System.out.println("Niv d'alerte " + niveauAlerte);
		        	
	        		Alerte alerte = new Alerte(ID, niveauAlerte, sens);
	        		listeAlertes.add(alerte);
	        		modelAlerte.addRow(new Object[]{ID, dm.getValueAt(selectedRow, 1), niveauAlerte, 0});
	        		System.out.println("Alerte Valide");
		        }
			}
		});
		
		buttonSupprimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableauAlerte.getSelectedRow();
		        if(selectedRow != -1) {
		        	String ID = (String) modelAlerte.getValueAt(selectedRow, 0);
		        	/* Verif */
		        	System.out.println("ID du capteur à supprimer" + ID);
		        	for (int i = 0; i < listeAlertes.size(); i++) {
		        		if (ID.equals(listeAlertes.get(i).getIDCapteur())) {
		        			listeAlertes.remove(i);
		        			modelAlerte.removeRow(selectedRow);
		        		}
		        	}
	        		System.out.println("Suppr done");
		        }
			}	
		});
		
		capteurTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath[] selected =capteurTree.getSelectionPaths();
				if(selected!=null){
					List<Capteur> selectedCapteurs=new ArrayList<>();
					for(TreePath node: selected){
						List<Capteur>selectedCapteursInNode=ctm.getCapteurs(node);
						for(Capteur c : selectedCapteursInNode)
							if(!selectedCapteurs.contains(c))
								selectedCapteurs.add(c);
					}
					dm.show(selectedCapteurs);
				}
			}
		});
		
		connectB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean toutVaBien=true;
				int port=0;
				try{
					port=Integer.parseInt(textPort.getText());
					if(port<1024 || port >65356){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
					}
				}catch(Exception ex){
					toutVaBien=false;
					JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
				}
				if(toutVaBien && !validIP(textIp.getText())){
					toutVaBien=false;
					JOptionPane.showMessageDialog(new JFrame(), "Saisir une adresse ip valide");
				}
				if(toutVaBien)if(textID.getText()==null || textID.getText().isEmpty()){
					toutVaBien=false;
					JOptionPane.showMessageDialog(new JFrame(), "Saisir un identifiant pour le capteur");
				}
				if(toutVaBien)try{
					sIHM=new ServeurIHM(textIp.getText(),port,capteurListModel,dm,ctm);
					toutVaBien=sIHM.envoyerConnexionIHM(textID.getText());
					if(!toutVaBien)JOptionPane.showMessageDialog(new JFrame(), "Connexion refusée");
				}catch(Exception ex){
					toutVaBien=false;
					JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
				}
				if(toutVaBien){
					JOptionPane.showMessageDialog(new JFrame(), "Connexion réussie");
					connected=true;
					connectB.setEnabled(false);
					deconnectB.setEnabled(true);
					textIp.setEnabled(false);
					textID.setEnabled(false);
					textPort.setEnabled(false);
					tp.addTab("Data",data);
					tp.addTab("Alerte",alerte);
					tp.setSelectedComponent(data);
				}
			}
		});
		
		deconnectB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					InterfaceSansConnexion.stopToutEnregistrement(); //Rajout
					if(sIHM.deconnecterIHM(textID.getText())){
						connectB.setEnabled(true);
						deconnectB.setEnabled(false);
						textIp.setEnabled(true);
						textPort.setEnabled(true);
						connected=false;
						tp.remove(data);
						tp.remove(alerte);
					}
					else JOptionPane.showMessageDialog(new JFrame(), "Déconnexion refusée");
				} catch (Exception e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), "Erreur de déconnexion??");
				}
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(!list.isSelectionEmpty())
					inscB.setEnabled(true);
			}
		});
		
		inscB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				List<Capteur> capteurInscription= list.getSelectedValuesList();
				Capteur[] capteurValide = sIHM.inscrire(textID.getText(), capteurInscription);
				for(Capteur cap : capteurValide){
					ctm.add(cap);
					dm.add(cap);
					InterfaceSansConnexion.commencerEnregistrement(cap); //Rajout
					capteurListModel.removeElement(cap);
				}
				list.clearSelection();
				inscB.setEnabled(false);
			}
		});
		
		setLayout(new GridLayout(1, 1));
		contentPane.add(tp);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				InterfaceSansConnexion.stopToutEnregistrement(); //Rajout
				if(sIHM!=null)sIHM.terminate();
				if(connected){
					sIHM.deconnecterIHM(textID.getText());
				}
			}
		});
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				InterfaceVisu frame = new InterfaceVisu();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}
}