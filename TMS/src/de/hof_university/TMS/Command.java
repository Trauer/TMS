package de.hof_university.TMS;

public class Command {
	private String source;
	private String destination;
	private char msgType;
	private String msgNumber;
	private String msgID;
	private String payload;
	private boolean ack1=false;
	private boolean ack2=false;
	
	private long time;
	static private long lasttime;
	static private int index;
	
	public Command(){};
	
	public Command(String command){
		parse(command);
	}
	public Command(String source,String destination,
	char msgType,int msgNumber,String payload){
		this.source=source;
		this.destination=destination;
		this.msgType=msgType;
		this.msgNumber=(msgNumber<10)?"00"+msgNumber:(msgNumber<100)?"0"+msgNumber:""+msgNumber;
		this.msgID=getMessageID();
		this.payload=payload;
	}
	public String getSource(){
		return source;
	}
	public String getDestination(){
		return destination;
	}
	public char getmsgType(){
		return getmsgType();
	}
	public String getmsgNumber(){
		return msgNumber;
	}
	
	public String getPayload (){
		return payload;
	}
	public void setack1(){
		ack1=true;
	}
	public boolean getack1(){
		return ack1;
	}
	public void setack2(){
		ack2=true;
	}
	public boolean getack2(){
		return ack2;
	}
	
	
	
	
	public void parse(String command){
		source=command.substring(0, 2);
		destination=command.substring(2, 4);
		msgType=command.charAt(4);
		msgNumber=command.substring(5, 8);
		msgID=command.substring(8, 21);
		payload=command.substring(25);
		
	}
	
	public boolean compare(String source,char msgType,String msgNumber){
		if (this.source.compareTo(source)==0){
			if (this.msgType==msgType){
				if (this.msgNumber.compareTo(msgNumber)==0){return true;}}}
		return false;
	}
	public boolean compare(Command command){
		if(this.source.compareTo(command.source)==0){
			if (this.msgType==command.msgType){
				if (this.msgNumber.compareTo(command.msgNumber)==0){return true;}}}
		return false;
	}
	
	
	private String getMessageID(){
		time=System.currentTimeMillis() / 1000L;
		if (time>lasttime){
			lasttime=time;
			index=0;
		}
		else if (time==lasttime){
			index++;
		}
		return ""+time+":"+((index<10)?"0"+index:index);
	}
	public String toString(){
		int pll=payload.length();
		String plLength= (pll<10)?"000"+pll:(pll<100)?"00"+pll:(pll<1000)?"0"+pll:""+pll;
		return source+destination+msgType+msgNumber+msgID+plLength+payload;
	}
}
