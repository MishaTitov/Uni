package filesprocessing.orders;
import java.io.File;

/** Class for type order */
public class typeOrder extends absOrder implements order  {

    /**
     * Compare 2 files according to the theirs type
     * @param file1 file one to compare
     * @param file2 file two to compare
     * @return -1 if file1 < file2, 0 if file1 = file2, 1 if file1 > file2
     */
    @Override
    public int compare(File file1, File file2){
        String typeFile1 = getFileType(file1);
        String typeFile2 = getFileType(file2);
        int value = typeFile1.compareTo(typeFile2);
        if (value == 0)
            return super.compare(file1, file2);
        return value;
    }

    /** Method that get type of file
     * @param file file that need to get its type
     * @return string of type of file, its empty if file don't have type
     */
    private String getFileType(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
}
