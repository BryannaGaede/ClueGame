package clueGame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;
import clueGame.BoardCell.DoorDirection;

public class Board {
	private Map<BoardCell, Set<BoardCell>> adjtMtx1 = new HashMap<>();
	HashSet<BoardCell> targets = new HashSet<BoardCell>();
	HashSet<BoardCell> visited = new HashSet<BoardCell>();

	private int numRows = 30;
	private int numColumns = 30;
	public BoardCell cells[][] = new BoardCell[numRows][numColumns];

	public final int MAX_BOARD_SIZE = 25;
	private BoardCell[][] board;

	private Map<Character, String> legend;

	private Map<BoardCell, Set<BoardCell>> adjMatrix1;
	private Map<BoardCell, Set<BoardCell>> adjMatrix2;

	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		//creating the cells
		/*for (int i = 0; i < 4; i++) {
			for (int j= 0; j < 4; j++) {
				cells[i][j]=  new BoardCell();
				cells[i][j].setRow(i);
				cells[i][j].setColumn(j);
			}
		}
		*/
		calcAdjacencies();
		
	}

	// needs to get changed
	public void loadRoomConfig(String legend_txt) throws IOException {
		String line = "";
		String cvsSplitBy = ", ";
		String[] csv_line;
		legend = new HashMap<Character, String>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(legend_txt), "UTF-8"))) {
			while ((line = br.readLine()) != null) {
				//split csv up
				csv_line = line.split(cvsSplitBy);
				char x = csv_line[0].charAt(0);
				legend.put(x, csv_line[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadBoardConfig(String layout) throws FileNotFoundException {
		// set up file reader for csv
		FileReader layRead = new FileReader(layout);
		Scanner layIn = new Scanner(layRead);
		String line;
		int row = 0;
		int prevCol = -1;
		while (layIn.hasNextLine()) {
			int col = 0;
			line = layIn.nextLine();
			// Goes through each line of the file character by character
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				// Passes over commas
				if (c == ',')
					continue;
				String s = line.charAt(i) + "";
				// gets multiple characters (doorways)
				if (i + 1 < line.length() && line.charAt(i + 1) != ',') {
					s += line.charAt(i + 1);
					i++;
				}
				//System.out.println(row + " " + col + " " + s);
				cells[row][col] = new BoardCell(row, col, s);
				col++;
			}
			// Throws an error if the columns are not all the same size
			if (prevCol >= 0 && prevCol != col) {
				System.out.println("Error in the legend file.");
			}
			prevCol = col;
			row++;
		}
		// Sets the number of rows and columns
		setNumRows(row);
		numRows = row;
		setNumColumns(prevCol);
		numColumns = prevCol;
		
	}

	public BoardCell getCellAt(int i, int j) {
		// return the corresponding2D array
		return cells[i][j];
	}
	

	public void calcAdjacencies() {
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				System.out.println(cells[i][j].getRow() + " " + cells[i][j].getColumn() + " " + cells[i][j].getInitialFull());
				HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
				// up
				if (i > 0) {
					adjSet.add(cells[i - 1][j]);
				}
				// down
				if (numColumns - 1 > i) {
					adjSet.add(cells[i + 1][j]);
				}
				// right
				if (j < numRows - 1) {
					adjSet.add(cells[i][j + 1]);
				}
				// left
				if (j > 0) {
					adjSet.add(cells[i][j - 1]);
				}
				// push the adj list and corresponding cell to map
				adjtMtx1.put(getCellAt(i, j), adjSet);
			}
		}
	}
	
	

	

	public HashSet<BoardCell> calcTargets(int i, int j, int k) {
		// create new hashset
		BoardCell startCell = getCellAt(i, j);
		HashSet<BoardCell> c = new HashSet<BoardCell>();
		// clear the old one
		targets.clear();
		// remove the startCell if it exists (illegal move)
		c = findAllTargets(startCell, k);
		c.remove(startCell);
		return (c);
		
	}
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

	public HashSet<BoardCell> findAllTargets(BoardCell curr, int numSteps) {
		visited.clear();
		// go through every adj cell
		for (BoardCell b : getAdjList1(curr.getRow(), curr.getColumn())) {
			char curr_initial = curr.getInitial();
			//check if a door is an access point to a room
			boolean goodDoor = true;
			if(b.getInitialFull().length()==2 && (goodDoor = isGoodDoor(b,curr))) {
				targets.add(b);
			}
			
			//stay in walkway
			boolean walk = true;
			if (curr.getInitial() == 'W' && b.getInitial() != 'W') {
				walk = false;
			}
			
			// if visited loop
			if (visited.contains(b) || b.getInitial() == 'X'|| (curr_initial == b.getInitial() && curr_initial != 'W') || !goodDoor || !walk) {
				continue;
			}
		
			// add to visited
			visited.add(b);
			// if numsteps are 1 we have reached are target
			if (numSteps == 1) {
				//System.out.println(b.getInitialFull());
				targets.add(b);
			}
			else if (b.isDoorway()) {
				targets.add(b);
			}
			// else call recursive
			else {
				findAllTargets(b, numSteps - 1);
			}
			visited.remove(b);
		}
		// return them targets
		return (targets);
	}

	public void setConfigFiles(String string, String string2) throws IOException {
		try {
			// try to load the rooms
			loadRoomConfig(string2); // csv
			loadBoardConfig(string); // text
		}
		// catch file not found error
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}



	public Set<BoardCell> getAdjList1(int i, int j) {
		
		return adjtMtx1.get(getCellAt(i,j));
	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		
		return calcTargets(i, j, 1);
	}
	
	
	// getters and setters
		public int getNumRows() {
			return numRows;
		}

		public void setNumRows(int numRows) {
			this.numRows = numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}

		public void setNumColumns(int numColumns) {
			this.numColumns = numColumns;
		}

		public BoardCell[][] getBoard() {
			return board;
		}

		public void setBoard(BoardCell[][] board) {
			this.board = board;
		}

		public Map<Character, String> getLegend() {
			return legend;
		}

		public void setLegend(Map<Character, String> legend) {
			this.legend = legend;
		}

		public Map<BoardCell, Set<BoardCell>> getAdjMatrix() {
			return adjMatrix1;
		}

		public void setAdjMatrix(Map<BoardCell, Set<BoardCell>> adjMatrix) {
			this.adjMatrix1 = adjMatrix;
		}

		public Set<BoardCell> getTargets() {
			return targets;
		}

		public String getBoardConfigFile() {
			return boardConfigFile;
		}

		public void setBoardConfigFile(String boardConfigFile) {
			this.boardConfigFile = boardConfigFile;
		}

		public String getRoomConfigFile() {
			return roomConfigFile;
		}

		public void setRoomConfigFile(String roomConfigFile) {
			this.roomConfigFile = roomConfigFile;
		}

		public int getMAX_BOARD_SIZE() {
			return MAX_BOARD_SIZE;
		}

}
