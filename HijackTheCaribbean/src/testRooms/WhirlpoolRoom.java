package testRooms;

import oceanExplorer.CaveExplorer;
import oceanExplorer.CaveRoom;
import oceanExplorer.Door;

/**
 * Whirlpool room
 * User will be unable to move and will turn the user a certain amount of time
 * Then it will send the user into a room in the direction they will be facing last.
 * 
 * 
 * @author BT_1N3_06
 *
 */
public class WhirlpoolRoom extends CaveRoom {

	private CaveRoom[] borderingRooms;
	private Door[] doors;
	private String currentValidKeys;
	
	private int turnCount; //amount of turns until the whirlpool disappears
	private int distanceFromCenter; //distance the user is from the center of the whirlpool(dying)
	
	private int directionFacing; //direction the user's ship is facing
	
	private String trueDescription;

	public WhirlpoolRoom() {
		super("You are in a whirlpool");
		borderingRooms = new CaveRoom[4];
		doors = new Door[4];
		setDirections();
		
		trueDescription = "The ship is in a whirlpool";
	
		turnCount = (int)(Math.random()*6)+4;
		distanceFromCenter = turnCount-1; //user will have to input the right thing at least once to survive
		
		/**
		 * Whirlpool will always spin counter clock-wise (coriolis effect in S Hemisphere)
		 * So the center will always be to the left
		 */
		//directionFacing = CaveExplorer.inventory.getLastDirection(); //will get the first direction the user is facing
		directionFacing = (int)(Math.random()*4);
		//Will be random for now
		/** HAVE TO MAKE getLastDirection() WORK PROPERLY IN INVENTORY**/
		
		trueDescription += "\nThe ship is pointed to the "+translateDirection(directionFacing)+"."; //always tell the user their direction
		
		setDescription(trueDescription);
	}
	

	
	public int centerDirection(int direction){
		if(direction == 0){
		   return 3;
		}
		return direction - 1;
	}
	
	public static String translateDirection(int direction) {
		String[] dir = {"North", "East", "South", "West"};
		return dir[direction];
	}
	
	//OVERIDE
	
	public void performAction(int direction) {
		if(direction == centerDirection(directionFacing)) {
			//user heads deeper into the center and faster
			distanceFromCenter -= 2;
			trueDescription = "The ship heads deeper to the center.";
		}else if(direction == oppositeDirection(centerDirection(directionFacing))){
			//user goes the proper direction against the center
			trueDescription = "The ship maintains its distance from the center.";
		}else {
			distanceFromCenter --;
			trueDescription = "The whirlpool pulls the ship in closer.";
		}
			turnCount --;
			
		//change the direction (basically turn left) since user is going in a circle
			directionFacing = centerDirection(directionFacing);
		
			trueDescription += "\nThe ship is pointed to the "+translateDirection(directionFacing)+".";
			setDescription(trueDescription);
		
		//user survives
		if(turnCount == 0) {
			setDescription("The whirlpool disappears.");
			setContents("x");
			//OPTIONAL: user is launched towards the last direction they were facing
		}
		if(distanceFromCenter <= 0) {
			//user loses
			setDescription("The whirlpool consumes your ship. YOU LOSE.");
		}
	}
	
	public void enter() {
		setContents("G");
	}

	public void respondToKey(int direction) {
		if(turnCount == 0){
			if(direction < 4) {
				if(borderingRooms[direction] != null && 
						getDoor(direction) != null) {
					CaveExplorer.currentRoom.leave();
					CaveExplorer.currentRoom = borderingRooms[direction];
					CaveExplorer.currentRoom.enter();
					CaveExplorer.inventory.updateMap();
				}
			}
		}
		else {
			performAction(direction);
		}
	}
	

}

