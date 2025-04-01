/** This is to certify that this project is my own work, based on my personal efforts in studying
* and applying the concepts learned. I have constructed the functions and their respective
* algorithms and corresponding code by myself. The program was run, tested, and
* debugged by my own efforts. I further certify that I have not copied in part or whole or
* otherwise plagiarized the work of other students and/or persons.
* John Joseph F. Giron, 12198129
 */
package com.ccprog3.myfarm.model;

import com.ccprog3.myfarm.Action;

/**
 * The Crop class. The following fields are contained in this class:
 * <br> type ("Root crop", "Flower", or "Fruit Tree")
 * <br> name - the name of the crop
 * <br> waterPassed - Boolean variable that checks if the crop is watered 
 * enough.
 * <br> fertilizerPassed - Boolean variable that checks if the crop is 
 * fertilized enough.
 * <br> harvestable - Boolean variable if the crop is ready to be harvested.
 * <br> waterGiven - the number of times the crop is watered.
 * <br> fertilizerGiven - the number of times the crop is fertilized
 * <br> dayPassed - the age of the crop
 * <br> isWithered - Boolean variable that checks for the crop's withered
 * status.
 * @author John Joseph F. Giron
 */
public class Crop {
    private String type;
    private String name;
    
    // water/fertilizerPassed[0] - if the player has done enough time
    // water/fertilizerPassed[1] - if the player has reached the bonus
    private boolean[] waterPassed;
    private boolean[] fertilizerPassed;
    
    private boolean harvestable;
    
    private int waterGiven = 0;
    private int fertilizerGiven = 0;
    
    private int dayPassed = 0;
    
    private int harvestTime;
    
    private boolean isWithered = false;
    
    /**
     * The constructor. Takes the selected ground and the name of the seed.
     * @param tile The ground selected from the planting process
     * @param seed The name of the seed. This determines the type of the seed.
     */
    public Crop(Ground tile, String seed) {
                                          // {needed, bonus}
        this.fertilizerPassed = new boolean[]{false, false};
        
                                     // {needed, bonus}
        this.waterPassed = new boolean[]{false, false};
        
        this.harvestTime = 0;
        this.name = seed;
        
        switch (seed) {
            case "Turnip", 
                 "Carrot", 
                 "Potato" ->    this.type = "Root crop";
            
            case "Rose", 
                 "Turnips", 
                 "Sunflower" -> this.type = "Flower";
            
            case "Mango Tree",
                 "Apple Tree" ->     this.type = "Fruit Tree";
        }
    }
    
    /**
     * Ages the crop. Also sets the Boolean fields when the conditions are met.
     */
    public void age() {
        dayPassed += 1;
        
        if (dayPassed == CropInfo.maxHarvestTimeList[getSeedIdx()]) {
            isWithered = (!getWaterPassed() && !getFertilizerPassed());
            harvestable = getWaterPassed() && getFertilizerPassed();
        }
        else if (dayPassed > CropInfo.maxHarvestTimeList[getSeedIdx()]) {
            isWithered = true;
            harvestable = false;
        }
    }
    
    /**
     * Gives water to the crop. The condition changes depending on the player's
     * farmer type.
     * @param farmerStatus The integer representation of the player's farmer
     * status
     */
    public void giveWater(int farmerStatus) {
        int levelUpBenefit = Action.levelUpBenefits[farmerStatus][Action.WATER_BONUS_LIMIT];
        
        waterGiven += 1;
        
        waterPassed[0] = (waterGiven >= getWaterNeeds());
        waterPassed[1] = (waterGiven >= getWaterBonus() + levelUpBenefit);
    }
    
    /**
     * Gives fertilizer to the crop. The condition changes depending on the player's
     * farmer type.
     * @param farmerStatus The integer representation of the player's farmer
     * status
     */    
    public void giveFertilizer(int farmerStatus) {
        int levelUpBenefit = Action.levelUpBenefits[farmerStatus][Action.FERTILIZER_BONUS_LIMIT];
        
        fertilizerGiven += 1;
        
        fertilizerPassed[0] = (fertilizerGiven >= getFertilizerNeeds());
        fertilizerPassed[1] = (fertilizerGiven >= getFertilizerBonus() + levelUpBenefit);
    }
    
