package filesprocessing;
import java.io.File;

/**
 * Open directory and gets all files in it
 */
public class DirectoryParse {

    private File directory;
    private static File[] fileList;

    /**
     * Constructor for open folder and get list of files in it
     * @param path - source directory
     */
    public DirectoryParse(String path){
        try{
            this.directory = new File(path);
            fileList = this.directory.listFiles();
        }catch (NullPointerException e){
            System.err.println("ERROR: no such directory");
            System.exit(-1);
        }
    }

    /**
     * @return - return list of all files in directory
     */
    public static File[] getFileList(){return fileList;}
}
