import java.io.* ;
import java.net.* ;
import java.util.* ;

final class HttpRequest implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception {
	this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface.
    public void run() {
	try {
	    processRequest();
	} catch (Exception e) {
	    System.out.println(e);
	}
        }

/**
* ProcessRequest Method class handles HTTP Request Messages
* 1. Receive and send HTTP Request and HTTP Response
* 2. Parse HTTP request line and save
* 3. Parse HTTP Header line and save
* 
* The server runs indefinitely, continuously accepting new connections
* and spawning threads to process HTTP requests.
*/
    private void processRequest() throws Exception {
    	//Mission 2: parse the HTTP request (Fill #5 ~ #7)
    	// Fill#5 Create input stream from socket to receive data from client
    	// Fill#6 Create output stream via socket to send data to client
    	// Fill#7, bufferedReader filter around the input stream to parse HTTP Request
    	//to Get a reference to the socket's input and output streams.
    	// https://docs.oracle.com/javase/8/docs/api/java/io/package-summary.html
    	InputStream is = /*Fill in the Blank*/ //Mission 2: Get input stream from the socket
    	DataOutputStream os = /*Fill in the Blank*/ //Mission 2: Get Output stream from the socket
	
    	// Set up input stream filters.
    	BufferedReader br = /*Fill in the Blank*/ //Mission 2: wrap InputStreamReader and BufferedReader filters around the input stream 

        // Mission 2(2-A, 2-B, 2-C): parse the HTTP request (Fill #8 ~ #9)
        String requestLine = /*Fill in the Blank*/ // Mission 2: Get the request line of the HTTP request message

        // Fill #9: Use StringTokenizer to HTTP request
        // StringTokenizer from Java.util
        // https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html
        /*Fill in the Blank*/        
        String method = /*Fill in the Blank*/ // Mission 2-A: Get method information, Optional Exercises
        String fileName = /*Fill in the Blank*/ // Mission 2-B: Get URI information 
        String version = /*Fill in the Blank*/ // Mission 2-C: Get HTTP Version information
	
        // Prepend a "." so that file request is within the current directory.
        fileName = "." + fileName ;
	
    	// Open the requested file.
        FileInputStream fis = null ;
        boolean fileExists = true ;
        try {
        	fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
        	fileExists = false ;
        }
        // Debug info for private use
        System.out.println("Incoming!!!");
        System.out.println(requestLine);
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
        	System.out.println(headerLine);
        }
        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String contentLengthLine = null;
        String entityBody = null; 

/**
 * Mission 3. Analyze the request and send an appropriate response 
 * Mission 3. If HTTP response message consisting of the requested file, make the code with 200 OK
* If the requested file is not present in the server, the server should send an HTTP “404 Not Found” message back to the client.
* If the request message is not proper, the server should send an HTTP “400 BAD REQUEST” message back to the client.
* and make more response codes for your HTTP web server
* Optional Projects. Not only for the Method “GET”, you also have to consider handling other Methods. 
 */
        if (fileExists) { 
                      //Fill#10. When requested file exists, Status Code 200 OK
	    statusLine = /*Fill in the Blank*/                       // Mission 3-A: Status Code 200 OK
	    contentTypeLine = "Content-Type: " +  contentType(fileName) + CRLF;                        
contentLengthLine = "Content-Length: " + getFileSizeBytes(fileName) + CRLF;

        } else {
        //#Fill#11. When requested file doesn’t exist, Status Code 404 NOT FOUND
        	statusLine = /*Fill in the Blank*/            // Mission 3-B: Status Code 404 Not found
        	contentTypeLine = "Content-Type: text/html" + CRLF; 
        	entityBody = "<HTML>" + 
        			"<HEAD><TITLE>Not Found</TITLE></HEAD>" +
        			"<BODY>Not Found</BODY></HTML>";
        }

        //Mission 3-C(option): Status Code 400 Bad Request 
        	/*Fill in the Blank*/ 

        // Send the status line.
        os.writeBytes(statusLine);

        // Send the content type line.
        os.writeBytes(contentTypeLine); 

// Send the content length line.
        os.writeBytes(contentLengthLine); 


        // Send a blank line to indicate the end of the header lines.
        os.writeBytes(CRLF);

        // Send the entity body.
        if (fileExists) {
	    sendBytes(fis, os);
	    fis.close();
        } else {
	    os.writeBytes(/*Fill in the Blank*/) ; // Mission 3: Send appropriate entity body
        }

        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }

/**
 * Method which sends the context
 * @param fis FileInputStream to transfer
 * @param os outputstream to client
 */
    private static void sendBytes(FileInputStream fis, 
				  OutputStream os) throws Exception {
	// Construct a 1K buffer to hold bytes on their way to the socket.
	byte[] buffer = new byte[1024];
	int bytes = 0;
	
	// Copy requested file into the socket's output stream.
	while ((bytes = fis.read(buffer)) != -1) {
	    os.write(buffer, 0, bytes);
	}
    }

/**
 * Method to return appropriate
 * @param fileName 
 */
private static String contentType(String fileName) {
	if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
	    return "text/html";
	}
/**
 * Mission 4, create an HTTP response message consisting of the requested file preceded by header lines
 * Now, you are just handling text/html, is there any more context-types? Find and make codes for it.
 */
	//#Fill 12 Detect appropriate file extensions and return appropriate response type(audio)
	/*Fill in the Blank*/ 

	//#Fill 13 Detect appropriate file extensions and return appropriate response type(image)
	/*Fill in the Blank*/
}

/**
 * Get the File name, and through the file name, get the size of the file.
 *.@param fileName
 */
private static long getFileSizeBytes(String fileName) throws IOException {
        File file = new File(fileName);
        return file.length();
    }
  // This method returns the size of the specified file in bytes.
}
