package capteur;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import capteur.emplacement.Batiment;
import capteur.emplacement.CapteurInterieur;
import capteur.emplacement.Etage;
import capteur.emplacement.Salle;

public class InterfaceDeSimu extends JFrame {

	private static final long serialVersionUID = 1L;
	boolean connected=false;
	int frequence;
	private ServeurCapteur sc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					InterfaceDeSimu frame = new InterfaceDeSimu();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	private boolean validIP (String ip) {
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
	
	/**
	 * Create the frame.
	 */
	public InterfaceDeSimu(){
		/* Create the content pane */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tp= new JTabbedPane();
		JScrollPane connexion = new JScrollPane();
		JScrollPane capteur = new JScrollPane();
		tp.addTab("Connexion", connexion);
		tp.addTab("Capteur", capteur);
		
		JLabel lblIdentifiant = new JLabel("Identifiant :");
		JLabel lblType= new JLabel("Type de mesure :");
		JLabel lblLatitude = new JLabel("Latitude :");
		JLabel lblLongitude = new JLabel("Longitude :");		
		JTextField textID = new JTextField();
		JTextField textLat = new JTextField();
		JTextField textLon = new JTextField();
		JLabel lblBatiment = new JLabel("Batiment :");
		JLabel lblEtage = new JLabel("Etage :");
		JLabel lblSalle = new JLabel("Salle :");
		JLabel lblPosition = new JLabel("Position :");
		JRadioButton rdbtnInterieur = new JRadioButton("Interieur");
		JRadioButton rdbtnExterieur = new JRadioButton("Exterieur");
		ButtonGroup group = new ButtonGroup();
		JComboBox<String> batiBox = new JComboBox<>();
		JComboBox<String> etageBox = new JComboBox<>();
		JComboBox<String> salleBox = new JComboBox<>();
		JTextField textPos = new JTextField();
		JComboBox<String> mesureBox = new JComboBox<>();
		JButton valideButton = new JButton("Valide");
		JLabel lblIp = new JLabel("Addresse du serveur :");
		JLabel lblPort = new JLabel("Numero de port :");
		JTextField textIp = new JTextField();
		JTextField textPort = new JTextField();
		JButton connectB = new JButton("Connexion au Serveur");
		JButton deconnectB = new JButton("Deconnexion");
		JProgressBar pb= new JProgressBar();
		pb.setMinimum(0);

		lblIdentifiant.setBounds(10, 10, 300, 16);
		textID.setBounds(90, 7, 200, 26);
		lblType.setBounds(10, 38, 122, 28);
		lblLatitude.setBounds(10, 118, 61, 16);
		textLon.setBounds(115, 138, 130, 26);
		lblLongitude.setBounds(10, 143, 73, 16);
		textLat.setBounds(115, 113, 130, 26);
		batiBox.setBounds(80, 112, 130, 27);
		etageBox.setBounds(80, 142, 130, 27);
		lblBatiment.setBounds(10, 115, 70, 16);
		lblEtage.setBounds(10, 145, 61, 16);
		lblSalle.setBounds(10, 175, 61, 16);
		lblPosition.setBounds(10, 205, 61, 16);
		salleBox.setBounds(80, 172, 130, 27);
		textPos.setBounds(80, 202, 130, 27);
		rdbtnExterieur.setBounds(105, 78, 88, 23);
		mesureBox.setBounds(118, 40, 130, 27);
		rdbtnInterieur.setBounds(10, 77, 88, 26);
		valideButton.setBounds(5, 227, 117, 29);
		lblIp.setBounds(130, 133, 150, 16);
		textIp.setBounds(280, 130, 122, 26);
		lblPort.setBounds(130, 193, 150, 16);
		textPort.setBounds(280, 190, 55, 26);
		connectB.setBounds(70, 300, 200, 100);
		deconnectB.setBounds(290, 300, 200, 100);
		pb.setBounds(5, 500, 560, 20);

		capteur.setLayout(null);
		connexion.setLayout(null);
		textLat.setColumns(10);
		textLon.setColumns(10);
		batiBox.setVisible(false);
		etageBox.setVisible(false);
		lblBatiment.setVisible(false);
		lblEtage.setVisible(false);
		lblSalle.setVisible(false);
		lblPosition.setVisible(false);
		salleBox.setVisible(false);
		textPos.setVisible(false);
		rdbtnExterieur.setSelected(true);
		rdbtnExterieur.setEnabled(true);
		rdbtnInterieur.setEnabled(true);
		lblIp.setVisible(true);
		deconnectB.setEnabled(false);
		pb.setVisible(false);
		capteur.add(lblEtage);
		capteur.add(lblSalle);
		capteur.add(lblPosition);
		capteur.add(salleBox);
		capteur.add(textPos);
		capteur.add(rdbtnExterieur);
		capteur.add(rdbtnInterieur);
		capteur.add(lblIdentifiant);
		capteur.add(textID);
		capteur.add(lblType);
		capteur.add(lblLatitude);
		capteur.add(textLat);
		capteur.add(lblLongitude);
		capteur.add(textLon);
		capteur.add(batiBox);
		capteur.add(etageBox);
		capteur.add(lblBatiment);
		group.add(rdbtnExterieur);
		group.add(rdbtnInterieur);
		connexion.add(lblIp);
		connexion.add(textIp);
		connexion.add(lblPort);
		connexion.add(textPort);
		connexion.add(connectB);
		connexion.add(deconnectB);
		connexion.add(pb);
		
		mesureBox.addItem("Température");
		mesureBox.addItem("Humidité");
		mesureBox.addItem("Luminosité");
		mesureBox.addItem("Pression");
		mesureBox.addItem("Vitesse vent");
		Timer t=new Timer(40, new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(pb.getValue()!=pb.getMaximum())
					pb.setValue(pb.getValue()+1);
				else
					pb.setValue(0);
			}
		});
		pb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
				if (pb.getValue()==pb.getMaximum()){
					if(rdbtnExterieur.isSelected()){
						try{
							CapteurExterieur ce= new CapteurExterieur(textID.getText(), null, TypeCapExter.getType((String)mesureBox.getSelectedItem()));
							sc.envoyerValeurCapteur(ce.simule());
						}catch (Exception e1){
							JOptionPane.showMessageDialog(new JFrame(), "Erreur : serveur non connecte");
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
							pb.setValue(0);
							pb.setVisible(false);
							t.stop();
						}
					}else{
						try{
							CapteurInterieur ci= new CapteurInterieur(textID.getText(), null, "", TypeCapInter.getType((String)mesureBox.getSelectedItem()));
							sc.envoyerValeurCapteur(ci.simule());
						}catch (Exception e1){
							JOptionPane.showMessageDialog(new JFrame(), "Erreur : serveur non connecte");
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
							pb.setValue(0);
							pb.setVisible(false);
							t.stop();
						}
					}
				}	
			}
		});
		
		rdbtnExterieur.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mesureBox.addItem("Pression");
				mesureBox.addItem("Vitesse vent");
				mesureBox.removeItem("Consommation éclairage");
				mesureBox.removeItem("Volume sonore");
				mesureBox.removeItem("Eau froide");
				mesureBox.removeItem("Eau chaude");
				textLon.setVisible(true);
				textLat.setVisible(true);
				lblLongitude.setVisible(true);
				lblLatitude.setVisible(true);
				
				batiBox.setVisible(false);
				etageBox.setVisible(false);
				salleBox.setVisible(false);
				textPos.setVisible(false);
				lblBatiment.setVisible(false);
				lblEtage.setVisible(false);
				lblSalle.setVisible(false);
				lblPosition.setVisible(false);
			}
		});
	
		rdbtnInterieur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mesureBox.addItem("Consommation éclairage");
				mesureBox.addItem("Volume sonore");
				mesureBox.addItem("Eau froide");
				mesureBox.addItem("Eau chaude");
				
				mesureBox.removeItem("Pression");
				mesureBox.removeItem("Vitesse vent");
				
				textLon.setVisible(false);
				textLat.setVisible(false);
				lblLongitude.setVisible(false);
				lblLatitude.setVisible(false);
				
				batiBox.setVisible(true);
				etageBox.setVisible(true);
				salleBox.setVisible(true);
				textPos.setVisible(true);
				lblBatiment.setVisible(true);
				lblEtage.setVisible(true);
				lblSalle.setVisible(true);
				lblPosition.setVisible(true);
			}
		});
		
		capteur.add(mesureBox);
		
		GestionFichierPositionExterieur g= new GestionFichierPositionExterieur("./src/capteur/format_bat");
		String[] bats=g.listeBatiment();
		String[] etages = g.listeEtageFromBatiment(bats[0]);
		String[] salles=g.listeSalleFromEtageAndBatiment(bats[0],etages[0]);
		for(String s : bats) batiBox.addItem(s);
		for(String s : etages) etageBox.addItem(s);
		for(String s : salles) salleBox.addItem(s);
		batiBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					Object item = e.getItem();
					String bat= (String)item;
					etageBox.removeAllItems();
					for(String s : g.listeEtageFromBatiment(bat)) etageBox.addItem(s);
					salleBox.removeAllItems();
					for(String s : g.listeSalleFromEtageAndBatiment((String)batiBox.getSelectedItem(),(String)etageBox.getSelectedItem())) salleBox.addItem(s);
				}
			}
		});
		
		etageBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					Object item = e.getItem();
					String etage= (String)item;
					salleBox.removeAllItems();
					for(String s : g.listeSalleFromEtageAndBatiment((String)batiBox.getSelectedItem(),etage)) salleBox.addItem(s);
				}
			}
		});
		
		connectB.addActionListener(new ActionListener() {
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
				if(toutVaBien)if(rdbtnExterieur.isSelected()){
					TypeCapExter t=TypeCapExter.getType((String)mesureBox.getSelectedItem());
					GPSCoord gps=null;
					if(toutVaBien) try{
						toutVaBien=true;
						double lat = Double.parseDouble(textLat.getText());
						double lon = Double.parseDouble(textLon.getText());
						while(lat>90 || lat<=-90) lat=lat<0?lat+90:lat-90;
						while(lon>180 || lon<=-180) lon=lon<0?lon+180:lon-180;
						textLat.setText(String.valueOf(lat));
						textLon.setText(String.valueOf(lon));
						gps= new GPSCoord(lat,lon);
					} catch (Exception e1) {
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir des valeurs decimales pour la longitude et latitude");
					}
					if(toutVaBien) try{
						CapteurExterieur ce= new CapteurExterieur(textID.getText(), gps, t);
						sc=new ServeurCapteur(textIp.getText(),port);
						toutVaBien=sc.envoyerConnexionCapteur(ce);	
						pb.setMaximum(ce.getFrec()*25);
						frequence=ce.getFrec();
					}
					catch(Exception e1){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
					}
				}else{
					TypeCapInter t =TypeCapInter.getType((String)mesureBox.getSelectedItem());
					//System.out.println(t.toString());
					int etage=0;
					if(toutVaBien) try{
						etage=Integer.parseInt((String)etageBox.getSelectedItem());
					}catch(Exception ex){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Etage : Saisir un entier");
					}
					if(toutVaBien)try{
						CapteurInterieur ci=new CapteurInterieur(textID.getText(),new Salle(new Etage(etage,new Batiment((String)batiBox.getSelectedItem(), 0, 0)),(String)salleBox.getSelectedItem()),textPos.getText(),t);
						sc=new ServeurCapteur(textIp.getText(),port);
						toutVaBien=sc.envoyerConnexionCapteur(ci);
						pb.setMaximum(ci.getFrec()*25);
						frequence=ci.getFrec();
					}catch(Exception ex){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
					}
				}
				if(toutVaBien){
					JOptionPane.showMessageDialog(new JFrame(), "Connexion reussie");
					connected=true;
					connectB.setEnabled(false);
					deconnectB.setEnabled(true);
					textIp.setEnabled(false);
					textPort.setEnabled(false);
					pb.setVisible(true);
					t.start();
					tp.remove(capteur);
				}
			}
		});
	
		deconnectB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(rdbtnExterieur.isSelected()){
					try {
						CapteurExterieur ce=new CapteurExterieur(textID.getText(), null, null);
						if(sc.deconnecterCapteur(ce.getID())){							
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
							pb.setValue(0);
							pb.setVisible(false);
							t.stop();
						}else
							JOptionPane.showMessageDialog(new JFrame(), "Deconnexion refusee");
					}catch (Exception e1){
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de deconnexion??");							
					}
				}else{
					try {
						CapteurInterieur ci=new CapteurInterieur(textID.getText(), null, "", null);
						if(sc.deconnecterCapteur(ci.getID())){
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
							pb.setValue(0);
							pb.setVisible(false);
							t.stop();
						}else
							JOptionPane.showMessageDialog(new JFrame(), "Deconnexion refusee");							
					}catch (Exception e1){
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de deconnexion??");
					}
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(connected){
					try {
						sc.deconnecterCapteur(textID.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		setLayout(new GridLayout(1, 1));
		contentPane.add(tp);
	}
}