package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.ActorInterface;

import java.util.ArrayList;

/**
 * A class that allow the game to ended earlier
 *
 * @author Tan Sook Mun
 */
public class EndGame extends Action {
    private Boolean hflag=true;
    private Boolean zflag = true;

    public String execute(Actor actor , GameMap map){
        if (actor instanceof Player){
            map.removeActor(actor);
            return "Quit Game";
        }
        return null;


    }

    public String menuDescription(Actor actor){
        return "End Game";
    }

    /**
     * Check whether any human, farmers or zombies are left.
     * @param map the map the player is at
     *
     */
    public String checkAlive(GameMap map){
        Actor player=null;
        for (int x=0 ; x<=map.getXRange().max()-1; x++){
            for (int y=0; y<map.getYRange().max()-1; y++){
                Actor actor1=map.at(x,y).getActor();
                if(actor1 !=null) {
                    if (actor1 instanceof Human) {
                        if (actor1.getDisplayChar() - 'H' == 0) { //when there is still humans left
                            hflag = false;
                        } else if (actor1.getDisplayChar() - 'f' == 0) {
                            hflag = false;
                        } else {
                            player = actor1;
                        }
                    } else {
                        zflag = false;
                    }
                }
            }
        }
        if ((hflag || zflag) && player == null){
            return "Watch out player! Other Map is overun with zombies";
        }
        if (hflag){
            map.removeActor(player);
            return '\n'+"No more humans left. Player lose";
        }
        if(zflag){
            map.removeActor(player);
            return '\n'+"No more zombies. Player Wins" ;
        }
        return null;
    }


}
