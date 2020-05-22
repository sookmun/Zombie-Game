package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A sow action. To sow crops
 * Author:Tan Sook Mun
 */
public class SowAction extends Action {
    private Random rand = new Random();
    private int[][] values ={{1,-1},{1,0},{1+1},{0,-1},{0,1},{-1,1},{-1,0},{-1,-1}};
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
            if(loca.getGround().getDisplayChar()-'.'==0){
                loca.setGround(new Crop());
                return actor.toString() + "sowed the ground";
            }


        }


        return null;


    }

    @Override
    public String menuDescription(Actor actor){return actor.toString() + " sow the ground"; }

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
        return locations;
    }
}
