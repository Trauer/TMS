package de.hof_university.TMS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.text.html.FormSubmitEvent.MethodType;

public class InputOutput {
	public String reciveMessage(InputStream source) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(source));
		char[] buffer = new char[1000];
		int anzahlZeichen = bufferedReader.read(buffer);
		String message = new String(buffer, 0, anzahlZeichen);

		System.out.println("Nachricht  \"" + message + "\" empfangen.");

		return message;
	}

	public void sendMessage(OutputStream destination, String message)
			throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				destination));
		printWriter.print(message);
		printWriter.flush();

		
	}
	public void sendMessage(OutputStream destination, Command command)
			throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				destination));
		printWriter.print(command.toString());
		printWriter.flush();

		
	}
}
