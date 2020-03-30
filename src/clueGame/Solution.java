package clueGame;

public class Solution {
	public static String person = null;
	public static String room = null;
	public static String weapon = null;
	
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
