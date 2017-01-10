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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import capteur.Capteur;
import capteur.CapteurExterieur;
import capteur.GPSCoord;
import capteur.TypeCapExter;
import capteur.TypeCapInter;
import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;

public class Fenetre extends JFrame {
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
	
	private TypeCapExter getTypeExter(String t){
		switch(t){
			case "PRESSION":
				return TypeCapExter.PRESSION_ATMOSPHERIQUE;
			case "VITESSE_VENT":
				return TypeCapExter.VITESSE_VENT;
			case "TEMPERATURE":
				return TypeCapExter.TEMPERATURE;
			case "HUMIDITE":
				return TypeCapExter.HUMIDITE;
			case "LUMINOSITE":
				return TypeCapExter.LUMINOSITE;
			default: return null;
		}
	}
	private TypeCapInter getTypeInter(String t){
		switch(t){
			case "TEMPERATURE":
				return TypeCapInter.TEMPERATURE;
			case "HUMIDITE":
				return TypeCapInter.HUMIDITE;
			case "LUMINOSITE":
				return TypeCapInter.LUMINOSITE;
			case "VOLUME_SONORE":
				return TypeCapInter.VOLUME_SONORE;
			case "EAU_FROIDE":
				return TypeCapInter.EAU_FROIDE;
			case "EAU_CHAUDE":
				return TypeCapInter.EAU_CHAUDE;
			case "CONSOMMATION_ECLAIRAGE":
				return TypeCapInter.CONSOMMATION_ECLAIRAGE;
			default: return null;
		}
	}
	
	int getIndex(String[] ss, String s){
		for(int i=0; i<ss.length; i++){
			if(ss[i].equals(s))
				return i;
		}
		return -1;
	}
	
	public Fenetre(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tp= new JTabbedPane();
		JScrollPane connexion = new JScrollPane();
		Container data = new Container();
		tp.addTab("Connexion", connexion);
		tp.addTab("Data", data);
		GridBagLayout gbl = new GridBagLayout();
		data.setLayout(gbl);
		connexion.setLayout(null);
		
		JLabel lblID = new JLabel("Identifiant :");
		JLabel lblIp = new JLabel("Addresse du serveur :");
		JLabel lblPort = new JLabel("Numéro de port :");
		JTextField textID = new JTextField("hjsf");
		JTextField textIp = new JTextField("127.0.0.1");
		JTextField textPort = new JTextField("7888");
		JButton connectB = new JButton("Connexion au Serveur");
		JButton deconnectB = new JButton("Déconnexion");
		
		JPanel dataTable= new JPanel(new GridLayout(2, 1));
		TableauCapteurModel dm= new TableauCapteurModel();
		JTable t = new JTable(dm);
		CapteurInterieur test1= new CapteurInterieur("test1" , new Salle(new Etage(2, new Batiment("U3", 0, 1)), "103"), "testt", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test2= new CapteurInterieur("test2" , new Salle(new Etage(2, new Batiment("U3", 0, 1)), "103"), "test", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test3= new CapteurInterieur("test3" , new Salle(new Etage(1, new Batiment("U2", 0, 1)), "104"), "test", TypeCapInter.EAU_CHAUDE);
		CapteurInterieur test4= new CapteurInterieur("test4" , new Salle(new Etage(2, new Batiment("U2", 0, 1)), "104"), "test", TypeCapInter.EAU_CHAUDE);
		dataTable.add(t);
		
		DefaultListModel<String> capteurListModel= new DefaultListModel<>();
		JList<String> list=new JList<>(capteurListModel);
		JScrollPane listScroll=new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(600,100));
		
		DefaultMutableTreeNode root =new DefaultMutableTreeNode("Capteurs");
		CapteurTreeModel ctm= new CapteurTreeModel(root);
		JTree capteurTree=new JTree(ctm);
		ctm.add(test1);
		ctm.add(test2);
		ctm.add(test3);
		ctm.add(test4);
		dm.add(test1);
		dm.add(test2);
		dm.add(test3);
		dm.add(test4);
		
		lblID.setBounds(130, 123, 150, 16);
		textID.setBounds(280, 120, 150, 26);
		lblIp.setBounds(130, 183, 150, 16);
		textIp.setBounds(280, 180, 122, 26);
		lblPort.setBounds(130, 243, 150, 16);
		textPort.setBounds(280, 240, 55, 26);
		connectB.setBounds(70, 350, 200, 100);
		deconnectB.setBounds(290, 350, 200, 100);
		deconnectB.setEnabled(false);
		
		connexion.add(lblID);
		connexion.add(textID);
		connexion.add(lblIp);
		connexion.add(textIp);
		connexion.add(lblPort);
		connexion.add(textPort);
		connexion.add(connectB);
		connexion.add(deconnectB);
		JScrollPane scrollTree= new JScrollPane(capteurTree);
		GridBagConstraints c= new GridBagConstraints();
		c.weightx=5;
		c.weighty=5;
		c.fill=GridBagConstraints.BOTH;
		gbl.setConstraints(dataTable, c);
		data.add(dataTable);
		c.weightx=1;
		gbl.setConstraints(scrollTree, c);
		data.add(scrollTree);
		c.gridy=1;
		c.weightx=5;
		c.weighty=1;
		gbl.setConstraints(listScroll, c);
		data.add(listScroll);
		JButton inscB= new JButton("S'inscrire");
		inscB.setEnabled(false);
		c.weightx=1;
		gbl.setConstraints(inscB, c);
		data.add(inscB);
		
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
					sIHM=new ServeurIHM(textIp.getText(),port,capteurListModel,dm);
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
					tp.setSelectedComponent(data);
				}
			}
		});
		deconnectB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					if(sIHM.deconnecterIHM(textID.getText())){
						connectB.setEnabled(true);
						deconnectB.setEnabled(false);
						textIp.setEnabled(true);
						textPort.setEnabled(true);
						connected=false;
						tp.remove(data);
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
				int ind[]=list.getSelectedIndices();
				String[] capteurInscription= new String[ind.length];
				for(int i=0; i<ind.length; i++){//obtenir les identifiants des capteurs selectionnés
					capteurInscription[i]=capteurListModel.get(ind[i]);
					String[] parts=capteurInscription[i].split(";");
					capteurInscription[i]=parts[0];
				}
				String[] capteurValide = sIHM.inscrire(textID.getText(), capteurInscription);
				for(String cap : capteurValide){
					int i= getIndex(capteurInscription, cap);
					String[] parts=capteurListModel.get(ind[i]).split(";");
					if(parts.length==4 || parts.length==6){
						if(parts.length==4){
							GPSCoord gps= new GPSCoord(Double.parseDouble(parts[2]),Double.parseDouble(parts[3]));
							TypeCapExter t= getTypeExter(parts[1]);
							CapteurExterieur ce= new CapteurExterieur(parts[0], gps, t);
							ctm.add(ce);
							dm.add(ce);
						}else{
							TypeCapInter t= getTypeInter(parts[1]);
							CapteurInterieur ci = new CapteurInterieur(parts[0], new Salle(new Etage(Etage.toInt(parts[3]), new Batiment(parts[2], 1, 2)), parts[4]), parts[5], t);
							ctm.add(ci);
							dm.add(ci);
						}
						capteurListModel.removeElementAt(i);
					}
				}
				list.clearSelection();
				inscB.setEnabled(false);
			}
		});
		setLayout(new GridLayout(1, 1));
		contentPane.add(tp);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(connected){
					sIHM.deconnecterIHM(textID.getText());
				}
				if(sIHM!=null)sIHM.terminateThread();
			}
		});
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				Fenetre frame = new Fenetre();
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}
}