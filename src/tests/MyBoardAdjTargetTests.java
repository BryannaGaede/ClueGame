package tests;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class MyBoardAdjTargetTests {

	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		@BeforeClass
		public static void setUp() throws IOException {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("CLUE_BOARD.csv", "ClueRooms.txt");		
			// Initialize will load BOTH config files 
			board.initialize();
		}
		

		// Ensure that player does not move around within room
		// These cells are ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms()
		{
			// Test a corner
			Set<BoardCell> testList = board.getAdjList(0, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway underneath
			testList = board.getAdjList(4, 17);
			assertEquals(0, testList.size());
			// Test one that has walkway above
			testList = board.getAdjList(17,14);
			assertEquals(0, testList.size());
			// Test one that is in middle of room
			testList = board.getAdjList(21, 7);
			assertEquals(0, testList.size());
			// Test one beside a door
			testList = board.getAdjList(4, 5);
			assertEquals(0, testList.size());
			// Test one in a corner of room
			testList = board.getAdjList(18, 5);
			assertEquals(0, testList.size());
		}

		// Ensure that the adjacency list from a doorway is only the
		// walkway. NOTE: This test could be merged with door 
		// direction test. 
		// These tests are PURPLE on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit()
		{
			// TEST DOORWAY RIGHT 
			Set<BoardCell> testList = board.getAdjList(2, 5);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(2, 6)));
			// TEST DOORWAY LEFT 
			testList = board.getAdjList(22, 12);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(22, 11)));
			//TEST DOORWAY DOWN
			testList = board.getAdjList(13, 5);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(14, 5)));
			//TEST DOORWAY UP
			testList = board.getAdjList(18, 3);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(17, 3)));
			//TEST DOORWAY DOWN, WHERE THERE'S A WALKWAY RIGHT
			testList = board.getAdjList(4, 19);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(5, 19)));
			
		}
		
		// Test adjacency at entrance to rooms
		// These tests are GREEN in planning spreadsheet
		@Test
		public void testAdjacencyDoorways()
		{
			// Test beside a door direction RIGHT
			Set<BoardCell> testList = board.getAdjList(2, 6);
			assertTrue(testList.contains(board.getCellAt(2, 5)));
			assertTrue(testList.contains(board.getCellAt(1, 6)));
			assertTrue(testList.contains(board.getCellAt(3, 6)));
			assertEquals(3, testList.size());
			// Test beside a door direction DOWN
			testList = board.getAdjList(14, 4);
			assertTrue(testList.contains(board.getCellAt(13, 4)));
			assertTrue(testList.contains(board.getCellAt(15, 4)));
			assertTrue(testList.contains(board.getCellAt(14, 3)));
			assertTrue(testList.contains(board.getCellAt(14, 5)));
			assertEquals(4, testList.size());
			// Test beside a door direction LEFT
			testList = board.getAdjList(22, 11);
			assertTrue(testList.contains(board.getCellAt(22, 12)));
			assertTrue(testList.contains(board.getCellAt(22, 10)));
			assertTrue(testList.contains(board.getCellAt(21, 11)));
			assertTrue(testList.contains(board.getCellAt(23, 11)));
			assertEquals(4, testList.size());
			// Test beside a door direction UP
			testList = board.getAdjList(17, 3);
			assertTrue(testList.contains(board.getCellAt(17, 4)));
			assertTrue(testList.contains(board.getCellAt(17, 2)));
			assertTrue(testList.contains(board.getCellAt(16, 3)));
			assertTrue(testList.contains(board.getCellAt(18, 3)));
			assertEquals(4, testList.size());
		}

		// Test a variety of walkway scenarios
		// These tests are LIGHT PURPLE on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
	
			
			// Test on left edge of board, three walkway pieces
			Set<BoardCell> testList = board.getAdjList(15, 0);
			assertTrue(testList.contains(board.getCellAt(15, 1)));
			assertTrue(testList.contains(board.getCellAt(14, 0)));
			assertTrue(testList.contains(board.getCellAt(16, 0)));
			assertEquals(3, testList.size());

			// Test between two rooms, walkways right and left
			testList = board.getAdjList(18, 11);
			assertTrue(testList.contains(board.getCellAt(17, 11)));
			assertTrue(testList.contains(board.getCellAt(19, 11)));
			assertEquals(2, testList.size());

			
			// Surrounded by 4 walkways
			testList = board.getAdjList(10, 15);
			assertTrue(testList.contains(board.getCellAt(10, 16)));
			assertTrue(testList.contains(board.getCellAt(10, 14)));
			assertTrue(testList.contains(board.getCellAt(11, 15)));
			assertTrue(testList.contains(board.getCellAt(9, 15)));
			assertEquals(4, testList.size());

			// Test on walkway on edge of board with room on 2 other sides
			testList = board.getAdjList(0, 5);
			assertTrue(testList.contains(board.getCellAt(0, 6)));
			assertEquals(1, testList.size());
		}
		
		
		// Tests of just walkways, 1 step, includes on edge of board
		// and beside room
		// Have already tested adjacency lists on all four edges, will
		// only test two edges here
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsOneStep() {
			board.calcTargets(15, 3, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(15, 2)));
			assertTrue(targets.contains(board.getCellAt(15, 4)));
			assertTrue(targets.contains(board.getCellAt(14, 3)));
			assertTrue(targets.contains(board.getCellAt(16, 3)));	
			
			board.calcTargets(5, 0, 1);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(4, 0)));
			assertTrue(targets.contains(board.getCellAt(6, 0)));	
			assertTrue(targets.contains(board.getCellAt(5, 1)));			
		}
		
		// Tests of just walkways, 2 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsTwoSteps() {
			board.calcTargets(15, 25, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(5, targets.size());			
			assertTrue(targets.contains(board.getCellAt(13, 25)));
			assertTrue(targets.contains(board.getCellAt(17, 25)));
			assertTrue(targets.contains(board.getCellAt(15, 23)));
			assertTrue(targets.contains(board.getCellAt(14, 24)));	
			assertTrue(targets.contains(board.getCellAt(16, 24)));

			
			
			board.calcTargets(11, 15, 2);
			targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(9, 15)));
			assertTrue(targets.contains(board.getCellAt(13,15)));	
			assertTrue(targets.contains(board.getCellAt(11, 17)));
			assertTrue(targets.contains(board.getCellAt(12, 14)));
			assertTrue(targets.contains(board.getCellAt(10, 14)));	
			assertTrue(targets.contains(board.getCellAt(10, 16)));
			assertTrue(targets.contains(board.getCellAt(12, 16)));			
		}
		
		// Tests of just walkways, 4 steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsFourSteps() {
			board.calcTargets(25, 17, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(21, 17)));
			
			
			// Includes a path that doesn't have enough length
			board.calcTargets(24, 4, 4);
			targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(20, 4)));
		}	
		
		// Tests of just walkways plus one door, 6 steps
		// These are LIGHT BLUE on the planning spreadsheet
		//think i am counting correctly (double check)
		@Test
		public void testTargetsSixSteps() {
			board.calcTargets(5, 0, 6);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCellAt(6, 5)));
			assertTrue(targets.contains(board.getCellAt(5, 6)));	
			assertTrue(targets.contains(board.getCellAt(6, 3)));	
			assertTrue(targets.contains(board.getCellAt(5, 4)));	
			assertTrue(targets.contains(board.getCellAt(4, 4)));
			assertTrue(targets.contains(board.getCellAt(6, 1)));
			assertTrue(targets.contains(board.getCellAt(5, 2)));
		}	
		
		// Test getting into a room
		// These are LIGHT BLUE on the planning spreadsheet

		@Test 
		public void testTargetsIntoRoom()
		{
			// One room is exactly 2 away
			board.calcTargets(11, 15, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(7, targets.size());			
			assertTrue(targets.contains(board.getCellAt(9, 15)));
			assertTrue(targets.contains(board.getCellAt(13,15)));	
			assertTrue(targets.contains(board.getCellAt(11, 17)));
			assertTrue(targets.contains(board.getCellAt(12, 14)));
			assertTrue(targets.contains(board.getCellAt(10, 14)));	
			assertTrue(targets.contains(board.getCellAt(10, 16)));
			assertTrue(targets.contains(board.getCellAt(12, 16)));		
				
			
		}
		
		
		// Test getting into room, doesn't require all steps
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsIntoRoomShortcut() 
		{

			// directly under a down door (can't go 2)
			Set<BoardCell> targets= board.getTargets();
			board.calcTargets(14, 4, 2);
			targets= board.getTargets();
			assertEquals(8, targets.size());			
			assertTrue(targets.contains(board.getCellAt(13, 4)));
			assertTrue(targets.contains(board.getCellAt(13, 5)));
			assertTrue(targets.contains(board.getCellAt(13, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 5)));
			assertTrue(targets.contains(board.getCellAt(15, 3)));
			assertTrue(targets.contains(board.getCellAt(16, 4)));
			assertTrue(targets.contains(board.getCellAt(14, 2)));
			assertTrue(targets.contains(board.getCellAt(14, 6)));
			
			//door available in 2 moves, die roll 3
			board.calcTargets(15, 3, 3);	
			targets= board.getTargets();
			assertEquals(14, targets.size());			
			assertTrue(targets.contains(board.getCellAt(15, 6)));
			assertTrue(targets.contains(board.getCellAt(18, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 0)));
			assertTrue(targets.contains(board.getCellAt(16, 1)));
			assertTrue(targets.contains(board.getCellAt(16, 5)));
			assertTrue(targets.contains(board.getCellAt(14, 5)));
			assertTrue(targets.contains(board.getCellAt(14, 1)));
			assertTrue(targets.contains(board.getCellAt(17, 2)));
			assertTrue(targets.contains(board.getCellAt(17, 4)));
			assertTrue(targets.contains(board.getCellAt(13, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 2)));
			assertTrue(targets.contains(board.getCellAt(14, 3)));
			assertTrue(targets.contains(board.getCellAt(15, 4)));
			assertTrue(targets.contains(board.getCellAt(16, 3)));
		
			
		}

		// Test getting out of a room
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testRoomExit()
		{	
			//a door next to another doorway only 1 move, should go onto walkway
			Set<BoardCell> targets= board.getTargets();
			board.calcTargets(9, 17, 1);	
			targets= board.getTargets();
			assertEquals(1, targets.size());			
			assertTrue(targets.contains(board.getCellAt(9, 16)));
			
			//door next to another door and open walkway
			board.calcTargets(11, 17, 2);	
			targets= board.getTargets();
			assertEquals(2, targets.size());			
			//assertTrue(targets.contains(board.getCellAt(11, 15)));
			assertTrue(targets.contains(board.getCellAt(10, 16)));
			assertTrue(targets.contains(board.getCellAt(12, 16)));
		}
}
