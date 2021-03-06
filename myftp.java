import java.io.*;
import java.net.*;
import java.net.Socket;

public class myftp {
	
    public static void main(String[] args) {
    	
        if (args.length != 2)
        {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];

        int portNumber = Integer.parseInt(args[1]);

        try
        {
            Socket echoSocket    = new Socket(hostName, portNumber);
            PrintWriter out      = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in    = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            
            while ((userInput = stdIn.readLine()) != null)
            {
                out.println(userInput);
                String outPutString = null;
                while(!userInput.equals("quit"))
                { 
                		outPutString = in.readLine();
                		if(!outPutString.equals(""))
	                    {
	                        System.out.println(outPutString);
	                    }
	                    else
	                    {
	                        break;
	                    }
                	}
                if (userInput.equals("quit")) {               	
                	echoSocket.close();
                	System.exit(0);
                }
                
                }
                
               

            
            
        } catch (UnknownHostException e)
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }

        System.out.println("Got here");
    }

}
