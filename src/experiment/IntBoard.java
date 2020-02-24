//Luke Valentine
//Brianna Gaede

package experiment;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjtMtx1;
	public BoardCell cells[][] = new BoardCell[4][4]; 
	
	public IntBoard() {
		super();
		for (int i = 0; i < 4; i++) {
			for (int j=0; j < 4; j++) {
				cells[i][j]=  new BoardCell();
				cells[i][j].column = i;
				cells[i][j].column = j;
			}
		}
		calcAdjacencies();
		
	}
	
	public void calcAdjacencies() {
		//check each cell and add valid adjacent cells to the adjmtx
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			BoardCell cell = cells[i][j];
			Set<BoardCell> adjSet = null;
			if (cell.row + 1 < 4) {
				//BoardCell cell1 = new BoardCell();
				cell.row += 1;
				adjSet.add(cell);
			}
			else if(cell.row - 1 >= 0) {
				//BoardCell cell1 = new BoardCell();
				cell.row -= 1;
				adjSet.add(cell);
			}
			else if(cell.column + 1 < 4) {
				//BoardCell cell1 = new BoardCell();
				cell.column += 1;
				adjSet.add(cell);
			}
			else if(cell.column - 1 > 4) {
				//BoardCell cell1 = new BoardCell();
				cell.column -= 1;
				adjSet.add(cell);
				}
			adjtMtx1.put(cell,adjSet);
		}
		}
	}
	
	public BoardCell getCell(int i, int j) {
		return cells[i][j];
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjtMtx1.get(cell);
	}
	
	public Set<BoardCell> calcTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> targets = null;
		for(BoardCell cell: adjtMtx1.get(startCell)) {
			Set<BoardCell> visited = null;
			targets.add(findAllTargets(startCell,pathLength,visited));
		}
		return targets;
	}
	
	public BoardCell findAllTargets(BoardCell curr, int numSteps, Set<BoardCell> visited) {
		for (BoardCell cell: adjtMtx1.get(curr)) {
			if (visited.contains(curr)) {
				continue;
			}
			else if(numSteps == 1) {
					return cell;
				}
					return(findAllTargets(cell, numSteps-1, visited));		
		}
		return null;
	}
	
	public Set<BoardCell> getTargets() {
		return null;
		
	}

}
