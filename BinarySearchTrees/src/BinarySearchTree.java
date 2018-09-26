import sun.tools.tree.UnsignedShiftRightExpression;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class BinarySearchTree<T extends Comparable<T>> {
    private Node root;
    private int nodesCount;

    public BinarySearchTree() {
    }

    private BinarySearchTree(Node root) {
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

    private int count(Node node){
        if (node == null) {
            return 0;
        }

        return  node.childrenCount;
    }

    public void insert(T value) {
        this.nodesCount++;

        if (this.root == null) {
            this.root = new Node(value);
            return;
        }

        Node parent = null;
        Node current = this.root;

        //namirame posledniq vkaran element koito se okazva poslednqi parent prosto
        // vurtime while cikul dokato ne grumne, 4e nqma otlqvo ili otdqsno
        while (current != null) {
            parent = current;

            parent.childrenCount+=1;

            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return;
            }
        }

        // kazvame ot koq strana da go setnem, otlqvo ili otdqsno
        Node newNode = new Node(value);

        //newNode.childrenCount = 1 + newNode.childrenCount;
        if (value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
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

    public BinarySearchTree<T> search(T item) {
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

        return new BinarySearchTree<>(current);
    }

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

        // kato stane null, ozna4ava 4e sme namerili nai-maalkiq element
        while (min.left != null) {
            parent = min;
            parent.childrenCount--;
            min = min.left;
        }

        // ako se okaje 4e parenta e nai-malkiq element
        if (parent == null) {
            // roota stava desniq node
            this.root = this.root.right;
        } else {
            // ina4e ot lqvo na parenta stava na min noda dqsnata strana
            parent.left = min.right;
        }

        // broikata na nodovete
        this.nodesCount--;
    }

    public void deleteMax() {
//        if(this.root == null){
//            throw new IllegalArgumentException("Tree is empty!");
//        }
//
//        Node max = this.root;
//        Node parent = null;
//
//        while (max.right != null){
//            parent = max;
//            max = max.right;
//            System.out.println(max.right);
//        }
////
////        if(parent.left != null || parent.right != null){
////
////        }
//
//        parent.right = null;
//        if(max.left != null){
//            parent.right = max.left;
//        } else if (max.right != null){
//            parent.right = max.right;
//        }
//
//        max = null;
//        this.nodesCount--;

        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty!");
        }

        Node max = this.root;
        Node parent = null;

        while (max.right != null){
            parent = max;
            parent.childrenCount--;
            max = parent.right;
        }

        // ako parent-a e null, ozna4ava, 4e nqma desni deca, i sa samo dva node-a v durvoto, zatova trqbva da biem
        // 6uta na root-a
        if(parent == null) {
            this.root = this.root.left;
        } else{
            parent.right = max.left;
        }

        this.nodesCount--;
    }

    public T ceil(T element) {
        throw new UnsupportedOperationException();
    }

    public T floor(T element) {
        throw new UnsupportedOperationException();
    }

    public void delete(T key) {
        // 1st case: if the left side is empty, take the roots right child as new root
        // 2nd case: when left side is not empty, but the first left child of the root doesn't have right child - the
        // root = root.left
        // 3rd case: if the left side is not empty, and the left child (root.left) has right child, get the right
        // most node -> while(current.right != null)




        throw new UnsupportedOperationException();
    }

    public int rank(T item) {
        if(this.root == null){
            return 0;
        }

        return this.inOrderTraversalRank(this.root, item);
    }

    public int inOrderTraversalRank(Node currentNode, T element){

//        if(currentNode == null){
//            return 1;
//        }

        int rank = 0;

        if(element.compareTo(currentNode.value) > 0){
            rank++;
        }

        if(currentNode.left != null){
            rank += inOrderTraversalRank(currentNode.left, element);
        }

        if(currentNode.right != null && element.compareTo(currentNode.value) > 0){
            rank += inOrderTraversalRank(currentNode.right, element);
        }

        return rank;
    }

    public void getChildrensCountWithInOrderTraversal(Node currentNode){
        if(currentNode == null) {
            return;
        }

        if(currentNode.left != null){
            getChildrensCountWithInOrderTraversal(currentNode.left);
        }

        System.out.println(currentNode.childrenCount);
        if(currentNode.right != null){
            getChildrensCountWithInOrderTraversal(currentNode.right);
        }
    }

    public T select(int n) {

        return this.inOrderTraversalSelect(this.root, n, new SelectCounter()) ;
    }

    public T inOrderTraversalSelect(Node currentNode, int selectedNumber,SelectCounter kur){
        if(currentNode.left != null){
            kur.value += 1;
            inOrderTraversalSelect(currentNode.left, selectedNumber, kur);
        }

        if(currentNode.right != null && selectedNumber != kur.value){
            kur.value += 1;
            inOrderTraversalSelect(currentNode.right, selectedNumber, kur);
        } else {
            kur.nodeValue = currentNode.value;
        }

        return kur.nodeValue;
    }

    private class SelectCounter{
        private int value;
        private T nodeValue;

        SelectCounter(){
            this.value = 0;
        }

        public int getValue(){return this.value;}
    }

    class Node {
        private T value;
        private Node left;
        private Node right;

        private int childrenCount;
        private int rank;

        public Node(T value) {
            this.value = value;
            this.childrenCount = 0;
            this.rank = 0;
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

        @Override
        public String toString() {
            return this.value + "";
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(1);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(8);
        tree.insert(9);
        tree.insert(10);
        tree.insert(37);
        tree.insert(39);
        tree.insert(45);


        System.out.println(tree.select(4));
        //tree.getChildrensCountWithInOrderTraversal(tree.root);
       // System.out.println(tree.inOrderTraversalRank(tree.root, 20));
    }
}




