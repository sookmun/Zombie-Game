package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class MamboMarie extends ZombieActor {
    private Behaviour behaviour = new WanderBehaviour();
    protected int create_zombie_count = 0;
    protected  int vanish_count = 0;
    protected Random rand = new Random();


    public MamboMarie() {
        super("MAMBO MARIE" , 'V', 100, ZombieCapability.ALIVE);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        tick();
        if (vanish_count == 30){
            return new VanishAction();
        }
        if (create_zombie_count ==10){
            create_zombie_count =0;
            return new ChantAction();
        }

        return behaviour.getAction(this, map);
    }


    public void tick(){
        create_zombie_count +=1;
        vanish_count += 1;
    }
}
