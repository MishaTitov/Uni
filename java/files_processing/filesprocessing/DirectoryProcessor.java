package filesprocessing;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** Class for get source directory and commands file and print all files in directory according to commands*/
public class DirectoryProcessor {
    /**
     * Method manage all classes to filter and order files
     * @param args get 2 parameters source directory where need to sort files and commands file gow to filter and order
     */
    public static void main(String[] args){
        new DirectoryParse(args[0]);
        File[] fileList = DirectoryParse.getFileList();
        FilterOrderParse FilterOrder = new FilterOrderParse();
        ArrayList<Section> sectionList;
        try{
            sectionList = FilterOrder.getFilterOrderList(args[1]);
        }catch (IOException e){
            System.err.println("ERROR: " +  e.getMessage());
            return;
        }
        for (Section section: sectionList)
            printHelper(section.getListByFilterOrder(fileList));
    }

    /**
     * Method helper for printing all files
     * @param list list of files that need to print
     */
    private static void printHelper(ArrayList<File> list){
        for (File file: list)
            System.out.println(file.getName());
    }
}
