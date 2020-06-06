package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class MamboMarie extends ZombieActor {
//    private Behaviour behaviour = new WanderBehaviour();
    protected int create_zombie_count = 0;
    protected  int vanish_count = 0;
    protected Random rand = new Random();

    private Behaviour[] behaviours = {
            new AttackBehaviour(ZombieCapability.UNDEAD),
            new WanderBehaviour()
    };


    public MamboMarie() {
        super("MAMBO MARIE" , 'V', 100, ZombieCapability.UNDEAD);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        tick();
        System.out.println(create_zombie_count);
        if (vanish_count == 30){
            return new VanishAction();
        }
        if (create_zombie_count == 10){
            create_zombie_count = 0;
            return new ChantAction();
        }

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;

        }
        return new DoNothingAction();
    }


    public void tick(){
        create_zombie_count +=1;
        vanish_count += 1;
    }
}
