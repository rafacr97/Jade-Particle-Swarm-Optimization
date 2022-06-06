import java.text.DecimalFormat;
/**
 *  
 *  -En esta 'clase' se desarrollan todas las funciones para el desarrollo del algoritmo PSO.
 *  -El algoritmo se desarrolla en los siguientes pasos:
 *      1.- Inicialización
 *      2.- Evaluar la función de ajuste para cada particula
 *      3.- Para cada particula, calcular el valor de velocidad (v) y posición (x)
 *      4.- Evaluar la función de ajuste para encontrar el actual 'global best'
 *  -Los pasos mencionados teriormente se indicarán (Inicio y Final) en sus respectivas lineas mas adelante.
 *  -Función utilizada para la minimización: f(x) = 10*(x1 - 1)^2 + 20*(x2-2)^2 + 30*(x3-3)^2
 * 
 *  @author Rafael Camacho Roldán *Student account <za18012201@zapopan.tecmm.edu.mx>
 *                                *Personal account <rafacr_@hotmail.com>
 */
class Methods{
    DecimalFormat df = new DecimalFormat("#.###");
    DataSet ds = new DataSet();

    //Display array passed by parameter in console
    void showArray(double []array){
        for(int i=0;i<array.length;i++){
            System.out.println(df.format(array[i]));
        }
    }
    //Display matrix passed by parameter in console
    void showMatrix(double [][]matrix){
        for(int row=0;row<matrix.length;row++){
            for(int column=0;column<matrix[0].length;column++){
                System.out.print(df.format(matrix[row][column])+"\t");
            }
            System.out.println("");
        }
    }
    /*  ________________________________________________________________________________________________________________________
        STEP 1 - INITIALIZATION
        * INITIALIZE PARAMETERS
            -Number of variables (x1, x2, x3 ... x^n-1)
            -Population = Quantity of parcles (xN_i, xN_i+1, xN_i+1 ... xN_i-1)
            -Constants (Acelerator factor)
            -Inertia value (Random value between [0,1])
            -Maximun iteration (preferably:  iteration>1)
        ** INITIALIZE POPULATION (x_i) Ramdomly for each particle  
            -Initialize Velocity (v_i) Ramdomly for each particle
            -Initialize position
                -Set current personal best = Initialize position
     */

        // *INITIALIZE PARAMETERS
            //-Number of variables and population was declared in 'DataSet' class.
            double c1=2, c2=2;          //-constants values (Aceleration factor)
            double w = Math.random();   //-Inertia value between[0,1]
            int iterations = 50;       //-Maximun iterations

        // **INITIALIZE POPULATION
            double [][] initPopulation(){
                    double[][] x_values = new double[ds.particles][ds.x];
                    //Normaly population is declared ramdomly, but in this case it was hard-coded with the following data set:
                    double [][] dataSet = {{41.9,  43.4,  43.9,  44.5,  47.3,  47.5,  47.9,  50.2,  52.8,  53.2,  56.7,  57.0,  63.5,  65.3,  71.1,  77.0,  77.8 },  //x1
                                           {29.1,  29.3,  29.5,  29.7,  29.9,  30.3,  30.5,  30.7,  30.8,  30.9,  31.5,  31.7,  31.9,  32.0,  32.1,  32.5,  32.9 },  //x2
                                           {251.3, 251.3, 248.3, 267.5, 273.0, 276.5, 270.3, 274.9, 285.0, 290.0, 297.0, 302.5, 304.5, 309.3, 321.7, 330.7, 349.0}}; //x3

                    //"for" statement to get the matrix transposed of ${x_values} and save in ${matrix_aux} matrix
                    for(int row=0;row<dataSet.length;row++){
                        for(int column=0;column<dataSet[0].length;column++){
                        x_values[column][row] = dataSet[row][column];
                        }
                    }
                    return x_values; //Population has been initialized
                }
            //-Initialize velocity
                double [][] initVelocity(){
                    double [][]velocity = new double[ds.particles][ds.x];
                    for(int row=0;row<velocity.length;row++){
                        for(int column=0;column<velocity[0].length;column++){
                            //Random value between [0,1]
                            double r = Math.random();
                                velocity[row][column]=r;
                        }
                    }
                    return velocity; //Velocity has been initialized
                }
            //-Initialize position (x_i) <--- Current position = Previous position (population in first iteration) + velocity
                double [][] initPosition(double [][]population, double [][]velocity){
                    for(int row=0;row<population.length;row++){
                        for(int column=0;column<population[0].length;column++){
                            population[row][column]+=velocity[row][column];
                        }
                    }
                    return population; //Position has been initialized
                }
            //Initialize "Personal Best" with first position values
                double [][] initPBest(double [][]position){
                    double [][]pBest = new double [ds.particles][ds.x];
                    for(int row=0;row<position.length;row++){
                        for(int column=0;column<position[0].length;column++){
                            pBest[row][column]=position[row][column];
                        }
                    }
                    return pBest;
                }
    /*
     * END OF STEP 1 - INITILIZATION
     */

