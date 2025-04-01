/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.terminalinterface;

import java.util.Scanner;

/**
 * A class that simplifies the building of multiple choice type question
 * in terminals.
 * 
 * @author John Joseph F. Giron
 */
public class IntegerInput extends Question{
    private int integerChoice = -1;
    private int min, max;
    /**
     * Prepares the number input type question. Only accepts
 integer number; no decimals!
     * @param input main's Scanner object
     * @param caption displays long or short text describing
     * the context of the question.
     * @param min the minimum number beyond which the user must not
     * input the number.
     * @param max the maximum number beyond which the user must not
     * input the number.
     */
    public IntegerInput(Scanner input, String caption, int min, int max) {
//        super(input, caption, QuestionType.MultipleChoice);
        super(input, caption);
        this.min = min;
        this.max = max;
    }

    /**
     * Prepares the number input type question. Only accepts
     * integer number; no decimals! Only accepts a number
     * from 0 to your max.
     * @param input main's Scanner object
     * @param caption displays long or short text describing
     * the context of the question.
     * @param max the maximum number beyond which the user must not
     * input the number.
     */
    public IntegerInput(Scanner input, String caption, int max) {
//        super(input, caption, QuestionType.MultipleChoice);
        this(input, caption, 0, max);
    }

    /**
     * Prepares the number input type question. Only accepts
     * integer number; no decimals! The minimum is zero, and
     * the maximum is set to 1000.
     * @param input main's Scanner object
     * @param caption displays long or short text describing
     * the context of the question.
     */
    public IntegerInput(Scanner input, String caption) {
//        super(input, caption, QuestionType.MultipleChoice);
        this(input, caption, 0, 1000);
    }
    
    /**
     * Displays the number input type question and prompts
     * the user to type a number.
     * @return Returns true if the input is valid. Returns false
     * otherwise
     */
    @Override
    public boolean invoke() {
        System.out.println(super.caption);
        
        System.out.println("Input: ");
        if (super.input.hasNextInt()) {
            integerChoice = super.input.nextInt();
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
        return integerChoice >= min && integerChoice <= max;
    }
    
    /**
     * Returns the valid integer choice the user inputted.
     * @return returns the valid integer choice the user inputted.
     */
    public int getAnswer() {
        return integerChoice;
    }
}
