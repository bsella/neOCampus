package IHM;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import capteur.CapteurExterieur;
import capteur.GPSCoord;
import capteur.TypeCapExter;

public class IHM extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JFrame capteur;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try{
					IHM frame = new IHM();
					frame.setVisible(true);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public IHM() {
	
	/* Create the content pane */
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 600, 600);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	/*  */
	capteur = new JFrame("Creer Capteur");
	capteur.setVisible(false);
	capteur.setSize(300,300);
	
	/* Create the title label */
	JLabel lblNewLabel = new JLabel("IHMface Simulation");
	lblNewLabel.setBounds(6, 0, 132, 34);
	contentPane.add(lblNewLabel);
	capteur.getContentPane().setLayout(null);
	
	JLabel lblNewLabel_1 = new JLabel("Identifiant :");
	lblNewLabel_1.setBounds(0, 0, 300, 16);
	capteur.getContentPane().add(lblNewLabel_1);
	
	JLabel lblNewLabel_2 = new JLabel("Type de mesure :");
	lblNewLabel_2.setBounds(0, 28, 122, 28);
	capteur.getContentPane().add(lblNewLabel_2);
	
	JLabel lblLatitude = new JLabel("Latitude");
	lblLatitude.setBounds(0, 108, 61, 16);
	capteur.getContentPane().add(lblLatitude);
	
	textField = new JTextField();
	textField.setBounds(105, 128, 130, 26);
	capteur.getContentPane().add(textField);
	textField.setColumns(10);
	
	JLabel lblLongitude = new JLabel("Longitude");
	lblLongitude.setBounds(0, 133, 73, 16);
	capteur.getContentPane().add(lblLongitude);
	
	textField_1 = new JTextField();
	textField_1.setEnabled(true);
	textField_1.setBounds(105, 103, 130, 26);
	capteur.getContentPane().add(textField_1);
	textField_1.setColumns(10);
	
	JComboBox<String> batiBox = new JComboBox<>();
	batiBox.setBounds(105, 104, 130, 27);
	capteur.getContentPane().add(batiBox);
	batiBox.setVisible(false);
	
	JComboBox<String> etageBox = new JComboBox<>();
	etageBox.setBounds(105, 129, 130, 27);
	capteur.getContentPane().add(etageBox);
	etageBox.setVisible(false);
	
	JLabel lblBatiment = new JLabel("Batiment");
	lblBatiment.setBounds(0, 108, 61, 16);
	capteur.getContentPane().add(lblBatiment);
	lblBatiment.setVisible(false);
	
	JLabel lblEtage = new JLabel("Etage");
	lblEtage.setBounds(0, 136, 61, 16);
	capteur.getContentPane().add(lblEtage);
	lblEtage.setVisible(false);
	
	JLabel lblSalle = new JLabel("Salle");
	lblSalle.setBounds(0, 161, 61, 16);
	capteur.getContentPane().add(lblSalle);
	lblSalle.setVisible(false);
	
	JLabel lblPosition = new JLabel("Position");
	lblPosition.setBounds(0, 189, 61, 16);
	capteur.getContentPane().add(lblPosition);
	lblPosition.setVisible(false);
	
	JComboBox<String> salleBox = new JComboBox<>();
	salleBox.setBounds(105, 157, 130, 27);
	capteur.getContentPane().add(salleBox);
	salleBox.setVisible(false);
	
	JComboBox<String> posBox = new JComboBox<>();
	posBox.setBounds(105, 185, 130, 27);
	capteur.getContentPane().add(posBox);
	posBox.setVisible(false);
	
	JRadioButton rdbtnExterieur = new JRadioButton("Exterieur");
	rdbtnExterieur.setSelected(true);
	rdbtnExterieur.setEnabled(true);
	rdbtnExterieur.setBounds(95, 68, 88, 23);
	capteur.getContentPane().add(rdbtnExterieur);
	
	JRadioButton rdbtnInterieur = new JRadioButton("Interieur");
	rdbtnInterieur.setEnabled(true);
	rdbtnInterieur.setBounds(0, 67, 88, 26);
	capteur.getContentPane().add(rdbtnInterieur);
	
	ButtonGroup group = new ButtonGroup();
	group.add(rdbtnExterieur);
	group.add(rdbtnInterieur);
	
	JComboBox<String> mesureBox = new JComboBox<>();
	mesureBox.setBounds(108, 30, 130, 27);
	mesureBox.addItem("Temperature");
	mesureBox.addItem("Humidité");
	mesureBox.addItem("Luminosité");
	mesureBox.addItem("Pression");
	mesureBox.addItem("Vitesse vent");
	
	rdbtnExterieur.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		mesureBox.addItem("Pression");
		mesureBox.addItem("Vitesse vent");
		
		mesureBox.removeItem("Consommation éclairage");
		mesureBox.removeItem("Volume Sonore");
		mesureBox.removeItem("Eau froide");
		mesureBox.removeItem("Eau Chaude");
		textField_1.setVisible(true);
		textField.setVisible(true);
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
			
			textField_1.setVisible(false);
			textField.setVisible(false);
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
	
	capteur.getContentPane().add(mesureBox);
	
	Button valideButton = new Button("Valide");
	valideButton.setBounds(5, 227, 117, 29);
	valideButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(rdbtnExterieur.isSelected()){
				TypeCapExter t;
				switch((String)mesureBox.getSelectedItem()){
					case "Pression":
						t=TypeCapExter.PRESSION_ATMOSPHERIQUE;
						break;
					case "Vitesse Vent":
						t=TypeCapExter.VITESSE_VENT;
						break;
					case "Temperature":
						t=TypeCapExter.TEMPERATURE;
						break;
					case "Humidite":
						t=TypeCapExter.HUMIDITE;
						break;
					case "Luminosite":
						t=TypeCapExter.LUMINOSITE;
						break;
					default: t=null;
				}
				double lat = 12.45;
				double lon = 34.4;
				GPSCoord gps= new GPSCoord(lat,lon);
				try{
					CapteurExterieur ce= new CapteurExterieur(123, gps, t);
					ce.envoyerConnectionCapteur();
				} catch (IOException e1){}
			}
			capteur.dispose();
		}
	});
	
	capteur.getContentPane().add(valideButton);
	
	TextArea textArea = new TextArea();
	textArea.setBackground(Color.WHITE);
	textArea.setBounds(304, 15, 286, 553);
	contentPane.add(textArea);
	
	Button createButton = new Button("Create Capteur");
	createButton.setForeground(Color.DARK_GRAY);
	createButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			capteur.setVisible(true);
		}
	});
	createButton.setBounds(6, 40, 117, 29);
	contentPane.add(createButton);
	}
}
