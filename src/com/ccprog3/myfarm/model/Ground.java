/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.model;

/**
 * A class that deals with soil tile, plant tile, rock tile, etc.
 * @author Dan
 */
public class Ground {
    private Crop crop;
    private boolean hasRock = false;
    private boolean isPlowed = false;
    private int row, column;
    
    /**
     * The constructor. Takes the grid's position.
     * @param row - the ground's row position in the game grid
     * @param column - the ground's column position in the game grid
     */
    public Ground (int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Removes the crop regardless the tool used. It only
     * sets the seed to null if the ground is holding a
     * seed, otherwise it returns false for unsuccessful
     * removal. It only checks for the presence of the seed,
     * so it does not care if the ground has a rock.
     * @return Returns true if the ground has a crop, withered
     * or not. Only returns false if the ground already does not
     * have anything.
     */
    public boolean removeCrop() {
        if (crop == null) {
            return false;
        }
        crop = null;
        return true;
    }
    
    /**
     * Plow the ground. Returns the Boolean status for error checking.
     * @return Returns true if the ground is successfully plowed.
     * It only returns false when the player attempts to plow on
     * the already plowed ground.
     */
    public boolean plowMe() {
        if (this.isPlowed) {
            // prompt the player that this ground
            // is plowed already
            return false;
        }
        
        // PLOW THAT SOIL
        this.isPlowed = true;
        return true;
    }
    
    /**
     * Plants the seed. Just setting the seed field with
     * the new seed in the parameter.
     * @param seed The new seed with given data (e.g. name,
     * type, lifetime, bonuses)
     * @return Returns true after successfully plants the seed.
     * It will return false otherwise when the ground has a seed 
     * already.
     */
    public boolean plant(Crop seed) {
        if (this.crop != null) {
            return false;
        }
        this.crop = seed;
        return true;
    }
    
    /**
     * Spawns a single rock on the ground. Sets the hasRock to
     * true. This ground only accepts actions with a pickaxe.
     * This function won't run if there is a seed there, withered
     * or not.
     * @return Returns true if the rock is successfully spawned.
     * If it has a seed, it returns false otherwise.
     */
    public boolean spawnRock() {
        if (this.crop != null) {
            return false;
        }
        this.hasRock = true;
        return true;
    }
    
    /**
     * Clears both seed and rocks. Also clears the
     * plowed status.
     */
    public void clear() {
        this.crop = null;
        this.hasRock = false;
        this.isPlowed = false;
    }
    
    /**
     * Destroys the rock of the selected ground. Simply
     * sets the hasRock Boolean to false.
     * @return Returns true if the rock is successfully
     * destroyed. It only returns false otherwise if
     * it does not have a rock in the first place.
     */
    public boolean destroyRock() {
        if (!hasRock) {
            return false;
        }
        hasRock = false;
        return true;
    }
    
    /**
     * Gets the row position on the grid.
     * One of the getters for gathering information about the
     * grid positioning.
     * @return Returns a non-negative integer.
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Gets the column position on the grid.
     * One of the getters for gathering information about the
     * grid positioning.
     * @return Returns a non-negative integer.
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Returns a seed object that the ground currently holds.
     * A getter method that returns the seed field.
     * @return Returns either a seed object or null.
     */
    public Crop getSeed() {
        return crop;
    }
    
    /**
     * Returns a Boolean value whether the ground has a rock
     * or not. A getter of the rock presence field.
     * @return Returns true if it has a rock. Returns false
     * otherwise.
     */
    public boolean getHasRock() {
        return hasRock;
    }
    
    /**
     * Returns a Boolean value that indicates the ground's
     * plow status. Useful to determine if the player can plant 
     * crops on that ground.
     * @return Returns true if it is already plowed. Returns false
     * otherwise.
     */
    public boolean isPlowed() {
        return isPlowed;
    }

    /**
     * Removes the plowed status of the ground.
     * @return Returns true if it successfully unplowed.
     */
    public boolean turnToUnPlowed() {
        if (!isPlowed) {
            return false;
        }
        isPlowed = false;
        return true;
    }
}
