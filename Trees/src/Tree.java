import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tree<T> {

    private T value;
    private List<Tree<T>> children;

    public Tree(T value, Tree<T>... children) {
        this.value = value;
        this.children = new ArrayList<>();

        for (Tree<T> child : children) {
            this.children.add(child);
        }
    }

    // append output to builder
    public String print(int indent, StringBuilder builder) {

        if(indent == 0){
           builder.append(this.value +"\n");
        }
        else{
            String spaces = String.format("%1$"+indent+"s", "");
            builder.append(spaces + this.value + "\n");
        }

        for (Tree<T> child : this.children) {
            child.print(indent+2, builder);
        }

        return builder.toString();
    }

    public void each(Consumer<T> consumer) {
        consumer.accept(this.value);

        for (Tree<T> child : this.children) {
            child.each(consumer);
        }
    }

    public Iterable<T> orderDFS() {
        ArrayList<T> resultList = new ArrayList<>();
        this.DFS(this, resultList);
        return resultList;
    }

    private void DFS(Tree<T> tree, List<T> list){
        for (Tree<T> child : tree.children) {
            this.DFS(child, list);
        }

        list.add(tree.value);
    }

    public Iterable<T> orderBFS() {
        ArrayList<T> resultList = new ArrayList<>();
        this.BFS(this, resultList);
        return resultList;
    }

    private void BFS(Tree<T> tree, ArrayList<T> list){
        ArrayDeque<Tree<T>> queue = new ArrayDeque<Tree<T>>();
        queue.add(tree);

        while (!queue.isEmpty()){
            Tree<T> currentElement = queue.pop();
            list.add(currentElement.value);
            for (Tree<T> child : currentElement.children) {
                queue.add(child);
            }
        }
    }

}