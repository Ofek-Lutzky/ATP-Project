package algorithms.search;
import java.io.Serializable;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm, Serializable {

    Queue<AState> openList;
    private int visitedNodes;

    public ASearchingAlgorithm() {
        this.visitedNodes = 0;
    }

    /**
     *
     * @param s - ISearchable, the problem we try to solve
     * @return Solution
     */
    @Override
    public abstract Solution solve(ISearchable s);

    /**
     * add +1 to visited
     */
    public void addOneToVisited(){this.visitedNodes++;}

    /**
     *
     * @return string name of the algo that solved the problem
     */
    public abstract String getName();

    /**
     *
     * @return number of the visited nodes
     */
    public int getNumberOfNodesEvaluated(){return this.visitedNodes;}


}
