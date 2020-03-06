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

	private Map<BoardCell, Set<BoardCell>> adjMatrix;

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
					adjSet.add(cells[i][j + 1] );
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
		//if we don't start at a doorway
		if(!startCell.isDoorway()) {
		c = findAllTargets(startCell, k);
		// remove illegal moves;
		c.remove(startCell);
		//make sure the doors are the correct direction
		for(BoardCell cell: c) {
			if(!isGoodDoor(cell,startCell) && cell.isDoorway()&&!startCell.isDoorway()) {
				c.remove(cell);
			}
		}
		} 
		//otherwise, only add the exit square
		else {
			switch(startCell.getDoorDirection()) {
			case UP:
				BoardCell up = getCellAt(i+1, j);
				if(isExitSquare(up,startCell))
					c.add(up);
				break;
			case DOWN:
				BoardCell down = getCellAt(i+1, j);
				if(isExitSquare(down,startCell))
					c.add(down);
				break;
			case LEFT:
				BoardCell left = getCellAt(i+1, j);
				if(isExitSquare(left,startCell))
					c.add(left);
				break;
			case RIGHT:
				BoardCell right = getCellAt(i+1, j);
				if(isExitSquare(right,startCell))
					c.add(right);
				break;
			default:
				break;
			}
		}
		return (c);
		
	}
	public boolean isExitSquare(BoardCell target, BoardCell origin) {
		if(origin.getRow() > target.getRow() && target.getDoorDirection()==DoorDirection.UP) 
			return true;
		else if(origin.getRow() < target.getRow() && target.getDoorDirection()==DoorDirection.DOWN)
			return true;
		else if(origin.getColumn() > target.getColumn() && target.getDoorDirection()==DoorDirection.LEFT)
			return true;
		else if(origin.getColumn() < target.getColumn() && target.getDoorDirection()==DoorDirection.RIGHT)
			return true;
		else return false;
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
	
	public boolean sameRoom(BoardCell target, BoardCell origin){
	// 	for now this is not allowing movement within rooms, only walkways
		if(origin.getInitial() == target.getInitial() && origin.getInitial() == 'W')
			return true;
		else return false;
	}
	
	public boolean isCloset(BoardCell target) {
		if(target.getInitialFull() == "X")
			return true;
		else return false;
	}
	public void p(String mssg) {
		System.out.println(mssg);
	}
	
	public boolean exitCell(BoardCell target, BoardCell origin) {
		p("exit door" + origin);
		switch(origin.getDoorDirection()) {
		case UP:
			if(origin.getRow()+1 == target.getRow() && origin.getColumn() == target.getColumn()) {
				p("good"+target);
				return true;
			}
			break;
		case DOWN:
			if(origin.getRow()-1 == target.getRow() && origin.getColumn() == target.getColumn()){
				p("good"+target);
				return true;
			}
			break;
		case LEFT:
			if(origin.getRow() == target.getRow() && origin.getColumn()-1 == target.getColumn()){
				p("good"+target);
				return true;
			}
			break;
		case RIGHT:
			if(origin.getRow() == target.getRow() && origin.getColumn()+1 == target.getColumn()){
				p("good"+target);
				return true;
			}
			break;
		default:
			return false;
			}
		return false;
	}
	
	public HashSet<BoardCell> findAllTargets(BoardCell curr, int numSteps) {
		visited.clear();
		// go through every adj cell
		for (BoardCell b : getAdjList(curr.getRow(), curr.getColumn())) {		
			// if visited loop
			if (visited.contains(b)) {
				continue;
			}
			// if it is a closet, skip, or if it's not a walkway
			if(curr.getInitialFull().length() == 1 && isCloset(b))
				continue;
			if(curr.getInitialFull().length() == 1 && b.getInitialFull().length() == 1 && !sameRoom(b,curr))
				continue;
			
			// add to visited
		
			visited.add(b);
			// if numsteps are 1 we have reached are target
			if (numSteps == 1) {
				targets.add(b);
			}
			//if we reached a doorway before the die roll was over
			else if (b.isDoorway())
				targets.add(b);
					
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



	public Set<BoardCell> getAdjList(int i, int j) {
		return adjtMtx1.get(getCellAt(i,j));
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
			return adjMatrix;
		}

		public void setAdjMatrix(Map<BoardCell, Set<BoardCell>> adjMatrix) {
			this.adjMatrix = adjMatrix;
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
