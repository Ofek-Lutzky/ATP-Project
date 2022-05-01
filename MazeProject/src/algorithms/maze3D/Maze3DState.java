package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.MazeState;

public class Maze3DState extends AState {
    Position3D position;

    public Maze3DState(Position3D position) {
        this.position = position;
    }

    public int getDepth(){return this.position.getDepthIndex();}

    public int getRow(){
        return this.position.getRowIndex();
    }

    public int getColumn(){
        return this.position.getColumnIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Maze3DState)){
            return false;
        }

        Maze3DState other = (Maze3DState) o;
        return this.getDepth() == other.getDepth() && this.getRow() == other.getRow() && this.getColumn() == other.getColumn();
    }

    @Override
    public String toString() {
        return "{" + this.getDepth() + ","+ this.getRow() + "," + this.getColumn() + "}";
    }
}
