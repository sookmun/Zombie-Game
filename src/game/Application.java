package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.demo.mars.LockedDoor;
import edu.monash.fit2099.engine.*;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new SubWorld(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree(), new LockedDoor());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............oo...........##......................########.......................",
		"..............oooo.......#..............................##......................",
		".............ooo...ooo...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####......................######.....................",
		"..............................#########.........####............................",
		"............ooo.......................#.........#...............................",
		".............ooooo....................#.........#...............................",
		"...............oo........................................ooooo..................",
		".............ooo....................................oooooooo....................",
		"............ooo.......................................ooo.......................",
		"................................................................................",
		".........................................................................oo.....",
		"........................................................................oo.oo...",
		".........................................................................oooo...",
		"..........................................................................oo....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		System.out.println(gameMap);
		world.addGameMap(gameMap);

		List<String> townMap = Arrays.asList(

				"#######################.........................................................",
				"#............#........#.........................................................",
				"#........#...#....#...#.........................................................",
				"#........#...#....#...#.........................................................",
				"####...###...#....#...+.........................................................",
				"#........#...#....#...#.........................................................",
				"#........#........#...#.........................................................",
				"#######################.........................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................");
		GameMap town = new GameMap(groundFactory, townMap);
		world.addGameMap(town);
		Vehicle vehicle = new Vehicle();
		vehicle.addAction(new MoveActorAction(town.at(45,12), "go to town"));
		gameMap.at(65,12).addItem(vehicle);
		gameMap.at(42,15).addItem(vehicle); // put near player easy for testing

		Vehicle vehicletoForest = new Vehicle();
		town.at(45,12).addItem(vehicletoForest);
		vehicletoForest.addAction(new MoveActorAction(gameMap.at(45,12), "go to forest"));
		town.at(44,12).addItem(new Shotgun());
		town.at(35,13).addItem(new SniperRifle());
		town.at(36,14).addItem(new SniperRifleAmmunition());
		town.at(45,12).addItem(new ShotgunAmmunition());
		town.at(4,2).addActor(new Zombie("Zombie Guard1"));
		town.at(10,6).addActor(new Zombie("Zombie Guard2"));
		town.at(14,2).addActor(new Zombie("Zombie Guard3"));
		town.at(1,10).addActor(new Human("Rick"));
		town.at(2,10).addActor(new Human("dsdddddddddddddddd"));
		town.at(3,10).addActor(new Human("blaaaaaaaaaaaaaaaa"));

		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(42, 15));
		gameMap.at(43,15).addItem(new ZombieMace());
		gameMap.at(43, 15).addItem(new Plank());
//		gameMap.at(42, 16).addItem(new Food());

		// Added by LYY to test TradeAction between player and human
		gameMap.at(42, 16).addActor(new Human("Testing_human"));
		gameMap.at(42,16).addItem(new ZombieMace());
		gameMap.at(42, 16).addItem(new Plank());
		gameMap.at(42,15).addItem(new Food());

	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			}
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));
		}
		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank());

		// FIXME: Add more zombies!
		gameMap.at(30, 20).addActor(new Zombie("Groan"));
		gameMap.at(30, 18).addActor(new Zombie("Boo"));

		gameMap.at(10, 4).addActor(new Zombie("Uuuurgh"));
		gameMap.at(50, 18).addActor(new Zombie("Mortalis"));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh"));
		gameMap.at(1, 1).addActor(new Zombie("Zombie1"));    // add zombie
		gameMap.at(79, 0).addActor(new Zombie("Zombie2"));
		gameMap.at(70, 6).addActor(new Zombie("Zombie3"));
		gameMap.at(3, 23).addActor(new Zombie("Zombie4"));

		gameMap.at(25, 20).addActor(new Farmer("farmer1")); //added farmer
		gameMap.at(40, 15).addActor(new Farmer("farmer2"));
		gameMap.at(17, 18).addActor(new Farmer("farmer3"));
		world.run();

	}
}
