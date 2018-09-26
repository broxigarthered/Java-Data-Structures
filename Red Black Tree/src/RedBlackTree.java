import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class RedBlackTree<T extends Comparable<T>> {

    private final boolean Red = true;
    private final boolean Black = false;

    private Node root;
    private int nodesCount;


    public RedBlackTree() {
    }

    private RedBlackTree(Node root) {
        this.preOrderCopy(root);
    }

    private void preOrderCopy(Node node) {
        if (node == null) {
            return;
        }

        this.insert(node.value);
        this.preOrderCopy(node.left);
        this.preOrderCopy(node.right);
    }

    public Node getRoot() {
        return this.root;
    }

    public int getNodesCount() {
        return this.nodesCount;
    }

    private Node insert(T element, Node node) {

        // Recursive calls
        if (node == null) node = new Node(element, Red);
        else if(element.compareTo(node.value) < 0){
            node.setLeft(this.insert(element, node.getLeft()));
        } else if (element.compareTo(node.value) > 0){
            node.setRight(this.insert(element, node.getRight()));
        }

        if (this.isRed(node.getRight()) && !this.isRed(node.getLeft()))
            node = this.rotateLeft(node);
        if (this.isRed(node.getLeft()) && this.isRed(node.getLeft().getLeft()))
            node = this.rotateRight(node);
        if (this.isRed(node.getLeft()) && this.isRed(node.getRight()))
            this.flipColors(node);

        // Increase count
        node.childrenCount = 1 + this.getCount(node.getLeft()) + this.getCount(node.getRight());

        return node;
    }

    public void insert(T value) {
        this.nodesCount++;
        this.root = this.insert(value, this.root);
        this.root.color = Black;
    }

    private int getCount(Node node){
        if (node != null){
            return node.childrenCount;
        }
        return 0;
    }


    public boolean contains(T value) {
        Node current = this.root;
        while (current != null) {
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        return current != null;
    }

    public RedBlackTree<T> search(T item) {
        Node current = this.root;
        while (current != null) {
            if (item.compareTo(current.value) < 0) {
                current = current.left;
            } else if (item.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        return new RedBlackTree<>(current);
    }

    // Left leaning Red-Black tree methods

    private boolean isRed(Node node){
        if(node == null) return false;
        return node.getColor() == this.Red;
    }

    private Node flipColors(Node node){
        node.getLeft().setColor(Black);
        node.getRight().setColor(Black);
        node.setColor(Red);

        return node;
    }

    private Node rotateRight(Node node) {
        Node temp = node.getLeft();
        node.setLeft(temp.getRight());
        temp.setRight(node);
        temp.setColor(node.getColor());
        node.setColor(Red);
        temp.childrenCount = node.childrenCount;
        node.childrenCount = 1 + this.getCount(node.getLeft())+ this.getCount(node.getRight());

        return temp;
    }

    private Node rotateLeft(Node node){
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;

        node.color = Red;
        temp.color = node.color;
        temp.childrenCount = node.childrenCount;
        node.childrenCount = 1 + this.getCount(node.getLeft()) + this.getCount(node.getRight());

        return temp;
    }

    // Additional Tree Methods

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node node, Consumer<T> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, consumer);
        consumer.accept(node.value);
        this.eachInOrder(node.right, consumer);
    }

    public Iterable<T> range(T from, T to) {
        Deque<T> queue = new LinkedList<>();
        this.range(this.root, queue, from, to);
        return queue;
    }

    private void range(Node node, Deque<T> queue, T startRange, T endRange) {
        if (node == null) {
            return;
        }

        int compareStart = startRange.compareTo(node.value);
        int compareEnd = endRange.compareTo(node.value);
        if (compareStart < 0) {
            this.range(node.left, queue, startRange, endRange);
        }
        if (compareStart <= 0 && compareEnd >= 0) {
            queue.addLast(node.value);
        }
        if (compareEnd > 0) {
            this.range(node.right, queue, startRange, endRange);
        }
    }

    private T minValue(Node root) {
        T minv = root.value;
        while (root.left != null) {
            minv = root.left.value;
            root = root.left;
        }

        return minv;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        Node min = this.root;
        Node parent = null;

        while (min.left != null) {
            parent = min;
            parent.childrenCount--;
            min = min.left;
        }

        if (parent == null) {
            this.root = this.root.right;
        } else {
            parent.left = min.right;
        }

        this.nodesCount--;
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        Node max = this.root;
        Node parent = null;

        while (max.right != null) {
            parent = max;
            max = max.right;
        }

        if (parent == null) {
            this.root = this.root.left;
        } else {
            parent.right = max.left;
        }
    }

    public T ceil(T element) {
        return this.select(this.rank(element) + 1);
    }

    public T floor(T element) {
        return this.select(this.rank(element) - 1);
    }

    public void delete(T key) {
        this.root = deleteRecursive(this.root, key);
    }

    private Node deleteRecursive(Node root, T key) {
        if (root == null) {
            return root;
        }

        if (key.compareTo(root.value) < 0) {
            root.left = deleteRecursive(root.left, key);
        }
        else if (key.compareTo(root.value) > 0) {
            root.right = deleteRecursive(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.value = minValue(root.right);

            root.right = deleteRecursive(root.right, root.value);
        }

        return root;
    }

    public int rank(T element) {
        return this.rank(element, this.root);
    }

    private int rank(T element, Node node) {
        if (node == null) {
            return 0;
        }

        int compare = element.compareTo(node.value);

        if (compare < 0) {
            return this.rank(element, node.left);
        }

        if (compare > 0) {
            return 1 + this.size(node.left) + this.rank(element, node.right);
        }

        return this.size(node.left);
    }

    public T select(int rank) {
        Node node = this.select(rank, this.root);
        if (node == null) {
            throw new IllegalArgumentException("ERROR");
        }

        return node.value;
    }

    private Node select(int rank, Node node) {
        if (node == null) {
            return null;
        }

        int leftCount = this.size(node.left);
        if (leftCount == rank) {
            return node;
        }

        if (leftCount > rank) {
            return this.select(rank, node.left);
        } else {
            return this.select(rank - (leftCount + 1), node.right);
        }
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.childrenCount;
    }

    class Node {
        private T value;
        private Node left;
        private Node right;
        private boolean color;

        public int childrenCount;

        public Node(T value, boolean color) {
            this.value = value;
            this.setColor(color);
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public boolean getColor(){
            return this.color;
        }

        public void setColor(boolean color){this.color = color;}

        @Override
        public String toString() {
            return this.value + "";
        }
    }
}

//    public T ceil(T element) {
//        return ceil(this.root, element);
//    }
//
//    private T ceil(Node node, T input) {
//        if (node == null) {
//            return null;
//        }
//
//        if (node.value == input) {
//            return node.value;
//        }
//
//        if (node.value.compareTo(input) < 0) {
//            return ceil(node.right, input);
//        }
//
//        T ceil = ceil(node.left, input);
//        return (ceil != null) ? ceil : node.value;
//    }
//
//    public T floor(T element) {
//        return floor(this.root, element);
//    }
//
//    private T floor(Node node, T input) {
//        if (node == null) {
//            return null;
//        }
//
//        if (node.value == input) {
//            return node.value;
//        }
//
//        if (node.value.compareTo(input) > 0) {
//            return floor(node.left, input);
//        }
//
//        T floor = floor(node.right, input);
//        return (floor != null) ? floor : node.value;
//    }


