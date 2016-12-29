package IHM;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Comparator;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import capteur.Batiment;
import capteur.CapteurExterieur;
import capteur.CapteurInterieur;
import capteur.Emplacement;
import capteur.GPSCoord;
import capteur.TypeCapExter;
import capteur.TypeCapInter;

public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	private ServeurIHM sIHM;
	private EcouteThread ecouteThread;
	boolean connected=false;
	
	private NavigableSet<CapteurInterieur> captInt=
			new TreeSet<CapteurInterieur>(
				new Comparator<CapteurInterieur>(){
					public int compare(CapteurInterieur c, CapteurInterieur c2){
						return c.compareTo(c2);
					}
				}
			);
	private NavigableSet<CapteurExterieur> captExt=
			new TreeSet<CapteurExterieur>(
				new Comparator<CapteurExterieur>(){
					public int compare(CapteurExterieur c, CapteurExterieur c2){
						return c.compareTo(c2);
					}
				}
			);
	
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
	
	public Fenetre() throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tp= new JTabbedPane();
		JScrollPane connexion = new JScrollPane();
		Container data = new Container();
		tp.addTab("Connexion", connexion);
		tp.addTab("Data", data);
		data.setLayout(new BorderLayout());
		connexion.setLayout(null);
		
		JLabel lblID = new JLabel("Identifiant :");
		JLabel lblIp = new JLabel("Addresse du serveur :");
		JLabel lblPort = new JLabel("Numero de port :");
		JTextField textID = new JTextField();
		JTextField textIp = new JTextField();
		JTextField textPort = new JTextField();
		JButton connectB = new JButton("Connexion au Serveur");
		JButton deconnectB = new JButton("Deconnexion");
		
		JTable dataTable=new JTable();
		DefaultListModel<String> capteurListModel= new DefaultListModel<>();
		JList<String> list=new JList<>(capteurListModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroll=new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(600,100));
		data.add(listScroll, BorderLayout.PAGE_END);
		
		DefaultMutableTreeNode root =new DefaultMutableTreeNode("capteurs");
		DefaultMutableTreeNode treeInt =new DefaultMutableTreeNode("interieurs");
		DefaultMutableTreeNode treeExt =new DefaultMutableTreeNode("exterieurs");
		root.add(treeInt);
		root.add(treeExt);
		JTree capteurTree=new JTree(root);
		DefaultTreeModel tmodel = (DefaultTreeModel)capteurTree.getModel();
		data.add(capteurTree, BorderLayout.LINE_END);
		for(CapteurInterieur ci : captInt)
			treeInt.add(new DefaultMutableTreeNode(ci.toString()));
		for(CapteurExterieur ce : captExt)
			treeExt.add(new DefaultMutableTreeNode(ce.toString()));
		
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
		//data.add(dataTable);
		//data.add(capteursTree);
		
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
					sIHM=new ServeurIHM(textIp.getText(),port);
					toutVaBien=sIHM.envoyerConnexionIHM(textID.getText());
					if(!toutVaBien)JOptionPane.showMessageDialog(new JFrame(), "Connexion refusee");
				}catch(Exception ex){
					toutVaBien=false;
					JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
				}
				if(toutVaBien){
					ecouteThread=new EcouteThread(sIHM, capteurListModel);
					ecouteThread.start();
					JOptionPane.showMessageDialog(new JFrame(), "Connexion reussie");
					connected=true;
					connectB.setEnabled(false);
					deconnectB.setEnabled(true);
					textIp.setEnabled(false);
					textPort.setEnabled(false);
					tp.addTab("Data",data);
					tp.setSelectedComponent(data);
				}
			}
		});
		
		deconnectB.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				try{
					if(sIHM.deconnecterIHM(textID.getText())){
						connectB.setEnabled(true);
						deconnectB.setEnabled(false);
						textIp.setEnabled(true);
						textPort.setEnabled(true);
						connected=false;
						tp.remove(data);
						//ecouteThread.stop();
					}
					else JOptionPane.showMessageDialog(new JFrame(), "Deconnexion refusee");
				} catch (Exception e1){
					JOptionPane.showMessageDialog(new JFrame(), "Erreur de deconnexion??");
				}
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				int index=list.getSelectedIndex();
				if(index>=0){
					String buff=capteurListModel.get(index);
					String[] parts=buff.split(";");
					if(parts.length==4){
						GPSCoord gps= new GPSCoord(Double.parseDouble(parts[2]),Double.parseDouble(parts[3]));
						TypeCapExter t= TypeCapExter.HUMIDITE;
						CapteurExterieur ce =new CapteurExterieur(parts[0], gps, t);
						captExt.add(ce);
						treeExt.removeAllChildren();
						for(CapteurExterieur c : captExt)
							treeExt.add(new DefaultMutableTreeNode(c.toString()));
						tmodel.reload(treeExt);
					}else
					if(parts.length==6){
						Emplacement emp=new Emplacement(new Batiment(parts[2],0,0), Integer.parseInt(parts[3]), parts[4], parts[5]);
						TypeCapInter t= TypeCapInter.CONSOMMATION_ECLAIRAGE;
						CapteurInterieur ci = new CapteurInterieur(parts[0], emp, t);
						captInt.add(ci);
						treeInt.removeAllChildren();
						for(CapteurInterieur c : captInt)
							treeInt.add(new DefaultMutableTreeNode(c.toString()));
						tmodel.reload(treeInt);
					}
					capteurListModel.removeElementAt(index);
				}
			}
		});
		
		setLayout(new GridLayout(1, 1));
		contentPane.add(tp);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					Fenetre frame = new Fenetre();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}