# Intro

This program was written per the instructions given in the coding assignment from Dutch United Instruments. <br>
The program is able to sample a signal with a random factor once per second, and have the user enter scaling values and define an averaging period in seconds. <br>
Further implementations would include the writing of unit tests, and including a real-time graph to track the signals overtime and visualize them for the user.

# Instructions

To run the .jar file found under the `target` folder,
 execute the following command in a command prompt:

```java -jar target/DUI_SignalGen-1.0-SNAPSHOT.jar```

To change the values of the sinusoidal signal (i.e. amplitude, phase angle and radians), navigate to the UserInterfaceBase class and edit the `private final Sinusoidal sinusoidalSignal` variable.

# Rebuilding the JAR

It is possible to rebuild the JAR file. For this, a working Maven instance is necessary.
To rebuild the program, navigate to the parent folder `DUI_SignalGen` and run the command below:

```mvn clean package```