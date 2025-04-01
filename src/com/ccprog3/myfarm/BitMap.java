/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm;

/**
 * The static Bitmap class. For bitmap manipulation. This is only used for
 * game over condition checking.
 * @author John Joseph F. Giron
 */
public class BitMap {

    /**
     * Gets the Boolean value of the bit of index position in the bitmap
     * @param bitmap The bitmap in question
     * @param position The position of the bit
     * @return Returns the Boolean representation of the bit
     * <br> 0 - false
     * <br> 1 - true
     */
    public static boolean getBitBool(int bitmap, int position) {
        // let bitmap   = 0010 1010 1011
        //     position = 4
        bitmap &= 1 << position; // 1 << 3 = 10 000   "add 4 zeros"
                                 // bitmap = bitmap & 1 000
                                 //             0010 1010 1011
                                 //          &  0000 0010 0000
                                 //          -----------------
                                 // bitmap =    0000 0010 0000
        
        bitmap >>= position;
        return bitmap == 1;
    }
    
    /**
     * Gets the integer value of the bit of index position in the bitmap
     * @param bitmap The bitmap in question
     * @param position The position of the bit
     * @return Returns the integer value (0 or 1) of the bit
     */
    public static int getBitInt(int bitmap, int position) {
        // let bitmap   = 0010 1010 1011
        //     position = 4
        bitmap &= 1 << position; // 1 << 4 = 10 000   "add 4 zeros"
                                 // bitmap = bitmap & 1 000
                                 //             0010 1010 1011
                                 //          &  0000 0010 0000
                                 //          -----------------
                                 // bitmap =    0000 0010 0000
        
        bitmap >>= position;
        return bitmap;
    }
    
    /**
     * Sets the bit of index position in the bitmap.
     * @param bitmap The bitmap in question
     * @param position The position of the bit
     * @param set the new bit to replace the old bit with.
     * @return Returns the changed bitmap. No integer pointer in Java, though.
     */
    public static int setBit(int bitmap, int position, boolean set) {
        int setInt = (set ? 1 : 0); // a conditional ternary operator
                                    // c = (condition ? a : b)
                                    // if condition is true, set it to 'a'
                                    // c = a
                                    // if condition is false, set it to 'b'
                                    // c = b
        
        // let bitmap   = 0010 1010 1011
        //     position = 3
                                    
        setInt <<= position;        // 1 << 2 = 100 "add 2 zeros"
        
        bitmap |= setInt;           // bitmap = bitmap | 1 000
                                    //             0010 1010 1011
                                    //          |  0000 0000 0100
                                    //          ----------------- 
                                    // bitmap =    0010 1010 1111
        
        return bitmap;
    }
}
