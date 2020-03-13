package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class GameSetupTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int NUM_PLAYERS = 6;
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws IOException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setAllConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt","CluePlayers.txt", "ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testPlayerNames() {
		ArrayList<Player> players = board.getPlayers();
		//make sure the right number of player is being noticed
		assertEquals(NUM_PLAYERS, players.size());
		//the first player is Plum, who is purple and starts at 25,22
		assertEquals(players.get(0).getName(), "PROFESSOR PLUM");
		//the last player is Mustart, who is yellow and starts at 25,4
		assertEquals(players.get(NUM_PLAYERS-1).getName(), "COLONEL MUSTARD");
		//the third player is Green, who is green and starts at 0,6
		assertEquals(players.get(2).getName(), "MR. GREEN");
	}
	

}
