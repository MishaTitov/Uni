package filesprocessing.filters.myfilter.exceptions;

/** Class for exception of incorrect name of filter */
public class InvalidNameException extends FilterException {

    /** Constructor for this exception */
    public InvalidNameException(){}

    /** Method return string message for what this exception */
    public String getMessage() {return "Invalid name of filter";}
}
