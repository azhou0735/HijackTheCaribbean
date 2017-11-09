package oceanExplorer;

public class NPCRoom extends CaveRoom {

	private NPC presentNPC;
	
	public NPCRoom(String description) {
		super(description);
		presentNPC = null;
	}

	/**
	 * NPCs can only enter a room if no other NPCs is in the room
	 * @return if there is a NPC in the room or not
	 */
	public boolean canEnter() {
		return presentNPC == null;
	}
	
	public void enterNPC(NPC m) {
		presentNPC = m;
	}
	
	public void leaveNPC() {
		presentNPC = null;
	}
	
	/**
	 * canEnter() is like this but this one is more logical to refer to in certain cases where
	 * you want to know if the room contains an NPC or not. 
	 * Also, the rules for canEnter() can be changed and not affect this method
	 * @return if there is a NPC in the room or not
	 */
	public boolean containsNPC() {
		return presentNPC != null;
	}
	
	//----------------------------------------------------------------------------
	//The above methods are NEW features to a CaveRoom, the methods below REPLACE
	//CaveRoom methods (override)
	//----------------------------------------------------------------------------
	
	
	public String validKeys() {
		return "wdsae";
	}
	
	public void printAllowedEntry() {
		System.out.println("You can only enter 'w', 'a', 's', or 'd' to move or, you can enter 'e' to interact.");
	}
	
	
	public void performAction(int direction) {
		if(direction == 4) {
		  if(containsNPC() && presentNPC.isActive()) {
			  presentNPC.interact();
		  }
		  else {
			  CaveExplorer.print("There is nothing to interact with.");
		  }
		} else {
			System.out.println("That key does not exist.");
		}
	}
	
	public String getContents() {
		if(containsNPC() && presentNPC.isActive()) {
			return "M";
		}
		else {
			return super.getContents();
		}
	}
	
	public String getDescription() {
		if(containsNPC() && !presentNPC.isActive()) {
			return super.getDescription() + "\n" + presentNPC.getInactiveDescription();
		} else {
			String npcDesc = "";
			if(presentNPC != null) {
				npcDesc = presentNPC.getActiveDescription();
			}
			return super.getDescription() + "\n" + npcDesc;
		}
	}
}
