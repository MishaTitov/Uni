package filesprocessing.orders.myorder.exceptions;

/** Class for exception of incorrect "reverse" suffix */
public class InvalidReverseException extends OrderException {

    /** Constructor for this exception */
    public InvalidReverseException(){}

    /** return string message for what exception */
    public String getMessage() {return "Invalid #REVERSE suffix";}
}
