package filesprocessing.orders;
import java.io.File;

/** Class for Absolute Path order */
public class absOrder implements order {

    /**
     * Compare 2 files in AbsolutePath order
     * @param file1 file one to compare
     * @param file2 file two to compare
     * @return -1 if file1 < file2, 0 if file1 = file2, 1 if file1 > file2
     */
    @Override
    public int compare(File file1, File file2){return (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));}
}
