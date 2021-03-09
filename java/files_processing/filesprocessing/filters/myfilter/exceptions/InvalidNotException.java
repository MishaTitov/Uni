package filesprocessing.filters.myfilter.exceptions;

/** Class for exception of incorrect "not" suffix */
public class InvalidNotException extends FilterException {

    /** Constructor for this exception */
    public InvalidNotException(){}

    /** Method return string message for what this exception */
    public String getMessage() {return "Invalid #NOT suffix";}
}

