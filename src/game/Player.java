package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Menu menu = new Menu();
	private int[][] values ={{1,-1},{1,0},{1+1},{0,-1},{0,0},{0,1},{-1,1},{-1,0},{-1,-1}};


	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	/**
	 * Player actions when its turn to play. If there is food or zombie limbs add in the appropriate action in actions list
	 * @param actions list of actions that player is allow to have
	 * @param lastAction the last action that the player had
	 * @param map map of game
	 * @param display display
	 * @return return the action that is chosen by player
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		List<Item> groundItems = map.locationOf(this).getItems();
		//always checks if there are any zombie limbs in the inventory
		List<Item> inventory = this.getInventory();
		for (Item item : inventory) {
			if (item.getDisplayChar() - 'a' == 0 || item.getDisplayChar() - 'l' == 0) {
				//if there is zombie limbs then add craft action
				actions.add(new CraftAction());
			}
			//if there is food add eat action
			if (item.getDisplayChar() - 'F' == 0) {
				actions.add(new EatAction());
			}

		}
		HarvestAction harvest = new HarvestAction();
		for(Location location : harvest.getLocation(this,map)){
			if(location.getGround().hasCapability(CropCapability.Ripe)){
				actions.add(harvest);
			}

		}




		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this,actions,display);

	}


}
