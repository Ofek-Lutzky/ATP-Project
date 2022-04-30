package algorithms.maze3D;

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

    public String toString(){
        return String.format("{%d,%d,%d}", this.getDepthIndex(), this.getRowIndex(), this.getColumnIndex());
    }
}
