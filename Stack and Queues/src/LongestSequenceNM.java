import java.util.*;

public class LongestSequenceNM {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int[] numbers = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(i -> Integer.parseInt(i)).toArray();

        int n = numbers[0];
        int m = numbers[1];

        Deque<Item> stack = new ArrayDeque<>();
        stack.push(new Item(n,null));

        while (!stack.isEmpty()) {
            Item currentNode = stack.pop();

            if(currentNode.value + 1 <= m){
                stack.push(new Item(currentNode.value+1, currentNode));
            }
            if(currentNode.value + 2 <= m){
                stack.push(new Item(currentNode.value+2, currentNode));
            }
            if(currentNode.value * 2 <= m){
                stack.push(new Item(currentNode.value*2, currentNode));
            }

            if(stack.size() == 1){
                break;
            }
        }

        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

    private static class Item{
        private int value;
        private Item prevNode;
        private boolean flag;

        public Item(int value, Item prevNode) {
            this.value = value;
            this.prevNode = prevNode;
            this.flag = true;
        }
    }

}
