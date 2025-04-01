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
 * A class that simplifies the building of string input type question
 * in terminals.
 * @author Dan
 */
public class LineInput extends Question{
    private String inputStr;
    
    /**
     * Creates an object that handles the input-type question
     * @param input the main's Scanner object
     * @param caption displays long or short text describing
     *                the context of the question.
     */
    public LineInput(Scanner input, String caption) {
//        super(input, caption, QuestionType.LineInput);
        super(input, caption);
    }
//
//    public LineInput() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
    /**
     * Generates the question into terminal. This shows the question
     * (or caption) and the blinking cursor that indicates user input.
     * @return returns true for successful input. No cases result to false
     * return value.
     */
    @Override
    public boolean invoke() {
        System.out.println(super.caption);
        System.out.println("Input: ");
        
        inputStr = super.input.nextLine();
        
        return true;
    }
    
    /**
     * Returns the user's string input
     * @return Returns the user's string input
     */
    public String getString() {
        return inputStr;
    }
}
