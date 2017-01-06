package IHM;

import java.io.PipedOutputStream;
import javax.swing.DefaultListModel;

public class EcouteThread extends Thread{
	private ServeurIHM sIHM;
	private DefaultListModel<String> lm;
	private boolean running;
	PipedOutputStream out;
	public EcouteThread(ServeurIHM sIHM, DefaultListModel<String> lm, PipedOutputStream out){
		this.sIHM=sIHM;
		this.lm=lm;
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
				System.out.println(buff);
				if(buff!=null)send(buff);
				String parts[]= buff.split(";");
				if(parts[0].equals("CapteurPresent")){
					buff=parts[1];
					for(int i=2;i<parts.length; i++)
						buff=buff+";"+parts[i];
					lm.addElement(buff);
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
