/**
 * Created by Adrian on 1/29/18.
 */

import java.net.*;
import java.io.*;


public class myServer
{
    private int s_portNo;
    private ServerSocket s_socket;
    private Socket c_clientSocket;
    private Worker worker;


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

    public void start()
    {
        boolean running = true;

        try
        {
            c_clientSocket = s_socket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(c_clientSocket.getInputStream()));
            PrintWriter    writer = new PrintWriter(c_clientSocket.getOutputStream(), true);

            worker = new Worker(c_clientSocket);

            while(running)
            {
                String inputLine;

                while((inputLine = reader.readLine()) != null)
                {
                    String[] arguments = inputLine.split(" ");
                    System.out.println(arguments[0]);

                    if(arguments != null)
                    {
                        this.handleInput(arguments);
                    }

                }

            }


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void handleInput(String[] arguments) throws Exception
    {
        switch (arguments[0])
        {
            case "pwd" : worker.printWorkingDirectory();
                break;
            case "ls"  : worker.listSegments();
                break;
            case "cd"  : worker.changeDirectory(arguments);
                break;
            default: worker.printUnknownCmd();
        }
    }

    public static void main(String[] args) throws IOException
    {
        int portNumber = Integer.parseInt(args[0]);

        myServer s_server = new myServer(portNumber);

        s_server.start();
    }
}

/*
while(running)
        {
            try
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
 */