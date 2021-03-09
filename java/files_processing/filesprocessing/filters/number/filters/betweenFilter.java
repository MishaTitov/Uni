package filesprocessing.filters.number.filters;
import filesprocessing.filters.myfilter.exceptions.*;
import java.io.File;

/** Filter that check if file's size between 2 some values */
public class betweenFilter extends numberFilter {

    /**
     * Constructor for filter
     * @param value1 value of minimal limit
     * @param value2 value of maximal limit
     */
    public betweenFilter(double value1, double value2) throws InvalidOrderException, NegativeNumberException {
        super(value1, value2);
    }

    /**
     * Check if file's size between limits
     * @param file file that need to check
     * @return true if file's size between 2 limits, else false
     */
    @Override
    public boolean isPass(File file) {
        double size = file.length()/ratioByte;
        return  (this.value1 <= size && size <= this.value2);
    }
}
