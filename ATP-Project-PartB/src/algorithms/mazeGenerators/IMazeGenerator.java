package algorithms.mazeGenerators;

/**
 * to implement in other classes
 */
public interface IMazeGenerator {
    public Maze generate(int rows, int columns);
    public long measureAlgorithmTimeMillis(int rows, int columns);
}
