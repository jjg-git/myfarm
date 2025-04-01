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
 *
 * @author John Joseph F. Giron
 */
public class GridInput extends Question{
    private int x;
    private int y;
    
    private final int width, height;
    
    public GridInput(Scanner input, int width, int height, 
                     String caption) {
        super(input, caption);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public boolean invoke() {
        int xx, yy;
        System.out.println(caption);
        
        // Instruction
        System.out.println("""
                           (x is any one of the numbers
                           arranged horizontally at the top of the board)""");
        System.out.println("""
                           (y is any one of the number 
                           arranged vertically at the left side of the board)
                           """);
        
        System.out.println("Input:");
        System.out.print("x = ");
        if (super.input.hasNextInt()) {
            xx = super.input.nextInt();
        }
        else {
            super.input.nextLine();
            System.out.println("""
                               Error! Invalid input! Avoid using
                               letters, symbols, decimals, or
                               exponent form (e.g. 1.02E2).""");
            return false;
        }
        
        System.out.print("y = ");
        if (super.input.hasNextInt()) {
            yy = super.input.nextInt();
        }
        else {
            super.input.nextLine();
            System.out.println("""
                               Error! Invalid input! Avoid using
                               letters, symbols, decimals, or
                               exponent form (e.g. 1.02E2).""");
            return false;
        }
        
        setX(xx);
        setY(yy);
        
        if (this.x >= this.width || this.y >= this.height ||
            this.x < 0 || this.y < 0) {
            System.out.println("""
                               Error! Either x or y is outside the board""");
            return false;
        }
        
        return true;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
}
