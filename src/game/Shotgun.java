package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class Shotgun extends WeaponItem {
    private int bullets=0;
    private HashSet<Location> visitedLocations = new HashSet<Location>();



    public Shotgun(){
        super("Shotgun", 'S',50,"shoots");
        addCapability(WeaponCapability.SHORTRANGE);
    }

    public void loadBullets(int noOfBullets){
        bullets+=noOfBullets;
    }

    public void fired(){
        bullets-=1;
    }

    public int getBullets(){
        return bullets;
    }

}
