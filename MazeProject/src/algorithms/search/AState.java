package algorithms.search;

public abstract class AState{
    //todo check if i need cost and state?
    private String state;
    private double cost = 10;
    private AState cameFrom = null;

    public AState() { }

    public double getCost(){
        return this.cost;
    }

    //this method will give each kind of problem decide if she want other cost for steps
    public double getCostByPriority(){return this.cost;}

    //public void setCost(double cost){this.cost = cost; }

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
