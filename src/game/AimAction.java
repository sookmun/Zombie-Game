package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class AimAction extends Action {
    private SniperRifle sniperRifle;
    private Actor target;

    public AimAction(Actor target, SniperRifle weapon){
        this.target=target;
        this.sniperRifle=weapon;
    }

    public String execute(Actor actor, GameMap map){
        //return two string and in the player check from there?
        if(sniperRifle.getProbability()==0.75){//first round of aiming
            sniperRifle.setProbability(0.9);
            sniperRifle.double_damage();
        }
        else{ //second round of aiming
            sniperRifle.setProbability(1);
            sniperRifle.instakill();
            AttackAction attack= new AttackAction(target);
            return attack.execute(actor,map);
        }
        return actor + " aimed at "+target.toString();

    }

    public String menuDescription(Actor actor){return actor + " aims at " + target;}
}
