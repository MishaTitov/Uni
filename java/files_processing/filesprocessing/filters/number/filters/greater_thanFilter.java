package filesprocessing.filters.number.filters;
import filesprocessing.filters.myfilter.exceptions.NegativeNumberException;
import java.io.File;

/** Filter that check if file's size greater than some value */
public class greater_thanFilter extends numberFilter {

    /**
     * Constructor for filter
     * @param value value of minimal limit
     */
    public greater_thanFilter(double value) throws NegativeNumberException {super(value);}

    /**
     * Check if file's size equal or greater than limit
     * @param file file that need to check
     * @return true if file's size greater than limit, else false
     */
    @Override
    public boolean isPass(File file) {
        double size = file.length()/ratioByte;
        return (size > this.value1);
    }
}
