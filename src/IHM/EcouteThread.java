package IHM;

import java.io.PipedOutputStream;

public class EcouteThread extends Thread{
	private ServeurIHM sIHM;
	private boolean running;
	PipedOutputStream out;
	public EcouteThread(ServeurIHM sIHM, PipedOutputStream out){
		this.sIHM=sIHM;
		this.out=out;
		running = true;
	}
	public void terminate(){
		running=false;
	}
	public void send(String message)throws Exception{
		for(char c : message.toCharArray()){
			out.write(c);
		}
		out.write('\0');
	}
	public void run(){
		while(running){
			try{
				String buff =sIHM.read();
				String parts[]= buff.split(";");
				switch(parts[0]){
					case "CapteurPresent":
						sIHM.addToList(parts);						
					break;
					default:
						send(buff);
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
