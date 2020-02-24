package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjtMtx1;
	
	//BoardCell cells[] = new BoardCell[4]; 
	
	public IntBoard() {
		super();
		
		//hardcode in cells????
		//[0][1], [1][2], [2][1], [3][0]
		/*
		cells[0].column = 0;
		cells[0].row = 1;
		
		cells[0].column = 1;
		cells[0].row = 2;
		
		cells[0].column = 2;
		cells[0].row = 1;
		
		cells[0].column = 3;
		cells[0].row = 0;
		*/
		
	}
	
	Set<BoardCell> calcAdjacencies() {
		//for (int i = 0; i < cells.size())
		return null;
	}
	
	public BoardCell getCell(int i, int j) {
		return null;
		
	}

	Set<BoardCell> getAdjList() {
		return null;
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return null;
		
	}

}
