package filesprocessing;
import filesprocessing.filters.*;
import filesprocessing.orders.*;
import java.io.File;
import java.util.ArrayList;

/** Class for build section from filter and order */
public class Section {

    private filter myFilter;
    private order myOrder;

    /**
     * Default constructor
     */
    public Section(){}

    /**
     * add filter to section
     * @param nameFilter - name of filter to add
     * @param filterLine - number of line from where filter was taken
     */
    public void addFilter(String nameFilter, int filterLine){
        this.myFilter = filterFactory.select(nameFilter.split("#"), filterLine);
    }

    /**
     * add order to section
     * @param nameOrder - name of order to add
     * @param orderLine - number of line from where order was taken
     */
    public void addOrder(String nameOrder, int orderLine){
        this.myOrder = orderFactory.select(nameOrder.split("#"), orderLine);
    }

    /**
     * build list according for filter and order
     * @param files - list of files that need to filter and order
     * @return - filtered and ordered list of files
     */
    public ArrayList<File> getListByFilterOrder(File[] files){
        ArrayList<File> fileList = new ArrayList<File>();
        for (int i=0; i < files.length; i++) {
            if (files[i].isDirectory())
                continue;
            if (this.myFilter.isPass(files[i]))
                fileList.add(files[i]);
        }
        fileList.sort(this.myOrder);
        return fileList;
    }
}
