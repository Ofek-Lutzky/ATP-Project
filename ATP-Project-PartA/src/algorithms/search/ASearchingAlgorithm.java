package algorithms.search;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    Queue<AState> openList;
    private int visitedNodes;
    private String name;


    public ASearchingAlgorithm() {
        this.visitedNodes = 0;
    }

    /**
     *
     * @param s
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

//    public void setName(String s){
//        this.name = s;
//    }

//    public void addOpenList(AState s){
//        this.openList.add(s);
//    }
//
//    public PriorityQueue<AState> getOpenList(){
//        return this.openList;
//    }

//    public AState dequeFromQueue(){
//        return this.openList.poll();
//    }

}
