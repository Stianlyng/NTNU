import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTjener implements Runnable {

  private Socket socket; // denne variabelen brukes i run-metoden

  public SocketTjener(Socket socket) {
    this.socket = socket; // konstruktøren tar imot socket som parameter
  }

  @Override
  public void run() {
    try {
      // Åpner strømmer for kommunikasjon med klientprogrammet
      BufferedReader leseren = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter skriveren = new PrintWriter(socket.getOutputStream(), true);
      
      // Sender innledning til klienten
      skriveren.println("Hei, du har kontakt med tjenersiden!");
      skriveren.println("Skriv hva du vil, så skal jeg gjenta det, avslutt med linjeskift.");
      
      // Mottar data fra klienten
      String enLinje = leseren.readLine(); // mottar en linje med tekst
      while (enLinje != null) { // forbindelsen på klientsiden er lukket
        System.out.println("En klient skrev: " + enLinje);
        skriveren.println("Du skrev: " + enLinje); // sender svar til klienten
        enLinje = leseren.readLine();
      }
      
      // Lukker forbindelsen
      leseren.close();
      skriveren.close();
      socket.close(); // husk å lukke socketen
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    final int PORTNR = 1250;
    ServerSocket tjener = new ServerSocket(PORTNR);
    System.out.println("Logg for tjenersiden. Nå venter vi...");
    
    while (true) {
      Socket socket = tjener.accept(); // venter inntil noen tar kontakt
      System.out.println("En klient har koblet til.");
      // Opprett en ny tråd for hver ny forbindelse
      Thread t = new Thread(new SocketTjener(socket));
      t.start();
    }
    
    
  }
}
