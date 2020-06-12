package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * Can aim at a target using sniper rifle and can cause more damage if aimed
 * @author Tan Sook Mun
 */
public class SniperRifle extends WeaponItem{
    private int bullets;
    private double probability;
    private int damage;
    private Actor target;
    private Boolean aim;

    /**
     * A constructor for Sniper rifle. It has a capability Long Rage. Initial probabilty is 0.75 and damage 35 and the aim is
     * false. because it is not being aim
     * @author Tan Sook Mun
     */
    public SniperRifle() {
        super("Sniper Rifle", 'R', 35, "snipe");
        addCapability(WeaponCapability.LONGRANGE);
        bullets=0;
        probability=0.75;
        damage=35;
        aim=false;
    }

    /**
     * Load bullets into sniper rifle
     * @param noOfBullets number of bullets to be loaded
     */
    public void loadBullets(int noOfBullets){
        bullets+=noOfBullets;
    }

    /**
     * Reduce one bullet when fired
     */
    public void fired(){
        bullets-=1;
    }

    /**
     * get the number of butters
     * @return the number of bullets
     */
    public int getBullets(){
        return bullets;
    }

    public void setProbability(double newProbability){ probability=newProbability;}

    public double getProbability() {
        return probability;
    }

    @Override
    public int damage(){
        return damage;
    }
    public void setDamage(int damage){
        this.damage=damage;
    }

    /**
     * A method that cause double the amount of damage to the target
     */
    public void double_damage(){
        damage+=damage;
    }

    /**
     * A method that allow the target to be killed instantly
     */
    public void instakill(){
        damage=110;
    }
    public void setTarget(Actor target){
        this.target=target;
    }
    public Actor getTarget(){return target;}

    /**
     * Checks if weapon is being aim
     * @return a boolean of whether sniper is being aim
     */
    public Boolean getAim() {
        return aim;
    }

    public void setAim(Boolean aim) {
        this.aim = aim;
    }

    /**
     * After fire the weapon, reset all of its values back to original state
     */
    public void reset(){
        setDamage(35);
        setAim(false);
        setProbability(0.75);
        setTarget(null);
    }
}
