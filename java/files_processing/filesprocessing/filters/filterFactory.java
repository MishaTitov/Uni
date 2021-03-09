package filesprocessing.filters;
import filesprocessing.filters.myfilter.exceptions.*;
import filesprocessing.filters.number.filters.*;
import filesprocessing.filters.property.filters.*;
import filesprocessing.filters.string.filters.*;


public class filterFactory {

    private final static String STR_YES = "YES";
    private final static String STR_NO = "NO";

    /**
     * select filter according for name and parameters
     * @param nameFilter - filter name and parameters
     * @param line - line from where filter was taken
     * @return filter that was selected
     */

    public static  filter select(String[] nameFilter, int line){
        filter myFilter;
        int index = 0;
        try{
            if (nameFilter[index].equals("greater_than"))
                myFilter = new greater_thanFilter(Double.parseDouble(nameFilter[++index]));

            else if (nameFilter[index].equals("between"))
                myFilter = new betweenFilter(Double.parseDouble(nameFilter[++index]),
                        Double.parseDouble(nameFilter[2]));

            else if (nameFilter[index].equals("smaller_than"))
                myFilter = new smaller_thanFilter(Double.parseDouble(nameFilter[++index]));

            else if (nameFilter[index].equals ("file"))
                myFilter = new file_nameFilter(nameFilter[++index]);

            else if (nameFilter[index].equals ("contains"))
                myFilter = new containsFilter(nameFilter[++index]);

            else if (nameFilter[index].equals ("prefix"))
                myFilter = new prefixFilter(nameFilter[++index]);

            else if (nameFilter[index].equals ("suffix"))
                myFilter = new suffixFilter(nameFilter[++index]);

            else if (nameFilter[index].equals ("writable")){

                myFilter = new writableFilter(propertyFilterHelper(nameFilter[++index]));
                /**
                if (nameFilter[++index].equals("YES"))
                    myFilter = new writableFilter(true);
                else if (nameFilter[index].equals("NO"))
                    myFilter = new writableFilter(false);
                else
                    throw new InvalidBooleanException();*/
            }

            else if (nameFilter[index].equals ("executable")){

                myFilter = new executableFilter(propertyFilterHelper(nameFilter[++index]));
                /**
                if (nameFilter[++index].equals("YES"))
                    myFilter = new executableFilter(true);
                else if (nameFilter[index].equals("NO"))
                    myFilter = new executableFilter(false);
                else
                    throw new InvalidBooleanException();*/
            }

            else if(nameFilter[index].equals ("hidden")){

                myFilter = new hiddenFilter(propertyFilterHelper(nameFilter[++index]));
                /**
                if(nameFilter[++index].equals("YES"))
                    myFilter = new hiddenFilter(true);
                else if (nameFilter[index].equals("NO"))
                    myFilter = new hiddenFilter(false);
                else
                    throw new InvalidBooleanException();*/
            }

            else if (nameFilter[index].equals ("all"))
                myFilter = new allFilter();
            else
                throw new InvalidNameException();

            if (++index < nameFilter.length)
                if (nameFilter[index].equals("NOT"))
                    myFilter = new notFilter(myFilter);
                else
                    throw new InvalidNotException();

        }catch (FilterException e){
            System.out.println("Warning in line " + line + " " + e.getMessage());
            myFilter = new allFilter();
        }
        return myFilter;
    }

    private static boolean propertyFilterHelper (String parameter) throws InvalidBooleanException {
        if (parameter.equals(STR_YES))
            return true;
        if (parameter.equals(STR_NO))
            return false;
        throw new InvalidBooleanException();
    }
}
