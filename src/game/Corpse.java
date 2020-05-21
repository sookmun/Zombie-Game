package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class Corpse extends PortableItem {
    private int count = 0;
    private Location location;
    private Actor actor;
    private String name;
    private GameMap map;

    public Corpse(String name, GameMap map) {
        super("corpse", '%');
        this.name = name;
        this.map = map;
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        count+=1;
        this.location=currentLocation;
        this.actor = actor;
    }

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

    public void zombie_rise(GameMap map){
        Location here = this.location;
        if (location.getActor()==null){
            here.addActor(new Zombie(name));
        }
        else{
            Location next = new Location(map, here.x(), here.y()+1);
            next.addActor(new Zombie(name));
        }
        location.removeItem(location.getItems().get(location.getItems().size()-1)); // drop the last item in the list, which is the corpse
        System.out.println(name + "'s corpse rise as a zombie");
    }
}
