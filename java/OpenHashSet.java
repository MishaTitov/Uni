import java.util.LinkedList;

public class OpenHashSet extends SimpleHashSet{

    /**
     * Ratio for increase/decrease table.
     */
    private final double INCREASE_RATIO = 2;

    private final double DECREASE_RATIO = 0.5;

    /** Hash Table for this class */
    private LinkedListHelper[] hashTable;

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super();
        buildHashTable(capacity);
    }

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        buildHashTable(capacity);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        super();
        buildHashTable(capacity);
        for(String i:data)
            this.add(i);
    }

    /**
     * Constructor a new hash table with given capacity.
     * @param capacity initial capacity for new table.
     */
    private void buildHashTable(int capacity){
        this.hashTable = new LinkedListHelper[capacity];
        for (int i = 0; i < capacity; i++)
            this.hashTable[i] = new LinkedListHelper();
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False iff newValue already exists in the set.
     */
    public boolean add(java.lang.String newValue){
        if (this.contains(newValue))
            return false;
        this.hashTable[clamp(newValue.hashCode())].add(newValue);
        this.size++;
        if (checkIncrease())
            resize(INCREASE_RATIO);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(java.lang.String searchVal){
        return (this.hashTable[clamp(searchVal.hashCode())].contains(searchVal));
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(java.lang.String toDelete){
        if (this.contains(toDelete)){
            this.hashTable[clamp(toDelete.hashCode())].delete(toDelete);
            this.size--;
            if (checkDecrease())
                resize(DECREASE_RATIO);
            return true;
        }
        return false;
    }

    /**
     * Change table capacity by ratio.
     * @param ratio to change capacity by.
     */
    private void resize(double ratio){
        LinkedListHelper [] oldHashTable = this.hashTable;
        int oldCapacity = capacity();
        this.size =0;
        this.capacity = (int)(oldCapacity * ratio);
        buildHashTable(this.capacity);
        for (int i = 0; i < oldCapacity; i++)
            resizeHelper(oldHashTable[i]);
    }

    /**
     * Copy and delete all elements in list.
     * @param list list of element that need to copy.
     */
    private void resizeHelper(LinkedListHelper list){
        while (!list.isEmpty())
            this.add(list.pollFirst());
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity(){
        return this.capacity;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size(){
        return this.size;
    }

    /**
     * A wrapper-class that has a LinkedList<String> and delegates methods to it.
     */
    private class LinkedListHelper{

        private LinkedList<String> list;

        /**
         * Default constructor, initiate a linkedList.
         */
        private LinkedListHelper() {
            this.list = new LinkedList<String>();
        }

        /**
         * Add a specified element to the list.
         * @param newValue New value to add to the list.
         */
        private void add(String newValue) {
            this.list.add(newValue);
        }

        /**
         * Search for a specified value in the list.
         * @param searchVal specified value.
         * @return true if element exist in list.
         */
        private boolean contains(String searchVal) {
            return (!this.list.isEmpty()) && (this.list.contains(searchVal));
        }

        /**
         * Delete an element from list.
         * @param toDelete element to delete.
         */
        private void delete(String toDelete) {
            this.list.remove(toDelete);
        }

        /**
         * Retrieves and removes the first element of this list, or returns null if this list is empty.
         * @return the first string in list or null.
         */
        private String pollFirst(){
            return this.list.pollFirst();
        }

        /**
         * Check if list is empty.
         * @return true if list is empty, false else.
         */
        private boolean isEmpty(){
            return this.list.isEmpty();
        }
    }
}
