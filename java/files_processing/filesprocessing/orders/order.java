package filesprocessing.orders;
import java.io.File;
import java.util.Comparator;

/**
 * Interface for all orders
 */
public interface order extends Comparator<File> {

    /**
     * Compare 2 files
     * @param file1 file one to compare
     * @param file2 file two to compare
     * @return -1 if file1 < file2, 0 if file1 = file2, 1 if file1 > file2
     */
    int compare(File file1, File file2);
}
