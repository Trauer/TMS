package de.hof_university.TMS;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//Probeweiﬂe der Client in einem Thread, aber auch nicht notwendig ^^
public class TmsSocket_Client{

	Socket socket;
	
	
	public void run() {
		
		try {
			socket = new Socket("127.0.0.1", 40003);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
		
	}
	


		

	

}
