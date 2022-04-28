package algorithms.search;

public abstract class AState {
    protected String state;
    protected double cost;
    protected AState cameFrom = null;

    public AState() { }

    public double getCost(){
        return this.cost;
    }

    public void setCameFrom(AState cF){
        this.cameFrom = cF;
    }

    //todo check about this
//    @Override
//    public boolean equals(Object o){
//        return false;
//    }
}
