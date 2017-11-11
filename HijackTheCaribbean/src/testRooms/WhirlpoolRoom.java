package testRooms;

import oceanExplorer.CaveExplorer;
import oceanExplorer.CaveRoom;

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
	private String currentValidKeys;
	
	private int turnCount; //amount of turns until the whirlpool disappears
	private int distanceFromCenter; //distance the user is from the center of the whirlpool(dying)
	
	private int directionFacing; //direction the user's ship is facing
	private int directionToAvoid; //direction the user has to click in order to avoid moving towards the center

	public WhirlpoolRoom() {
		super("You fall into a whirlpool");
		turnCount = (int)(Math.random()*4)+1;
		distanceFromCenter = turnCount-1; //user will have to input the right thing at least once to survive
		
		/**
		 * Whirlpool will always spin counter clock-wise (coriolis effect)
		 * So the center will be to the EAST when coming from the NORTH
		 * and WEST when coming from the SOUTH
		 * from EAST to NORTH
		 * from WEST to SOUTH
		 */
		//directionFacing = CaveExplorer.inventory.getLastDirection(); //will get the first direction the user is facing
		directionFacing = (int)(Math.random()*4);
		//Will be random for now
		/** HAVE TO MAKE getLastDirection() WORK PROPERLY IN INVENTORY**/
		System.out.println("You are facing "+directionFacing+".");
		directionToAvoid = updateAvoidDirection(directionFacing);
	}
	
	public int updateAvoidDirection(int userDirection) {
		if(userDirection == NORTH) {
			return EAST;
		}
		if(userDirection == SOUTH) {
			return WEST;
		}
		if(userDirection == EAST) {
			return NORTH;
		}
		if(userDirection == WEST) {
			return SOUTH;
		}
		return -1;
	}
	
	public int directionToLeft(int ogDir){
		if(ogDir == 0){
		   return 3;
		}
		return ogDir - 1;
	}
	//OVERIDE

	/**
	 * 
	 */
	public void performAction(int direction) {
		if(direction == directionToAvoid) {
			//user failed to avoid center
			distanceFromCenter --;
			System.out.println("You fall closer to the center.");
		}
			//distance doesn't change
			turnCount --;
		//change the direction (basically turn left)
			directionFacing = directionToLeft(directionFacing);
			directionToAvoid = updateAvoidDirection(directionFacing);
		
			System.out.println("You are now facing "+directionFacing+".");
			
			
		if(turnCount == 0) {
			System.out.println("The whirlpool disappears.");
			//OPTIONAL: user is launched towards the last direction they were facing
		}
		if(distanceFromCenter == 0) {
			//user loses
			System.out.println("The whirlpool consumes your ship. YOU LOSE.");
		}
	}
	
	public void enter() {
		setContents("x");
	}

	public void respondToKey(int direction) {
		if(turnCount < 1){
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

