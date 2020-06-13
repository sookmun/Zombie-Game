package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An action that a player used to aim at zombie
 *
 */
public class AimAction extends Action {
    private SniperRifle sniperRifle;
    private Actor target;

    public AimAction(Actor target, SniperRifle weapon){
        this.target=target;
        this.sniperRifle=weapon;
    }

    /**
     * Check whether its the first or second round of aiming, can cause different damage
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     *
     * @author Tan Sook Mun
     */
    public String execute(Actor actor, GameMap map){
        if(sniperRifle.getProbability()==0.75){//first round of aiming
            sniperRifle.setProbability(0.9);
            sniperRifle.double_damage();
        }
        else{ //second round of aiming
            sniperRifle.setProbability(1);
            sniperRifle.instakill(); // instant kill the target
            AttackAction attack= new AttackAction(target);
            return attack.execute(actor,map);
        }
        return actor + " aimed at "+target.toString();

    }

    public String menuDescription(Actor actor){return actor + " aims at " + target;}
}
