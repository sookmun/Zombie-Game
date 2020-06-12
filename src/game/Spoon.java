package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.Random;

public class Spoon extends WeaponItem {
    private int count = 0;
    private final int max = 15;
    private Random rand = new Random();

    public Spoon(){
        super("A Spoon", 'M',80, "wallop");
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        count+=1;
        if (count>=max){
            if (rand.nextDouble() <0.3){
                actor.addItemToInventory(new Food());
                count=0;
            }
        }
    }


}
