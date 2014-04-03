package de.hof_university.TMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


//Probeweiße der Server in einem Thread, ist aber eig. nicht notwendig.
public class TmsSocket_Server extends Thread {
	private int port;
	private String url;
	private ServerSocket serversocket;
	private Socket socket;

	public TmsSocket_Server(int port) throws UnknownHostException, IOException {
		serversocket = new ServerSocket(port);

	}

	public void run() {
		System.out.println("Server wartet auf Verbindung mit Client");
		while (true) {
			try {
				socket = serversocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			SocketThread st = new SocketThread(socket);
			st.start();
		}
	}
}

class SocketThread extends Thread {
	private Socket socket;
	private String text;

	public SocketThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("Server ist mit " + socket.getInetAddress()
				+ " ueber Port " + socket.getPort() + " verbunden.");
		InputOutput io = new InputOutput();

		// übertragung empfangen
		try {
			while (true) {
				if (socket.getInputStream().available() != 0) {
					text = io.reciveMessage(socket.getInputStream());
					
					
					
				}

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// //veränderten text versenden
	// try {
	// io.sendMessage(socket.getOutputStream(), text);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }

}
