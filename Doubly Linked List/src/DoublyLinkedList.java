import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class DoublyLinkedList<E> implements Iterable<E> {

    private ListNode<E> head;
    private ListNode<E> tail;

    private int size;

    public DoublyLinkedList() {
        this.head = new ListNode<>(null);
        this.tail = new ListNode<>(null);
    }

    public int size() {
        return this.size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public void addFirst(E element) {
        if(this.size == 0){
            this.head = this.tail = new ListNode<E>(element);
        }
        else{
            // slagame glawata na purvoto mqsto v lista, kato izbutvame ostanalite prosto
            ListNode<E> newHead = new ListNode<>(element);
            newHead.nextNode = this.head;
            this.head.prevNode = newHead;
            this.head = newHead;
        }

        this.tail.nextNode = null;
        this.size++;
    }

    public void addLast(E element) {

        if(this.size == 0){
            this.head = this.tail = new ListNode<E>(element);
        }
        else{
            ListNode<E> newTail = new ListNode<>(element);
            newTail.prevNode = this.tail;
            this.tail.nextNode = newTail;
            this.tail = newTail;
        }

        this.size++;
    }

    public E removeFirst() {

//        Next, let's implement the method RemoveFirst()  T. It should remove the first element from the list and move its head to point to the second element. The removed element should be returned as a result from the method. In case of empty list, the method should throw an exception. We have to consider the following three cases:
//•	Empty list  throw and exception. ++
//•	Single element in the list  make the list empty (head == tail == null).
//•	Multiple elements in the list  remove the first element and redirect the head to point to the second element (head = head.NextNode).

        if(this.size == 0){
            throw new IllegalArgumentException("List is emtpy");
        }
        E valueToReturn = null;
        if(this.size == 1){
            valueToReturn = this.head.value;
            this.head = this.tail = null;
        }
        if(this.size > 1){
            valueToReturn = this.head.value;
            this.head = this.head.nextNode;
        }

        this.size--;

        return valueToReturn;
    }

    public E removeLast() {
        if(this.size == 0){
            throw new IllegalArgumentException("List is empty");
        }

        E valueToReturn = null;
        if(this.size == 1){
            valueToReturn = this.tail.value;
            this.head = this.tail = null;
        }
        if(this.size > 1){
            valueToReturn = this.tail.value;
            this.tail = this.tail.prevNode;
            this.tail.nextNode = null;
        }

        this.size--;
        return valueToReturn;
    }

    public E[] toArray() {
        E[] arrayToReturn = (E[])new Object[this.size];
        int counter = 0;
        ListNode<E> currentNode = this.head;

        if(this.size == 0){
            return arrayToReturn;
        }

        while (currentNode != null){
            arrayToReturn[counter] = currentNode.value;
            counter++;
            currentNode = currentNode.nextNode;
        }

        return arrayToReturn;
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            ListNode<E> currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public E next() {
                E result = currentNode.value;
                currentNode = currentNode.nextNode;
                return result;
            }
        };

        return it;
    }

    private class ListNode<E>{
        private E value;
        private ListNode<E> nextNode;
        private ListNode<E> prevNode;

        public ListNode(E value) {
            this.value = value;
        }
    }
}


