package filesprocessing.orders.myorder.exceptions;
import java.io.IOException;

/** Abstract class for all order exceptions */
public abstract class OrderException extends IOException {

    private static final long serialVersionUID = 1L;

    /** Constructor for order's exceptions */
    public OrderException(){}
}
