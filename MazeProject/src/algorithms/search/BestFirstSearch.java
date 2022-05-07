package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() {
        this.setName("BestFirstSearch");
        this.openList = new PriorityQueue<AState>(Comparator.comparing(AState::getCostByPriority));

        //todo maybe we can make a new comperator class but not shore in what way do so
        //todo check how to remove the getCost from Astate
//        this.openList=  new PriorityQueue<AState>(new Comparator<AState>() {
//
//            @Override
//            public int compare(AState o1, AState o2) {
//
//                return 0;
//            }
//
//            public double getCost() {
//                if (this.getCameFrom() == null){
//                    return 0;
//                }
//                if (isSlant()){
//                    return 15;
//                }
//                else{
//                    return 10;
//                }
//            }
//
//            private boolean isSlant(){
//                int fatherRow = ((MazeState)this.getCameFrom()).getRow();
//                int fatherColumn = ((MazeState)this.getCameFrom()).getColumn();
//                if (fatherRow+1 == this.getRow() && fatherColumn+1 == this.getColumn() ||
//                        fatherRow-1 == this.getRow() && fatherColumn+1 == this.getColumn() ||
//                        fatherRow+1 == this.getRow() && fatherColumn-1 == this.getColumn() ||
//                        fatherRow-1 == this.getRow() && fatherColumn-1 == this.getColumn()){
//                    return true;
//                }
//
//                return false;
//            }
//        });
    }

}
