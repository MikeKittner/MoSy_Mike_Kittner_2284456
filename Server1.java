
import java.awt.Label;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

public class Server1{
	
	

	public static void main(String[] args){
		//Nicht verwendet , eventuell für ein weiteres Projekt mit GUI
		 JFrame frame=new JFrame();
		    frame.setTitle("Word Cloud");
		    frame.setSize(1000, 620);
		    frame.setResizable(false);
		    frame.setLocation(50, 50);
		    frame.setVisible(true);
		    
		    final Label spielerLabel = new Label();
		
		    int anzahlSpieler=0;
			int abgegebeneAntworten=0;
		
		// TODO Auto-generated method
		try {
			//Server horcht auf den Port 3445
			ServerSocket serversocket = new ServerSocket(3445, 50);
			while(true) {
			//wartet auf Verbindung vom Client
			Socket socket = serversocket.accept();
			
		
			System.out.println("Der Server ist mit der folgenden ip verbunden: " + socket.getInetAddress());
			//Scanner brauchen wir zum lesen der Nachrichten
			Scanner scanner = new Scanner(socket.getInputStream());
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			
			boolean masterHandyBoolean=false;
			
			
			while(scanner.hasNextLine()) {
			String text = scanner.nextLine();
			
			String spieler1 = "Anzahl Spieler: 1";
			String spieler2 = "Anzahl Spieler: 2";
			String spieler3 = "Anzahl Spieler: 3";
			String masterHandy ="Master Handy: true";
			
			
			//Abragen, ob es ein Master Handy ist
			if(text.equals("Master Handy: true")) {
				masterHandyBoolean=true;
				System.out.println("Master Spieler: "+masterHandyBoolean);
			}
			
			//Nicht verwendet, Vorbeireitung für Multiplayer Projekt 
			if(text.equals("Master Handy: false")) {
				abgegebeneAntworten++;
				
				System.out.println(abgegebeneAntworten);
				if((abgegebeneAntworten % 2)==0) {
					System.out.println("Nächste Frage");
				}
			}
				
				
				if((text.equals(spieler1)) && masterHandyBoolean){
					anzahlSpieler=1;
					System.out.println("Anzahl der Spieler: "+anzahlSpieler);
				}
				if((text.equals(spieler2) && masterHandyBoolean) ){
					anzahlSpieler=2;
				
					spielerLabel.setText("Anzahl der Spieler: "+anzahlSpieler);
					frame.add(spielerLabel);
				
					
					System.out.println("Anzahl der Spieler: "+anzahlSpieler);
					
				}
				if((text.equals(spieler3)) && masterHandyBoolean){
					anzahlSpieler=3;
					System.out.println("Anzahl der Spieler: "+anzahlSpieler);
				}
				
				
			
			
			//System.out.println("Die App hat folgende Information uebermittelt: " + text );
			System.out.println(text);
			
			
			
			
			printWriter.println("Vielen dank fuer die Teilnahme am Test.");
			printWriter.flush();
			
			}
			}
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
