import java.util.ArrayList;
import java.util.List;

public class BinaryTreeTraversal {
    public static void main(String[] args) {

        BinaryTree<Integer> tree = new BinaryTree<Integer>(5,
                new BinaryTree<Integer>(2),
                new BinaryTree<Integer>(6));

        tree.PrintIndentedPreOrder(2);
    }

    private static class BinaryTree<E>{
        public E value;
        public BinaryTree<E> leftChild = null;
        public BinaryTree<E> rightChild = null;
        public List<BinaryTree<E>> children;

        public BinaryTree(E value, BinaryTree leftChild, BinaryTree rightChild) {
            this.value = (E) value;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public BinaryTree(E... value){
            this.leftChild = null;
            this.rightChild = null;
            this.value = (E)value;
        }

        public void PrintIndentedPreOrder(int indent){
            System.out.println(this.value);

            if(this.leftChild!= null){
                this.leftChild.PrintIndentedPreOrder(indent+2);
            }
            if(this.rightChild != null){
                this.rightChild.PrintIndentedPreOrder(indent+2);
            }

        }
    }
}
