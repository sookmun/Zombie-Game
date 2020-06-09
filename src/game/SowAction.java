package game;

import edu.monash.fit2099.engine.*;

import java.util.*;

/**
 * A sow action. To sow crops
 * Author:Tan Sook Mun
 */
public class SowAction extends Action {
    private Random rand = new Random();

    /**
     * add a new item, Crop on the ground
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String description of executed action
     */
    @Override
    public String execute(Actor actor, GameMap map){
        AroundLocation location = new AroundLocation(actor,map);
        for(Location loca : location.getLocation(actor,map) ){
            if (loca == map.locationOf(actor)){ //dont sow on the ground it is standing on
                break;
            }
            if(loca.getGround().getDisplayChar()-'.'==0){
                loca.setGround(new Crop());
                return actor.toString() + "sowed the ground";
            }
        }


        return null;


    }

    @Override
    public String menuDescription(Actor actor){return actor.toString() + " sow the ground"; }

    private ArrayList<ArrayList<Location>> getNextLayer(Actor actor, ArrayList<ArrayList<Location>> layer) {
        HashSet<Location> visitedLocations = new HashSet<Location>();
        ArrayList<ArrayList<Location>> nextLayer = new ArrayList<ArrayList<Location>>();

        for (ArrayList<Location> path : layer) {
            List<Exit> exits = new ArrayList<Exit>(path.get(path.size() - 1).getExits());
            Collections.shuffle(exits);
            for (Exit exit : path.get(path.size() - 1).getExits()) {
                Location destination = exit.getDestination();
                if (!destination.getGround().canActorEnter(actor) || visitedLocations.contains(destination))
                    continue;
                visitedLocations.add(destination);
                ArrayList<Location> newPath = new ArrayList<Location>(path);
                newPath.add(destination);
                nextLayer.add(newPath);
            }
        }
        return nextLayer;
    }

}
