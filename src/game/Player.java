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
	private Boolean flag = false;
	private int tick = 0;
	private final int max_tick = 15;
	private Random rand = new Random();
	private ArrayList<Item> itemsToDelete = new ArrayList<>();


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
		if (tick >= max_tick) {    // currently max_tick is 15, change it to 1 for testing.
			this.tick = 0;
			if (rand.nextInt(100) + 1 <= 50) {        // 50% chance of having an armour beside him
//			if (true){	// comment out the top line and uncomment this line for testing
				map.locationOf(this).addItem(new Armour());
			}
		}

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
				if (((Shotgun) item).getBullets() > 0)
					actions.add(new Shoot(item));
				loadBullets(this, item);

			}
			if (item.getDisplayChar() - 'R' == 0) {
				if (((SniperRifle) item).getAim() && (lastAction.menuDescription(this).contains("aims") ||
						lastAction.menuDescription(this).contains("shoots"))) {
					actions.add(new AttackAction(((SniperRifle) item).getTarget()));
					actions.add(new AimAction(((SniperRifle) item).getTarget(), (SniperRifle) item));
				} else {
					//maybe make this all into one method?
					((SniperRifle) item).reset();
					if (((SniperRifle) item).getBullets() > 0) {
						actions.add(new Shoot(item));
					}
					loadBullets(this, item);
				}
			}


		}
		for (Item items : itemsToDelete) {
			this.removeItemFromInventory(items);
		}

		AroundLocation location = new AroundLocation(this, map);
		for (Location locate : location.getLocation(this, map)) {
			if (locate.getGround().hasCapability(CropCapability.Ripe)) {
				actions.add(new HarvestAction());
				break;
			}

		}

		actions.add(new EndGame());

		//do we need this???
//		if (lastAction.getNextAction() != null)
//			return lastAction.getNextAction();

		return menu.showMenu(this, actions, display);

	}

	private void loadBullets(Actor actor, Item rangedWeapon) {
		List<Item> inventory = actor.getInventory();
		for (Item item : inventory) {
			if (rangedWeapon.getDisplayChar() - 'S' == 0 && item.getDisplayChar() - ';' == 0) {
				((Shotgun) rangedWeapon).loadBullets(3);
				itemsToDelete.add(item);

			}
			if (rangedWeapon.getDisplayChar() - 'R' == 0 && item.getDisplayChar() - ':' == 0) {
				((SniperRifle) rangedWeapon).loadBullets(3);
				itemsToDelete.add(item);
			}
		}

	}

	@Override
	public Weapon getWeapon() {
		ArrayList<Weapon> deletetemp = new ArrayList<>();//delete the ammunition after loading it

		ArrayList<Weapon> weapons = new ArrayList<>();
//		ArrayList<Weapon> other_weapons = new ArrayList<>();
		for (Item item : this.getInventory()) { //get the weapon in the inventory
			if (item.asWeapon() != null) {
				weapons.add((Weapon) item);
			}
		}
		if (weapons.size() == 0) { ///when you have no weapons
			return this.getIntrinsicWeapon();
		}

//		Actions choose_weapons= new Actions();

		for (Weapon weapon1 : weapons) { //loop through the weapon
			if (weapon1 instanceof Shotgun) {
				if (((Shotgun) weapon1).getBullets() > 0) {
					return weapon1;
				}
				deletetemp.add(weapon1); //delete shotgun so can get other weapon
				flag = true;
			}
			if (weapon1 instanceof SniperRifle) {
				if (((SniperRifle) weapon1).getBullets() > 0) {
					return weapon1;
				}
				deletetemp.add(weapon1); //delete sniper rifle
				flag = true;
			}
			if (!flag) {
//				other_weapons.add(weapon1);
//				System.out.println(weapon1, "weapon1");
//				choose_weapons.add(new ChooseWeapon(weapon1));

				return weapon1;//if is not sniper or shotgun the used it

			}
		}
//		if (!flag){
//			Action chosen_weapon = menu.showMenu(this, choose_weapons,new Display());
//			return chosen_weapon;
//			System.out.println(chosen_weapon + "here");
//			String weapon = wespon.execute(this,map);
//
//		}
		for (Weapon weapon : deletetemp) {
			this.removeItemFromInventory((Item) weapon);
		}
		Weapon weapon = this.getWeapon();
		for (Weapon weapon1 : deletetemp) {
			this.addItemToInventory((Item) weapon1);
		}
		return weapon;
	}
}

