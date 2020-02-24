package tests;

import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjtMtx1;
	
	public IntBoard() {
		super();
		BoardCell cells[] = new BoardCell[4]; 
		//hardcode in cells????
		//[0][1], [1][2], [2][1], [3][0]
		cells[0].column = 0;
		cells[0].row = 1;
		
		cells[0].column = 1;
		cells[0].row = 2;
		
		cells[0].column = 2;
		cells[0].row = 1;
		
		cells[0].column = 3;
		cells[0].row = 0;
		
	}
	
	void calcAdjacencies() {
		
	}
	
	BoardCell getAdjList(BoardCell cell) {
		return null;
	}
	
	void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	void getTargets() {
		
	}

}