    /*  _______________________________________________________________________________________________________________________
        STEP 2 - EVALUATE FITNESS 
        * CALCULATE FITNESS VALUE FOR EACH PARTICLE
            -Objetive funtion used for minimization: f(x) = 10*(x1 - 1)^2 + 20*(x2-2)^2 + 30*(x3-3)^2
        ** CHOOSE PARTICLE WITH BEST FITNESS VALUE AS 'Global Best'
            -Set 'Global best' to x1, x2, x3 ... xN-1
    */

        //* CALCULATE FITNESS VALUE FOR EACH PARTICLE
            //-Objetive funtion used for minimization: f(x) = 10*(x1 - 1)^2 + 20*(x2-2)^2 + 30*(x3-3)^2
                double[] calculateFitnessFuntion(double [][]x_values){
                    double fitnessValue[]=new double[ds.particles];
                        for(int row = 0; row<fitnessValue.length;row++){
                            fitnessValue[row]= 10*(Math.pow(x_values[row][0] - 1, 2)) + (20*(Math.pow(x_values[row][1] - 2, 2))) + (30*(Math.pow(x_values[row][2]-3,2)));
                        }
                 return fitnessValue;    
                }
        //** CHOOSE PARTICLE WITH BEST FITNESS VALUE AS 'Global Best'
            double fitnessValue(double []fitnessValue){
                    double bestFitnessValue=fitnessValue[0];
                    for(int i = 1; i<fitnessValue.length;i++){
                        if(fitnessValue[i]<bestFitnessValue){
                            bestFitnessValue=fitnessValue[i];
                        }
                    }
                    return bestFitnessValue;
                }
            //-Set 'Global best' to x1, x2, x3 ... xN-1
                double[] calculateGlobalBest(double []fitnessFuntion, double fitnessValue, double [][]x_positions){
                    int position=0; 
                    double []gBest= new double[ds.x];
                        for(int i=0;i<fitnessFuntion.length;i++){
                            if(fitnessValue==fitnessFuntion[i]){
                                position=i;
                            }
                        }
                        for(int row=0;row<gBest.length;row++){
                            gBest[row]=x_positions[position][row];
                        }
                    return gBest;
                }
    /*
    * END OF STEP 2 - EVALUATE FITNESS
    */

    /*  ____________________________________________________________________________________________________________________
        STEP 3 - FOR EACH PARTICLE CALCULATE VELOCITY AND POSITION
        * CALCULATE VELOCITY BY: Vi^t+1 = W*V^t + C1*R1(pBest - Xi^t) + C2R2(gBest - Xi^t)
        ** CALCULATE PARTICLES POSITIONS BY: X^t+1 = X^t + V^t
    */

        //* UPDATE VELOCITY BY: Vi^t+1 = W*V^t + C1*R1(pBest - Xi^t) + C2R2(gBest - Xi^t)
            double[][] updateVelocity(double [][]velocity, double [][]x_position, double [][]pBest, double []gBest){
                double r=Math.random();
                    for(int row = 0; row<velocity.length;row++){
                        for(int column=0;column<velocity[0].length;column++){
                            velocity[row][column] = (w*velocity[row][column]) + 
                                                    ((c1*r)*((pBest[row][column]) - (x_position[row][column]))) + 
                                                    ((c2*r)*((gBest[column])      - (x_position[row][column])));
                        }
                    }
                return velocity;
            }
        //** CALCULATE PARTICLES POSITIONS BY: X^t+1 = X^t + V^t
            double[][] updatePosition(double[][]velocity,double[][]x_values){
                for(int row = 0; row<velocity.length;row++){
                        for(int column=0;column<velocity[0].length;column++){
                            x_values[row][column]+=velocity[row][column];
                        }
                }
                return x_values;
            }
    /*
    *   END OF STEP 3 - FOR EACH PARTICLE CALCULATE VELOCITY AND POSITION
    */

    /*  ______________________________________________________________________________________________________________________
        STEP 4 - UPDATE 'GLOBAL BEST' AND 'PERSONAL BEST'
        * COMPARE THE CURRENT MINIMUN FITNESS VALUE VS NEW MINIMUN FITNESS VALUE AND CHOOSE THE BEST
        ** UPDATE GLOBAL BEST
        *** UPDATE PERSONAL BEST
     */

        //*  COMPARE THE CURRENT MINIMUN FITNESS VALUE VS NEW MINIMUN FITNESS VALUE AND CHOOSE THE BEST
            double updateFitnessValue(double minFitnessValue, double[] fitnessFuntion){
                for(int i=0;i<fitnessFuntion.length;i++){
                    if(fitnessFuntion[i] < minFitnessValue){
                        minFitnessValue=fitnessFuntion[i];
                    }
                }
                return minFitnessValue;
            }
        //*** UPDATE PERSONAL BEST
            double[][] updatePBest(double [][]x_position, double[][]pBest, double []fitnessFuntion, double []currentFitnessFuntion){
                for(int row=0;row<fitnessFuntion.length;row++){
                        if(currentFitnessFuntion[row]<fitnessFuntion[row]){
                            for(int column=0;column<pBest[0].length;column++){
                                pBest[row][column]=x_position[row][column];
                            }
                        }
                }
                return pBest;
            }
    /*
    *   END OF STEP 4 - UPDATE 'GLOBAL BEST' AND 'PERSONAL BEST'
    */ 
}