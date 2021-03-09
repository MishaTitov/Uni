package filesprocessing.filters.number.filters;
import filesprocessing.filters.filter;
import filesprocessing.filters.myfilter.exceptions.*;
import java.io.File;

/**
 * Abstract class for all Numbers's filters
 */
public abstract class numberFilter implements filter {

    /** Values for some use's of filter */
    double value1, value2;
    /** Ratio from bytes to k-bytes */
    final double ratioByte = 1024;

    /**
     * Default-Package constructor for number's filter with 1 parameter
     * @param value value for filter (greater, smaller)
     */
    numberFilter(double value) throws NegativeNumberException {
        if(isGreaterEqualToZero(value))
            this.value1 = value;
        else throw new NegativeNumberException();
    }

    /**
     * Default-Package constructor for number's filter with 2 parameters (example between filter)
     * @param value1 value for filter (greater)
     * @param value2 value for filter (smaller)
     */
    numberFilter(double value1, double value2) throws InvalidOrderException, NegativeNumberException {
        if(isGreaterEqualToZero(value1) && isGreaterEqualToZero(value2)){
            if (value1 <= value2){
                this.value1 = value1;
                this.value2 = value2;
            }else throw new InvalidOrderException();
        }else throw new NegativeNumberException();
    }

    /**
     * Check if number is not negative
     * @param value value is need to be checked
     * @return true if isn't negative, else false
     */
    private boolean isGreaterEqualToZero(double value){
        return (value >= 0);
    }

    /**
     * Abstract method that check if file passes the filter
     * @param file file that need to check
     * @return true if match, else false
     */
    @Override
    public abstract boolean isPass(File file);
}
