package capteur;

import java.util.Random;

import capteur.emplacement.CapteurInterieur;

public abstract class Capteur {
	private String ID;
	String uniteDeMesure;
	private double intervalleMin;
	private double intervalleMax;
	private double precision;
	double margeDeConfiance;
	private int frequence;
	String date;
	
	public Capteur(String id){
		ID=id;
	}
	public void setUDM(String s){
		uniteDeMesure=s;
	}
	public void setIMin(double d){
		intervalleMin=d;
	}
	public void setIMax(double d){
		intervalleMax=d;
	}
	public void setPrec(double d){
		precision=d;
	}
	public void setMarge(double d){
		margeDeConfiance=d;
	}
	public void setFrec(int i){
		frequence=i;
	}
	public void setDate(String s){
		date=s;
	}
	public String getID(){
		return ID;
	}
	public int getFrec(){
		return frequence;
	}
	public double simule(){
		Random r= new Random();
		double d= intervalleMin+r.nextDouble()*(intervalleMax-intervalleMin);
		return (double)((int)(d/precision)*precision);
	}
	public boolean equals(Object o){
		if(this instanceof CapteurExterieur){
			if(o instanceof CapteurExterieur){
				CapteurExterieur c = (CapteurExterieur)o;
				return ID.equals(c.getID())
						&& ((CapteurExterieur)this).getGPS().equals(c.getGPS())
						&& ((CapteurExterieur)this).getType().equals(c.getType());
			}
			return false;
		}
		if(this instanceof CapteurInterieur){
			if(o instanceof CapteurInterieur){
				CapteurInterieur c= (CapteurInterieur)o;
				CapteurInterieur thisc= (CapteurInterieur)this;
				return ID.equals(c.getID()) 
						&& thisc.getBatiment().equals(c.getBatiment())
						&& thisc.getEtage().equals(c.getEtage())
						&& thisc.getSalle().equals(c.getSalle())
						&& thisc.getType().equals(c.getType());
			}
			return false;
		}
		return false;
	}
}
