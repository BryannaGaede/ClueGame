package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Status;
import clueGame.Weapon;

public class GameSetupTests {
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
	
	/*
	 * ***************TESTS MAKING SURE PLAYERS ARE LOADED CORRECTLY************
	 */
	
	//CHECK IF NAMES ARE AS EXPECTED
	@Test
	public void testPlayerNames() {
		ArrayList<Player> players = board.getPlayers();
		//make sure the right number of players is being noticed
		assertEquals(NUM_PLAYERS, players.size());
		//the first player is Yue
		assertEquals(players.get(0).getName(), "DR. HUAN YUE");
		//the last player is Baldwin
		assertEquals(players.get(NUM_PLAYERS-1).getName(), "PROFESSOR BALDWIN");
		//the third player is Mehta
		assertEquals(players.get(2).getName(), "PROFESSOR DINESH MEHTA");
	}

	//CHECK IF ROW AND COLUMN ARE AS EXPECTED
	@Test
	public void testPlayerLocations() {
		ArrayList<Player> players = board.getPlayers();
		//the first player starts at 25,22
		assertEquals(players.get(0).getRow(), 25);
		assertEquals(players.get(0).getCol(), 22);
		//the last player starts at 25,4
		assertEquals(players.get(NUM_PLAYERS-1).getRow(), 25);
		assertEquals(players.get(NUM_PLAYERS-1).getCol(), 4);
		//the third player starts at 0,6
		assertEquals(players.get(2).getRow(), 0);
		assertEquals(players.get(2).getCol(), 6);
	}
	

	//CHECK IF COLOR IS AS EXPECTED
	@Test
	public void testPlayerColor() {
		ArrayList<Player> players = board.getPlayers();
		//the first player is orange
		assertEquals(players.get(0).getColor(), Color.ORANGE);
		//the last player is yellow
		assertEquals(players.get(NUM_PLAYERS-1).getColor(), Color.YELLOW);
		//the third player is green
		assertEquals(players.get(2).getColor(), Color.GREEN);
	}

	//CHECK IF COMPUTER OR HUMAN IS SET RIGHT
	@Test
	public void testPlayerStatus() {
		ArrayList<Player> players = board.getPlayers();
		//the first player human
		assertEquals(players.get(0).getStatus(), Status.HUMAN);
		//the last player is human
		assertEquals(players.get(NUM_PLAYERS-1).getStatus(), Status.HUMAN);
		//the third player is a computer
		assertEquals(players.get(2).getStatus(), Status.COMPUTER);
	}
	
	/*
	 * *********TESTS TO MAKE SURE WEAPONS ARE LOADED CORRECTLY*************
	 */
	
	//CHECK IF WEAPONS ARE SAVED AS EXPECTED
	@Test
	public void testWeapon() {
		ArrayList<Weapon> weapons = board.getWeapons();
		//make sure the right number of weapons is being noticed
		assertEquals(NUM_WEAPONS, weapons.size());
		//the first weapon is a computer
		assertEquals(weapons.get(0).getName(), "COMPUTER");
		//the last weapon is a time machine
		assertEquals(weapons.get(NUM_WEAPONS-1).getName(), "TIME MACHINE");
		//the third weapon is mind games
		assertEquals(weapons.get(2).getName(), "MIND GAMES");
	}
	
	/*
	 * *********TEST TO SHOW THAT THE DECK IS BUILT CORRECTLY****************
	 */
	
	//CHECK THE NUMBER OF CARDS IS ACCURATE
	@Test
	public void testDeckSize() {
		ArrayList<Card> cards = board.getCards();
		//make sure the right number of cards is being noticed
		assertEquals(NUM_CARDS, cards.size());
	}	
	
	//MAKE SURE EXPECTED CARDS ARE CONTAINED IN THE DECK
	@Test
	public void testDeckContents() {
		//make sure a room exists
		assertTrue(board.cardExists(CardType.ROOM,"Garage"));
		//make sure a person exists
		assertTrue(board.cardExists(CardType.PERSON, "PROFESSOR DINESH MEHTA"));
		//make sure a weapon exists
		assertTrue(board.cardExists(CardType.WEAPON, "LAZOR"));
		
		//make sure the count for each type is right
		assertEquals(board.cardCount(CardType.ROOM), 9);
		assertEquals(board.cardCount(CardType.PERSON), 6);
		assertEquals(board.cardCount(CardType.WEAPON), 6);
	}
	
	
	/*
	 * *********TEST TO SEE THAT THE CARDS ARE DELT CORRECTLY****************
	 */
	
	//CHECK THAT ALL CARDS ARE DEALT AND ONLY ONCE
	@Test
	public void testDeal() {
		//check multiple cards to see status as being dealt
		ArrayList<Card> cards = board.getCards();
		assertTrue(cards.get(0).getStatus());
		assertTrue(cards.get(NUM_CARDS-1).getStatus());
		assertTrue(cards.get(NUM_CARDS/2 -4).getStatus());
		assertTrue(cards.get(NUM_CARDS/2 +4).getStatus());
		
		//check that cards aren't dealt more than once
		assertTrue(board.noDuplicates(cards.get(0)));
		assertTrue(board.noDuplicates(cards.get(NUM_CARDS-1)));
		assertTrue(board.noDuplicates(cards.get(NUM_CARDS/2 -4)));
		assertTrue(board.noDuplicates(cards.get(NUM_CARDS/2 +4)));
		
		//all players should have roughly the same number of cards
		ArrayList<Player> players = board.getPlayers();
		int expected = NUM_CARDS/NUM_PLAYERS;
		//there will never be more than one extra card dealt to a player
		assertTrue(players.get(0).getCardCount()-expected < 2);
		assertTrue(players.get(NUM_PLAYERS-1).getCardCount()-expected < 2);
		assertTrue(players.get(3).getCardCount()-expected < 2);
	}
	
	@Test
	public void testSolution() {
		Solution theAnswer = board.getSolution(); 
		//checking let's sysou
		System.out.println(theAnswer.person + " " + theAnswer.weapon + " " + theAnswer.room);
		assertEquals(1,2);
		
	}
}
