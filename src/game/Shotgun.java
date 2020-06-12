package game;

import edu.monash.fit2099.engine.*;

import java.util.HashSet;


/**
 * A Shotgun has a short range, does area damage
 * @author Tan Sook Mun
 */
public class Shotgun extends WeaponItem {
    private int bullets=0;
    private HashSet<Location> visitedLocations = new HashSet<Location>();

    public Shotgun(){
        super("Shotgun", 'S',50,"shoots");
        addCapability(WeaponCapability.SHORTRANGE);
    }

    /**
     * A method to load bullets
     * @param noOfBullets   number of bullets to be loaded
     */
    public void loadBullets(int noOfBullets){
        bullets+=noOfBullets;
    }

    /**
     * A method that reduce the number of bullets when fired
     */
    public void fired(){
        bullets-=1;
    }

    /**
     * getter for the number of
     * @return
     */
    public int getBullets(){
        return bullets;
    }

}
