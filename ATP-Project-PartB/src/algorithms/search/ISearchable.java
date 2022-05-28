package algorithms.search;
import java.util.ArrayList;


public interface ISearchable {
    public AState getStartState();
    public AState getGoalState();
    ArrayList<AState> getAllPossibleStates(AState s);

}
