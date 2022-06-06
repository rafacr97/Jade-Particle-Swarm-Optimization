/**
 *  
 *  @author Rafael Camacho Roldán <za18012201@zapopan.tecmm.edu.mx>
 */
public class DataSet {
    int particles = 17;  //Quantity of parcles (population) (in xN_i, xN_i+1, xN_i+1 ... xN_i-1)
    int x=3;  // Quanty of 'X' variables in array (x1, x2, x3 ... Xn-1)
    
    private double [][]x_values=new double [particles][x];
    private double [][]velocity=new double [particles][x];
    
    private double []fitnessFuntion = new double[particles];   //Array to save the value result of the funtion using particles in x1, x2, x3 ... Xn-1
    private double [][]pBest = new double[particles][x]; //Matrix to save 'Personal Best Values' values
    private double []gBest = new double[x];
    private double minFitnessValue;

    public double getMinFitnessValue() {
        return minFitnessValue;
    }

    public void setMinFitnessValue(double minFitnessValue) {
        this.minFitnessValue = minFitnessValue;
    }
    
    public double[] getgBest() {
        return gBest;
    }

    public void setgBest(double[] gBest) {
        this.gBest = gBest;
    }
    

    public double[][] getpBest() {
        return pBest;
    }

    public void setpBest(double[][] pBest) {
        this.pBest = pBest;
    }
    
    public double[][] getX_values() {
        return x_values;
    }

    public void setX_values(double[][] x_values) {
        this.x_values = x_values;
    }

    public double[][] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[][] velocity) {
        this.velocity = velocity;
    }

    public double[] getFitnessFuntion() {
        return fitnessFuntion;
    }

    public void setFitnessFuntion(double[] fitnessFuntion) {
        this.fitnessFuntion = fitnessFuntion;
    }
}
