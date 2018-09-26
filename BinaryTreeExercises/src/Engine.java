import com.sun.source.tree.BinaryTree;

import java.lang.reflect.Array;
import java.util.*;

public class Engine {
    static Scanner scanner = new Scanner(System.in);
    static LinkedHashMap<Integer, Tree<Integer>> mapOfNodes = new LinkedHashMap<>();

    public static void main(String[] args) {
        readTree();
        Tree<Integer> root = getRootNode();
        // 1.0 System.out.println(getRootNode().value);
       //2.0 System.out.println(printTreeWithIndent(0, getRootNode(), new StringBuilder()));
//       3.0 List<Integer> result = getLeafNodesDFS(root, new ArrayDeque<Tree<Integer>>(), new ArrayList<>());
//        System.out.println(result);

        // 4.0 printMiddleNodesWithBFS(root);

        //5.0 findDeepestLeftMostNodeWithDFS(root)
        //System.out.println(findDeepestLeftMostNodeWithDFS(root, new ArrayDeque<>()).value);
        //5.0 findDeepestLeftMostNodeWithDFS(root, new ArrayDeque<>());

        //6.0
        //longestPath(root, new ArrayDeque<>());

        //7.0 All paths with given sum
        int sum = Integer.parseInt(scanner.nextLine());
        // 7.0
//        System.out.printf("Paths of sum %d\n", sum);
//        findAllPathsWithGivenSum(root, sum, new ArrayDeque<Tree<Integer>>(), 0);

        // 8.0
        System.out.printf("Subtrees of sum %d:\n", sum);
        findAllSubTreesWithGivenSum(root, sum);

    }

    static Tree<Integer> GetTreeNodeByValue(int value){
        if(!mapOfNodes.containsKey(value)){
            mapOfNodes.put(value, new Tree<Integer>(value));
        }

        return mapOfNodes.get(value);
    }

    public static void addEdge(int parent, int child){
        Tree<Integer> parentNode = GetTreeNodeByValue(parent);
        Tree<Integer> childNode = GetTreeNodeByValue(child);

        parentNode.children.add(childNode);
        childNode.parent = parentNode;
    }

    static void readTree(){
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n-1; i++) {
            int[] line = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(p -> Integer.parseInt(p)).toArray();
            int key = line[0];
            int value = line[1];

            addEdge(key, value);
        }
    }

    static Tree<Integer> getRootNode(){
        for (Tree<Integer> tree : mapOfNodes.values()) {
            if(tree.parent == null){
                return tree;
            }
        }

        return null;
    }

    static String printTreeWithIndent(int indent, Tree<Integer> tree, StringBuilder builder){

        if(indent == 0){
            builder.append(tree.value +"\n");
        }
        else{
            String spaces = String.format("%1$"+indent+"s", "");
            builder.append(spaces + tree.value + "\n");
        }

        for (Tree<Integer> child : tree.children) {
            printTreeWithIndent(indent + 2, child, builder );
        }

        return builder.toString();
    }

    static List<Integer> getLeafNodesDFS(Tree<Integer> node, ArrayDeque<Tree<Integer>> queue, List<Integer> leafNodes){
        queue.push(node);

        for (Tree<Integer> o : node.children) {
            queue.push(o);
            if(o.children.size() == 0){
                leafNodes.add(o.value);
            }

            getLeafNodesDFS(o, queue, leafNodes);
        }

        leafNodes.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? 1 : -1;
            }
        });

        return leafNodes;
    }

    static void printMiddleNodesWithBFS(Tree<Integer> root){
        ArrayDeque<Tree<Integer>> queue = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();

        queue.add(root);

        while (!queue.isEmpty()){
            Tree<Integer> currentNode = queue.pop();

            if(currentNode.children.size() > 1 && currentNode.parent != null){
                result.add(currentNode.value);
            }

            for (Tree<Integer> child : currentNode.children) {
                queue.add(child);
            }
        }

        result.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? 1 : -1;
            }
        });

        System.out.println(result);
    }

    static void findDeepestLeftMostNodeWithDFS(Tree<Integer> root, ArrayDeque<Tree<Integer>> stack){

        if(root.children.size() == 0){
            System.out.println(root.value);
            System.exit(0);
        }
        else {
            stack.push(root);
            for (Tree<Integer> child : root.children) {
                findDeepestLeftMostNodeWithDFS(child, stack);
            }
        }

//        queue.add(root);
//        Tree<Integer> elementToReturn = null;
//
//        while (!queue.isEmpty()){
//            Tree<Integer> currentElement = queue.pop();
//            if(currentElement.children.size() == 0){
//                elementToReturn = currentElement;
//                break;
//            }
//
//            for (Tree<Integer> child : currentElement.children) {
//                queue.add(child);
//            }
//        }
    }

    static void longestPath(Tree<Integer> root, ArrayDeque<Tree<Integer>> stack){
        stack.push(root);
        for (Tree<Integer> child : root.children) {
            longestPath(child, stack);
        }

        StringBuilder builder = new StringBuilder();
        ArrayDeque resultStack = new ArrayDeque();

        stack.forEach(i -> resultStack.push(i.value));
        //queue.forEach(i -> builder.append(i + " "));

        while (!resultStack.isEmpty()) builder.append(resultStack.pop() + " ");

        System.out.println(builder.toString().trim());
        System.exit(0);
    }

    //DFS
    static void findAllPathsWithGivenSum (Tree<Integer> node, int sum, ArrayDeque<Tree<Integer>> stack, int currentSum){
        stack.push(node);
        currentSum+=node.value;
        for (Tree<Integer> child : node.children) {
            findAllPathsWithGivenSum(child, sum, stack, currentSum);
        }

        // stigne li dotuk, ozna4ava, 4e e udarilo dunoto
        if(sum == currentSum){
            ArrayDeque<Tree<Integer>> resultStack = new ArrayDeque<>();
            StringBuilder builder = new StringBuilder();

            stack.forEach(e -> resultStack.push(e));
            while (!resultStack.isEmpty()) builder.append(resultStack.pop().value + " ");
            System.out.println(builder.toString().trim());
        }

        stack.pop();
    }

    //BFS
    static void findAllSubTreesWithGivenSum(Tree<Integer> node, int sum){
        ArrayDeque<Tree<Integer>> queue = new ArrayDeque<>();

        queue.add(node);
        while (!queue.isEmpty()){
            int currentSum = 0;

            Tree<Integer> currentElement = queue.pop();
            currentSum+= currentElement.value;
            for (Tree<Integer> child : currentElement.children) {
                queue.add(child);
                currentSum += child.value;
            }

            if(currentSum == sum){
                StringBuilder builder = new StringBuilder();
                builder.append(currentElement.value + " ");
                currentElement.children.forEach(c -> builder.append(c.value + " "));
                System.out.println(builder.toString().trim());
            }
        }
    }
}

/*
9
7 19
7 21
7 14
19 1
19 12
19 31
14 23
14 6
43
 */