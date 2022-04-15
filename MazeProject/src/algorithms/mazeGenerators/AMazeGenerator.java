package algorithms.mazeGenerators;

public abstract class  AMazeGenerator implements IMazeGenerator{
    @Override
    public abstract Maze generate(int rows, int columns); //we will leave it abstract for the childrens to implement

    //todo check: in the pdf there is a,b,c is be needed?

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {

        long timeBefore = System.currentTimeMillis();
        generate(rows,columns);
        long timeAfter = System.currentTimeMillis();

        return timeAfter-timeBefore;
    }
}
