
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;

/**
 * Created by Adrian on 1/31/18.
 */


public class serverThread extends Thread{

    private Socket c_socket = null;

    public serverThread(Socket clientSocket)
    {
        super("ServerThread");
        c_socket = clientSocket;
    }

    @Override
    public void run()
    {
        try
        (
                PrintWriter    writer = new PrintWriter(c_socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(c_socket.getInputStream()));
        )
        {
            String inputLine, outputLine;

            //outputLine = handleInput();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String handleInput(String[] args)
    {
        String stringToProcess = args[1];

      //  Path filePath = filePath.getFileSystem();

//        String string = filePath.toString();

        return stringToProcess;
    }


}
