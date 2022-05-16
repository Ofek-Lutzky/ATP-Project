package algorithms.search;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    //todo if need this here or as field of the subClasses
    Queue<AState> openList;
    private int visitedNodes;
    private String name;

    public ASearchingAlgorithm() {
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
