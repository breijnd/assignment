package dui.signalassignment.signal;

import java.util.*;

/** @class Sinusoidal
 *  Class responsible for generating a sinusoidal signal with a random vertical shift.
 */
public class Sinusoidal {

    private double amplitude;
    private double radians;
    private double phaseAngle;
    public ArrayList<Double> sinusoidalResults = new ArrayList<>();

    public Sinusoidal(double amp, double rad, double pha){
        this.amplitude = amp;
        this.radians = rad;
        this.phaseAngle = pha;
    }

    /** @function getResult
     * Function responsible for calculating the value of the sinusoidal signal based on time in seconds,
     * as well as the set radians, phase angle and amplitude. Also adds a random vertical shift value.
     * @param count the amount of seconds since starting the program
     * @return result   the result of the sinusoidal calculation
     */
    public double getResult(int count){
        double randomDeviation = Math.random() * 0.1;
        return this.amplitude * Math.cos((this.radians * count) + this.phaseAngle) + randomDeviation;
    }

    /** @function getResult
     * Function responsible for calculating the value of the sinusoidal signal based on time in seconds.
     * Also scales the result with the user-provided scaling factor.
     * @param count the amount of seconds since starting the program
     * @param scalingValue the scaling value set by the user
     * @return result   the result of the sinusoidal calculation, including scaling value
     */
    public double getScaledResult(int count, double scalingValue) {
        double scaledValue = getResult(count) * scalingValue;
        sinusoidalResults.add(scaledValue);
        return scaledValue;
    }

    /** @function getResult
     * Function responsible for calculating the average value of the sinusoidal signal over a set of samples
     * Sample size is dependent on sampling frequency, currently set at 1 sample per second (1Hz).
     * @param sampleSize the amount of samples to average (1 sample per second)
     * @return result   the average sinusoidal value, will be zero if no average can be calculated
     */
    public double getAverageResult(int sampleSize){
        List<Double> subList = sinusoidalResults.subList(Math.max(sinusoidalResults.size() - sampleSize, 0), sinusoidalResults.size());
        OptionalDouble avgResult = subList
                .stream()
                .mapToDouble(a -> a)
                .average();
        return avgResult.isPresent() ? avgResult.getAsDouble() : 0;
    }
}
