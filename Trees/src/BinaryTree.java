import java.util.function.Consumer;

public class BinaryTree<T> {

    public T value;
    public BinaryTree<T> leftNode;
    public BinaryTree<T> rightNode;

    public BinaryTree(T value) {
        this.value = value;
    }

    public BinaryTree(T value, BinaryTree<T> child) {
        this.value = value;
        this.leftNode = child;
    }

    public BinaryTree(T value, BinaryTree<T> leftChild, BinaryTree<T> rightChild) {
        this.value = value;
        this.leftNode = leftChild;
        this.rightNode = rightChild;
    }

    // append output to builder
    public String printIndentedPreOrder(int indent, StringBuilder builder) {
        if(indent == 0){
            builder.append(this.value +"\n");
        }
        else{
            String spaces = String.format("%1$"+indent+"s", "");
            builder.append(spaces + this.value + "\n");
        }

        if(this.leftNode != null){
            leftNode.printIndentedPreOrder(indent+2, builder);
        }

        if(this.rightNode != null){
            rightNode.printIndentedPreOrder(indent+2,builder);
        }

        return builder.toString();
    }

    public void eachInOrder(Consumer<T> consumer) {
        if(this.leftNode != null){
            this.leftNode.eachInOrder(consumer);
        }

        consumer.accept(this.value);

        if(this.rightNode != null){
            this.rightNode.eachInOrder(consumer);
        }
    }

    public void eachPostOrder(Consumer<T> consumer) {
        if(this.leftNode != null){
            this.leftNode.eachPostOrder(consumer);
        }
        if(this.rightNode != null){
            this.rightNode.eachPostOrder(consumer);
        }
        consumer.accept(this.value);
    }
}
