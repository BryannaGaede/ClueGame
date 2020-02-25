//Luke Valentine
//Brianna Gaede

package experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjtMtx1 = new HashMap<>();
	private BoardCell cells[][] = new BoardCell[4][4]; 
	HashSet<BoardCell> targets = new HashSet<BoardCell>();
	HashSet<BoardCell> visited = new HashSet<BoardCell>();
	protected int num_rows = 4;
	protected int num_columns = 4;

	public IntBoard() {
		super();
		//creating the cells
		for (int i = 0; i < 4; i++) {
			for (int j= 0; j < 4; j++) {
				cells[i][j]=  new BoardCell();
				cells[i][j].row = i;
				cells[i][j].column = j;
			}
		}
		//call the calc adj once and then get them 
		calcAdjacencies();
	}
	
	@Override
	public String toString() {
		return "IntBoard [adjtMtx1=" + adjtMtx1 + ", cells=" + Arrays.toString(cells) + ", targets=" + targets + "]";
	}

	public void calcAdjacencies() {
		for (int i = 0; i < num_rows; i++) {
			for (int j = 0; j < num_columns; j++) {
				HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
				//up
				if (i > 0) {
					adjSet.add(cells[i-1][j]);
				}
				//down
				if (num_columns-1 > i) {
					adjSet.add(cells[i+1][j]);
				}
				//right
				if (j < num_rows-1) {
					adjSet.add(cells[i][j+1]);
				}
				//left
				if (j >0) {
					adjSet.add(cells[i][j-1]);
				}
				//push the adj list and corresponding cell to map
				adjtMtx1.put(getCell(i, j), adjSet);
			}
		}
	}
	
	public BoardCell getCell(int i, int j) {
		//return the corresponding2D array
		return cells[i][j];
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		//get the corresponding adjList (to the given cell)
		return adjtMtx1.get(cell);
	}
	
	public HashSet<BoardCell> calcTargets(BoardCell startCell, int pathLength) {
		//create new hashset
		HashSet<BoardCell> c= new HashSet<BoardCell>();
		//clear the old one
		targets.clear();
		//remove the startCell if it exists (illegal move)
		c = findAllTargets(startCell, pathLength);
		c.remove(startCell);
		return (c);
	}
	
	public HashSet<BoardCell> findAllTargets(BoardCell curr, int numSteps) {
		visited.clear();
		//go through every adj cell
		for (BoardCell b : getAdjList(curr)) {
			//if visited loop
			if (visited.contains(b)) {
				continue;
			}
			//add to visited
			visited.add(b);
			//if numsteps are 1 we have reached are target
			if (numSteps == 1) {
				targets.add(b);
			}
			//else call recursive
			else {
				findAllTargets(b, numSteps-1);
			}
			visited.remove(b);
		}
		//return them targets
		return(targets);
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
		
	}

}
