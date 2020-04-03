package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_CARDS = NUM_PLAYERS+NUM_WEAPONS+NUM_ROOMS;
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	private static ArrayList<Card> weapons = new ArrayList<Card>();
	private static ArrayList<Card> players = new ArrayList<Card>();
	private static ArrayList<Card> allCards = Board.getCards();
	
	@BeforeClass
	public static void setUp() throws IOException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setCardConfigFiles("CluePlayers.txt", "ClueWeapons.txt");
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");
		// Initialize will load BOTH config files 
		board.initialize();
		for(Card all : allCards) {
			if(all.getType() == CardType.PERSON) {
				players.add(all);
			} else if(all.getType() == CardType.WEAPON) {
				weapons.add(all);
			}
		}
	}
	//select a target location - computer player
	@Test
	public void testSelectTarget() {
		//if no rooms in list, select randomly
		Player testPlayer = new Player("test", 8,7,"red");
		board.calcTargets(8, 7, 1);
		Set<BoardCell> targets = board.getTargets();
		int selectedTarget[] = {0,0,0,0};
		for(int i = 0; i < 100; i++) {
			testPlayer.setLocation(targets);
			if(testPlayer.getRow() == 7 && testPlayer.getCol() == 7) {
				selectedTarget[0] += 1;
				} 
			else if (testPlayer.getRow() == 8 && testPlayer.getCol() == 6) {
					selectedTarget[1] += 1;
				} 
			else if (testPlayer.getRow() == 9 && testPlayer.getCol() == 7) {
					selectedTarget[2] += 1;
				} 
			else {
					selectedTarget[3] += 2;
				}
		}
		assertTrue(selectedTarget[0] > 0);
		assertTrue(selectedTarget[1] > 0);
		assertTrue(selectedTarget[2] > 0);
		assertTrue(selectedTarget[3] == 0);

		//if room in list that was not just visited, must select it
		int unvisitedRoom[] = {0,0};
		for(int i = 0; i < 10; ++i) {
			Player test = new Player("test",5,4, "red");
			Set<BoardCell> targets2 = board.calcTargets(5,4,1);
			test.setLocation(targets2);
			if(test.getRow() == 4 && test.getCol() == 4) {
				unvisitedRoom[0] += 1;
			} else {
				unvisitedRoom[1] += 1;
			}
		}
		assertTrue(unvisitedRoom[0] > 1);
		assertTrue(unvisitedRoom[1] == 0);

		//if room just visited is in list, each target(including room) selected randomly
		Player testPlayer2 = new Player("test",5,4,"red");
		Set<BoardCell> targets2 = board.calcTargets(5,4,1);
		testPlayer2.setLocation(targets2);
		targets2 = board.calcTargets(testPlayer2.getRow(), testPlayer2.getCol(), 1);
		testPlayer2.setLocation(targets2);
		//player should now have visited a room and left		
		int randomWithRoom[] = {0,0,0,0,0};
		targets2 = board.calcTargets(testPlayer2.getRow(), testPlayer2.getCol(), 1);		
		for(int i = 0; i < 100; i++) {
			testPlayer2.setLocation(targets2);
			if(testPlayer2.getRow() == 4 && testPlayer2.getCol() == 4) {
				randomWithRoom[0] += 1;
				} 
			else if (testPlayer2.getRow() == 5 && testPlayer2.getCol() == 3) {
					randomWithRoom[1] += 1;
				} 
			else if (testPlayer2.getRow() == 5 && testPlayer2.getCol() == 5) {
					randomWithRoom[2] += 1;
				} 
			else if(testPlayer2.getRow() == 6 && testPlayer2.getCol() == 4) {
					randomWithRoom[3] += 1;
				} 
			else { 
					randomWithRoom[4] += 1; 
				}
		}
		assertTrue(randomWithRoom[0] > 0);
		assertTrue(randomWithRoom[1] > 0);
		assertTrue(randomWithRoom[2] > 0);
		assertTrue(randomWithRoom[3] > 0);
		assertTrue(randomWithRoom[4] == 0);
	}

	//check make an accusation - board
	@Test
	public void testAccusation() {
		board.setAnswer("","","");
		Solution test1 = new Solution("","","");
		//solution is correct
		assertTrue(board.checkAccusation(test1));
		//solution with wrong person
		Solution test2 = new Solution("a","","");
		assertTrue(!board.checkAccusation(test2));
		//solution with wrong weapon
		Solution test3 = new Solution("","a","");
		assertTrue(!board.checkAccusation(test3));
		//solution with wrong room
		Solution test4 = new Solution("","","a");
		assertTrue(!board.checkAccusation(test4));
	}
	
	//create a suggestion - computer player
	@Test
	public void testCreateSuggestion() {
		//set a player in room "Auditorium"
		Player testPlayer = new Player("test",4,4,"red");
		testPlayer.createSuggestion();
		Solution testSuggestion = testPlayer.getSuggestion();
		//room matches current location
		assertTrue(testSuggestion.getRoom().equals("Auditorium"));
		//check that solution room is the same as room player is in
		assertTrue(testPlayer.getRoom() == testSuggestion.getRoom());
		//room matches current location
		
		Card onePlayer = players.get(0);
		Card oneWeapon = weapons.get(0);
		ArrayList<Card> seenTemp = new ArrayList<Card>();
		//add all but one player and weapon to seen cards
		for(int i = 1; i < players.size(); ++i) {
			seenTemp.add(players.get(i));
		}
		for(int i = 1; i < weapons.size(); ++i) {
			seenTemp.add(weapons.get(i));
		}
		for(Card seen : seenTemp) {
			testPlayer.viewCard(seen);
		}
		testPlayer.createSuggestion();
		testSuggestion = testPlayer.getSuggestion();
		//if only one person/weapon not seen, it's selected(can be same test as weapon)
		assertTrue(testSuggestion.getPerson().equals(onePlayer.getName()));
		assertTrue(testSuggestion.getWeapon().equals(oneWeapon.getName()));
		
		//if multiple weapons not seen, one of them is randomly selected	
		//if multiple persons not seen, one of them is randomly selected
		
	}
	
	//disprove a suggestion - player
	@Test
	public void testDisproveSuggestion() {
		Player testPlayer = new Player("",5,5,"red");
		Card answerRoom = new Card(CardType.ROOM,"room");
		Card answerPlayer = new Card(CardType.PERSON, "player");
		Card answerWeapon = new Card(CardType.WEAPON, "weapon");
		Solution testSuggestion = new Solution(answerRoom.getName(),answerPlayer.getName(),answerWeapon.getName());	
		//if player has no matching card, null is returned
		assertTrue(testPlayer.disproveSuggestion(testSuggestion) == null);
		//if player has only one matching card it should be returned
		testPlayer.addCard(answerRoom);
		assertTrue(testPlayer.disproveSuggestion(testSuggestion) == answerRoom);
		//if player has >1 matching card, returned card should be chosen randomly
		testPlayer.addCard(answerPlayer);
		int seen[] = {0,0,0};
		for(int i = 0; i < 10; i++) {
			if(testPlayer.disproveSuggestion(testSuggestion) == answerRoom) {
				seen[0] += 1;
			} else if(testPlayer.disproveSuggestion(testSuggestion) == answerPlayer) {
				seen[1] += 1;
			} else {
				seen[2] += 1;
			}
		}
		assertTrue(seen[0]>0);
		assertTrue(seen[1]>0);
		assertTrue(seen[2] == 0);
	}

	//handle a suggestion - board
	@Test
	public void testHandleSuggestion() {
		fail("Not yet implemented");
//		board.setSolution("","","");
//		board.setCards("","",CardType.ROOM);
		//suggestion no one can disprove returns null
//		assertTrue(board.getDisproveCard() == null);
		//suggestion only accusing player can disprove returns null
		
		//suggestion only human can disprove returns answer(i.e., card that disproves suggestion)
		//suggestion only human can disprove, but human is accuser, returns null
		//suggestion that two players can disprove, correct player based on starting with next player in list) returns answer
		//suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
	}

}
