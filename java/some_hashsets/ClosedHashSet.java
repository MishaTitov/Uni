public class ClosedHashSet extends SimpleHashSet {

    /**
     * Ratio for increase/decrease table
     */
    private final double INCREASE_RATIO = 2;

    private final double DECREASE_RATIO = 0.5;

    /** Hash Table of this class */
    private String[] hashTable;
    /**
     * String defines that element was deleted
     */
    private final String NONE = "NoN";

    /**
     * A default constructor.
     */
    public ClosedHashSet(){
        super();
        this.hashTable = new String[capacity];
    }

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        this.hashTable = new String[capacity];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        super();
        this.hashTable = new String[capacity];
        for(String i:data) {
            this.add(i);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (this.contains(newValue))
            return false;
        addHelper(newValue);
        if (checkIncrease())
            resize(INCREASE_RATIO);
        return true;
    }

    /**
     * Method helps to find valid index of place for new value in table.
     * @param newValue New value to add to the set.
     */
    private void addHelper(String newValue){
        int valueHash = clamp(newValue.hashCode());
        int index = 0;
        while (true){
            if ((this.hashTable[valueHash] == null) || (this.hashTable[valueHash].equals(NONE))){
                this.hashTable[valueHash] = newValue;
                this.size++;
                return;
            }
            index++;
            valueHash = reHash(clamp(newValue.hashCode()), index);
        }
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(java.lang.String searchVal){
        return containsHelper(searchVal) >= 0;
    }

    /**
     * Method helps to find index of place where located value
     * @param searchVal Value to search for.
     * @return index of place where located value or -1 in other case
     */
    private int containsHelper(String searchVal){
        int valueHash = clamp(searchVal.hashCode());
        int index = 0;
        while (true){
            if (this.hashTable[valueHash] == null)
                return -1;
            if (this.hashTable[valueHash].equals(searchVal) &&
                    (!(System.identityHashCode(this.hashTable[valueHash]) == System.identityHashCode(NONE))))
                return valueHash;
            index++;
            valueHash = reHash(clamp(searchVal.hashCode()), index);
        }
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return True iff toDelete is found and deleted.
     */
    public boolean delete(java.lang.String toDelete){
        int valHash = containsHelper(toDelete);
        if (valHash >= 0){
            this.hashTable[valHash] = NONE;
            this.size--;
            if (checkDecrease())
                resize(DECREASE_RATIO);
            return true;
        }
        return false;
    }

    /**
     * Hash function for strings but quadratic probing.
     * @param valueHash a string to be hashed
     * @param index index to find get new value.
     * @return the rehashed value.
     */
    private int reHash(int valueHash, int index){
        return (valueHash + ((index * index +
                index)/2)) & (capacity()-1);
    }

    /**
     * change table capacity by ratio.
     * @param ratio to change capacity by.
     */
    private void resize(double ratio){
        String[] oldHashTable = this.hashTable;
        int oldCapacity = capacity();
        this.size = 0;
        this.capacity = (int) (oldCapacity * ratio);
        this.hashTable = new String[capacity()];
        for (int i=0; i < oldCapacity; i++)
            if ((oldHashTable[i] != null) && (!oldHashTable[i].equals(NONE)))
                addHelper(oldHashTable[i]);
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size(){
        return this.size;
    }

    /**
     * @return The current capacity (number of cells) of the table..
     */
    public int capacity(){
        return this.capacity;
    }
}
