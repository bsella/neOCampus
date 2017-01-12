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
		return latitude+", "+longitude;
	}
	public int compareTo(GPSCoord gps){
		if(this.latitude>gps.latitude) return 1;
		if(this.latitude<gps.latitude) return -1;
		if(this.longitude>gps.longitude) return 1;
		if(this.longitude<gps.longitude) return -1;		
		return 0;//cas extremement rare
	}
	public boolean equals(Object o){
		if(o instanceof GPSCoord)
			return compareTo((GPSCoord)o)==0;
		return false;
	}
}
