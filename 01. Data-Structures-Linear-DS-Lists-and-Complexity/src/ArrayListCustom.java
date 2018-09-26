import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class ArrayListCustom<I extends Number> extends Object {
    private int count;
    private Object[] Indexer;
    private LinkedList<Object> stack;

    public ArrayListCustom() {
        this.Indexer = new Object[16];
        this.stack = new LinkedList<>();
    }

    private int getCapacity() {
        return this.Indexer.length;
    }

    public int getCount(){ return this.count; }

    public void add(Object o){
        if(this.count == this.getCapacity()){
            this.increaseArrayListSize();
        }

        this.stack.add(o);
        this.Indexer[this.count] = o;
        this.count++;
    }

    public Object removeAt(int index){
        if(this.count <= this.getCapacity() / 4){
            this.reduceArrayListSize();
        }

        if(index < 0 || index > this.getCapacity()){
            throw new ArrayIndexOutOfBoundsException();
        }

        this.Indexer[index] = "";
        return null;
    }

    public Object getElementAtIndex(int index){
        if(index < 0 || index > this.getCount()){
            throw new ArrayIndexOutOfBoundsException();
        }

        this.stack.get(index);
        return this.Indexer[index];
    }

    private void reduceArrayListSize(){
        Object[] newArray = new Object[this.Indexer.length / 2];

        for (int i = 0; i < this.Indexer.length; i++) {
            newArray[i] = this.Indexer[i];
        }

        this.Indexer = newArray;
    }

    private void increaseArrayListSize(){
        Object[] newArray = new Object[this.Indexer.length * 2];

        for (int i = 0; i < this.Indexer.length; i++) {
            newArray[i] = this.Indexer[i];
        }

        this.Indexer = newArray;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Indexer.length; i++) {
            if(this.Indexer[i] != null) sb.append(Indexer[i] + " ");
        }

        return sb.toString().trim();
    }
}
