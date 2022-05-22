package algorithms.search;

public abstract class AState{

    private double cost = 10;
    private AState cameFrom = null; // futher node

    public AState() { }

    public double getCost(){
        return this.cost;
    }

    //this method will give each kind of problem decide if she want other cost for steps

//    public void setCost(double cost){this.cost = cost; }

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