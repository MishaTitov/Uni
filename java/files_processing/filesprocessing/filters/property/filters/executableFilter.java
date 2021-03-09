package filesprocessing.filters.property.filters;
import java.io.File;

/** Filter that check if file have/don't have executable permission */
public class executableFilter extends propertyFilter {

    /** Constructor for filter
     * @param value sets if property exist or not
     */
    public executableFilter(boolean value){super(value);}

    /**
     * Check if file (doesn't) has property executable
     * @param file file that need to check
     * @return true if filter value and file's property is same, else false
     */
    @Override
    public boolean isPass(File file){return (file.canExecute() == this.value);}
}
