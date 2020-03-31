package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameActionTests {

	//select a target location - computer player
	@Test
	public void testSelectTarget() {
		fail("Not yet implemented");
		//if no rooms in list, select randomly
		//if room in list that was not just visited, must select it
		//if room just visited is in list, each target(including room) selected randomly
	}

	//check make an accusation - board
	@Test
	public void testAccusation() {
		fail("Not yet implemented");
		//solution is correct
		//solution with wrong person
		//solution with wrong weapon
		//solution with wrong room
	}
	
	//create a suggestion - computer player
	@Test
	public void testCreateSuggestion() {
		fail("Not yet implemented");
		//room matches current location
		//if only one person not seen, it's selected(can be same test as weapon)
		//if multiple weapons not seen, one of them is randomly selected
		//if multiple persons not seen, one of them is randomly selected
	}
	
	//disprove a suggestion - player
	@Test
	public void testDisproveSuggestion() {
		fail("Not yet implemented");
		//if player has only one matching card it should be returned
		//if player has >1 matching card, returned card should be chosen randomly
		//if player has no matching card, null is returned
	}

	//handle a suggestion - board
	@Test
	public void testHandleSuggestion() {
		fail("Not yet implemented");
		//suggestion no one can disprove returns null
		//suggestion only accusing player can disprove returns null
		//suggestion only human can disprove returns answer(i.e., card that disproves suggestion)
		//suggestion only human can disprove, but human is accuser, returns null
		//suggestion that two players can disprove, correct player based on starting with next player in list) returns answer
		//suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
	}

}
