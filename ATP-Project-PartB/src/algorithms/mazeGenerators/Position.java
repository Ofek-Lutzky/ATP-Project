package algorithms.mazeGenerators;

import algorithms.search.MazeState;

import java.io.Serializable;

public class Position implements Serializable {

    private int R;
    private int C;


    public Position(int r, int c) {
        this.R = r;
        this.C = c;
    }

    public int getRowIndex(){
        return this.R;
    }

    public int getColumnIndex(){
        return this.C;
    }

    /**
     *
     * @return "{ROW,COLUMN}"
     */
    public String toString(){
        return String.format("{%d,%d}", this.getRowIndex(), this.getColumnIndex());
    }

    /**
     *
     * @param o - object of Position, that we compare to.
     * @return boolean if the row and the column are the same. what is mean that it is the same cell on board
     * equal between two objects of position
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Position)){
            return false;
        }

        Position other = (Position) o;
        return this.getRowIndex() == other.getRowIndex() && this.getColumnIndex() == other.getColumnIndex();
    }

}
