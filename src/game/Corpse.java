package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Class representing Corpse that is able to rise as a Zombie
 */
public class Corpse extends PortableItem {
    private int count = 0;
    private Location location;
    private Actor actor;
    private String name;
    private GameMap map;

    /**
     * Constructor
     *
     * @param name: name of the corpse
     * @param map: GameMap
     */
    public Corpse(String name, GameMap map) {
        super(name + "'s corpse", '%');
        this.name = name;
        this.map = map;
    }

    /**
     * This method will increment the counter
     * @param currentLocation: current location of the actor
     * @param actor: the actor carrying the corpse
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        count+=1;
        this.location=currentLocation;
        this.actor = actor;
    }

    /**
     * This method will increment the counter
     * @param currentLocation: current location of the actor
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        count+=1;
        this.location=currentLocation;
        this.actor = null;
        if (count>=10){
            zombie_rise(map);
        }
    }

    /**
     * This method will create a new zombie, and remove the corpse from the map
     * @param map: currentMap
     */
    public void zombie_rise(GameMap map){
        Location here = this.location;
        if (location.getActor()==null){
            here.addActor(new Zombie(name));
        }
        else{
            Location next;
            if (here.y()+1 <= map.getYRange().max()){
                next = new Location(map, here.x(), here.y()+1);
            } else{
                next = new Location(map, here.x(), here.y()-1);
            }
            next.addActor(new Zombie(name));
        }
        location.removeItem(location.getItems().get(location.getItems().size()-1)); // drop the last item in the list, which is the corpse
        System.out.println(name + "'s corpse rise as a zombie");
    }
}
