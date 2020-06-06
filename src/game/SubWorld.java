package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class SubWorld extends World {
    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public SubWorld(Display display) {
        super(display);
    }

    @Override
    public void run() {
        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations) {
            lastActionMap.put(actor, new DoNothingAction());
        }


        // This loop is basically the whole game
        while (stillRunning()) {
            GameMap playersMap = actorLocations.locationOf(player).map();
            playersMap.draw(display);

            boolean mambo = false;  // mambo is false, means Mambo Marie does not exist in the map

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (actor instanceof MamboMarie){
                    mambo = true;   // change mambo into true when Mambo Marie exist in the map
                }
                if (stillRunning())
                    processActorTurn(actor);
            }

            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();

                if (gameMap.toString().equals("edu.monash.fit2099.engine.GameMap@2503dbd3")){
                    if (!mambo){
                        if (new Random().nextInt(100) + 1 <= 70){
                            Location location = new Location(gameMap, 0, 0);
                            if (!location.containsAnActor()){
//                                location.addActor(new MamboMarie());
//                                gameMap.at(0,0).addActor(new MamboMarie());
                                gameMap.addActor(new MamboMarie(), location);
                                System.out.println("MamboMarie is added ");
                            }
                        }
                }



                }
            }

        }
        display.println(endGameMessage());
    }

}
