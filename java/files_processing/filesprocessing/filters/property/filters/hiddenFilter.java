package filesprocessing.filters.property.filters;
import java.io.File;

/** Filter that check if file is/isn't hidden */
public class hiddenFilter extends propertyFilter {

    /** Constructor for filter
     * @param value sets if property exist or not
     */
    public hiddenFilter(boolean value){super(value);}

    /**
     * Check if file is/isn't hidden
     * @param file file that need to check
     * @return true if filter value and file's property is same, else false
     */
    @Override
    public boolean isPass(File file){return (file.isHidden() == this.value);}
}
