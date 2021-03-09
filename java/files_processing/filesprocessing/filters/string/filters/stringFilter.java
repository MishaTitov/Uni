package filesprocessing.filters.string.filters;
import filesprocessing.filters.filter;
import java.io.File;

/**
 * Abstract class for all String's filters
 */
public abstract class stringFilter implements filter {

    /** Values for some use's of filter */
    String value;

    /**
     * Default-Package constructor for string's filter
     * @param value string value for filter
     */
    stringFilter(String value){this.value = value;}

    /**
     * Abstract method that check if file passes the filter
     * @param file file that need to check
     * @return true if match, else false
     */
    @Override
    public abstract boolean isPass(File file);
}
