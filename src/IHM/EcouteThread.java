package IHM;

public class EcouteThread extends Thread{
	private ServeurIHM sIHM;
	private boolean running;
	public EcouteThread(ServeurIHM sIHM){
		this.sIHM=sIHM;
		running = true;
	}
	public void terminate(){
		running=false;
	}

	public void run(){
		while(running){
			try{
				String buff =sIHM.read();
				String parts[]= buff.split(";");
				if(buff!=null)
				switch(parts[0]){
					case "CapteurPresent":
						sIHM.addToList(parts);						
						break;
					case "ValeurCapteur":
						sIHM.changeVal(parts[1], Double.parseDouble(parts[2]));
						break;
					case "CapteurDeco":
						//sIHM.
						break;
					default:
						sIHM.updateBuffer(buff);
				}
			}catch(Exception e){
				break;
			}
		}
		try{
			this.join();
		}catch(Exception e){}
	}
}
