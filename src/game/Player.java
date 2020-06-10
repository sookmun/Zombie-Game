package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Human {
	private Menu menu = new Menu();
	private ArrayList<Item> itemsToDelete= new ArrayList<>();



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
			if (item.getDisplayChar() - 'S' == 0){

				if (((Shotgun) item).getBullets()>0)
					actions.add(new Shoot(item));
				loadBullets(this,';',item);

			}
			if (item.getDisplayChar()-'R'==0){
				if (((SniperRifle) item).getBullets()>0)
					actions.add(new Shoot(item));
				loadBullets(this,':',item);
			}


		}
		for(Item items: itemsToDelete){
			this.removeItemFromInventory(items);
		}
		AroundLocation location =new AroundLocation(this,map);
		for(Location locate : location.getLocation(this,map)){
			if(locate.getGround().hasCapability(CropCapability.Ripe)){
				actions.add(new HarvestAction());
				break;
			}

		}

		actions.add( new EndGame());
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this,actions,display);

	}

	private void loadBullets(Actor actor, char character, Item rangedWeapon){
		List<Item> inventory = actor.getInventory();
		for (Item item: inventory){
			if(rangedWeapon.getDisplayChar()-'S'==0 && item.getDisplayChar()-';'==0){
				((Shotgun) rangedWeapon).loadBullets(3);
				itemsToDelete.add(item);

			}
			if(rangedWeapon.getDisplayChar()-'R'==0 && item.getDisplayChar()-':'==0){
				((SniperRifle) rangedWeapon).loadBullets(3);
				itemsToDelete.add(item);
			}
		}

	}
	@Override
	public Weapon getWeapon(){
		ArrayList<Weapon> deletetemp= new ArrayList<>();

		ArrayList<Weapon> weapons = new ArrayList<>();
		for(Item item: this.getInventory()){
			if(item.asWeapon() != null){
				weapons.add((Weapon)item);
			}
		}
		if(weapons.size()==0){ ///when you have no weapons
			return this.getIntrinsicWeapon();
		}
		for(Weapon weapon1: weapons){
			Boolean flag=false;
			if(weapon1 instanceof Shotgun){
				if(((Shotgun) weapon1).getBullets()>0){
					return weapon1;
				}
				deletetemp.add(weapon1);
				flag=true;
			}
			if(weapon1 instanceof SniperRifle){
				if(((SniperRifle) weapon1).getBullets()>0){
					return weapon1;
				}
				deletetemp.add(weapon1);
				flag=true;
			}
			if(!flag){
				return weapon1;
			}

		}
		for(Weapon weapon : deletetemp){
			this.removeItemFromInventory((Item)weapon);
		}
		Weapon weapon =this.getWeapon();
		for(Weapon weapon1: deletetemp){
			this.addItemToInventory((Item)weapon1);
		}
		return weapon;
	}





}
