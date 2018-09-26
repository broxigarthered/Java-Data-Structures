import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DistanceInLab07 {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int size = Integer.parseInt(scanner.nextLine());
        String[][] matrix = new String [size][size];
        LinkedList<Cell> queue = new LinkedList<Cell>();

        int startRow = 0;
        int startCol = 0;

        for (int x = 0; x < size; x++) {
            char[] row = scanner.nextLine().toCharArray();

            for (int y = 0; y < size; y++) {
                if(row[y] == '*'){
                    startRow = x;
                    startCol = y;
                }  matrix[x][y] = String.valueOf(row[y]);
            }
        }

        Cell currentStartingCell = new Cell(startRow, startCol, 0);
        queue.push(currentStartingCell);

        while (queue.size() > 0)
        {
            Cell currentCell = queue.pollFirst();

            //enque left/right/Up/Down
            Cell leftCell = getLeftCell(currentCell, matrix);
            if(leftCell != null){
                matrix[leftCell.x][leftCell.y] = String.valueOf(leftCell.value);
                queue.push(leftCell);
            }

            // right
            Cell rightCell = getRightCel(currentCell, matrix);
            if(rightCell != null){
                matrix[rightCell.x][rightCell.y] = String.valueOf(rightCell.value);
                queue.push(rightCell);
            }


            //up
            Cell upCell = getUpCell(currentCell, matrix);
            if(upCell != null){
                matrix[upCell.x][upCell.y] = String.valueOf(upCell.value);
                queue.push(upCell);
            }

            //down
            Cell downCell = getDownCell(currentCell, matrix);
            if(downCell != null){
                matrix[downCell.x][downCell.y] = String.valueOf(downCell.value);
                queue.push(downCell);
            }

        }


        for (String[] ints : matrix) {
            for (String sec : ints) {
                if(sec == "0"){
                    System.out.print('u');
                }else System.out.print(sec);
            }
            System.out.println();
        }



    }

    public static Cell getLeftCell(Cell currentCell, String[][]matrix){
        if (currentCell.y - 1 >= 0){
            int cellYPosition = currentCell.y-1;
            if ((!matrix[currentCell.x][cellYPosition].equals("x") &&
                    !matrix[currentCell.x][cellYPosition].equals("*")) &&
                    Integer.parseInt(matrix[currentCell.x][cellYPosition]) < 1){
                Cell cell = new Cell(currentCell.x, cellYPosition, currentCell.value + 1);
                return cell;
            }
        }
        return null;
    }

    public static Cell getRightCel(Cell currentCell, String[][]matrix){
        if (currentCell.y + 1 < matrix.length){
            int cellYPosition = currentCell.y + 1;
            if ((!matrix[currentCell.x][cellYPosition].equals("x") &&
                    !matrix[currentCell.x][cellYPosition].equals("*")) &&
                    Integer.parseInt(matrix[currentCell.x][cellYPosition]) < 1){
                Cell cell = new Cell(currentCell.x, cellYPosition, currentCell.value + 1);
                return cell;
            }
        }
        return null;
    }

    public static Cell getUpCell(Cell currentCell, String[][]matrix){
        if (currentCell.x - 1 >= 0){
            int cellXPosition = currentCell.x-1;
            if ((!matrix[cellXPosition][currentCell.y].equals("x") &&
                    !matrix[cellXPosition][currentCell.y].equals("*")) &&
            Integer.parseInt(matrix[cellXPosition][currentCell.y]) < 1){
                Cell cell = new Cell(cellXPosition, currentCell.y ,  (currentCell.value + 1));
                return cell;
            }
        }
        return null;
    }

    public static Cell getDownCell(Cell currentCell, String[][]matrix){
        if (currentCell.x + 1 < matrix.length){
            int cellXPosition = currentCell.x+1;
            if ((!matrix[cellXPosition][currentCell.y].equals("x") &&
                    !matrix[cellXPosition][currentCell.y].equals("*")) &&
                    Integer.parseInt(matrix[cellXPosition][currentCell.y]) < 1){
                Cell cell = new Cell(cellXPosition, currentCell.y, currentCell.value+1);
                return cell;
            }
        }
        return null;
    }

    static class Cell{

        public int x;
        public int y;
        int value;
        public boolean visited;

        public Cell(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.visited = false;
        }
    }
}

/*
6
000x0x
0x0x0x
0*x0x0
0x0000
000xx0
000x0x
 */