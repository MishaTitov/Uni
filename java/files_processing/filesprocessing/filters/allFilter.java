package filesprocessing.filters;
import java.io.File;

/** Class for all filter */
public class allFilter implements filter {

    /** Constructor for filter */
    public allFilter(){}

    /**
     * Filter that all files pass
     * @param file file that need to check
     * @return true in order to file pass filter
     */
    @Override
    public boolean isPass(File file){return true;}
}
