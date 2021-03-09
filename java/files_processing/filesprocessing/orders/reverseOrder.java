package filesprocessing.orders;
import java.io.File;

/** Class for reverse order */
public class reverseOrder implements order{
    /** Order that need to reverse */
    private order oldOrder;

    /**
     * Constructor for reverse order
     * @param oldOrder order that need to reverse
     */
    public reverseOrder(order oldOrder){ this.oldOrder = oldOrder;}

    /**
     * Compare method for reverse order
     * @param file1 file that need to be compare
     * @param file2 file that need to be compare
     * @return -1 if file1 > file2, 0 if file1 = file2, 1 if file1 < file2
     */
    public int compare(File file1, File file2){return -1*(this.oldOrder.compare(file1, file2));}
}
