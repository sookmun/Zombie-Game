package game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	protected int num_of_arms;
	protected int num_of_legs;

	protected Random rand = new Random();

	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		this.num_of_arms = 2;
		this.num_of_legs = 2;
	}

	/**
	 * A method that determines which IntrinsicWeapon should be used by the Zombie
	 * The probability of the action returned will depend on the number of arms left
	 *
	 * @return either a bite or punch action
	 *
	 * @author Lai Ying Ying
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		boolean bool;
		if (num_of_arms==2){			// if both arms are present, 50% chance of doing either
			bool = rand.nextBoolean();
		}
		else if (num_of_arms==1){		// if one arm left, 75% chance of biting
			bool = rand.nextInt(100)+1 <= 75;
		}
		else{
			bool = true;	// no arms are present, can only bite
		}

		if (bool){		// true --> bites
			return new IntrinsicWeapon(20, "bites");
		}
		else{		// false --> punches
			return new IntrinsicWeapon(10, "punches");
		}
	}


	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (rand.nextInt(100)+1 <= 10){	// 10% chance of saying 'Braaaaaains'
			return new GroanAction();
		}

		for (Action action: actions){		// if there's a weapon on the floor, pick up the item
			if (action.getClass().getName().equals("edu.monash.fit2099.engine.PickUpItemAction")){
				if (num_of_arms>=1){
					return action;
				}
			}
		}

		for (Behaviour behaviour : behaviours) {
			if (num_of_legs==2){
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
			else if (num_of_legs==1){
				Action action = behaviour.getAction(this, map);
				if(behaviour.getClass().getName().equals("game.AttackBehaviour")){
					if (action != null) {
						return action;
					}
				}
				else if (behaviour.getClass().getName().equals("game.WanderBehaviour") || (behaviour.getClass().getName().equals("game.HuntBehaviour"))) {	// if current action is to move
					if (lastAction.getClass().getName().equals("edu.monash.fit2099.engine.DoNothingAction")) {
						if (action != null){
							return action;
						}
					}
				}
			}
			else if (num_of_legs==0){
				Action action = behaviour.getAction(this, map);
				if (behaviour.getClass().getName().equals("game.WanderBehaviour") || (behaviour.getClass().getName().equals("game.HuntBehaviour"))) {    // if current action is to move
					System.out.println(name + " can't move as it has no more legs");
					return new DoNothingAction();    // zombie can't move at all
				}
				else{
					if (action != null)
						return action;
				}
			}
		}
		return new DoNothingAction();
	}
}
