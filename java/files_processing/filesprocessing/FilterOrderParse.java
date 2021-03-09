package filesprocessing;
import filesprocessing.mysection.exceptions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** Class for parse filters and orders in commands file*/
public class FilterOrderParse {


    private int indexLine;
    private String stringLine;
    private ArrayList<Section> listSections = new ArrayList<Section>();

    /**
     * Default constructor
     */
    public FilterOrderParse(){}

    /**
     * Open and parse filter and order to sections
     * @param filePath - path to file
     * @return ArrayList of sections
     * @throws IOException
     */
    public ArrayList<Section> getFilterOrderList (String filePath) throws IOException{
        File fileFilterOrder = new File(filePath);
        this.indexLine = 0;
        Section section = new Section();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileFilterOrder))){
            readerHelper(reader);

            while (this.stringLine != null){
                if (!this.stringLine.equals("FILTER"))
                    throw new NoFilterException();
                readerHelper(reader);
                if (this.stringLine.equals("ORDER"))
                    throw new NoFilterException();
                section.addFilter(this.stringLine, this.indexLine);

                readerHelper(reader);
                if (this.stringLine == null || !this.stringLine.equals("ORDER"))
                    throw new NoOrderException();
                readerHelper(reader);
                if (this.stringLine == null || this.stringLine.equals("FILTER"))
                    section.addOrder("abs", this.indexLine );
                else{
                    section.addOrder(this.stringLine, this.indexLine);
                    readerHelper(reader);
                }
                this.listSections.add(section);
            }
            return this.listSections;
        }
    }

    /**
     * Method helper for reading line from file
     * @param reader - Buffered Reader
     * @throws IOException
     */
    private void readerHelper(BufferedReader reader) throws IOException{
        this.stringLine = reader.readLine();
        this.indexLine++;
    }
}
