public abstract class SimpleHashSet extends java.lang.Object implements SimpleSet{
/**
 * Describes the higher load factor, the lower load factor and the capacity of a newly created hash set.
 * Size - the number of elements currently in the set.
 */
        protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

        protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

        protected static int INITIAL_CAPACITY = 16;

        protected float upperLoadFactor;

        protected float lowerLoadFactor;

        protected int capacity;

        protected int size;

        /**
         * Constructs a new hash set with the default
         * capacities given in DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY.
         */
        protected SimpleHashSet(){
            this(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
        }

        /**
         * Constructs a new hash set with capacity INITIAL_CAPACITY.
         * @param upperLoadFactor The upper load factor before rehashing.
         * @param lowerLoadFactor The lower load factor before rehashing.
         */
        protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
            this.capacity = INITIAL_CAPACITY;
            this.size = 0;
            this.upperLoadFactor = upperLoadFactor;
            this.lowerLoadFactor = lowerLoadFactor;
        }

        /**
         * @return The current capacity (number of cells) of the table.
         */
        public abstract int capacity();

        /**
         * Clamps hashing indices to fit within the current table capacity
         * (see the exercise description for details).
         * Because of poor describing in exercise and moodle forum, I don't understand what it supposed to be.
         * @param index The index before clamping.
         * @return an index properly clamped.
         */
        protected int clamp(int index){return index&(capacity()-1);}

        /**
         * @return The lower load factor of the table.
         */
        protected float getLowerLoadFactor(){ return lowerLoadFactor; }

        /**
         *@return The higher load factor of the table.
         */
        protected float getUpperLoadFactor(){ return upperLoadFactor; }

        /**
         * Check if table needs to decrease.
         * @return true if lower load bound bigger
         */
        protected boolean checkDecrease(){
            float check = ((float) size())/ ((float) capacity());
            return (check < getLowerLoadFactor() && capacity() > 1);
        }

        /**
         * Check if table needs to increase.
         * @return true if upper load bound smaller
         */
        protected boolean checkIncrease(){
            float check = ((float) size())/ ((float) capacity());
            return check > getUpperLoadFactor();
        }
}