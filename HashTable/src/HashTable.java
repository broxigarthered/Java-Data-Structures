import java.util.Iterator;
import java.util.LinkedList;

public class HashTable<TKey, TValue> implements Iterable<KeyValue<TKey, TValue>> {

    private LinkedList<KeyValue<TKey, TValue>>[] slots;

    public final float loadFactor = 0.75f;

    private int size;

    private int count;

    public HashTable() {
        this.slots = new LinkedList[16];
        this.count = 0;
    }

    public HashTable(int capacity) {
        this.slots = new LinkedList[capacity];
        this.count = 0;
    }

    public void add(TKey key, TValue value) {
        this.growIfNeed();
        int slotNumber = this.findSlotNumber(key);

        // if there isnt any element at the position
        if(this.slots[slotNumber] == null){
            this.slots[slotNumber] = new LinkedList<KeyValue<TKey, TValue>>();
        }

        // Note: Itterate through the chain and throw an exception on duplicated key
        for (KeyValue<TKey, TValue> element : this.slots[slotNumber]) {
            if(element.getKey().equals(key)){
                throw new IllegalArgumentException("Key alredy exists " + key);
            }
        }

        KeyValue<TKey, TValue> newElement = new KeyValue<>(key, value);
        this.slots[slotNumber].addLast(newElement);
        this.count++;
    }

    private int findSlotNumber(TKey key) {
        return Math.abs(key.hashCode() % this.slots.length);
    }

    private void growIfNeed() {
        if((float)(this.count + 1) / this.slots.length > loadFactor){
            this.grow();
        }
    }

    private void grow() {
        HashTable<TKey, TValue> newHashTable = new HashTable<TKey, TValue>(2 * this.capacity());

        for (LinkedList<KeyValue<TKey, TValue>> element : this.slots) {
            if(element != null) {
                for (KeyValue<TKey, TValue> tKeyTValueKeyValue : element) {
                    newHashTable.add(tKeyTValueKeyValue.getKey(), tKeyTValueKeyValue.getValue());
                }

            }
        }

        this.slots = newHashTable.slots;
        this.count = newHashTable.count;
    }

    public int size() {
        return this.count;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int capacity() {
        return this.slots.length;
    }

    private int loadFactor(){
        return this.capacity() / this.count;
    }

    public boolean addOrReplace(TKey key, TValue value) {

        this.growIfNeed();
        int slotNumber = this.findSlotNumber(key);

        // if there isnt any element at the position
        if(this.slots[slotNumber] == null){
            this.slots[slotNumber] = new LinkedList<KeyValue<TKey, TValue>>();
        }

        // Note: Itterate through the chain and throw an exception on duplicated key
        for (KeyValue<TKey, TValue> element : this.slots[slotNumber]) {
            if(element.getKey().equals(key)){
                element.setValue(value);
                return false;
            }
        }

        KeyValue<TKey, TValue> newElement = new KeyValue<>(key, value);
        this.slots[slotNumber].addLast(newElement);
        this.count++;
        return true;
    }

    public TValue get(TKey key) {
        // Note: throw an exception on missing key
        KeyValue<TKey, TValue> element = this.find(key);
        if(element == null){
            throw new IllegalArgumentException("There is no such key in the hashtable.");
        }
        return element.getValue();
    }

// Depricated
//   public boolean tryGetValue(TKey key, TValue value) {
//        throw new UnsupportedOperationException();
//    }

    public KeyValue<TKey, TValue> find(TKey key) {
        int keySlothNumber = this.findSlotNumber(key);

        if(this.slots[keySlothNumber] != null){
            for (KeyValue<TKey, TValue> element : slots[keySlothNumber]) {
                if (element.getKey().equals(key)){
                    return element;
                }
            }
        }

        return null;
    }

    public boolean containsKey(TKey key) {
        int keySlotNumber = this.findSlotNumber(key);

        if(this.slots[keySlotNumber] == null){
            return false;
        }
        return true;
    }

    public boolean remove(TKey key) {

        int slotNumber = this.findSlotNumber(key);
        LinkedList<KeyValue<TKey, TValue>> elements = this.slots[slotNumber];

        if(elements != null){
            KeyValue<TKey, TValue> currentElement = elements.getFirst();


            for (KeyValue<TKey, TValue> element : elements) {
                if(element.getKey().equals(key)){
                    elements.remove(element);
                    this.count--;
                    return true;
                }
            }
        }

        return false;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Iterable<TKey> keys() {
        throw new UnsupportedOperationException();
    }

    public Iterable<TValue> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<KeyValue<TKey, TValue>> iterator() {
        return new HashSetIterator();
    }

    private class HashSetIterator implements Iterator {
        private int position = 0;

        public boolean hasNext() {
            if (position < slots.length)
                return true;
            else
                return false;
        }

        public LinkedList<KeyValue<TKey,TValue>> next() {
            if (this.hasNext())
                return slots[position++];
            else
                return null;
        }

        @Override
        public void remove() {

        }
    }
}
