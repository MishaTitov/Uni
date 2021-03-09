package filesprocessing.filters.string.filters;
import java.io.File;

/** Filter that check if file's name starts with some value */
public class prefixFilter extends stringFilter {

    /**
     * Constructor for filter
     * @param value value that use filter for checking
     */
    public prefixFilter(String value){super(value);}

    /**
     * Check if file's name starts with filter's value
     * @param file file that need to check
     * @return true if file's name starts with filter's value, else false
     */
    @Override
    public boolean isPass(File file){return (file.getName().startsWith(this.value));}
}
