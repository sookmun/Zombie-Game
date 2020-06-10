package game;

import edu.monash.fit2099.engine.WeaponItem;

public class SniperRifle extends WeaponItem{
    private int bullets;
    private double probability;
    private int damage=50;

    public SniperRifle() {
        super("Sniper Rifle", 'R', 50, "snipe");
        addCapability(WeaponCapability.LONGRANGE);
        bullets=0;
        probability=0.75;

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

    public void setProbability(double newProbability){ probability=newProbability;}

    public double getProbability() {
        return probability;
    }
}
