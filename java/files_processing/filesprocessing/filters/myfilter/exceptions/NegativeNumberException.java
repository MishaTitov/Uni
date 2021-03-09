package filesprocessing.filters.myfilter.exceptions;

/** Class for exception of negative number for filters with numbers*/
public class NegativeNumberException extends FilterException{

    /** Constructor for this exception */
    public NegativeNumberException(){}

    /** Method return string message for what this exception */
    public String getMessage() {return "Negative number for filter";}
}