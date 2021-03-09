package filesprocessing.filters;
import java.io.File;

/** Class for filter with not */
public class notFilter implements filter {
    /** Filter that need to be reverse */
    private filter oldFilter;

    /** Constructor for filter with not*/
    public notFilter(filter oldFilter){ this.oldFilter = oldFilter;}

    /**
     * Check if file pass that that source filter and change it to reverse
     * @param file file that need to check
     * @return if file pass true, else false
     */
    public boolean isPass(File file){return (!this.oldFilter.isPass(file));}
}
