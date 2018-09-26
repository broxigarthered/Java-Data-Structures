import java.util.*;

public class SequenceNM {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int[] numbers = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(i -> Integer.parseInt(i)).toArray();

        int n = numbers[0];
        int m = numbers[1];

        Queue<Item> queue = new ArrayDeque<>();
        queue.add(new Item(n,null));

        while (!queue.isEmpty()){
            Item currentNumber = queue.remove();
            if(currentNumber.value < m){
                queue.add(new Item(currentNumber.value + 1, currentNumber));
                queue.add(new Item(currentNumber.value + 2, currentNumber));
                queue.add(new Item(currentNumber.value*2, currentNumber));
            }

            if(currentNumber.value == m){
                Deque<Integer> items = new ArrayDeque<>();
                while (currentNumber != null){
                    items.push(currentNumber.value);
                    currentNumber = currentNumber.previousItem;
                }

                while (items.size() > 0){
                    System.out.print(items.pop() + " ");
                }

                break;
            }

        }
    }

    private static class Item{
        private int value;
        private Item previousItem;

        public Item(int value, Item previousItem) {
            this.value = value;
            this.previousItem = previousItem;
        }
    }
}
