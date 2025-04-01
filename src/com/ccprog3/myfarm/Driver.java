/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */

package com.ccprog3.myfarm;

import java.util.Scanner;

/**
 * The driver class of MyFarm game.
 * @author John Joseph F. Giron
 */
public class Driver {
    /**
     * The entry point of the program.
     * @param args the usual parameters of the main function.
     */
    public static void main(String[] args){
//        Game currentGame = new Game(1, 1);
        Game currentGame = new Game(10, 10, 7);
//        Game currentGame = new Game();
        Action gameAction = new Action(currentGame);
        
        Scanner mainInput = new Scanner(System.in);
        
//        gameAction.playTerminal(mainInput);
        gameAction.playGUI();
    }
}
