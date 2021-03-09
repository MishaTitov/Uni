package filesprocessing.filters.number.filters;
import filesprocessing.filters.myfilter.exceptions.NegativeNumberException;
import java.io.File;

/** Filter that check if file's size smaller than some value */
public class smaller_thanFilter extends numberFilter {

    /**
     * Constructor for filter
     * @param value value of maximal limit
     */
    public smaller_thanFilter(double value) throws NegativeNumberException {super(value);}

    /**
     * Check if file's size equal or smaller than limit
     * @param file file that need to check
     * @return true if file's size equal or smaller than limit, else false
     */
    @Override
    public boolean isPass(File file) {
        double size = file.length()/ratioByte;
        return (size <= this.value1);
    }
}
