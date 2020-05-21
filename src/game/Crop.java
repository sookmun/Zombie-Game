package game;

import edu.monash.fit2099.engine.Item;

public class Crop extends Item {
    public Crop(){
        super("Crop",'C',false);
        addCapability(CropCapability.Unripe);
    }
}
