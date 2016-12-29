package IHM;

import javax.swing.DefaultListModel;

public class EcouteThread extends Thread{
	private ServeurIHM sIHM;
	private DefaultListModel<String> lm;
	public EcouteThread(ServeurIHM sIHM, DefaultListModel<String> lm){
		this.sIHM=sIHM;
		this.lm=lm;
	}
	public void run(){
		while(true){
			try{
				String buff =sIHM.ecouteCapteur();
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
	}
}
