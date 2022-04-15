package algorithms.mazeGenerators;

public class Position {

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

    public String toString(){
        //todo check if the start and the end position need to be added to here as some kind of print
        return String.format("{%d,%d}", this.getRowIndex(), this.getColumnIndex());
//        return "{" + String.valueOf(this.getRowIndex()) + "," + String.valueOf(this.getColumnIndex()) + "}";
    }
}
