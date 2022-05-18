package algorithms.search;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm{

    public DepthFirstSearch() {
        this.setName("DepthFirstSearch");
    }

    /**
     *
     * @param s
     * @return rtnNode
     */

//    @Override
//    public Solution solve(ISearchable s) {
//        AState rtnNode = null;
//
//        Stack<AState> stack = new Stack<>();
//        HashSet<AState> visited = new HashSet<>();
//
//        stack.push(s.getStartState());
//
//        while(!stack.empty()){
//
//            this.addOneToVisited();
//
//            AState currentState = stack.pop();
//            visited.add(currentState);
//
//            if (currentState.equals(s.getGoalState())){
//                rtnNode = currentState;
//                break;
//            }
//
//            ArrayList<AState> nb = s.getAllPossibleStates(currentState);
//
//            //todo if the contain was working
////            for (int i = 0; i < nb.size(); i++){
////                if (!visited.contains(nb.get(i))){
////                    stack.push(nb.get(i));
////                    nb.get(i).setCameFrom(currentState);
////                }
////            }
//
//            for (int i = 0; i < nb.size(); i++){
//                if (!compare(visited, nb.get(i))){
//                    stack.push(nb.get(i));
//                    nb.get(i).setCameFrom(currentState);
//                }
//            }
//        }
//
//        Solution sol = new Solution();
//        while (rtnNode.getCameFrom() != null){
//            sol.addToSolution(rtnNode);
//            rtnNode = rtnNode.getCameFrom();
//        }
//
//        return sol;
//    }


    @Override
    public Solution solve(ISearchable s) {
        AState rtnNode = null;

        Stack<AState> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();

        //put the start in the stack
        stack.push(s.getStartState());

        //While there is node to be handl in the stack
        while(!stack.empty()){

            this.addOneToVisited();

            AState currentState = stack.pop();
            visited.add(currentState.toString());// todo if need it here or in the loop

            if (currentState.equals(s.getGoalState())){
                rtnNode = currentState;
                break;
            }

            ArrayList<AState> nb = s.getAllPossibleStates(currentState);
            for (int i = 0; i < nb.size(); i++){
                if (!visited.contains(nb.get(i).toString())){
                    stack.push(nb.get(i));
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

}
