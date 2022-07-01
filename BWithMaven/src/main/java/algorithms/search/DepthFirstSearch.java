package algorithms.search;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm{

    public DepthFirstSearch() { }

    /**
     *
     * @param s  - ISearchable, the problem we try to solve
     * @return Solution to maze by DFS called: sol
     */

    @Override
    public Solution solve(ISearchable s) {
        AState rtnNode = null;

        Stack<AState> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();

        //put the start in the stack
        stack.push(s.getStartState());
        visited.add(s.getStartState().toString());//added

        //While there is node to be handl in the stack
        while(!stack.empty()){

            this.addOneToVisited();

            // take the cell on the top and check for its unvisited neighbors
            AState currentState = stack.pop();
//            visited.add(currentState.toString()); //removed

            // stop, if the goal is reached
            if (currentState.equals(s.getGoalState())){
                rtnNode = currentState;
                break;
            }

            ArrayList<AState> nb = s.getAllPossibleStates(currentState);
            for (int i = 0; i < nb.size(); i++){
                if (!visited.contains(nb.get(i).toString())){
                    visited.add(nb.get(i).toString()); // added
                    nb.get(i).setCameFrom(currentState); // changed place with line under
                    stack.push(nb.get(i));

                }
            }
        }


//        Solution sol = new Solution();
//        while (rtnNode.getCameFrom() != null){
//            sol.addToSolution(rtnNode);
//            rtnNode = rtnNode.getCameFrom();
//        }

        return new Solution(rtnNode);
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

}
