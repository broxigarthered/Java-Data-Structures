
import com.sun.tools.corba.se.idl.InterfaceGen;

import java.util.*;
import java.lang.String;

public class ImpelemntTreeNodeDFSAndBFS {

    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<Integer>(7,
                new Tree<Integer>(19,
                        new Tree<Integer>(1),
                        new Tree<Integer>(12),
                        new Tree<Integer>(31)),
                new Tree<Integer>(21),
                new Tree<Integer>(14,
                        new Tree<Integer>(23),
                        new Tree<Integer>(6))
                );

        // rekursiven metod, koito polu4ava durvo i broi na intervalite
        // vzima value-to, printira go, na dolniq red printira decata mu s dva intervala nadqsno
        // podavame na rekursiqta purvoto dete

        //PrintTree(tree, 0);
        //DFS(tree);

        //Deque<Tree<Integer>> firstLongestPath = DFSWithStack(tree, new ArrayDeque<Tree<Integer>>());
        /*while (!firstLongestPath.isEmpty()){
            System.out.println(firstLongestPath.pop().value);
        }
        */

        DFSPrinting(tree, new ArrayDeque<>());

        //BFS
        BFS(tree, new ArrayDeque<>());
        BFSWithReturn(tree).stream().forEach(e -> System.out.println(e));
    }



    public static void BFS(Tree<Integer> node, ArrayDeque<Tree<Integer>> queue){
        queue.add(node);

        while (!queue.isEmpty()){
            Tree<Integer> currentNode = queue.pop();
            System.out.println(currentNode.value);

            for (Tree<Integer> child : currentNode.children) {
                queue.add(child);
            }
        }
    }

    public static List<Integer> BFSWithReturn(Tree<Integer> tree){
        List<Integer> result = new ArrayList<>();
        Deque<Tree<Integer>> queue = new ArrayDeque<>();

        queue.add(tree);
        while (!queue.isEmpty()){
           Tree<Integer> currentElement = queue.pop();
           result.add(currentElement.value);

            for (Tree<Integer> node : currentElement.children) {
                queue.add(node);
            }
        }

        return result;
    }

    private static void DFS (Tree<Integer> node){
        for (Tree<Integer> child : node.children) {
            DFS(child);
        }

        System.out.println(node.value);
    }

    private static Deque<Tree<Integer>> DFSWithStack(Tree<Integer> node, ArrayDeque<Tree<Integer>> stack){
        for (Tree<Integer> child : node.children) {
            stack.push(child);
            DFSWithStack(child, stack);
        }

        return stack;
    }

    private static void DFSPrinting(Tree<Integer> node, Deque<Integer> stack){

        stack.push(node.value);

        for (Tree<Integer> child : node.children) {
            DFSPrinting(child, stack);
        }

        System.out.println(stack.pop());
    }

    private static void PrintTree(Tree<Integer> tree, int intervals){
        intervals+=1;
        String result = String.format("%"+intervals+"d", tree.value);
        System.out.println(result);


        for (Object o : tree.children) PrintTree((Tree<Integer>) o, intervals + 2);
    }

    private static class Tree<E>{
        public E value;
        public List<Tree<E>> children;

        public Tree(E value, Object... children) {
            this.value = value;
            this.children = new ArrayList<Tree<E>>();

            for (Object child : children) {
                this.children.add((Tree<E>) child);
            }
        }

        public void Print(int indent){
            String result = String.format("%"+indent+"d", this.value);
            System.out.println(result);
            for (Tree<E> child : this.children) {
                child.Print(5);
            }
        }

    }
}
