/**********************************************************************************************************************
 * Frank Hulse
 * CSCI 4463-091 Artificial Intelligence
 * Homework 7
 * Professor Cicirello
 *
 * This program takes user input for a name of a text file in which contains a sudoku problem.
 * The "sudokus" folder must be places correctly in the filepath in order for the program to see the correct files.
 * Using Recursive Backtracking most of the given problems can be solved between 7 and 10 Milliseconds.
 * Multiple comments are left in for testing purposes.
 *********************************************************************************************************************/

import java.util.*;
import java.io.*;

public class AiHw7
{
    //This func prints a 2d array
    public static void print2DArray(int[][] arr)
    {
        //Loop through array and print it to specs.  (space/ newline)
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < arr[i].length; j++)
            {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    //This func checks if a column is a valid solution.  (1-9 no repeating).  Helper to checkAnswer func
    public static boolean checkVertical(int[][] arr, int col)
    {
        //Create array to save values
        int[] check = new int[9];

        //Populate array with values
        for (int i = 0; i < 9; i++)
        {
            check[i] = arr[i][col];
            //System.out.println(check[i]);
        }

        //Correct array to check against later
        int[] correct = {1,2,3,4,5,6,7,8,9};

        //Check if all cells are in range 1-9
        for (int i = 0; i < 9; i++)
        {
            //less than 0 or greater than 9 is invalid
            if (check[i] < 0 || check[i] > 9)
            {
                return false;
            }
        }

        //Check 1-9 non-repeating is true.  Can just look for every number from 1-9, if there is duplicate we won't make it to 9.
        //Sort array into asc order
        Arrays.sort(check);
        //Check vs correct array to see if values are valid
        for (int i = 0; i < 9; i++)
        {
            if (check[i] != correct[i])
            {
                return false;
            }
        }

        //Only way to get here is to pass all tests.  Column is Valid
        return true;
    }

    //This func checks if a row is a valid solution.  (1-9 no repeating)  Helper to checkAnswer func
    public static boolean checkHorizontal(int[][] arr, int row)
    {
        //Create array to save values
        int[] check = new int[9];

        //Populate array with values
        for (int i = 0; i < 9; i++)
        {
            check[i] = arr[row][i];
            //System.out.println(check[i]);
        }

        //Correct array to check against later
        int[] correct = {1,2,3,4,5,6,7,8,9};

        //Check if all cells are in range 1-9
        for (int i = 0; i < 9; i++)
        {
            //less than 0 or greater than 9 is invalid
            if (check[i] < 0 || check[i] > 9)
            {
                return false;
            }
        }

        //Check 1-9 non-repeating is true.  Can just look for every number from 1-9, if there is duplicate we won't make it to 9.
        //Sort array into asc order
        Arrays.sort(check);
        //Check vs correct array to see if values are valid
        for (int i = 0; i < 9; i++)
        {
            if (check[i] != correct[i])
            {
                return false;
            }
        }

        //Only way to get here is to pass all tests.  Column is Valid
        return true;
    }

    //This func checks if a cell is a valid solution.  (1-9 no repeating)  Helper to checkAnswer func
    public static boolean checkCell(int[][] arr, int cell)
    {
        //Values for later
        int i = 0;
        int j = 0;

        //Switch statement to get cell values for array.
        //Figured out a better solution to this switch statement using %.  RETURN HERE TO FIX LATER
        switch (cell)
        {
            case 0 -> {} //Do nothing
            case 1 -> j = 3;
            case 2 -> j = 6;
            case 3 -> i = 3;
            case 4 -> {j = 3;i = 3;}
            case 5 -> {j = 6;i = 3;}
            case 6 -> i = 6;
            case 7 -> {j = 3;i = 6;}
            case 8 -> {j = 6;i = 6;}
            default -> System.out.println("INVALID Selection");
        }
        //System.out.println("i " + i + " j " + j);

        //Create array to save values
        int[] check = new int[9];

        //Populate array with values
        for (int x = 0; x < 9; x += 3)
        {
            check[x] = arr[i][j];
            check[x + 1] = arr[i][j + 1];
            check[x + 2] = arr[i][j + 2];
            i += 1;
        }

        //Correct array to check against later
        int[] correct = {1,2,3,4,5,6,7,8,9};

        //Check if all cells are in range 1-9
        for (i = 0; i < 9; i++)
        {
            //less than 0 or greater than 9 is invalid
            if (check[i] < 0 || check[i] > 9)
            {
                return false;
            }
        }

        //Check 1-9 non-repeating is true.  Can just look for every number from 1-9, if there is duplicate we won't make it to 9.
        //Sort array into asc order
        Arrays.sort(check);
        //Check vs correct array to see if values are valid
        for (i = 0; i < 9; i++)
        {
            if (check[i] != correct[i])
            {
                return false;
            }
        }

        //Only way to get here is to pass all tests.  Column is Valid
        return true;
    }

    //This func combines the other 3 check func to see if an entire puzzle is solved correctly.
    public static boolean checkAnswer(int[][] arr)
    {
        //Iterate through all rows, columns, and cells in order to check answer is valid.  Will return false if not valid.
        for (int i = 0; i < 9; i++)
        {
            if (checkVertical(arr, i))
                if (checkHorizontal(arr, i))
                    if (checkCell(arr, i))
                        continue;
                    else
                        return false;
                else
                    return false;
            else
                return false;
        }

        //Only way here is to avoid all false checks
        return true;
    }

    //This func checks if a specific num is valid in a specific row, col, and cell in the array.
    public static boolean isValid(int[][] arr, int row, int col, int num)
    {
        //Check row
        for (int i = 0; i < 9; i++)
        {
            //Num exists in row already
            if (arr[row][i] == num)
            {
                //System.out.println("Row");
                return false;
            }
        }

        //Check column
        for (int i = 0; i < 9; i++)
        {
            //Num exists in column already
            if (arr[i][col] == num)
            {
                //System.out.println("Col");
                return false;
            }
        }

        //Check cell
        //This is the better solution to above.  FIX CHECK CELL FUNC LATER
        row -= row % 3;
        col -= col % 3;

        //Searches the 3x3 cell that the location is in.  %3 takes care of this
        for (int i = row; i < row + 3; i++)
        {
            for (int j = col; j < col + 3; j++)
            {
                //Num exists in cell already
                if (arr[i][j] == num)
                {
                    //System.out.println("Cell");
                    return false;
                }
            }
        }

        //Only way here is to avoid all other cases, this is a valid location.
        return true;
    }

    //This func uses backtracking to find a solution to the puzzle recursively
    public static boolean solve(int[][] arr)
    {
        //Iterate rows
        for (int row = 0; row < 9; row++)
        {
            //Iterate columns
            for (int col = 0; col < 9; col++)
            {
                //Find an empty cell (Denoted by 0)
                if (arr[row][col] == 0)
                {
                    //Start testing numbers, 1 - 9.  Since this is backtracking it will keep going to find one
                    for (int num = 1; num < 10; num++)
                    {
                        //Check if a location is legal for current number we are on
                        if (isValid(arr, row, col, num))
                        {
                            //This number is legal in this location.  Set new value.
                            arr[row][col] = num;

                            //Start backtracking (recursivley)
                            if (solve(arr))
                            {
                                return true;
                            }
                            //This is not a solution, empty the cell
                            else
                            {
                                arr[row][col] = 0;
                            }
                        }
                    }
                    //Returning false here denotes puzzle being unsolvable.
                    return false;
                }
            }
        }
        //Puzzle is solved
        return true;
    }




    public static void main(String[] args) throws Exception
    {
        //Get user input as string.  This is the filename of the game to be played
        System.out.print("What file would you like to try: ");
        Scanner input = new Scanner(System.in);
        String t = (input.nextLine());

        //For timing how long solution takes
        final long startTime = System.currentTimeMillis();

        //create array
        int[][] board = new int[9][9];

        //Read in user specified file
        File inFile = new File("src/sudokus/" + t +".txt");
        BufferedReader br = new BufferedReader(new FileReader(inFile));

        //Create values for later
        String line;
        int j = 0;

        //Keep reading file until end
        while ((line = br.readLine()) != null)
        {
            //Delete all white space
            line = line.replaceAll("\\s", "");

            //Store value in array
            for (int i = 0; i < line.length(); i++)
            {
                String c = String.valueOf(line.charAt(i));
                board[j][i] = Integer.parseInt(c);
            }
            j++;

        }

        //print2DArray(board);
        //System.out.println(checkVertical(board, 5));
        //System.out.println(checkHorizontal(board, 5));
        //System.out.println(checkCell(board, 1));
        //System.out.println(checkAnswer(board));
        //System.out.println(isValid(board, 0, 0, 1));

        //Solve the puzzle using backtracking.
        solve(board);

        //Check answer is valid.  Display if so.
        if (checkAnswer(board))
        {
            //For timing solution
            final long endTime = System.currentTimeMillis();

            //Output success message with board solution and elapsed time.
            System.out.println("This is a/the solution to the puzzle:");
            print2DArray(board);
            System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
        }
        else
        {
            System.out.println("ERROR. The generated board is invalid.");
        }

    }
}
