package capteur;

public class GPSCoord {
	private double longitude;
	private double latitude;
	public GPSCoord(double lo, double la){
		this.longitude=lo;
		this.latitude=la;
	}
	public double getLongitude(){
		return longitude;
	}
	public double getLatitude(){
		return latitude;
	}
	public String toString(){
		return "("+latitude+", "+longitude+")";
	}
}
