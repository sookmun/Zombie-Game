package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

public class AroundLocation {
    private Actor actor;
    private GameMap map;
    public AroundLocation(Actor actor, GameMap map){
        this.actor=actor;
        this.map=map;
    }
    public ArrayList<Location> getLocation(Actor actor, GameMap map){
        int x = map.locationOf(actor).x();
        int y = map.locationOf(actor).y();
        ArrayList<Location> locations = new ArrayList<>();
        if (map.getXRange().contains(x+1)){
            locations.add(map.at(x+1,y));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x,y+1));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x,y-1));
        }
        if (map.getYRange().contains(y+1)){
            locations.add(map.at(x-1,y));
        }
        if (map.getYRange().contains(x+1)&&map.getXRange().contains(y-1)){
            locations.add(map.at(x+1,y-1));
        }
        if (map.getXRange().contains(x-1) &&map.getYRange().contains(y-1)){
            locations.add(map.at(x-1,y-1));
        }
        if (map.getXRange().contains(x+1) &&map.getYRange().contains(y+1)){
            locations.add(map.at(x-1,y-1));
        }
        if (map.getXRange().contains(x-1) &&map.getYRange().contains(y-1)){
            locations.add(map.at(x-1,y+1));
        }
        locations.add(map.at(x,y));
        return locations;
    }
}
