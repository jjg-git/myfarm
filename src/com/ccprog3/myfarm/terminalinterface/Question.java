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
 * The parent class that inherits other types of questions.
 * @author John Joseph F. Giron
 */
public abstract class Question{

    /**
     * A Scanner object that must be the main's Scanner object.
     */
    protected final Scanner input;

    /**
     * A string that holds the text above the user input. Usually the text 
     * that displays above the user input.
     */
    protected final String caption;
    
    protected boolean hasBack;
    
    //private QuestionType type;
    
//    public Question(Scanner input, String caption, QuestionType type) {
    public Question(Scanner input, String caption) {
        this.input = input;
        this.caption = caption;
        this.hasBack = false;
        //this.type = type;
    }
    
    public abstract boolean invoke();
    
//    public QuestionType getType() {
//        return type;
//    }
    
    
    public void hasBackTest() {
        System.out.println("(In Question class) hasBack = " + hasBack);
    }
}
