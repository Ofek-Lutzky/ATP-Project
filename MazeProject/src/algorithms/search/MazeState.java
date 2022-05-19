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

    /**
     *
     * @return double of the cost of the cell move in considre it father place
     */
    @Override
    public double getCost() {
        if (this.getCameFrom() == null){
            return 0;
        }
        else if (isSlant()){
            return 15;
        }
        else{
            return 10;
        }
    }

    /**
     *
     * @return check if according to his father it a slent
     */
    private boolean isSlant(){
        int fatherRow = ((MazeState)this.getCameFrom()).getRow();
        int fatherColumn = ((MazeState)this.getCameFrom()).getColumn();
        if (fatherRow+1 == this.getRow() && fatherColumn+1 == this.getColumn() ||
                fatherRow-1 == this.getRow() && fatherColumn+1 == this.getColumn() ||
                fatherRow+1 == this.getRow() && fatherColumn-1 == this.getColumn() ||
                fatherRow-1 == this.getRow() && fatherColumn-1 == this.getColumn()){
            return true;
        }

        return false;
    }

    /**
     *
     * @param o
     * @return bool sign if the cell are same
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MazeState)){
            return false;
        }

        MazeState other = (MazeState) o;
        return this.getRow() == other.getRow() && this.getColumn() == other.getColumn();
    }

    /**
     *
     * @return string {ROW,COL}
     */
    @Override
    public String toString() {
        return "{" + this.getRow() + "," + this.getColumn() + "}";
    }

}
