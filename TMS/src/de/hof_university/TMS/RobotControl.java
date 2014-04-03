package de.hof_university.TMS;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class RobotControl {
	private final String CONTROL = "ST";
	private final String ROBOT_CONTROL = "Sr";
	private final String ROBOT = "ro";

	private final char CMD = 'K';
	private final char ACK = 'A';
	private final char ERROR = 'F';

	private boolean pointA = false;
	private boolean pointB = false;
	private boolean pointX = false;
	private boolean pointT = false;

	private ArrayList<ArrayList<Character>> smartiesPalette = new ArrayList<ArrayList<Character>>(
			13);
	private ArrayList<ArrayList<Character>> storagePalette = new ArrayList<ArrayList<Character>>(
			13);

	private Stack<Command> commands = new Stack<Command>();
	private String host = "127.0.0.1";
	private int clientPort = 40003;
	private int serverPort = 30000;
	private Socket control;
	private ServerSocket server;
	private Socket robot;
	private InputOutput inout = new InputOutput();
	private boolean firststart = true;

	public RobotControl() throws IOException {
		init();
		
			connectClient();
			startServer();
			readcommand();
			handlecommand();

		
	}

	private void init() {
		for (int i = 0; i < 13; i++) {
			storagePalette.add(new ArrayList<Character>());
		}
	}

	private void connectClient() {

		try {

			while (firststart || !control.isConnected()) {
				control = new Socket(host, clientPort);
				firststart = false;
			}
		} catch (UnknownHostException e) {

		} catch (ConnectException e) {
			System.out.println("klappt?");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void startServer() {
		try {
			server = new ServerSocket(serverPort);
			robot = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void acknoledge(Command command){
		if(!command.getack1()){
			if (command.getSource()==CONTROL){
				inout.sendMessage(control.getOutputStream(), command);new Command(command.getDestination(),command.getSource(),'A',001,"");
				command.setack1();
			}
			else if (command.getSource()==ROBOT){
				if(command.getmsgType()=='A'){
					if(command.getmsgNumber().compareTo("001")==0){
						command.setack1();
					}
				}
				else if (command.getmsgType()=='F'){
					
				}
			}
			else{
				
			}
		}
		else if(!command.getack2()){
			if (command.compare(CONTROL, CMD, 002)){
				return new Command(command.getDestination(),command.getSource(),'A',002,"");
			}
			
			
		}
		
	}

	public void readcommand() {
		try {

			commands.add(new Command(inout.reciveMessage(control
					.getInputStream())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void moveStpFromTtoAB() throws IOException{
		String payload = commands.get(0).getPayload();
		int length = payload.length();
		for (int i = 0; i < length; i++) {
			// if(i%7==0)storagePalette.add(new ArrayList<Character>());
			storagePalette.get(i / 7).add(i % 7, payload.charAt(i));
			// System.out.print(payload.charAt(i)+" "+((i%7==6)?"\n":""));
		}
		inout.sendMessage(control.getOutputStream(), acknoledge(commands.peek()));
		if (!pointA) {
			commands.add(new Command(
					ROBOT_CONTROL, ROBOT, CMD, 000, "A"));
			inout.sendMessage(robot.getOutputStream(),commands.peek() );
		} else if (!pointB) {
			commands.add(new Command(
					ROBOT_CONTROL, ROBOT, CMD, 000, "B"));
			inout.sendMessage(robot.getOutputStream(), commands.peek());
		} else {
			inout.sendMessage(control.getOutputStream(), new Command(
					ROBOT_CONTROL, ROBOT, ERROR, 004, ""));
		}
		do{
			boolean ack1ed = new Command(inout.reciveMessage(robot.getInputStream())).compare(ROBOT, ACK, "001");
		}while()
		
	}
	public void handlecommand() throws IOException {
		if (robot.isConnected() && control.isConnected()) {
			if (commands.get(commands.size()-1).compare(CONTROL, CMD, "000")) {
				
			}
				
				
			}
			if (commands.get(commands.size()-1).compare(CONTROL, CMD, "001")) {
				moveStpFromTtoAB();
				
			

		}

	}
}
