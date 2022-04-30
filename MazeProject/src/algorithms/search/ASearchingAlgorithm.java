package algorithms.search;
import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
//    ISearchable problem; //todo delete
    //todo if need this here or as field of the subClasses
    private PriorityQueue<AState> openList;
    private int visitedNodes;
    private String name;

    public ASearchingAlgorithm() {
        this.openList = new PriorityQueue<AState>();
        this.visitedNodes = 0;
    }

    @Override
    public abstract Solution solve(ISearchable s);


    public void addOneToVisited(){this.visitedNodes++;}

    @Override
    public String getName() {
        return this.name;
    }

    public int getNumberOfNodesEvaluated(){return this.visitedNodes;}

    public void setName(String s){
        this.name = s;
    }

    public void addOpenList(AState s){
        this.openList.add(s);
    }

    public PriorityQueue<AState> getOpenList(){
        return this.openList;
    }

    public AState dequeFromQueue(){
        return this.openList.poll();
    }

}
