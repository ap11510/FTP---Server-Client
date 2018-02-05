import java.io.*;
import java.net.Socket;

/**
 * Created by Adrian on 2/1/18.
 */
//Worker Class
//Handles all the logic of constructing output to send back to user

public class Worker {

    private Socket clientSocket;
    private PrintWriter writer;
    InputStream is;
    FileOutputStream fos;
    BufferedOutputStream bos;
    File directory;

    Worker(Socket c_socket) throws Exception
    {
        clientSocket = c_socket;
        writer       = new PrintWriter(clientSocket.getOutputStream(), true);
        directory    = new File(".");
    }
    
    public void quit() throws IOException
    {
        writer.flush();
        writer.close();
        //clientSocket.close();
        System.out.println("Server Now Ready To Accept New Connection");
    }
    public void msg(String str)
    {
    	writer.println(str);
    }

    public void listSegments()
    {
        File[] fileList           = directory.listFiles();

        StringBuffer fileNameList = new StringBuffer();

        for(File file: fileList)
        {
            //StringBuffer msg = new StringBuffer();

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

        //System.out.print(fileNameList);
        writer.println(fileNameList);
    }

    public void printWorkingDirectory() throws IOException
    {
        writer.println(directory.getCanonicalPath() + "\n");
    }

    public void printUnknownCmd() throws IOException
    {
        writer.println("Unknown Command" + "\n");
    }

    public void deleteFile(String[] arguments) throws IOException
    {
        if(arguments.length != 2)
        {
            writer.println("Improper Usage: Command is delete <remote_file_name>" + "\n");
        }
        else
        {
            StringBuffer constructorString = new StringBuffer(directory.getCanonicalPath()).append("/").append(arguments[1]);
            File fileToDelete = new File(constructorString.toString());

            if(!fileToDelete.isDirectory())
            {
                if(fileToDelete.exists())
                {
                    fileToDelete.delete();
                    writer.println("File Deleted" + "\n");
                }
                else
                {
                    writer.println("File does not exist" + "\n");
                }
            }
            else
            {
                writer.println("Cannot delete a directory" + "\n");
            }
        }
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
        else if(arguments[1].startsWith("/"))
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

            File newFile = new File(newDirectory.toString());
            //if (newFile.exists() && newFile.isDirectory()) {
            if(newFile.isDirectory())
            {
                this.directory = newFile;
                printWorkingDirectory();
            }
            else
            {
                writer.println("Directory doesn't exist" + "\n");
            }
        }
    }

    public void makeDirectory(String[] arguments) throws IOException
    {
        StringBuffer constructorString = new StringBuffer(directory.getCanonicalPath()).append("/").append(arguments[1]);
        File directoryToMake = new File(constructorString.toString());

        if(directoryToMake.isDirectory())
        {
            writer.println("Directory already exists" + "\n");
        }
        else
        {
            directoryToMake.mkdir();
            writer.println("Directory Made" + "\n");
        }
    }
    public void get(String[] arguments) throws IOException
    {
    	msg("(1337)pushing");
    	msg(arguments[1]);
    	DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
		FileInputStream fis = new FileInputStream(arguments[1]);
		byte[] buffer = new byte[4096];
		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		dos.flush();
		fis.close(); 
    }
    public void put(String[] arguments) throws IOException
    {
    	
    	
    }

   
}

