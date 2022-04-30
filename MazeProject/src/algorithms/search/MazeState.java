package algorithms.search;
import algorithms.mazeGenerators.Position;

//this class will help us to change the problem to be maze kind
//Before we get it as a Astate generic
public class MazeState extends AState {

    Position position;

    public MazeState(Position p) {
        this.position = p;
    }

    public int getRow(){
        return this.position.getRowIndex();
    }

    public int getColumn(){
        return this.position.getColumnIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MazeState)){
            return false;
        }

        MazeState other = (MazeState) o;
        return this.getRow() == other.getRow() && this.getColumn() == other.getColumn();
    }

    @Override
    public String toString() {
        return "{" + this.getRow() + "," + this.getColumn() + "}";
    }

}