    // GETTERS AND SETTERS of Private Attributes
    
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getWaterGiven() {
        return waterGiven;
    }

    public int getFertilizerGiven() {
        return fertilizerGiven;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    /**
     * Returns the Boolean water status. Also updates the water status.
     * @return Returns the Boolean water status.
     */
    public boolean getWaterPassed() {
        waterPassed[0] = (waterGiven >= getWaterNeeds());
        return waterPassed[0];
    }

    /**
     * Returns the Boolean fertilize status. Also updates the fertilize status.
     * @return Returns the Boolean fertilize status.
     */
    public boolean getFertilizerPassed() {
        fertilizerPassed[0] = (fertilizerGiven >= getFertilizerNeeds());
        return fertilizerPassed[0];
    }
    
    /**
     * Returns the Boolean water bonus status. Also updates the water bonus status.
     * @param farmerStatus The integer representation of the player's farmer
     * status
     * @return Returns the Boolean water bonus status.The condition changes depending on the player's
     * farmer type.
     */
    public boolean hasWaterBonus(int farmerStatus) {
        int levelUpBenefit = Action.levelUpBenefits[farmerStatus][Action.WATER_BONUS_LIMIT];
        waterPassed[1] = (waterGiven >= getWaterBonus() + levelUpBenefit);
        return waterPassed[1];
    }
    
    /**
     * Returns the Boolean fertilize bonus status. Also updates the water bonus status.
     * The condition changes depending on the player's farmer type.
     * @return Returns the Boolean fertilize bonus status.
     * @param farmerStatus The integer representation of the player's farmer
     * status
     */
    public boolean hasFertilizerBonus(int farmerStatus) {
        int levelUpBenefit = Action.levelUpBenefits[farmerStatus][Action.WATER_BONUS_LIMIT];
        fertilizerPassed[1] = (fertilizerGiven >= getFertilizerBonus() + levelUpBenefit);
        return fertilizerPassed[1];
    }

    /**
     * Crop's age
     * @return Returns how many days this crop is living.
     */
    public int getDayPassed() {
        return dayPassed;
    }


    /**
     * The crop's withered status
     * @return Returns if the crop is withered
     */
    public boolean isWithered() {
        return isWithered;
    }

    /**
     * The state where whether the crop is ready to be harvested.
     * @return Returns the crop's harvest status
     */
    public boolean isHarvestable() {
        return harvestable;
    }

    /**
     * The integer representation of the crop's type
     * @return Returns the integer index of the crop's type
     */
    private int getSeedIdx() {
        int i = 0;
        for (i = 0; i < CropInfo.seedList.length; i++) {
            if (this.name.equals(CropInfo.seedList[i])) {
                break;
            }
        }
        return i;
    }

    public int getMaxHarvestTimeList() {
        return CropInfo.maxHarvestTimeList[getSeedIdx()];
    }

    public int getWaterNeeds() {
        return CropInfo.waterNeeds[getSeedIdx()][0];
    }

    public int getFertilizerNeeds() {
        return CropInfo.fertilizerNeeds[getSeedIdx()][0];
    }
    
    public int getWaterBonus() {
        return CropInfo.waterNeeds[getSeedIdx()][1];
    }

    public int getFertilizerBonus() {
        return CropInfo.fertilizerNeeds[getSeedIdx()][1];
    }

    public int[] getProduceRange() {
        return CropInfo.produceRange[getSeedIdx()];
    }

    public int getSellPiece() {
        return CropInfo.sellPiece[getSeedIdx()];
    }

    public float getExpYield() {
        return CropInfo.expYield[getSeedIdx()];
    }
    
    public int getCropIdx() {
        int idx = 0;
        
        for (; idx < CropInfo.seedList.length; idx++) {
            if (name.equals(CropInfo.seedList[idx])) {
                break;
            }
        }
        
        return idx;
    }
}
