package filesprocessing.filters.string.filters;
import java.io.File;

/** Filter that check if file's name ends with some value */
public class suffixFilter extends stringFilter {

    /**
     * Constructor for filter
     * @param value value that use filter for checking
     */
    public suffixFilter(String value){super(value);}

    /**
     * Check if file's name ends with filter's value
     * @param file file that need to check
     * @return true if file's name ends with filter's value, else false
     */
    @Override
    public boolean isPass(File file){return (file.getName().endsWith(this.value));}
}
