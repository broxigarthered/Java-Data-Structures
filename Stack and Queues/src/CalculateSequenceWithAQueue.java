import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CalculateSequenceWithAQueue {
    /*

    Problem 1.
We are given the following sequence of numbers:
•	S1 = N
•	S2 = S1 + 1
•	S3 = 2*S1 + 1
•	S4 = S1 + 2
•	S5 = S2 + 1
•	S6 = 2*S2 + 1
•	S7 = S2 + 2
•	…

     */
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<Integer>();
        Queue<Integer> resultQueue = new LinkedList<Integer>();

        int n = Integer.parseInt(scanner.nextLine());
        queue.add(n);
        resultQueue.add(n);

        while (resultQueue.size() <= 50){
            int currentNumber = queue.poll();
            int s2 = currentNumber+1;
            int s3 = (currentNumber * 2) + 1;
            int s4 = currentNumber + 2;
            queue.add(s2);
            queue.add(s3);
            queue.add(s4);
            resultQueue.add(s2);
            resultQueue.add(s3);
            resultQueue.add(s4);
        }

        System.out.println(resultQueue.toString());
    }
}
