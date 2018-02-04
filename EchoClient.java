import java.io.*;
import java.net.*;
import java.net.Socket;

public class EchoClient {
	
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
            InputStream is = echoSocket.getInputStream();
    	    FileOutputStream fos;
    	    BufferedOutputStream bos;
            String userInput;
            
            while ((userInput = stdIn.readLine()) != null)
            {
            	
                out.println(userInput);
                String outPutString = null;
                while(!userInput.equals("quit"))
                { 
                	outPutString = in.readLine();
	                if(outPutString.equals("(1337)pushing")){
		                String file_name = in.readLine();
		                byte[] data = new byte[1024];
	            	    fos = new FileOutputStream(file_name);
	            	    bos = new BufferedOutputStream(fos);
	            	    int bytesRead = is.read(data, 0, data.length);
	            	    bos.write(data, 0, bytesRead);
	            	    bos.close();
	            	    System.out.println("file saved");
	                   }
	                else if(outPutString.equals("(1337)recieving"))
	                {
	                	String file_name = in.readLine();
	                	File myFile = new File(file_name);
	                	byte[] mybytearray = new byte[(int) myFile.length()];
	                	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));

	                      bis.read(mybytearray, 0, mybytearray.length);
	                      OutputStream os = echoSocket.getOutputStream();
	                      os.write(mybytearray, 0, mybytearray.length);
	                      os.flush();
	                      System.out.println("file sent");
	                      bis.close();
	                }
	                
	                   else if(!outPutString.equals(""))
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
