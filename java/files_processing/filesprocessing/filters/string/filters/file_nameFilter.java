package filesprocessing.filters.string.filters;
import java.io.File;

/** Filter that check if file's name equals some value */
public class file_nameFilter extends stringFilter {

    /**
     * Constructor for filter
     * @param value name that constructor'll use
     */
    public file_nameFilter(String value){super(value);}

    /**
     * Check if file's name is equal to filter's value
     * @param file file that need to check
     * @return true if file's name is equal to filter's value, else false
     */
    @Override
    public boolean isPass(File file){return (file.getName().equals(this.value));}
}
