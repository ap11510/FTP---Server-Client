/**
 * Created by Adrian on 1/29/18.
 */

import java.net.*;
import java.io.*;


public class myServer
{
    private int s_portNo;
    private ServerSocket s_socket;

    public myServer(int port)
    {
        s_portNo = port;
        try
        {
            s_socket = new ServerSocket(s_portNo);
        }
        catch (Exception e)
        {
            System.out.println("Server cannot be constructed: " + e.getMessage());
        }
    }

    public void start() throws IOException
    {
        boolean running = true;

        while(running)
        {
            try
            (
                Socket c_clientSocket = s_socket.accept();
                PrintWriter    writer = new PrintWriter(c_clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(c_clientSocket.getInputStream()));
            )
            {
                String inputLine;

                while((inputLine = reader.readLine()) != null)
                {
                    System.out.println(inputLine);

                    inputLine.toLowerCase();

                    if(inputLine.equals("do a job"))
                    {
                        //System.out.println("Initializing job class");
                        writer.println("Initializing job class");



                    }
                    else if (inputLine.equals("goodbye"))
                    {
                        writer.println("Server Will now exit , thank you!");
                        System.out.println("Server now exiting");
                        System.exit(0);
                    }

                    writer.println("Server got this line: " + inputLine);
                }


            }
            catch (IOException e)
            {
                System.out.println("Error connecting a client in start(): " + e.getMessage());
                System.out.println("Exception caught when trying to listen on port "
                                    + s_portNo + " or listening for connection");
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        int    portNumber = Integer.parseInt(args[0]);

        myServer s_server = new myServer(portNumber);

        s_server.start();
    }
}
