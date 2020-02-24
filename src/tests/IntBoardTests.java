//Luke Valentine
//Brianna Gaede

package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

import java.util.Random;

public class IntBoardTests {
	IntBoard board;

	@Before
    public void beforeAll() {
       board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
    }
	
	@Test
	public void testAdjacency() {  
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testTargets0_3()
	{
		//1
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		
		//2
		BoardCell cell2 = board.getCell(0, 3);
		board.calcTargets(cell2, 3);
		Set targets2 = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets2.contains(board.getCell(0, 0)));
		assertTrue(targets2.contains(board.getCell(3, 3)));
		assertTrue(targets2.contains(board.getCell(2, 3)));
		assertTrue(targets2.contains(board.getCell(3, 0)));
		assertTrue(targets2.contains(board.getCell(1, 1)));
		assertTrue(targets2.contains(board.getCell(2, 2)));
	}

}
