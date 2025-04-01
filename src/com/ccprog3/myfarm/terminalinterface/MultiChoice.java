/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.terminalinterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class that simplifies the building of multiple choice type question
 * in terminals.
 * 
 * @author John Joseph F. Giron
 */
public class MultiChoice extends Question{
    private int chosenChoice = -1;
    private ArrayList<String> choices;

    /**
     * Prepares the multiple choice type question.
     * @param input main's Scanner object
     * @param caption displays long or short text describing
     * the context of the question.
     * @param hasBack if true, adds a "back" option, reverting to the
     * previous question
     * @param choices accepts multiple string arguments and store
     * them into the list of choices. First string will be set
     * at choice 1.
     */
    public MultiChoice(Scanner input, boolean hasBack, 
                       String caption, String... choices) {
//        super(input, caption, QuestionType.MultipleChoice);
        super(input, caption);

        super.hasBack = hasBack;
        //this.choices = new String[5];
        this.choices = new ArrayList<String>();
        /*
        for (int i = 0; i < choices.length; i++) {
            this.choices[i] = choices[i];
        }
        */
        
        // Does the same thing that the above code (line 39-41) do,
        // transferring array elements to another array.
        
        this.choices.addAll(Arrays.asList(choices));
    }
    
    /**
     * Displays the multiple choice type question and prompts
     * the user to type the number that matches their choice.
     * @return Returns true if the input is valid. Returns false
     * otherwise
     */
    @Override
    public boolean invoke() {
        System.out.println(super.caption);
        
        // Listing all the choices
        for (int i = 0; i < choices.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + choices.get(i));
        }
        
        if (hasBack) {
            System.out.println("[0] Back");
        }

        
        System.out.println("Input: ");
        if (super.input.hasNextInt()) {
            chosenChoice = super.input.nextInt();
            if (!withinRange()) {
                System.out.println("""
                                   Error. Input is out of the accepted 
                                   options. Type one of the number above that 
                                   matches your option.""");
                return false;
            }
        }
        else {
            super.input.nextLine();
            System.out.println("""
                               Error. Input must be a number that is not 
                               decimal nor any characters.""");
            return false;
        }
        
        return true;
    }
    
    private boolean withinRange() {
        int min = 1;
        if (hasBack) {
            min = 0;
        }
        return chosenChoice >= min && chosenChoice <= choices.size();
    }
    
    /**
     * Returns the valid integer choice the user inputted.
     * @return returns the valid integer choice the user inputted.
     */
    public int getAnswer() {
        return chosenChoice;
    }
    
    public int getSize() {
        return choices.size();
    }
    
    public void addAnswer(String answer) {
        this.choices.add(answer);
    }
    
    public void addAnswer(String... answer) {
        this.choices.addAll(Arrays.asList(answer));
    }
    
    public void replaceListAnswer(String ... answers) {
        this.choices.clear();
        this.choices.addAll(Arrays.asList(answers));
    }
    
    public void replaceListAnswer(ArrayList<String> answers) {
        this.choices.clear();
        this.choices.addAll(answers);
    }
}
