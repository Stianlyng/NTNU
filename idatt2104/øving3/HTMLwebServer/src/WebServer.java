import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class WebServer {
    public static final int PORT = 1250;
    
    public static void main(String[] args) throws Exception {
     
      ServerSocket serverSocket = new ServerSocket(PORT);
      System.err.println("Server is online on port: " + PORT);
  
      while (true) {
         
          Socket clientSocket = serverSocket.accept();
          System.err.println("Client connected");

  
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
  
        
          String s;
          ArrayList<String> lines = new ArrayList<String>();


          while ((s = in.readLine()) != null) {
              System.out.println(s);
              lines.add(s);
              if (s.isEmpty()) {
                  break;
              }
          }
  
          out.write("HTTP/1.0 200 OK\r\n");
          out.write("Content-Type: text/html\r\n");
          out.write("\r\n");
          out.write("<TITLE>A simple Web Server</TITLE>");
          out.write("<H1> Du har koblet deg opp til min enkle web-tjener </h1>");
          out.write("<UL>");
           
          for (String line : lines) {
              out.write("<LI>" + line + "</LI>");
          } 
          out.write("</UL>");
  
          System.err.println("Client disconnected");
          out.close();
          in.close();
          clientSocket.close();
      }
  }
}