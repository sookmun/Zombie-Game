package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Menu menu = new Menu();
	private int[][] values = {{1, -1}, {1, 0}, {1 + 1}, {0, -1}, {0, 0}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
	private WeaponItem rangedweapon;
	private int tick = 0;
	private final int max_tick = 15;
	private Random rand = new Random();


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
	 *
	 * @param actions    list of actions that player is allow to have
	 * @param lastAction the last action that the player had
	 * @param map        map of game
	 * @param display    display
	 * @return return the action that is chosen by player
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		this.tick += 1;
		if (tick >= max_tick){	// currently max_tick is 15, change it to 1 for testing.
			this.tick = 0;
			if (rand.nextInt(100)+1 <= 50){		// 50% chance of having an armour beside him
//			if (true){	// comment out the top line and uncomment this line for testing
				map.locationOf(this).addItem(new Armour());
				}
			}

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
			if (item.getDisplayChar() - 'S' == 0) {
				actions.add(new Shoot(item));
			}
		}

		AroundLocation location = new AroundLocation(this, map);
		for (Location locate : location.getLocation(this, map)) {
			if (locate.getGround().hasCapability(CropCapability.Ripe)) {
				actions.add(new HarvestAction());
				break;
			}
			if ((locate.getActor() instanceof Human)) {
				if ((locate.getActor() instanceof Player) || (locate.getActor() instanceof Farmer)) {
					break;
				} else {    // if the actor in a normal Human
					boolean human_contain_weapon = false;
					for (Item item : locate.getActor().getInventory()) {
						if (item instanceof WeaponItem) {    // check if the human contains WeaponItem to trade
							human_contain_weapon = true;
							break;
						}
					}
					if (human_contain_weapon) {    // if human contains WeaponItem
						boolean player_contain_weapon = false;
						boolean player_contain_food = false;
						for (Item item : inventory) {    // check player's inventory
							if (item instanceof WeaponItem) {
								player_contain_weapon = true;
							} else if (item instanceof Food) {
								player_contain_food = true;
							}
						}
						if (player_contain_weapon && player_contain_food) {    // if player has both weapon and food
							actions.add(new TradeAction(locate.getActor(), true));
						} else if (player_contain_weapon) {    // if player has weapon only
							actions.add(new TradeAction(locate.getActor(), false));
						}
					}
				}

			}

		}
		actions.add(new EndGame());
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this, actions, display);

	}

	@Override
	public Weapon getWeapon() {
		int damage_count = 0;
		Weapon weapon = null;
		boolean contain_weapon = false;
		for (Item item : inventory) {
			if (item instanceof Weapon){
				contain_weapon = true;
//				System.out.println(item + " " + item.getDisplayChar());
				if (((Weapon) item).damage() > damage_count){
					weapon = (Weapon)item;
					damage_count = weapon.damage();
				}
			}
		}
		if (contain_weapon){
//			System.out.println("Weapon used: " + weapon + weapon.damage());
			return weapon;
		}
		return getIntrinsicWeapon();
	}


}