package filesprocessing.filters.property.filters;
import filesprocessing.filters.filter;
import java.io.File;

/**
 * Abstract class for all Property filters
 */
public abstract class propertyFilter implements filter {

    /** Value of some property */
    boolean value;

    /**
     * Default-Package Constructor for property filter
     * @param bool sets if some property true of false */
    propertyFilter(boolean bool){this.value = bool;}

    /** Abstract method that check if file passes the filter
     * @param file file that need to check
     * @return true if match, else false
     */
    @Override
    public abstract boolean isPass(File file);
}
