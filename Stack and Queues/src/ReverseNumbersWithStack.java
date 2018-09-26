import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;


/*
Write a program that reads N integers from the console and reverses them using a stack. Use the Stack<int> class from .NET Framework. Just put the input numbers in the stack and pop them. Examples:
Input
Output
1 2 3 4 5
5 4 3 2 1
1
1
(empty)
(empty)
1 -2
-2 1

 */
public class ReverseNumbersWithStack {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] line = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i : line) {
            stack.push(i);
        }

        String result = "";
        while(stack.size() > 0){
            result += stack.pop() + " ";
        }

        System.out.println(result.trim());
    }
}
