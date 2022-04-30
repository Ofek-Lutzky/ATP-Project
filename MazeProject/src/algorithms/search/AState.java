package algorithms.search;

public abstract class AState{
    protected String state;
    protected double cost;
    protected AState cameFrom = null;

    public AState() { }

    public double getCost(){
        return this.cost;
    }

    public void setCost(double cost){this.cost = cost; }

    public AState getCameFrom(){
        return this.cameFrom;
    }

    public void setCameFrom(AState cF){
        this.cameFrom = cF;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();
}
