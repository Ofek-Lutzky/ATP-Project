package algorithms.search;

public interface ISearchingAlgorithm {
    public AState search(ISearchable s);
    public int getNumberOfVisitedNodes();

}
