package clueGame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;
import clueGame.BoardCell.DoorDirection;

public class Board {
	public final int MAX_BOARD_SIZE = 25;
	private int numRows = 30;
	private int numColumns = 30;
	private String boardConfigFile;
	private String roomConfigFile;

	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<>();
	private HashSet<BoardCell> targets = new HashSet<BoardCell>();
	private HashSet<BoardCell> visited = new HashSet<BoardCell>();
	private Map<Character, String> legend;
	public BoardCell board[][] = new BoardCell[numRows][numColumns];
	//BoardCell start = new BoardCell();

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	//constructor is private to ensure only one copy
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
			try {
				loadRoomConfig(roomConfigFile);
			} catch (IOException e) {
				System.out.println("Trouble loading roomConfigFile - txt");
			}
			try {
				loadBoardConfig(boardConfigFile);
			} catch (IOException e) {
				System.out.println("Trouble loading boardConfigFile - csv");
			}
		
		calcAdjacencies();
	}
	
	

	public void calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
				BoardCell testCell = new BoardCell();
				BoardCell startCell = new BoardCell();
				startCell = board[row][col];
				//up
				if (row > 0) {
					testCell = board[row - 1][col];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row - 1][col]);
				}
				// down
				if (numRows - 1 > row) {
					testCell = board[row + 1][col];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row + 1][col]);
				}
				// right
				if (col < numColumns - 1) {
					testCell = board[row][col + 1];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row][col + 1] );
				}
				// left
				if (col > 0) {
					testCell = board[row][col - 1];
					if (adjHelp(testCell, startCell))
						adjSet.add(board[row][col - 1]);
				}
				// push the adj list and corresponding cell to map
				adjMatrix.put(getCellAt(row, col), adjSet);
			}
		}
		
		
	}
	
	public HashSet<BoardCell> calcTargets(int row, int col, int dieRoll) {
		// create new hashset
		BoardCell startCell = getCellAt(row, col);
		BoardCell start_og = new BoardCell();
		start_og = getCellAt(row, col);
		HashSet<BoardCell> foundTargets = new HashSet<BoardCell>();
		// clear the old one
		targets.clear();
		
		foundTargets = findAllTargets(startCell, dieRoll, start_og);
		// remove the startCell if it exists (illegal move)
		foundTargets.remove(startCell);
		return (foundTargets);	
	}
	
	public HashSet<BoardCell> findAllTargets(BoardCell startCell, int stepsRemaining, BoardCell start_og) {
		// go through every adj cell
		for (BoardCell testCell : getAdjList(startCell.getRow(), startCell.getColumn())) {
			if (testCell.getRow() == start_og.getRow() && testCell.getColumn() == start_og.getColumn()) {
				visited.add(start_og);
				continue;
			}
			if(testCell.isDoorway() && isGoodDoor(testCell,startCell)) {
				targets.add(testCell);
				continue;
			}
			if (visited.contains(testCell)) {
				continue;
			}
			// add to visited
			visited.add(testCell);
			// if numsteps are 1 we have reached are target
			if (stepsRemaining == 1) {
				//System.out.println(b.getInitialFull());
				targets.add(testCell);
			}
			// else call recursive
			else if(!testCell.isDoorway()){
				findAllTargets(testCell, stepsRemaining - 1, start_og);
			}
			visited.remove(testCell);
		}
		// return them targets
		return (targets);
	}

	/*
	 * *************************HELPER FUNCTIONS******************************************
	 */
	
	
	public boolean adjHelp(BoardCell testCell, BoardCell startCell) {
		if (
				(!(testCell.isDoorway() && isGoodDoor(testCell,startCell)) && testCell.getInitials().length() == 2) 
				|| 
				((!isExitSquare(testCell, startCell)) && startCell.getInitials().length() == 2)
				|| 
				((isSameRoom(testCell,startCell) && startCell.getFirstInitial() != 'W'))
				||
				(startCell.getFirstInitial() == 'W' && testCell.getFirstInitial() != 'W' && testCell.getInitials().length() !=2)
				||
				(startCell.getFirstInitial() != 'W' && testCell.getFirstInitial() == 'W' && testCell.getInitials().length() !=2 && startCell.getInitials().length() != 2)) {
			return false;
		}
	
		else {
			return true;
		}
	}

	//checks if target is the right placement to exit based on starting origin being a door and it's direction
	public boolean isExitSquare(BoardCell target, BoardCell origin) {
		if(origin.getRow() > target.getRow() && origin.getDoorDirection()==DoorDirection.UP) 
			return true;
		else if(origin.getRow() < target.getRow() && origin.getDoorDirection()==DoorDirection.DOWN)
			return true;
		else if(origin.getColumn() > target.getColumn() && origin.getDoorDirection()==DoorDirection.LEFT)
			return true;
		else if(origin.getColumn() < target.getColumn() && origin.getDoorDirection()==DoorDirection.RIGHT)
			return true;
		else return false;
	}
	
	//checks if the target is good to enter from origin
	public boolean isGoodDoor(BoardCell target, BoardCell origin) {
		if(origin.getRow() < target.getRow() && target.getDoorDirection()==DoorDirection.UP) 
				return true;
		else if(origin.getRow() > target.getRow() && target.getDoorDirection()==DoorDirection.DOWN)
				return true;
		else if(origin.getColumn() < target.getColumn() && target.getDoorDirection()==DoorDirection.LEFT)
				return true;
		else if(origin.getColumn() > target.getColumn() && target.getDoorDirection()==DoorDirection.RIGHT)
				return true;
		else return false;
	}
	
	public boolean isSameRoom(BoardCell target, BoardCell origin){
		if(origin.getFirstInitial() == target.getFirstInitial())
			return true;
		else return false;
	}
	
	public boolean isCloset(BoardCell target) {
		if(target.getFirstInitial() == 'X')
			return true;
		else return false;
	}
	public void p(String msg) {
		System.out.println(msg);
	}

	/*
	 * ********************CONFIGURATION METHODS********************************
	 */

	public void setConfigFiles(String csv, String txt){
		boardConfigFile = csv;
		roomConfigFile = txt;
	}
	// needs to get changed
	public void loadRoomConfig(String legend_txt) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(legend_txt), "UTF-8"));
	}	
	
	public void loadBoardConfig(String layout_csv) throws IOException {
		// set up file reader for csv
		BufferedReader in = new BufferedReader(new FileReader(layout_csv));
		String line;
		int row = 0;
		int col = 0;
		// used to make sure there are the same number of columns in each row
		int checked = 0;
		int cols_seen = 0;
		while((line = in.readLine()) != null) {
			String[] initials = line.split(",");
			for(String i: initials) {
				board[row][col] = new BoardCell(row, col, i);
				col++;
			}
			if(col != cols_seen) {
				if(checked == 0) {
					cols_seen = col;
					checked += 1;
				} else {
					System.out.println("CSV file has columns of uneven length");
				}
			}
			col = 0;
			row++;
		}
		numRows = row;
		numColumns = cols_seen;
	}
	
	/*
	 ************************** GETTERS AND SETTERS *********************************
	 */
	
	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(getCellAt(row,col));
	}

	public BoardCell getCellAt(int row, int col) {
		// return the corresponding2D array
		return board[row][col];
	}
		public int getNumRows() {
			return numRows;
		}

		public void setNumRows(int newNumRows) {
			this.numRows = newNumRows;
		}

		public int getNumColumns() {
			return numColumns;
		}

		public void setNumColumns(int newNumColumns) {
			this.numColumns = newNumColumns;
		}

		public BoardCell[][] getBoard() {
			return board;
		}

		public void setBoard(BoardCell[][] newBoard) {
			this.board = newBoard;
		}

		public Map<Character, String> getLegend() {
			return legend;
		}

		public void setLegend(Map<Character, String> newLegend) {
			this.legend = newLegend;
		}

		public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
			return adjMatrix;
		}

		public void setAdjMatrix(Map<BoardCell, Set<BoardCell>> newAdjMatrix) {
			this.adjMatrix = newAdjMatrix;
		}

		public Set<BoardCell> getTargets() {
			return targets;
		}

		public String getBoardConfigFile() {
			return boardConfigFile;
		}

		public void setBoardConfigFile(String newBoardConfigFile) {
			this.boardConfigFile = newBoardConfigFile;
		}

		public String getRoomConfigFile() {
			return roomConfigFile;
		}

		public void setRoomConfigFile(String newRoomConfigFile) {
			this.roomConfigFile = newRoomConfigFile;
		}

		public int getMAX_BOARD_SIZE() {
			return MAX_BOARD_SIZE;
		}

}
