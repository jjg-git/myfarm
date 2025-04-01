/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.model;

import javax.swing.ImageIcon;

/**
 * The CropInfo class. This mostly contains constant fields that is accessible 
 * to all game-related classes.
 * @author John Joseph F. Giron
 */
public class CropInfo {    

    /**
     * Instead of typing the string itself, this is used for setting values
     * and passing arguments.
     */
    public static final String seedList[] = {
        // Root crop
        "Turnip",
        "Carrot",
        "Potato",
        // Flower
        "Rose",
        "Turnip Flower",
        "Sunflower",
        // Fruit Tree
        "Mango Tree",
        "Apple Tree"
    };
    
    /**
     * Contains the lifetime of all crop types.
     */
    public static final int maxHarvestTimeList[] = {
        2,  // Turnip
        3,  // Carrot
        5,  // Potato
        1,  // Rose
        2,  // Turnip Flower
        3,  // Sunflower
        10, // Mango
        10  // Apple
    };
    
    /**
     * Contains the required number of times to water for all crop types.
     */
    public static final int waterNeeds[][] = {
        // Required  Bonus
           {1,       2},  // Turnip
           {1,       2},  // Carrot
           {3,       4},  // Potato
           {1,       2},  // Rose
           {2,       3},  // Turnip Flower
           {2,       3},  // Sunflower
           {7,       7},  // Mango
           {7,       7}   // Apple
    };
    
    /**
     * Contains the required number of times to fertilize for all crop types.
     */
    public static final int fertilizerNeeds[][] = {
        // Required  Bonus
           {0,       1},  // Turnip
           {0,       1},  // Carrot
           {1,       2},  // Po tato
           {0,       1},  // Rose
           {0,       1},  // Turnip Flower
           {1,       2},  // Sunflower
           {4,       4},  // Mango
           {5,       5}   // Apple
    };
    
    /**
     * Contains arrays that store minimum and maximum of the range of
     * number of produce.
     */
    public static final int produceRange[][] = {
        // Min  Max
           {1,  2},  // Turnip
           {1,  2},  // Carrot
           {1,  10},  // Potato
           {1,  1},  // Rose
           {1,  1},  // Turnip Flower
           {1,  2},  // Sunflower
           {5,  12},  // Mango
           {10, 15}   // Apple
    };
    
    /**
     * Contains the costs of each seed types.
     */
    public static final int seedCost[] = {
        5,    // Turnip
        10,   // Carrot
        20,   // Potato
        5,    // Rose
        10,   // Turnip Flower
        20,   // Sunflower
        100,  // Mango
        200,  // Apple
    };
    
    /**
     * Contains the selling cost of each seed types.
     */
    public static final int sellPiece[] = {
        6,   // Turnip
        9,   // Carrot
        3,   // Potato
        5,   // Rose
        9,   // Turnip Flower
        19,  // Sunflower
        8,   // Mango
        5    // Apple
    };
    
    /**
     * Contains the EXP yielded amount from harvesting each seed types.
     */
    public static final float expYield[] = {
        5.0f,   // Turnip
        7.5f,   // Carrot
        12.5f,  // Potato
        2.5f,   // Rose
        5.0f,   // Turnip Flower
        7.50f,  // Sunflower
        25.0f,  // Mango
        25.0f   // Apple
    };
    
    public static final int TURNIP = 0;
    public static final int CARROT = 1;
    public static final int POTATO = 2;
    public static final int ROSE = 3;
    public static final int TURNIP_FLOWER = 4;
    public static final int SUNFLOWER = 5;
    public static final int MANGO = 6;
    public static final int APPLE = 7;
    
    public static final int MIN = 0;
    public static final int MAX = 1;
    
}
