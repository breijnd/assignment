package dui.signalassignment.signal;

import java.util.ArrayList;

/** @class StandardDeviationCalculator
 *  A class responsible for handling the calculation of the Standard Deviation based on an ArrayList of
 *  Double-type data collected from a predefined signal.
 */
public class StandardDeviationCalculator {

    /** @function getMean
     *  Retrieves the mean of a given set of signal data stored in an ArrayList<Double>
     * @param list  A list with signal data in Double-type format.
     * @return the mean value of the given ArrayList
     */
    private double getMean(ArrayList<Double> list){
        double total = 0;
        for(double i : list){
            total += i;
        }
        return total / (double) list.size();
    }

    /** @function calculateSD
     *  Calculates the Standard Deviation based on the mean, mean difference, amount of samples.
     * @param list  A list containing all the values used for SD calculation.
     * @return the given standard deviation
     */
    public double calculateSD(ArrayList<Double> list){
        double mean = getMean(list);
        double temp = 0;

        for(double i : list){
            double differenceWithMeanSquared = Math.pow(i - mean, 2);

            temp += differenceWithMeanSquared;
        }

        double differenceMean = temp / (double) list.size();

        return Math.sqrt(differenceMean);
    }
}
