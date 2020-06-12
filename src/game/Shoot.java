package game;


import edu.monash.fit2099.engine.*;

import java.util.*;

public class Shoot extends Action{
    private Item weaponItem;
    private Boolean flag=false;
    private HashSet<Location> visitedLocations = new HashSet<Location>();
    private List<Location> zombielocation = new ArrayList<>();
    private Menu menu = new Menu();
    private ArrayList<String> retDirection = new ArrayList<>();
    private List<String> direction = new ArrayList<String>(){{
        add("North");
        add("North-East");
        add("East");
        add("South-East");
        add("South");
        add("South-West");
        add("West");
        add("North-West");
    }    };


    public Shoot(Item weaponItem){
        this.weaponItem =weaponItem;
    }

    public String execute(Actor actor, GameMap map){
        String result="";
        if (weaponItem instanceof Shotgun){
            ((Player) actor).setWeaponChosen((Weapon)weaponItem);
            if(((Shotgun) weaponItem).getBullets() > 0) {
                range(actor, map, 3);
                result += Shotgun(actor,map);
                ((Shotgun) weaponItem).fired();
            }
            else{
                return "Cant shoot no bulets.Search at town for Ammunition";
            }
        }
        else{
            if(((SniperRifle) weaponItem).getBullets() > 0) {
                ((Player) actor).setWeaponChosen((Weapon)weaponItem);
                if(map.getYRange().max()>map.getXRange().max()){
                    range(actor, map, map.getYRange().max());
                    result += Shotgun(actor,map);
                }
                else{
                    range(actor, map, map.getXRange().max());
                    result+=SniperRifle(actor,map);
                }

                ((SniperRifle) weaponItem).fired();
            }
            else{
                return "Cant shoot no bullets.Search at town for  Ammunition";
            }

        }
        return result;
    }

    public String menuDescription(Actor actor ){ return actor + " shoots " + weaponItem.toString();}



    public void range(Actor actor, GameMap map, int range){
        visitedLocations.clear();
        ArrayList<Location> now = new ArrayList<>();

        now.add(map.locationOf(actor));
        ArrayList<ArrayList<Location>> layer = new ArrayList<ArrayList<Location>>();
        layer.add(now);

        if(weaponItem instanceof Shotgun){
            Random random = new Random();
            getDirection(random.nextInt(direction.size()));
        }


        for (int i = 0; i<range; i++) {
            if(weaponItem instanceof SniperRifle){
                layer = getNextLayer(null, layer);
            }
            else{
                layer = getNextLayer(retDirection.get(i), layer);
            }
            ArrayList<Location> there = search(layer);
            zombielocation.addAll(there);

        }

    }
    private ArrayList<ArrayList<Location>> getNextLayer(String direction, ArrayList<ArrayList<Location>> layer) {
        ArrayList<ArrayList<Location>> nextLayer = new ArrayList<ArrayList<Location>>();

        for (ArrayList<Location> path : layer) {
            for (Exit exit : path.get(path.size() - 1).getExits()) {
                if (retDirection.contains(exit.getName()) || direction == null) {
                    Location destination = exit.getDestination();
                    if (visitedLocations.contains(destination))
                        continue;
                    visitedLocations.add(destination);
                    ArrayList<Location> newPath = new ArrayList<Location>(path);
                    newPath.add(destination);
                    nextLayer.add(newPath);
                }
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

    private String SniperRifle(Actor actor, GameMap map){
        Actions actions= new Actions();
        for (Location location : zombielocation){
            actions.add(new ChooseZombie(location.getActor(), Integer.toString(zombielocation.indexOf(location))));

        }
        Action action = menu.showMenu(actor, actions,new Display());
        String zombie=action.execute(actor,map);

        Actions next_actions= new Actions();
        next_actions.add(new AttackAction(zombielocation.get(Integer.parseInt(zombie)).getActor()));
        next_actions.add(new AimAction(zombielocation.get(Integer.parseInt(zombie)).getActor(),(SniperRifle)weaponItem));
        action = menu.showMenu(actor,next_actions,new Display());
        if(action instanceof AimAction){
            ((SniperRifle) weaponItem).setAim(true);
            ((SniperRifle) weaponItem).setTarget(zombielocation.get(Integer.parseInt(zombie)).getActor());
        }
        else { ((SniperRifle) weaponItem).setAim(false);}
        return action.execute(actor,map);
    }

    private String Shotgun(Actor actor, GameMap map){
        String result ="";
        for(Location location : zombielocation){
           AttackAction action= new AttackAction(location.getActor());
           result += action.execute(actor,map) + '\n';
        }
        if (result.equals("")){
            result += "Shoot in the direction of "+ retDirection.get(1) + " No Zombies was shoot";
        }

        return result;
    }

    public void getDirection(int index){
        try {
            retDirection.add(direction.get(index+1));
        }
        catch (Exception e){
            retDirection.add(direction.get(0));
        }
        retDirection.add(direction.get(index));
        try {
            retDirection.add(direction.get(index - 1));
        }
        catch (ArrayIndexOutOfBoundsException e){
            retDirection.add(direction.get(direction.size()-1));
        }

    }

}
