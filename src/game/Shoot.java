package game;

import edu.monash.fit2099.engine.*;

import java.util.*;

public class Shoot extends Action{
    private Item weaponItem;
    private Boolean flag=false;
    private HashSet<Location> visitedLocations = new HashSet<Location>();
    private List<String> direction = new ArrayList<String>(){{
        add("West");
        add("North");
        add("South-East");
        add("South");
        add("South-West");
        add("North-West");
        add("East");
        add("North-East");
    }

    };


    public Shoot(Item weaponItem){
        this.weaponItem =weaponItem;
    }

    public String execute(Actor actor, GameMap map){
        String result="";
        if (weaponItem instanceof Shotgun){
            checkBullets(actor, "S");
            if(((Shotgun) weaponItem).getBullets() > 0) {
                result = shortrange(actor, map, direction.get(0));
                ((Shotgun) weaponItem).fired();
            }
            else{
                return "Cant shoot no bulets.Search at town for Ammunition";
            }
        }
        else{
            checkBullets(actor, ":");
            if(((SniperRifle) weaponItem).getBullets() > 0) {
                result = shortrange(actor, map, direction.get(0));
                ((SniperRifle) weaponItem).fired();
            }
            else{
                return "Cant shoot no bulets.Search at town for  Ammunition";
            }

        }
        return result;
    }

    public String menuDescription(Actor actor ){ return actor + " shoots";}

    private void checkBullets(Actor actor,String ammunition){
        Item ammu= null;
        for (Item item: actor.getInventory()){
            if (item instanceof ShotgunAmmunition && ammunition.equals("S")){ //a box of ammunition has 3 bullets
                ((Shotgun) weaponItem).loadBullets(3);
                ammu=item;

            }
            if (item instanceof SniperRifleAmmunition && ammunition.equals(":")){
                ((SniperRifle) weaponItem).loadBullets(1);
            }
        }
        if( ammu != null)
            actor.removeItemFromInventory(ammu);

    }


    public String shortrange(Actor actor, GameMap map, String direction){
        String result= "";
        visitedLocations.clear();
        ArrayList<Location> now = new ArrayList<Location>();
        now.add(map.locationOf(actor));
        ArrayList<ArrayList<Location>> layer = new ArrayList<ArrayList<Location>>();
        layer.add(now);


        for (int i = 0; i<3; i++) {
            layer = getNextLayer(direction, layer);
            ArrayList<Location> there = search(layer);
            for (Location loca : there){
                if (loca != null) {
                    AttackAction attack = new AttackAction(loca.getActor());
                    result +=attack.execute(actor,map);
                    result += '\n';

                }
            }

        }

        return result;

    }
    private ArrayList<ArrayList<Location>> getNextLayer(String direction, ArrayList<ArrayList<Location>> layer) {
        ArrayList<ArrayList<Location>> nextLayer = new ArrayList<ArrayList<Location>>();

        for (ArrayList<Location> path : layer) {
//            List<Exit> exits = new ArrayList<Exit>(path.get(path.size() - 1).getExits());
            for (Exit exit : path.get(path.size() - 1).getExits()) {
                    Location destination = exit.getDestination();
                    if (visitedLocations.contains(destination))
                        continue;
                    visitedLocations.add(destination);
                    ArrayList<Location> newPath = new ArrayList<Location>(path);
                    newPath.add(destination);
                    nextLayer.add(newPath);
            }
        }
        return nextLayer;
    }

    private ArrayList<Location> search(ArrayList<ArrayList<Location>> layer) {
        ArrayList<Location> ac = new ArrayList<>();

        for (ArrayList<Location> path : layer) {
            if (containsTarget(path.get(path.size() - 1))) {
                ac.add(path.get(path.size() - 1));
            }
        }
        return ac;
    }

    private boolean containsTarget(Location here) {
        return (here.getActor() != null &&
                here.getActor() instanceof Zombie);
    }
}
