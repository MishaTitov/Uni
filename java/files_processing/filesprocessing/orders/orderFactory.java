package filesprocessing.orders;
import filesprocessing.orders.myorder.exceptions.*;

public class orderFactory {
    /**
     * select order according for name filter
     * @param nameOrder name order that need to select
     * @param line line from where was taken name of order
     * @return order that need to select
     */
    public static order select (String[] nameOrder, int line){
        order myOrder;
        int index = 0;
        try{
            if (nameOrder[index].equals("abs"))
                myOrder = new absOrder();

            else if (nameOrder[index].equals("type"))
                myOrder = new typeOrder();

            else if (nameOrder[index].equals("size"))
                myOrder = new sizeOrder();

            else
                throw new InvalidNameException();
            if (++index < nameOrder.length)
                if (nameOrder[nameOrder.length - 1].equals("REVERSE"))
                    myOrder = new reverseOrder(myOrder);
                else
                    throw new InvalidReverseException();
        }catch (OrderException e){
            System.out.println("Warning in line " + line + " " + e.getMessage());
            myOrder = new absOrder();
        }
        return myOrder;
    }
}
