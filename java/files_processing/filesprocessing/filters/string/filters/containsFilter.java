package filesprocessing.filters.string.filters;
import java.io.File;

/** Filter that check if file's name contains some value */
public class containsFilter extends stringFilter {

    /**
     * Constructor for filter
     * @param value string value for checking
     */
    public containsFilter(String value){super(value);}

    /**
     * Check if file's name contains filter's value
     * @param file file that need to check
     * @return true if file's name contains filter's value, else false
     */
    @Override
    public boolean isPass(File file){return (file.getName().contains(this.value));}
}
