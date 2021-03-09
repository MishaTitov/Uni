package filesprocessing.orders.myorder.exceptions;

/** Class for exception of incorrect name of order */
public class InvalidNameException extends OrderException {

    /** Constructor for this exception */
    public InvalidNameException(){}

    /** return string message for what exception */
    public String getMessage() {return "Invalid name of order";}
}
