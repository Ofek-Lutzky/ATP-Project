package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    public BreadthFirstSearch() {
        this.setName("BreadthFirstSearch");
        this.openList = new LinkedList<AState>();
    }


    @Override
    public Solution solve(ISearchable s) {
        HashSet<AState> visited = new HashSet<>();
        this.openList.add(s.getStartState());
//        boolean gotToEnd = false;
        AState rtnNode = null;

        while(!this.openList.isEmpty()){

            this.addOneToVisited();

            AState currentState = this.openList.remove();
            visited.add(currentState);

            if (currentState.equals(s.getGoalState())){
                rtnNode = currentState;
                break;
            }

            ArrayList<AState> nb = s.getAllPossibleStates(currentState);


            for (int i = 0; i < nb.size(); i++){
                if (!compare(visited, nb.get(i))){
                    this.openList.add(nb.get(i));
                    nb.get(i).setCameFrom(currentState);
                }
            }
        }

        Solution sol = new Solution();

        while (rtnNode.getCameFrom() != null){
            sol.addToSolution(rtnNode);
            rtnNode = rtnNode.getCameFrom();
        }

        return sol;
    }


    private Boolean compare(HashSet<AState> visited,AState s){
        Object[] a = visited.toArray();
        for (int i = 0; i < a.length; i++){
            if (a[i].equals(s)){
                return true;
            }
        }
        return false;
    }

}
