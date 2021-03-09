public class CollectionFacadeSet extends java.lang.Object implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False iff newValue already exists in the set.
     */
    public boolean add(java.lang.String newValue){
        if (!this.contains(newValue)){
            this.collection.add(newValue);
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for.
     * @return True iff searchVal is found in the set.
     */
    public boolean contains(String searchVal){
        return (this.collection != null) && (this.collection.contains(searchVal));
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete.
     * @return Remove the input element from the set.
     */
    public boolean delete(java.lang.String toDelete){
        if (this.contains(toDelete)){
            this.collection.remove(toDelete);
            return true;
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size(){
        return this.collection.size();
    }
}
