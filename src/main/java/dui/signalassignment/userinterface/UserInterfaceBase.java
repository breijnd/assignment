package dui.signalassignment.userinterface;

import dui.signalassignment.signal.StandardDeviationCalculator;
import dui.signalassignment.signal.Sinusoidal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

/** @class UserInterfaceBase
 *  Base class for handling UI initialization, implementation and calling other necessary components.
 */
public class UserInterfaceBase {

    private Timer timer;
    private int counter = 0;
    private InputVerifier dblVerifier;
    private InputVerifier intVerifier;

    private final Sinusoidal sinusoidalSignal = new Sinusoidal(3.05, 0.88, 2.91);

    private JFormattedTextField t1;
    private JFormattedTextField t2;
    private JTextField t3;
    private JTextField t4;
    private JTextField t5;

    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;


    private JFrame frame;


    double t1Val = 1;
    int t2Val = 1;

    /** @function setUpDblVerifier
     *  Creates an InputVerifier object responsible for verification that textfield input is of Type Double and appropriate
     *  exception handling
     */
    private void setUpDblVerifier() {
        this.dblVerifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JFormattedTextField verificationTF = (JFormattedTextField) input;
                try{
                    double verifiedOutput = Double.parseDouble(verificationTF.getText());
                    verificationTF.setBackground(Color.WHITE);
                    return true;
                }catch(NumberFormatException numberFormatException){
                    verificationTF.setBackground(Color.PINK);
                    System.out.println("ERROR: Only Type DOUBLE allowed in textfield!");
                }
                return false;
            }
        };
    }

    /** @function setUpIntegerVerifier
     *  Creates an InputVerifier object responsible for verification that textfield input is of Type Integer and appropriate
     *  exception handling
     */
    private void setUpIntVerifier(){
        this.intVerifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JFormattedTextField verificationTF = (JFormattedTextField) input;
                try{
                    double verifiedOutput = Integer.parseInt(verificationTF.getText());
                    verificationTF.setBackground(Color.WHITE);
                    return true;
                }catch(NumberFormatException numberFormatException){
                    verificationTF.setBackground(Color.PINK);
                    System.out.println("ERROR: Only Type INTEGER allowed in textfield!");
                }
                return false;
            }
        };
    }

    /** @function initializeTextBoxes
     *  Responsible for creating the InputVerifier objects and initializing all textfield
     *  properties (e.g. size, edit properties, keylisteners)
     */
    private void initializeTextBoxes(){
        setUpDblVerifier();
        setUpIntVerifier();

        t1=new JFormattedTextField();
        t1.setValue(t1Val);
        t1.setBounds(50,70, 200,30);
        t1.setInputVerifier(this.dblVerifier);
        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    boolean isDouble = t1.getInputVerifier().verify(t1);
                    if(isDouble){
                        t1Val = Double.parseDouble(t1.getText());
                    }
                }
            }
        });

        t2=new JFormattedTextField();
        t2.setValue(t2Val);
        t2.setBounds(50,120, 200,30);
        t2.setInputVerifier(this.intVerifier);
        t2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    boolean isDouble = t2.getInputVerifier().verify(t2);
                    if(isDouble){
                        t2Val = Integer.parseInt(t2.getText());
                    }
                }
            }
        });

        t3 = new JTextField("Result");
        t3.setBounds(50, 170, 200, 30);
        t3.setEditable(false);

        t4 = new JTextField("Scaled Result");
        t4.setBounds(50, 220, 200, 30);
        t4.setEditable(false);

        t5 = new JTextField("Average Value");
        t5.setBounds(50, 270, 200, 30);
        t5.setEditable(false);
    }

    /** @function initializeLabels
     *  Responsible for creating and configuring all label properties (e.g. text, size)
     */
    private void initializeLabels(){
        l1 = new JLabel();
        l1.setBounds(50, 45, 200, 30);
        l1.setText("Scaling Value");

        l2 = new JLabel();
        l2.setBounds(50, 95, 200, 30);
        l2.setText("Sampling Time (in seconds)");

        l3 = new JLabel();
        l3.setBounds(50, 145, 200, 30);
        l3.setText("Scaled Value");

        l4 = new JLabel();
        l4.setBounds(50, 195, 200, 30);
        l4.setText("Average Sample Value");

        l5 = new JLabel();
        l5.setBounds(50, 245, 200, 30);
        l5.setText("Standard Deviation");
    }

    /** @function addComponentsToFrame
     *  Adds all labels and text fields to the JFrame
     */
    private void addComponentsToFrame(){
        frame.add(t1);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        frame.add(t5);

        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);
        frame.add(l5);
    }

    /** @function initializeUI
     *  Handles all necessary steps for initialization of the UI, instantiates the JFrame,
     *  calls all initialization functions and sets JFrame properties
     */
    private void initializeUI(){
        frame = new JFrame("DUI Assignment");

        initializeTextBoxes();
        initializeLabels();
        addComponentsToFrame();

        frame.setSize(320,400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /** @function startUI
     *  Starts the initialization of the UI, calls the Timer function and starts the timer
     */
    public void startUI() {
        initializeUI();
        updateUIeverySecond();
        timer.start();
    }

    /** @function updateUIeverySecond
     *  Responsible for (at a frequency of 1Hz or 1 sample per second) collecting the signal values (scaled, averages etc.)
     *  and updates the text boxes with the newly retrieved values.
     *
     *  Also updates the JFrame once finished with the updating of all elements.
     */
    public void updateUIeverySecond(){
        timer = new Timer(1000, e -> {
            double scaledResult = sinusoidalSignal.getScaledResult(counter, t1Val);
            double avgResult = sinusoidalSignal.getAverageResult(t2Val);
            double standardDev = new StandardDeviationCalculator().calculateSD(sinusoidalSignal.sinusoidalResults);

            t3.setText(String.valueOf(scaledResult));
            t4.setText(String.valueOf(avgResult));
            t5.setText(String.valueOf(standardDev));

            counter++;
        });

        frame.repaint();
    }

}
