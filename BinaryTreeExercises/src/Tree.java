import java.util.ArrayList;
import java.util.List;

public class Tree<T> {

    public T value;
    public List<Tree<T>> children;
    public Tree<T> parent;

    public Tree(T value, Tree<T>... children) {
        this.value = value;
        this.children = new ArrayList<>();

        for (Tree<T> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }
}
