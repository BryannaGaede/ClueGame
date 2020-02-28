package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class MyTest {

	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 9;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Closet", legend.get('F'));
		assertEquals("Elevator", legend.get('E'));
		assertEquals("Garage", legend.get('G'));
		assertEquals("Hill Hall", legend.get('H'));
		assertEquals("Mines Market", legend.get('M'));
		assertEquals("Coolbuagh", legend.get('C'));
		assertEquals("Dungeon", legend.get('D'));
		assertEquals("Stariway", legend.get('S'));
		ssertEquals("Berthoud", legend.get('B'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		//Testing right entry to room E
		BoardCell room = board.getCellAt(2, 10);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		//Testing down entry to room A
		room = board.getCellAt(4, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		
		//testing left entry to room H
		room = board.getCellAt(10, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		//testing up entry to room C
		room = board.getCellAt(18, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		
		// Test that room pieces that aren't doors know it (so kinda random)
		room = board.getCellAt(10, 20);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(14, 15);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(20, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room 
		assertEquals('C', board.getCellAt(17, 0).getInitial());
		assertEquals('B', board.getCellAt(9, 0).getInitial());
		assertEquals('A', board.getCellAt(3, 1).getInitial());
		// Test last cell in room
		assertEquals('D', board.getCellAt(25, 10).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(16, 13).getInitial());
		// Test the closet
		assertEquals('F', board.getCellAt(10,10).getInitial());
	}

}
