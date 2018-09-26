import javax.naming.OperationNotSupportedException;
import java.awt.event.TextEvent;
import java.util.Arrays;

public class LinkedStack<E>{

//    private Node<T> firstNode;
//    public int Count { get; private set; }
//    public void Push(T element) { … }
//    public T Pop() { … }
//    public T[] ToArray() { … }
//    private class Node<T>
//    {
//        private T value;
//        public Node<T> NextNode { get; set; }
//        public Node(T value, Node<T> nextNode = null) { … }
//    }

    private Node<E> firstNode;
    private int count;

    public int count() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    public void push (E element){
        //The Push(element) operation should create a new Node<T> and put it as firstNode, followed by the old value of the firstNode, e.g. this.firstNode = new Node<T>(element, this.firstNode).
        Node<E> newElement = new Node<>(element);
        newElement.nextNode = this.firstNode;
        this.firstNode = newElement;
        this.count++;
    }

    public E pop() throws OperationNotSupportedException {
        if(this.count == 0){
            throw new OperationNotSupportedException("The stack is empty.");
        }

        E elementToRemove = this.firstNode.value;
        this.firstNode = this.firstNode.nextNode;
        this.count--;
        return elementToRemove;
    }

    public E[] toArray(){
        E[] arrayToReturn = (E[]) new Object[this.count];
        int counter = 0;

        Node<E> currentTopNode = this.firstNode;
        while(currentTopNode != null){
            arrayToReturn[counter] = currentTopNode.value;
            currentTopNode = currentTopNode.nextNode;
            counter++;
        }

        return arrayToReturn;
    }

    private class Node<E>{
        E value;
        Node<E> nextNode;

        private Node(E value){
            this.value = value;
            this.nextNode = null;
        }
    }

    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(5);
        stack.push(11);
        stack.push(9);

        try {
            int element = stack.pop();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }



        Arrays.stream(stack.toArray()).forEach(e -> System.out.println(e));
    }
}
