import java.io.* ;
import java.net.* ;
import java.util.* ;

/**
* WebServer class implements a simple multi-threaded web server.
* 
* This class serves as the main entry point for the web server application.
* Its primary responsibilities include:
* 1. Initializing the server on a specified port
* 2. Listening for incoming client connections
* 3. Creating new threads to handle each client request
* 
* The server runs indefinitely, continuously accepting new connections
* and spawning threads to process HTTP requests.
*/

public final class WebServer {
    public static void main(String argv[]) throws Exception {
//// Check if command line argument for port number is provided
//		if(argv.length < 1){
//		System.out.println("Usage: java WebServer <port number : >");
//			return ;
//		}
//
	// Get the port number from the command line.
    	int port = (new Integer(argv[0])).intValue();
	
    	// Mission 1(Handle Connection): create and bind a socket (Fill #1 ~ #2)  
    	// Fill #1 Create the Serversocket and wait for the TCP Connection
    	// Establish the Serversocket wait for the TCP Connection
    	ServerSocket socket = /*Fill in the Blank*/
    	
			// Process HTTP service requests in an infinite loop.
			while (true) {
				// Fill #2 Listen for a TCP connection request.
				/*Fill in the Blank*/
				//Mission 1-B Fill #3 Construct an object to process the HTTP request message. 
				HttpRequest request = new HttpRequest(/*Fill in the Blank*/);
				// Mission 1-BFill #4, Create a new thread to process the request and start the thread
			/*Fill in the Blank*/ 	
				}
          }
}
