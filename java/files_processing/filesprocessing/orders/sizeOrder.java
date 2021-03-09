package filesprocessing.orders;
import java.io.File;

/** Class for size order */
public class sizeOrder extends absOrder implements order {

    /**
     * Compare 2 files according to theirs sizes
     * @param file1 file one to compare
     * @param file2 file two to compare
     * @return -1 if file1 < file2, 0 if file1 = file2, 1 if file1 > file2
     */
    @Override
    public int compare(File file1, File file2){
        int difference = (int) (file1.length() - file2.length());
        if (difference < 0)
            return -1;
        else if(difference > 0)
            return 1;
        return super.compare(file1, file2);
    }
}
