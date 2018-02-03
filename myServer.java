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

                    String[] parsedArgs = new String[2];
                    System.out.println(arguments[0]);

                    if(arguments.length >= 3)
                    {
                        String command  = inputLine.substring(0, inputLine.indexOf(" "));
                        String argument = inputLine.substring(inputLine.indexOf(" ") + 1, inputLine.length());
                        parsedArgs[0] = command;
                        parsedArgs[1] = argument;

                        if(parsedArgs != null)
                        {
                            this.handleInput(parsedArgs);
                        }

                    }
                    else if(arguments != null)
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
            case "quit": worker.quit();
                break;
            case "delete": worker.deleteFile(arguments);
                break;
            case "mkdir": worker.makeDirectory(arguments);
                break;
            case "put": worker.put(arguments);
                    break;
            case "get": worker.get(arguments);
            default: worker.printUnknownCmd();
        }
    }

    public static void main(String[] args) throws IOException
    {
        int portNumber = Integer.parseInt(args[0]);

        myServer s_server = new myServer(portNumber);
        while(true)
        {
            s_server.start();
        }

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