package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponItem;

public class SniperRifle extends WeaponItem{
    private int bullets;
    private double probability;
    private int damage;
    private Actor target;
    private Boolean aim;


    public SniperRifle() {
        super("Sniper Rifle", 'R', 35, "snipe");
        addCapability(WeaponCapability.LONGRANGE);
        bullets=0;
        probability=0.75;
        damage=35;
        aim=false;

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

    @Override
    public int damage(){
        return damage;
    }
    public void setDamage(int damage){
        this.damage=damage;
    }

    public void double_damage(){
        damage+=damage;
    }
    public void instakill(){
        damage=110;
    }
    public void setTarget(Actor target){
        this.target=target;
    }
    public Actor getTarget(){return target;}

    public Boolean getAim() {
        return aim;
    }

    public void setAim(Boolean aim) {
        this.aim = aim;
    }

    public void reset(){
        setDamage(35);
        setAim(false);
        setProbability(0.75);
        setTarget(null);
    }
}
