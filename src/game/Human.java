package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	private Behaviour behaviour = new WanderBehaviour();
	private Random rand = new Random();
	private Boolean flag=false;
	private Item crop;

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
	}


	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		if (rand.nextInt(100)+1 <= 5){		// 5 % chance of human accidentally step on crop and damage them
			if (map.locationOf(this).getGround().getDisplayChar() - 'C' == 0 ){
				return new UnsowAction();
			}
		}
		for (Action action : actions) {
			//human will always pick up stuff (ect food or weapon) and if there is food it will eat
			if (action instanceof PickUpItemAction) {
				return action;
			}
		}
		if(lastAction instanceof PickUpItemAction){ //if human pick of item check if is a food
			List<Item> inventory= this.getInventory();
			for(Item item: inventory){
				if(item.getDisplayChar()-'F'==0){
					return new EatAction(); // if it is a food eat it
				}
			}
		}
//human has the ability to destroy the crop idea came from misreading the instructions. Maybe use it in assignment 3?
//		List<Item> itemGround = map.locationOf(this).getItems();
//		for (Item item : itemGround){ // loop through what is on the ground
//			if(item.getDisplayChar()-'C'==0){ //if there is a crop set flag and save item
//				flag=true;
//				crop=item;
//			}
//
//		}
//		//with a probability of 30% humans may damage a crop when standing on it
//		if(rand.nextDouble()<0.30 && flag ){
//			map.locationOf(this).removeItem(crop);
//			System.out.println(this.toString()+ "destroyed the crop");
//		}

		return behaviour.getAction(this, map);
	}




}
