package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Menu menu = new Menu();


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

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		//added by tsm remove if interfere with application
		//always checks if there are any zombie limbs in the inventory
		List<Item> inventory= this.getInventory();
		for(Item item: inventory){
			if(item.getDisplayChar()-'a' ==0 || item.getDisplayChar()-'l'==0){
				//if there is zombie limbs then add craft action
				Action craft = new CraftAction();
				actions.add(craft);
			}
		}



		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this,actions,display) ;

	}

}
