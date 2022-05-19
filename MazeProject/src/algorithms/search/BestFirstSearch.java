package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() {
        this.openList = new PriorityQueue<AState>(Comparator.comparing(AState::getCost));
    }

    /**
     *
     * @return string "BestFirstSearch"
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }

}
