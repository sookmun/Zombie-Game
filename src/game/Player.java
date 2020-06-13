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
	private GameMap map;
	private List<Item> inventory = new ArrayList<>();
	private Weapon weaponChosen;


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
		this.map=map;
		this.tick += 1;
		if (tick >= max_tick) {    // currently max_tick is 15, change it to 1 for testing.
			this.tick = 0;
			if (rand.nextInt(100) + 1 <= 50) {        // 50% chance of having an armour beside him
//			if (true){	// comment out the top line and uncomment this line for testing
				map.locationOf(this).addItem(new Armour());
			}
		}

		//always checks if there are any zombie limbs in the inventory
		inventory = this.getInventory();
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
					actions.add(new ShootAction(item));
				loadBullets(this, item);

			}
			if (item.getDisplayChar() - 'R' == 0) {
				if (((SniperRifle) item).getAim() && (lastAction.menuDescription(this).contains("aims") ||
						lastAction.menuDescription(this).contains("shoots"))) {
					actions.add(new AttackAction(((SniperRifle) item).getTarget())); // if it was aming or it shoots
					actions.add(new AimAction(((SniperRifle) item).getTarget(), (SniperRifle) item));
				} else {
					((SniperRifle) item).reset();
					if (((SniperRifle) item).getBullets() > 0) { //if sniper rifle has bullets
						actions.add(new ShootAction(item));
					}
					loadBullets(this, item);
				}
			}


		}
		for (Item items : itemsToDelete) {//when reloading or armour delete the items
			this.removeItemFromInventory(items);
		}
		itemsToDelete.clear();

		AroundLocation location = new AroundLocation(this, map); //player harvesting the crop
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
	public Weapon getWeapon(){
		ArrayList<Weapon> weapons = new ArrayList<>();
		inventory=this.getInventory();
		Actions actions = new Actions();
		if(weaponChosen instanceof SniperRifle || weaponChosen instanceof Shotgun){
		        return weaponChosen;
        }
		for (Item item : inventory) { //get the weapons in the inventory
			if (item.asWeapon() != null) {
				weapons.add((Weapon) item);
				if(!(item instanceof SniperRifle) && !(item instanceof Shotgun)){
					actions.add(new ChooseWeapon((Weapon)item, Integer.toString(inventory.indexOf(item))));
				}
//
			}
		}
		if(weapons.isEmpty()){
			return getIntrinsicWeapon();
		}
		if(weapons.size()==1){//if only one weapon just return that weapon
			weaponChosen= weapons.get(0);
			return weaponChosen;
		}
		Action action = menu.showMenu(this, actions,new Display());
		String weapon=action.execute(this,map);
		weaponChosen = (Weapon) inventory.get(Integer.parseInt(weapon));
		return weaponChosen;
	}

	@Override
	public void hurt(int points){
		for(Item item :this.getInventory()){
			if (item.getDisplayChar()-'U'==0 && ((Armour)item).use()){
				this.hitPoints-=points- rand.nextInt(5);
				return;
			}
			else{
			    if(item.getDisplayChar()-'U'==0)
				    itemsToDelete.add(item);}
		}
		this.hitPoints-=points;
	}

	public Weapon getWeaponChosen(){
		return weaponChosen;
	}

    public void setWeaponChosen(Weapon weaponChosen) {
        this.weaponChosen = weaponChosen;
    }
}

