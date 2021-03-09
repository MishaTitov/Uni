package filesprocessing.filters.property.filters;
import java.io.File;

/** Filter that check if file have/don't have writable permission */
public class writableFilter extends propertyFilter {

    /** Constructor for filter
     * @param value sets if property exist or not
     */
    public writableFilter(boolean value){super(value);}

    /**
     * Check if file (doesn't) has property writable
     * @param file file that need to check
     * @return true if filter value and file's property is same, else false
     */
    @Override
    public boolean isPass(File file){return (file.canWrite() == this.value);}
}
