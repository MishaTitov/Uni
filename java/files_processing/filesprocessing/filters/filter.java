package filesprocessing.filters;
import java.io.File;

/**
 * Interface for all filters
 */
public interface filter {

    /**
     * Check if file passes the filter
     * @param file file that need to check
     * @return true if match, else false
     */
    boolean isPass(File file);
}
