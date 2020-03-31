package clueGame;

public class Solution {
	public String person = null;
	
	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public String room = null;
	public String weapon = null;
	
	//used for testing
	
	public String getPerson() {
		return person;
	}
	@Override
	public String toString() {
		return "Solution [getPerson()=" + getPerson() + ", getRoom()=" + getRoom() + ", getWeapon()=" + getWeapon()
				+ "]";
	}
	public String getRoom() {
		return room;
	}
	public String getWeapon() {
		return weapon;
	}
}
