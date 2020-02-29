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

public class Board {
	private Map<BoardCell, Set<BoardCell>> adjtMtx1 = new HashMap<>();
	HashSet<BoardCell> targets = new HashSet<BoardCell>();
	HashSet<BoardCell> visited = new HashSet<BoardCell>();
	
	private int numRows = 23;
	private int numColumns = 24;
	public BoardCell cells[][] = new BoardCell[numRows][numColumns]; 
	
	public final int MAX_BOARD_SIZE = 25;
	private BoardCell[][] board;
	
	private Map<Character,String> legend;
	
	private Map<BoardCell,Set<BoardCell>> adjMatrix;

	private String boardConfigFile;
	private String roomConfigFile;
	
	
	// variable used for singleton pattern
		private static Board theInstance = new Board();
		// constructor is private to ensure only one can be created
		private Board() {}
		// this method returns the only Board
		public static Board getInstance() {
			return theInstance;
		}
	
	
	public void initialize() {
		//creating the cells
		for (int i = 0; i < 4; i++) {
			for (int j= 0; j < 4; j++) {
				cells[i][j]=  new BoardCell(i,j, "");
				//cells[i][j].setRow(i);
				//cells[i][j].setColumn(j);
			}
		}
		//call the calc adj once and then get them 
		calcAdjacencies();
		
	}
	
	
	
	//needs to get changed
	public void loadRoomConfig(String legend_txt) throws IOException {
		//initialize();
		
		//BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ", ";
        String[] csv_line;
        legend = new HashMap<Character, String>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(legend_txt), "UTF-8"))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
            	//System.out.println(line);
            	
                csv_line = line.split(cvsSplitBy);
                
                //System.out.println(csv_line[0].charAt(0));
                //System.out.println(csv_line[1]);
                char x = csv_line[0].charAt(0);
                legend.put(x, csv_line[1]);
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            } 
	}
	
	public void loadBoardConfig(String layout) throws FileNotFoundException {
		FileReader layRead = new FileReader(layout);
		Scanner layIn = new Scanner(layRead);
		String line;
		int row = 0;
		int prevCol = -1;
		while (layIn.hasNextLine()) {
			int col = 0;
			line = layIn.nextLine();
			// Goes through each line of the file character by character
			for (int i =  0; i < line.length(); i++) {
				char c = line.charAt(i);
				// Passes over commas
				if (c == ',')
					continue;
				String s = line.charAt(i)+"";
				// Throws an error if there is not a room at the next character
				if (legend.get(line.charAt(i)) == null) {
					System.out.println("Error in the legend file.");
					
				}
				// Accounts for multiple characters (doorways)
				if (i+1 < line.length() && line.charAt(i+1) != ',') {
					s += line.charAt(i+1);
					i++;
				}
				// creates a new boardcell at that row and col with the string s that contains
				// either a room character, a walkway character, or a doorway
				//BoardCell temp = createBoardCell(row,col,s);
				cells[row][col] = new BoardCell(row, col, s);
				//cells[row][col].setRow(row);
				///cells[row][col].setColumn(col);
				//cells[row][col].setInitial(s);
				//System.out.println(s + " " +cells[row][col].getInitial() +cells[row][col].getRow()+cells[row][col].getColumn());
				
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
		numRows = row;
		numColumns = prevCol;
	}
	
	public clueGame.BoardCell getCellAt(int i, int j) {
		//return the corresponding2D array
		return cells[i][j];
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				HashSet<BoardCell> adjSet = new HashSet<BoardCell>();
				//up
				if (i > 0) {
					adjSet.add(cells[i-1][j]);
				}
				//down
				if (numColumns-1 > i) {
					adjSet.add(cells[i+1][j]);
				}
				//right
				if (j < numRows-1) {
					adjSet.add(cells[i][j+1]);
				}
				//left
				if (j >0) {
					adjSet.add(cells[i][j-1]);
				}
				//push the adj list and corresponding cell to map
				adjtMtx1.put(getCellAt(i, j), adjSet);
			}
		}
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
			
	public void setConfigFiles(String string, String string2) throws IOException {
		try {
			loadRoomConfig(string2); //csv
			loadBoardConfig(string);  //text
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		}
	}
	public Set<BoardCell> getAdjList(BoardCell cell) {
		//get the corresponding adjList (to the given cell)
		return adjtMtx1.get(cell);
	}
	
	//getters and setters
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
