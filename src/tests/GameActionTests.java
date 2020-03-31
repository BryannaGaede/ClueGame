package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.CardType;
import clueGame.Player;

public class GameActionTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_CARDS = NUM_PLAYERS+NUM_WEAPONS+NUM_ROOMS;
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws IOException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setCardConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files 
		board.initialize();
	}
	//select a target location - computer player
	@Test
	public void testSelectTarget() {
		//if no rooms in list, select randomly
		Player testPlayer = new Player("test", 8,7,"red");
		board.calcTargets(8, 7, 1);
		Set<BoardCell> targets = board.getTargets();
		int selectedTarget[] = {0,0,0,0};
		for(int i = 0; i < 10; i++) {
			testPlayer.setLocation(targets);
			if(testPlayer.getRow() == 7 && testPlayer.getCol() == 7) {
				selectedTarget[0] += 1;
				} else if (testPlayer.getRow() == 8 && testPlayer.getCol() == 6) {
					selectedTarget[1] += 1;
				} else if (testPlayer.getRow() == 9 && testPlayer.getCol() == 7) {
					selectedTarget[2] += 1;
				} else {
					selectedTarget[3] += 2;
				}
		}
		assertTrue(selectedTarget[0] > 0);
		assertTrue(selectedTarget[1] > 0);
		assertTrue(selectedTarget[2] > 0);
		assertTrue(selectedTarget[3] == 0);
		//if room in list that was not just visited, must select it
		Player testPlayer2 = new Player("test2",5,4, "red");
		Set<BoardCell> targets2 = board.calcTargets(5,4,1);
		testPlayer2.setLocation(targets2);
		assertTrue(testPlayer2.getRow() == 4 && testPlayer2.getCol() == 4);
		//if room just visited is in list, each target(including room) selected randomly
		testPlayer2.setLocation(5,4);
		targets2 = board.calcTargets(5,4,1);
		for(int i : selectedTarget) {
			i = 0;
		}
		for(int i = 0; i < 10; i++) {
			testPlayer.setLocation(targets);
			if(testPlayer.getRow() == 5 && testPlayer.getCol() == 5) {
				selectedTarget[0] += 1;
				} else if (testPlayer.getRow() == 5 && testPlayer.getCol() == 3) {
					selectedTarget[1] += 1;
				} else if (testPlayer.getRow() == 4 && testPlayer.getCol() == 4) {
					selectedTarget[2] += 1;
				} else {
					selectedTarget[3] += 2;
				}
		}
		assertTrue(selectedTarget[0] > 0);
		assertTrue(selectedTarget[1] > 0);
		assertTrue(selectedTarget[2] > 0);
		assertTrue(selectedTarget[3] > 0);
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
		board.setSolution("","","");
		board.setCards("","",CardType.ROOM);
		//suggestion no one can disprove returns null
		assertTrue(board.getDisproveCard() == null);
		//suggestion only accusing player can disprove returns null
		
		//suggestion only human can disprove returns answer(i.e., card that disproves suggestion)
		//suggestion only human can disprove, but human is accuser, returns null
		//suggestion that two players can disprove, correct player based on starting with next player in list) returns answer
		//suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
	}

}
