package algorithms.maze3D;

import algorithms.mazeGenerators.Position;

public class Position3D {

    private int D;
    private int R;
    private int C;


    public Position3D(int d,int r, int c) {
        this.D = d;
        this.R = r;
        this.C = c;
    }

    public int getDepthIndex(){return this.D; }

    public int getRowIndex(){
        return this.R;
    }

    public int getColumnIndex(){
        return this.C;
    }

    /**
     *
     * @return "{DEPTH,ROW,COLUMN}"
     */
    public String toString(){
        return String.format("{%d,%d,%d}", this.getDepthIndex(), this.getRowIndex(), this.getColumnIndex());
    }

    /**
     *
     * @param o
     * @return boolean if the depth, row and the column are the same. what is mean that it is the same cell on board
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Position3D)){
            return false;
        }

        Position3D other = (Position3D) o;
        return this.getDepthIndex() == other.getDepthIndex() && this.getRowIndex() == other.getRowIndex() && this.getColumnIndex() == other.getColumnIndex();
    }
}
