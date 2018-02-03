import java.io.*;
import java.net.Socket;

/**
 * Created by Adrian on 2/1/18.
 */
public class Worker {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    File directory;

    Worker(Socket c_socket) throws Exception
    {
        clientSocket = c_socket;
        writer = new PrintWriter(clientSocket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        directory = new File(".");
    }


    public void listSegments()
    {
        File[] fileList = directory.listFiles();

        StringBuffer fileNameList = new StringBuffer();

        for(File file: fileList)
        {
            StringBuffer msg = new StringBuffer();
            if(file.isFile())
            {
                fileNameList.append("File:");
            }
            else if(file.isDirectory())
            {
                fileNameList.append("Directory:");
            }

            fileNameList.append(file.getName());

            fileNameList.append("\n");

        }

        System.out.print(fileNameList);
        writer.println(fileNameList);
    }

    public void printWorkingDirectory() throws IOException
    {
        writer.println(directory.getCanonicalPath() + "\n");
    }

    public void printUnknownCmd()
    {
        writer.println("Unknown Command" + "\n");
    }

    public void changeDirectory(String[] arguments) throws IOException
    {
        if(arguments.length != 2)
        {
            writer.println("Improper Usage: Need either <Directory> or <..>" + "\n");
        }
        else if(arguments[1].equals(".."))
        {
            StringBuffer currentDirectory = new StringBuffer();
            String[] directoryUp = directory.getCanonicalPath().split("/");
            int length = directoryUp.length;

            if(length == 0)
            {
                System.out.println("Cannot go higher than root");
            }

            int i = 0;

            while(i < length - 1)
            {
                currentDirectory.append("/");
                currentDirectory.append(directoryUp[i]);
                i = i + 1;
            }

            this.directory = new File(currentDirectory.toString());
            printWorkingDirectory();
        }
        else if(arguments[1].substring(1,1).equals("/"))
        {
            File newFile = new File(arguments[1]);
            if (newFile.exists() && newFile.isDirectory()) {
                this.directory = newFile;
                printWorkingDirectory();
            }
            else
            {
                writer.println("Directory doesn't exist" + "\n");
            }
        }
        else
        {
            StringBuffer newDirectory = new StringBuffer();
            newDirectory.append(directory.getCanonicalPath());
            newDirectory.append("/");
            newDirectory.append(arguments[1]);

            File newFile = new File(arguments[1]);
            //if (newFile.exists() && newFile.isDirectory()) {
            if(newFile.isDirectory()){
                this.directory = newFile;
                printWorkingDirectory();
            }
            else
            {
                writer.println("Directory doesn't exist" + "\n");
            }
        }




    }

    public static void main(String[] args) throws IOException
    {

    }
}

