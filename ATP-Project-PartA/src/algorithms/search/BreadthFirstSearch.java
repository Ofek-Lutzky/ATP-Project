package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    public BreadthFirstSearch() {
        this.openList = new LinkedList<AState>();
    }

    /**
     *
     * @param s
     * @return Solution to maze by BFS called: sol
     */
    @Override
    public Solution solve(ISearchable s) {
        //the Hash is to store the Astates we all ready reached
        HashSet<String> visited = new HashSet<>();

        visited.add(s.getStartState().toString());

        AState currentState = s.getStartState();

        //the loop repeats we get to the goal
        while(!currentState.equals(s.getGoalState())){

            this.addOneToVisited();

            ArrayList<AState> nb = s.getAllPossibleStates(currentState);

            for (int i = 0; i < nb.size(); i++){
                if (!visited.contains(nb.get(i).toString())){
                    visited.add(nb.get(i).toString());
                    nb.get(i).setCameFrom(currentState);
                    this.openList.add(nb.get(i));
                }
            }

            currentState = this.openList.poll();
        }

        Solution sol = new Solution();

        while (currentState.getCameFrom() != null){
            sol.addToSolution(currentState);

            currentState = currentState.getCameFrom();
        }

        return sol;
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

}
