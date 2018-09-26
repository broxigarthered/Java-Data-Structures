public class ArrayBasedStack<E> {
    /*
    private T[] elements; -
    public int Count { get; private set; } -
    private const int InitialCapacity = 16; -
    public ArrayStack(int capacity = InitialCapacity) { … } -
    public void Push(T element) { … }
    public T Pop() { … }
    public T[] ToArray() { … }
    private void Grow() { … }
     */

    private E[] elements;
    private int count;
    private int size;
    private final int InitialCapacity = 16;

    public ArrayBasedStack(int initialCapacity){
        this.setSize(initialCapacity);
        this.elements = (E[])new Object[this.size];
    }

    public int count() {
        return count;
    }

    private void setSize(int size) {
        this.size = size > 0 ? size : InitialCapacity;
    }

    private void grow(){
        this.setSize(this.size * 2);
        E[] newElements = (E[]) new Object[this.size];
        for (int i = 0; i < this.elements.length; i++) {
            newElements[i] = this.elements[i];
        }

        this.elements = newElements;
    }

    public void push(E element){
        if(this.elements.length == this.count()){
            this.grow();
        }

        this.elements[count] = element;
        this.count++;
    }

    public E pop(){
        E elementToReturn = this.elements[this.count-1];
        this.elements[this.count] = null;
        this.count--;
        return elementToReturn;
    }

    public E[] toArray(){
        E[] arrayToReturn = (E[]) new Object[this.count];

        for (int i = 0; i < arrayToReturn.length; i++) {
            arrayToReturn[i] = this.elements[i];
        }

        return arrayToReturn;
    }

    public static void main(String[] args) {
        ArrayBasedStack<Integer> stack = new ArrayBasedStack<>(0);
        stack.push(11);
        stack.push(5);
        stack.push(9);

        while (stack.count() > 0){
            System.out.println(stack.pop());
        }
    }

}
