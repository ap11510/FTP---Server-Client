import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Adrian on 2/1/18.
 */
public class Worker {

    public void listSegments()
    {
        File directory  = new File( System.getProperty("user.dir") );

        File[] fileList = directory.listFiles();

        System.out.println("Working Directory = " + System.getProperty("user.dir") );

        for(File loopFile : fileList)
        {
            if(loopFile.isFile())
            {
                System.out.println("File: " + loopFile.getName());
            }
            else if(loopFile.isDirectory())
            {
                System.out.println("Directory: " + loopFile.getName());

            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        Worker worker = new Worker();

        worker.listSegments();

    }
}

//System.getProperty("user.dir")