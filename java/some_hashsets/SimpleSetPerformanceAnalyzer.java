import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    /**
     * data 1,2 list of all words from data1,2.txt.
     * OPEN_HASH_SET ... option for switch.
     * example, exampleCollection variables of collections and hashSets.
     * timeBefore, differnce variables for counting time.
     * HI, NEGATIVE_NUM, NUM_23 strings that we check
     * DEFAULT_ITR, DEFAULT_LLIST_ITR amount of iteratiions for measurements
     */
    static String[] data1, data2;
    static final int OPEN_HASH_SET=0, CLOSED_HASH_SET=1, LINKED_LIST=2, TREE_SET=3, HASH_SET=4, NUM_OF_SETS=5;
    static SimpleHashSet example;
    static CollectionFacadeSet exampleCollection;
    static long timeBefore, difference;
    static int DEFAULT_ITR = 70000;
    static int DEFAULT_LLIST_ITR = 7000;
    static String HI="hi", NEGATIVE_NUM = "-13170890158", NUM_23="23";

    /**
     * Add and count time.
     * @param set set for which kind of set we need to do.
     * @param data which data we need to use.
     */
    static private void addAllWords(SimpleHashSet set, String[] data){
        timeBefore = System.nanoTime();
        for (int j = 0; j < data.length; j++)
            set.add(data[j]);
        difference = System.nanoTime() - timeBefore;
        System.out.println("The time to add all words: " + (double)(difference/1000000) + " ms");
    }
    static private void addAllWords (CollectionFacadeSet set, String[] data){
        timeBefore = System.nanoTime();
        for (int j = 0; j < data.length; j++)
            set.add(data[j]);
        difference = System.nanoTime() - timeBefore;
        System.out.println("The time to add all words: " + (double)(difference/1000000) + " ms");
    }

    /**
     * Strategy for measure time for method contain of some str and in some data
     * @param set set for which kind of set we need to do.
     * @param data - on what we check contain method
     * @param str - which string we want to check
     * @param itr - amount iterations for warm up and checking
     */
    static private void checkSet(SimpleHashSet set, String[] data, String str, int itr){
        addAllWords(set, data);
        warmUp(set, HI, itr);
        containMeasure(set, HI, itr);
        warmUp(set, str, itr);
        containMeasure(set, str, itr);
    }

    static private void checkSet(CollectionFacadeSet set, String[] data, String str, int itr){
        addAllWords(set, data);
        warmUp(set, HI, itr);
        containMeasure(set, HI, itr);
        warmUp(set, str, itr);
        containMeasure(set, str, itr);
    }

    /**
     * Measure time for method contain of some str
     * @param set set for which kind of set we need to do
     * @param str - which string we want to check
     * @param itr - amount iterations for warm up and checking
     */
    static private void containMeasure(SimpleHashSet set, String str, int itr){
        timeBefore = System.nanoTime();
        warmUp(set, str, itr);
        difference = (System.nanoTime() - timeBefore)/itr;
        System.out.println("The time to find " + str + ": " + difference + " ns");
    }
    static private void containMeasure(CollectionFacadeSet set, String str, int itr){
        timeBefore = System.nanoTime();
        warmUp(set, str, itr);
        difference = (System.nanoTime() - timeBefore)/itr;
        System.out.println("The time to find " + str + ": " + difference + " ns");
    }

    /**
     * Check contain(str) method for itr number times
     * @param set of which set's method contain() we check
     * @param str which string we need to check
     * @param itr amount of iterations for checking
     */
    static private void warmUp(SimpleHashSet set, String str, int itr){
        int percent = itr/10;
        for(int i = 0; i < itr; i++){
            if (i % percent == 0)
                System.out.print(i / percent + " ");
            set.contains(str);
        }
        System.out.println();
    }

    static private void warmUp(CollectionFacadeSet set, String str, int itr){
        for(int i = 0; i < itr; i++){
            int percent = itr/10;
            if (i % percent == 0)
                System.out.print(i / percent);
            set.contains(str);
        }
        System.out.println();
    }

    /**
     * Launch the analyzer.
     * @param args given argument.
     */
    public static void main(String[] args){
        if (args.length == 0) {
            data1 = Ex4Utils.file2array("data1.txt");
            data2 = Ex4Utils.file2array("data2.txt");
        }
        else{
            data1 = Ex4Utils.file2array(args[0]);
            data2 = Ex4Utils.file2array(args[1]);
        }
        for (int i = 0; i < NUM_OF_SETS; i++) {
            switch (i){
                case OPEN_HASH_SET:
                    //data 1
                    System.out.println("Case OpenHashSet\ndata 1");
                    example = new OpenHashSet();
                    checkSet(example, data1, NEGATIVE_NUM, DEFAULT_ITR);
                    //data 2
                    System.out.println("data 2");
                    example = new OpenHashSet();
                    checkSet(example, data2, NUM_23, DEFAULT_ITR);
                    break;
                case CLOSED_HASH_SET:
                    //data 1
                    System.out.println("Case ClosedHashSet\ndata 1");
                    example = new ClosedHashSet();
                    checkSet(example, data1, NEGATIVE_NUM, DEFAULT_ITR);
                    //data 2
                    System.out.println("data 2");
                    example = new ClosedHashSet();
                    checkSet(example, data2, NUM_23, DEFAULT_ITR);
                    break;
                case LINKED_LIST:
                    //data 1
                    System.out.println("Case LinkedList\ndata 1");
                    exampleCollection = new CollectionFacadeSet(new LinkedList<String>());
                    checkSet(exampleCollection, data1, NEGATIVE_NUM, DEFAULT_LLIST_ITR);
                    //data 2
                    System.out.println("data 2");
                    exampleCollection = new CollectionFacadeSet(new LinkedList<String>());
                    checkSet(exampleCollection, data2, NUM_23, DEFAULT_LLIST_ITR);
                    break;
                case TREE_SET:
                    //data 1
                    System.out.println("Case TreeSet\ndata 1");
                    exampleCollection = new CollectionFacadeSet(new TreeSet<String>());
                    checkSet(exampleCollection, data1, NEGATIVE_NUM, DEFAULT_ITR);
                    //data 2
                    System.out.println("data 2");
                    exampleCollection = new CollectionFacadeSet(new TreeSet<String>());
                    checkSet(exampleCollection, data2, NUM_23, DEFAULT_ITR);
                    break;
                case HASH_SET:
                    //data 1
                    System.out.println("Case HashSet\ndata 1");
                    exampleCollection = new CollectionFacadeSet(new HashSet<String>());
                    checkSet(exampleCollection, data1, NEGATIVE_NUM, DEFAULT_ITR);
                    //data 2
                    System.out.println("data 2");
                    exampleCollection = new CollectionFacadeSet(new HashSet<String>());
                    checkSet(exampleCollection, data2, NUM_23, DEFAULT_ITR);
                    break;
            }
        }
    }
}
