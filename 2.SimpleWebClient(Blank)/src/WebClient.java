import java.io.*;
import java.net.*;

/**
* WebClient class implements a simple web client.

* Its primary responsibilities include:
* 1. Initializing the tcp connection to web server
* 2. send HTTP request and receive HTTP response
*/
public class WebClient {
    public static void main(String[] args) {
        // Set the host, port and resource to send HTTP Request
String host = "example.com";
        int port = 80;
        String resource = "/index.html";
          // Mission 1: Establish a socket connection to Server
        try (
        		//Fill #1, Set TCP socket to HTTP Web Server
        		Socket socket = /*Fill in the Blank*/
        		//Fill #2, create PrintWirter instance with socket’s OutputStream
        		PrintWriter out = /*Fill in the Blank*/
        		//Fill #3, Get input stream from server, and insert it to BufferedReader instance
        		BufferedReader in /*Fill in the Blank*/  {

/**
* Improve your HTTP Client to provide other request Methods(POST, DELETE, …)
* and also improve to handle headers(Content-Type, User-Agent, …)
*/
            // Mission 2: Send HTTP GET Request and Read and display the response
        	// Fill#4, Send HTTP GET request
            out.println("/*Fill in the Blank*/");
            out.println("/*Fill in the Blank*/");
            out.println("/*Fill in the Blank*/");
            out.println();

            // Mission 3: Read and display the response
            //Fill#5,  Read and display the response
            String responseLine;
            while (/*Fill in the Blank*/) {
                /*Fill in the Blank*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
