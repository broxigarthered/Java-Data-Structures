import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> {

    //Parent(i) = (i - 1) / 2
    //Left(i) = 2 * i + 1;
    //Right(i) = 2 * i + 2

    private List<T> heap;

    public BinaryHeap() {
        this.heap = new ArrayList<>();
    }

    public int size() {
        return this.heap.size();
    }

    public void insert(T element) {

        this.heap.add(element);
        this.heapifyUp(this.heap.size()-1, element);
    }

    //In a max heap, the max element should always stay at index 0. Peek should return that element, without removing it
    public T peek() {
        return this.heap.get(0);
    }

    public T pull() throws IllegalArgumentException {
        if(this.size() == 0) {
            throw new IllegalArgumentException();
        }

        T item = this.heap.get(0);
        this.swap(0, this.size()-1);
        this.heap.remove(this.heap.size()-1);
        this.heapifyDown(0);

        return item;
    }

    private void heapifyDown(int current) {
        while(current < this.heap.size() / 2){
            int child = 2* current + 1;
            if(hasRight(child) && isLess(child, child + 1)){
                child = child + 1;
            }

            if(!isLess(current, child)) break;
            this.swap(current, child);
            current = child;
        }
    }

    private boolean isLess(int child, int second) {
        return this.heap.get(child).compareTo(this.heap.get(second)) < 0;
    }

    private boolean hasRight(int child) {
        return 2 * child + 2 <= this.size();
    }

    private void swap(int firstIndex, int secondIndex){
        T root = this.heap.get(firstIndex);
        T lastChild = this.heap.get(secondIndex);
        this.heap.set(firstIndex, lastChild);
        this.heap.set(secondIndex, root);
    }

    private void heapifyUp(int currentElementIndex, T element){
        int parentIndex = getParentIndex(currentElementIndex);
        T parentValue = this.heap.get(parentIndex);

        while (parentIndex >= 0 && parentValue.compareTo(element) < 0){

            // swap
            this.heap.set(parentIndex, element);
            this.heap.set(currentElementIndex, parentValue);

            currentElementIndex = parentIndex;
            parentIndex = getParentIndex(currentElementIndex);
            parentValue = this.heap.get(parentIndex);
        }
    }

    private int getParentIndex(int currentElementIndex){
        return (currentElementIndex-1)/2;
    }
}
