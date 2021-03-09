package filesprocessing.filters.myfilter.exceptions;

/** Class for invalid boolean parameter in filter */
public class InvalidBooleanException extends FilterException {

    /** Constructor for this exception */
    public InvalidBooleanException(){}

    /** Method return string message for what this exception */
    public String getMessage() {return "Invalid boolean parameter";}
}
