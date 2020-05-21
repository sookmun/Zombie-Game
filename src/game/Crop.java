package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Crop extends Item {
    private int count;
    public Crop(){
        super("Crop",'C',false);
        addCapability(CropCapability.Unripe);
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        System.out.println("first tick");
        count+=1;
        if (count==5){
            removeCapability(CropCapability.Unripe);
            addCapability(CropCapability.Ripe);
            count=0;
        }
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);

    }
}
