package capteur;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	boolean connected=false;
	int frequence;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	
	private TypeCapExter getTypeExter(String t){
		switch(t){
			case "Pression":
				return TypeCapExter.PRESSION_ATMOSPHERIQUE;
			case "Vitesse Vent":
				return TypeCapExter.VITESSE_VENT;
			case "Température":
				return TypeCapExter.TEMPERATURE;
			case "Humidité":
				return TypeCapExter.HUMIDITE;
			case "Luminosité":
				return TypeCapExter.LUMINOSITE;
			default: return null;
		}
	}
	
	private TypeCapInter getTypeInter(String t){
		switch(t){
			case "Température":
				return TypeCapInter.TEMPERATURE;
			case "Humidité":
				return TypeCapInter.HUMIDITE;
			case "Luminosité":
				return TypeCapInter.LUMINOSITE;
			case "Volume sonore":
				return TypeCapInter.VOLUME_SONORE;
			case "Eau froide":
				return TypeCapInter.EAU_FROIDE;
			case "Eau chaude":
				return TypeCapInter.EAU_CHAUDE;
			default: return null;
		}
	}
	/**
	 * Create the frame.
	 */
	public Fenetre(){
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
		JComboBox<String> posBox = new JComboBox<>();
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
		textLat.setBounds(115, 138, 130, 26);
		lblLongitude.setBounds(10, 143, 73, 16);
		textLon.setBounds(115, 113, 130, 26);
		batiBox.setBounds(80, 112, 130, 27);
		etageBox.setBounds(80, 142, 130, 27);
		lblBatiment.setBounds(10, 115, 70, 16);
		lblEtage.setBounds(10, 145, 61, 16);
		lblSalle.setBounds(10, 175, 61, 16);
		lblPosition.setBounds(10, 205, 61, 16);
		salleBox.setBounds(80, 172, 130, 27);
		posBox.setBounds(80, 202, 130, 27);
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
		connexion.setLayout(null);//new GridLayout(2, 1));
		textLat.setColumns(10);
		textLon.setColumns(10);
		batiBox.setVisible(false);
		etageBox.setVisible(false);
		lblBatiment.setVisible(false);
		lblEtage.setVisible(false);
		lblSalle.setVisible(false);
		lblPosition.setVisible(false);
		salleBox.setVisible(false);
		posBox.setVisible(false);
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
		capteur.add(posBox);
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
		textIp.setText("127.0.0.1");
		textPort.setText("7888");
		textID.setText("ssbsv");
		textLon.setText("345");
		textLat.setText("345");
		
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
				if (pb.getValue()==0){
					if(rdbtnExterieur.isSelected()){
						try{
							CapteurExterieur ce= new CapteurExterieur(textID.getText(), null, getTypeExter((String)mesureBox.getSelectedItem()), textIp.getText(), Integer.parseInt(textPort.getText()));
							ce.envoyerValeurCapteur(ce.simule());
						}catch (Exception e1) {
							e1.printStackTrace();
						}
					}else{
						try{
							CapteurInterieur ci= new CapteurInterieur(textID.getText(), null, getTypeInter((String)mesureBox.getSelectedItem()), textIp.getText(), Integer.parseInt(textPort.getText()));
							ci.envoyerValeurCapteur(ci.simule());
						}catch (Exception e1){
							e1.printStackTrace();
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
				mesureBox.removeItem("Volume Sonore");
				mesureBox.removeItem("Eau froide");
				mesureBox.removeItem("Eau Chaude");
				textLon.setVisible(true);
				textLat.setVisible(true);
				lblLongitude.setVisible(true);
				lblLatitude.setVisible(true);
				
				batiBox.setVisible(false);
				etageBox.setVisible(false);
				salleBox.setVisible(false);
				posBox.setVisible(false);
				lblBatiment.setVisible(false);
				lblEtage.setVisible(false);
				lblSalle.setVisible(false);
				lblPosition.setVisible(false);
			}
		});
	
		rdbtnInterieur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mesureBox.addItem("Consommation éclairage");
				mesureBox.addItem("Volume Sonore");
				mesureBox.addItem("Eau froide");
				mesureBox.addItem("Eau Chaude");
				
				mesureBox.removeItem("Pression");
				mesureBox.removeItem("Vitesse vent");
				
				textLon.setVisible(false);
				textLat.setVisible(false);
				lblLongitude.setVisible(false);
				lblLatitude.setVisible(false);
				
				batiBox.setVisible(true);
				etageBox.setVisible(true);
				salleBox.setVisible(true);
				posBox.setVisible(true);
				lblBatiment.setVisible(true);
				lblEtage.setVisible(true);
				lblSalle.setVisible(true);
				lblPosition.setVisible(true);
			}
		});
		
		capteur.add(mesureBox);
		
		connectB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				boolean toutVaBien=true;
				int port=0;
				if(rdbtnExterieur.isSelected()){
					TypeCapExter t=getTypeExter((String)mesureBox.getSelectedItem());
					GPSCoord gps=null;
					if(textID.getText()==null || textID.getText().isEmpty()){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir un identifiant pour le capteur");
					}
					if(toutVaBien)try{
						port=Integer.parseInt(textPort.getText());
						if(port<1024 || port >65356){
							toutVaBien=false;
							JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
						}
					}catch(Exception ex){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
					}
					if(toutVaBien) try{
						toutVaBien=true;
						double lat = Double.parseDouble(textLat.getText());
						double lon = Double.parseDouble(textLon.getText());
						gps= new GPSCoord(lat,lon);
					} catch (Exception e1) {
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir des valeurs decimales pour la longitude et latitude");
					}
					if(toutVaBien) try{
						CapteurExterieur ce= new CapteurExterieur(textID.getText(), gps, t,textIp.getText(),port);
						ce.envoyerConnectionCapteur();	
						pb.setMaximum(ce.getFrequence()*25);
						frequence=ce.getFrequence();
					}
					catch(Exception e1){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
					}
				}else{
					TypeCapInter t =getTypeInter((String)mesureBox.getSelectedItem());
					int etage=0;
					if(textID.getText()==null || textID.getText().isEmpty()){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir un identifiant pour le capteur");
					}
					if(toutVaBien)try{
						port=Integer.parseInt(textPort.getText());
						if(port<1024 || port >65356){
							toutVaBien=false;
							JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
						}
					}catch(Exception ex){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Saisir un numero de port entier entre 1024 et 65356");
					}
					if(toutVaBien) try{
						etage=Integer.parseInt((String)etageBox.getSelectedItem());
					}catch(Exception ex){
						toutVaBien=false;
						JOptionPane.showMessageDialog(new JFrame(), "Etage : Saisir un entier");
					}
					if(toutVaBien){
						Batiment bat=new Batiment((String)batiBox.getSelectedItem(), 0, 0);
						try{
							CapteurInterieur ci=new CapteurInterieur(textID.getText(),new Emplacement(bat, etage, (String)salleBox.getSelectedItem(), (String)posBox.getSelectedItem()),t,textIp.getText(),port);
							ci.envoyerConnexionCapteur();
							pb.setMaximum(ci.getFrequence()*25);
							frequence=ci.getFrequence();
						}catch(Exception ex){
							toutVaBien=false;
							JOptionPane.showMessageDialog(new JFrame(), "Erreur de connexion au serveur");
						}
					}
				}
				if(toutVaBien){
					JOptionPane.showMessageDialog(new JFrame(), "Connection reussie");
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
						CapteurExterieur ce=new CapteurExterieur(textID.getText(), null, null, textIp.getText(), Integer.parseInt(textPort.getText()));
						if(ce.deconnecterCapteur()){							
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
							pb.setVisible(false);
							t.stop();
						}else
							JOptionPane.showMessageDialog(new JFrame(), "Deconnexion refusee");
					}catch (Exception e1){
						JOptionPane.showMessageDialog(new JFrame(), "Erreur de deconnexion??");							
					}
				}else{
					try {
						CapteurInterieur ci=new CapteurInterieur(textID.getText(), null, null, textIp.getText(), Integer.parseInt(textPort.getText()));
						if(ci.deconnecterCapteur()){							
							connectB.setEnabled(true);
							deconnectB.setEnabled(false);
							textIp.setEnabled(true);
							textPort.setEnabled(true);
							tp.addTab("Capteur", capteur);
							connected=false;
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
		setLayout(new GridLayout(1, 1));
		contentPane.add(tp);
	}
}
