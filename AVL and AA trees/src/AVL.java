import com.sun.xml.internal.messaging.saaj.soap.impl.HeaderImpl;

import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        // adding elements, using recursion
        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            node.right = this.insert(node.right, item);
        }

        updateHeight(node);
        node = this.balance(node);

        return node;
    }

    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        // finding element, using recursion again
        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            return search(node.left, item);
        } else if (cmp > 0) {
            return search(node.right, item);
        }

        return node;
    }


    // Getting and fixing the height of the nodes
    private int height(Node<T> node){
        if(node == null) {
            return 0;
        }

        return node.height;
    }

    // Method for updating nodes height, gets called every time we insert a new node, using recursion, so it updates
    // the whole left or right side.
    private void updateHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }


    // Balancing the tree
    // For additional understanding of the rotations, refer to the presentation (AVL trees & AA trees)
    private Node<T> rotateLeft(Node<T> node){
        Node temp = node.right;
        node.right = node.right.left;
        temp.left = node;

        this.updateHeight(node);
        return temp;
    }

    private Node<T> rotateRight(Node<T> node){
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;

        this.updateHeight(node);
        return temp;
    }

    private Node<T> balance(Node<T> node){
        int balance = height(node.left) - height(node.right);

        if(balance < -1) // right child is heavy
        {
            balance = height(node.right.left) - height(node.right.right);
            if(balance <= 0){  // single left
                return this.rotateLeft(node);
            } else { // double left
                node.right = this.rotateRight(node.right);
                return this.rotateLeft(node);
            }

        }
        else if (balance > 1) // left child is heavy
        {
            balance = height(node.left.left) - height(node.left.right );
            if(balance >= 0) {
                return this.rotateRight(node);
            }
            else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        return node;
    }
}
