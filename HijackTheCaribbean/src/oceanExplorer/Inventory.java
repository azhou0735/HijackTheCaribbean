package oceanExplorer;

public class Inventory {

	private String map;
	private int lastDirection;

	private static int[] bossPowerUps; //It will contain 3 amounts corresponding to the amount of a certain power-up the player has
	/*
	 They're as follow: (Their code will be handled by my back-end class in the battleship package
	 BoinkRadar - Gives the player a general idea of where one of the opponent's ship is. 
	 CriticalMissile - Sets a missile off that will guarantee a hit on a boat in a turn but the user cannot do anything in that time. 
     Stormcaller - The opponent's battleships are surrounded by bad weather and unable to make a player for one turn. 
	*/
	
	public Inventory() {
		updateMap();
	}

	public int getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(int lastDirection) {
		this.lastDirection = lastDirection;
	}

	public static int[] getBossPowerUps() {
		return bossPowerUps;
	}

	public static void setBossPowerUps(int[] bossPowerUp) {
		bossPowerUps = bossPowerUp;
	}

	public void updateMap() {
		map = " ";
		for(int col = 0; col < CaveExplorer.caves[0].length - 1; col++) {
			map += "____"; // 4 underscores
		}
		map += "___ \n"; // 3 underscores
		for(CaveRoom[] row: CaveExplorer.caves) {
			//3 rows of text
			for(int i = 0; i < 3; i++) {
				String text = "";
				for(CaveRoom cr : row) {
					//If door is open, leave open
					if(cr.getDoor(CaveRoom.WEST) != null && cr.getDoor(CaveRoom.WEST).isOpen()) {
						text += " ";
					}
					else {
						text += "|";
					}
					//Contents of room depend on what row this is
					if(i == 0) {
						text += "   ";
					}
					else if(i == 1) {
						text += " " + cr.getContents() + " ";
					}
					else if(i == 2) {
						//Draw space if door to south is open 
						if(cr.getDoor(CaveRoom.SOUTH) != null && cr.getDoor(CaveRoom.SOUTH).isOpen()) {
							text += "   "; //3 spaces
						}
						else {
							text += "___";
						}
					}
				} // Last caveroom in row
				text += "|";
				map += text + "\n";
			}
		}
	}

	public String getDescription() {
 		// return "You have nothing in your inventory.";
		return map;
 	}
	
}
