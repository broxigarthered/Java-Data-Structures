import java.util.ArrayList;
import java.util.Scanner;

public class LongestSubsequence03 {
    private static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayListCustom<Integer> list = new ArrayListCustom<>();


        String[] input = console.nextLine().split(" ");
        for (String s : input) {
            list.add(Integer.valueOf(s));
        }

        ArrayListCustom<Integer> result = longestSubsequence(list);
        System.out.println(result);
    }

    public static ArrayListCustom<Integer> longestSubsequence(ArrayListCustom<Integer> subSequence){
        ArrayListCustom<Integer> resultList = new ArrayListCustom<Integer>();
        int bestCounter = 0;
        int counter = 0;
        int number = 0;
        int bestNumber = 0;

        for (int i = 0; i < subSequence.getCount(); i++) {
             int currentElement = (int) subSequence.getElementAtIndex(i);

             if (i + 1 <= subSequence.getCount()-1){
                 if((int)subSequence.getElementAtIndex(i+1) == currentElement){
                     for (int j = i; j < subSequence.getCount(); j++) {

                         if(subSequence.getElementAtIndex(j) != subSequence.getElementAtIndex(i)) break;

                        counter++;
                        number = (int)subSequence.getElementAtIndex(j);
                     }
                 }
             }

             if(counter > bestCounter){
                 bestCounter = counter;
                 bestNumber = number;
             }

             number = 0;
             counter = 0;
        }

        if(bestCounter == 0){
            bestCounter = 1;
            bestNumber = (int)subSequence.getElementAtIndex(0);
        }

        for (int i = 0; i < bestCounter; i++) {
             resultList.add(bestNumber);

        }

        return resultList;
    }
}
