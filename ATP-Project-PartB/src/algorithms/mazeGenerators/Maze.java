package algorithms.mazeGenerators;

import algorithms.search.AState;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Maze implements Serializable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";


    private Position startPosition;
    private Position goalPosition;
    private int[][] board;
//    0 = not visited
//    1 = Wall
//    S = start also 0
//    E = End also 0

    //for part B converter
    private int indexInArrayBytes=0;

    public Maze(Position startPosition, Position goalPosition, int[][] board) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        this.board = board;
    }



    public Position getStartPosition(){
        return this.startPosition;
    }

    public Position getGoalPosition(){
        return this.goalPosition;
    }

    /**
     * return the print of the maze in the order asked for
     */
    public void print(){
//
        //A nice presentation for us

        String[][] toPrint = new String[board.length][board[0].length];
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){
                if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
                    toPrint[i][j] = ANSI_GREEN +"S" + ANSI_RESET;
                else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
                    toPrint[i][j] = ANSI_RED + "E" + ANSI_RESET;
                else{
                    if (this.board[i][j] == 1){

                        toPrint[i][j] = "▓";
                    }
                    else {
                        toPrint[i][j] = " ";
                    }

//                    toPrint[i][j] = String.valueOf(this.board[i][j]);
                }

            }
        }
//
        for (int i = 0; i < this.board.length; i++){
            System.out.println(Arrays.toString(toPrint[i]));
        }



//        System.out.print("{\n");
//        for (int i = 0; i < this.board.length; i++){
//            System.out.print("\t{");
//            for (int j = 0; j < this.board[0].length; j++){
//                if (j<this.board.length-1){
//                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET + ",");
//                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                        System.out.print(ANSI_RED + "E" + ANSI_RESET + ",");
//                    else{
//                        System.out.print(String.valueOf(this.board[i][j])+",");
//                    }
//
//                }
//                else{
//                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET );
//                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                        System.out.print(ANSI_RED + "E" + ANSI_RESET );
//                    else{
//                        System.out.print(String.valueOf(this.board[i][j]));
//                    }
//                }
//
//
//
//            }
//            System.out.println("},");
//        }
//        System.out.print("};\n");


//        System.out.print("{\n");
//        for (int i = 0; i < this.board.length; i++){
//            System.out.print("\t{");
//            for (int j = 0; j < this.board[0].length; j++){
//                if (j<this.board.length-1){
//                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET + ",");
//                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                        System.out.print(ANSI_RED + "E" + ANSI_RESET + ",");
//                    else{
//                        System.out.print(String.valueOf(this.board[i][j])+",");
//                    }}
//
//
//                else{
//                    if (i == this.getStartPosition().getRowIndex() && j == this.getStartPosition().getColumnIndex())
//                        System.out.print(ANSI_GREEN +"S" + ANSI_RESET );
//                    else if(i == this.getGoalPosition().getRowIndex() && j == this.getGoalPosition().getColumnIndex())
//                        System.out.print(ANSI_RED + "E" + ANSI_RESET );
//                    else{
//                        System.out.print(String.valueOf(this.board[i][j]));
//                    }
//                }}
//
//
//
//            System.out.println("},");
//        }
//        System.out.print("};\n");
    }




    public int[][] getMap() {
        return board;
    }




    /**
     *
     * @return array of byte[] we deside that it will be in the next order [startR,startC,endR,endC,mazeSizeR,mazeSizeC,mazeData]
     * here we will decide the compress way we want to represent the maze
     */
    public byte[] toByteArray(){
        ArrayList<Byte> rtnArrayByByte = new ArrayList<Byte>();
        // add all will add all the nodes of the given array
        rtnArrayByByte.addAll(intToByte(startPosition.getRowIndex()));
        rtnArrayByByte.addAll(intToByte(startPosition.getColumnIndex()));
        rtnArrayByByte.addAll(intToByte(goalPosition.getRowIndex()));
        rtnArrayByByte.addAll(intToByte(goalPosition.getColumnIndex()));
        rtnArrayByByte.addAll(intToByte(this.board.length));
        rtnArrayByByte.addAll(intToByte(this.board[0].length));
        rtnArrayByByte.addAll(changeBoardToByte());

        byte[] array = new byte[rtnArrayByByte.size()];
        for (int i = 0 ; i < rtnArrayByByte.size();i++)
        {
            array[i] = rtnArrayByByte.get(i);
        }
        return array;
    }

    // my way to represent the byte
    private ArrayList<Byte> intToByte(int num){
        ArrayList<Byte> b = new ArrayList<Byte>();
        while(num > 255) // for number that big then 255 we will seperate the number and when we will convert back we will add to the sum
        { num-=255;
            b.add((byte)255); } // like to add -1
        if (num >=0) { b.add((byte)num);}
        b.add((byte)0); //seperator between the given integers
        return b;
    }

    private ArrayList<Byte> changeBoardToByte()
    {
        ArrayList<Byte> boardByte = new ArrayList<Byte>();
        for (int i = 0 ; i < this.board.length ; i++)
        {
            for (int j = 0 ; j < this.board[0].length ; j++)
            {
                boardByte.add((byte)this.board[i][j]); // change each cell to byte
            }
        }
        return boardByte;
    }





    public Maze(byte[] array)
    {
        int startR = convertFromByteToInt(array);
        int startC = convertFromByteToInt(array);
        int endR = convertFromByteToInt(array);
        int endC = convertFromByteToInt(array);
        int numOfRows = convertFromByteToInt(array);
        int numOfCol = convertFromByteToInt(array);
        Position start = new Position(startR,startC);
        this.startPosition = start;
        Position end = new Position(endR,endC);
        this.goalPosition = end;
        int[][] board = new int[numOfRows][numOfCol];
        for (int j = 0 ; j < numOfRows ; j++)
        {
            for (int k = 0 ; k < numOfCol ; k++)
            {
                int num = indexInArrayBytes;
                if (num >= array.length){
                    break;
                }
                board[j][k]= (int)array[num];
                indexInArrayBytes++;
            }
        }
        this.board = board;
    }

    private int convertFromByteToInt(byte[] array )
    {
        int sum = 0;

        if (array[indexInArrayBytes] == 0)
        {
            indexInArrayBytes+=2; // if you so 0 its mean we need to pass to the next value to convert so +2 becuase two zeros
            return 0;
        }

        else
        {
            while(array[indexInArrayBytes]!=0)
            {
                sum+=Byte.toUnsignedInt(array[indexInArrayBytes]);
                indexInArrayBytes++;
            }
            indexInArrayBytes++;
            return sum;
        }

    }

    public void setStartPosition(Position p){
        this.startPosition = p;
    }


}
