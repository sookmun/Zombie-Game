package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * is an item that can be sown, harvest and turn into food. During initialisation it has Unripe capability
 */
public class Crop extends Ground {
    private int count;
    public Crop(){
        super('C');
        addCapability(CropCapability.Unripe);
    }

    @Override
    /**
     * overiding a tick method to count the number of turns and if it reaches 20, the crop will ripe
     */
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        count+=1;
        if (count>=20){
            removeCapability(CropCapability.Unripe);
            addCapability(CropCapability.Ripe);
            count=0;
        }
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * actor cant throw objects here
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }
    /**
     * A function to add the counter when farmer fertilize
     */
    public void fertilize(){
        count=+10;
    }

}
