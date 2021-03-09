package filesprocessing.filters.myfilter.exceptions;

/** Class for exception of incorrect order of filter */
public class InvalidOrderException extends FilterException {

    /** Constructor for this exception */
    public InvalidOrderException(){}

    /** Method return string message for what this exception */
    public String getMessage() {return "Invalid order for filter";}
}
