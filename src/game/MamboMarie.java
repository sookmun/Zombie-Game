package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class MamboMarie extends ZombieActor {
    protected int create_zombie_count = 0;
    protected  int vanish_count = 0;
    protected Random rand = new Random();
    protected boolean alive = true;
    protected SubWorld subWorld;

    private Behaviour[] behaviours = {
            new AttackBehaviour(ZombieCapability.UNDEAD),
            new WanderBehaviour()
    };


    public MamboMarie(SubWorld world) {
        super("MAMBO MARIE" , 'V', 100, ZombieCapability.UNDEAD);
        this.subWorld = world;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        tick();
        if (vanish_count == 30){
            return new VanishAction();
        }
        if (create_zombie_count == 10){
            create_zombie_count = 0;
            return new ChantAction();
        }

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action instanceof AttackAction){
                if (!(((AttackAction) action).target instanceof Zombie)){    // can't attack Zombie, only Human and Player
                    return action;
                }
            }
            else if (action != null){
                return action;
            }
        }
        return new DoNothingAction();
    }


    public void tick(){
        create_zombie_count +=1;
        vanish_count += 1;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void is_killed(){
        if (!isAlive()){
            this.subWorld.mambo_alive = false;
        }
    }


}
