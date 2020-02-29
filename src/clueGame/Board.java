package clueGame;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	private int numRows;
	private int numColumns;
	private BoardCell cells[][] = new BoardCell[numRows][numColumns]; 
	
	public final int MAX_BOARD_SIZE = 25;
	private BoardCell[][] board;
	
	//private Map<Character, String> rooms;
	private Map<Character,String> legend;
	
	private Map<BoardCell,Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
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
		
	}
	
	//needs to get changed
	public void loadRoomConfig(String legend_txt) throws IOException {
		
		
		//BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(legend_txt), "UTF-8"))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] csv_line = line.split(cvsSplitBy);
                System.out.println(csv_line[0].charAt(0));
                System.out.println(csv_line[1]);
                legend.put(csv_line[0].charAt(0), csv_line[1]);
            }
            if (line == null) {
            	System.out.println("why?");
            }
        }
            catch (IOException e) {
                e.printStackTrace();
            } 
	}
      
           
            
		/*FileReader legRead = new FileReader(legend_txt);
		Scanner legIn = new Scanner(legRead);
		String line;
		// While the file has another line...
		while (legIn.hasNextLine()) {
			line = legIn.nextLine();
			char c = line.charAt(0);
			String s = line.substring(3);
			
			// Checks to make sure that the legend file is correctly formatted
			
			//if (!(Character.isLetter(c) && line.charAt(1) == ',' && line.charAt(2) == ' ') || s.indexOf(',') >= 0) {
			//	System.out.println("Error in the legend file.");
			//}
			
			// Puts the character of the room and the name of the room in the Map containing all the rooms
			legend.put(c, s);
		}
	}*/
	
	
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
				/*if (rooms.get(line.charAt(i)) == null) {
					logger.write("Error in the legend file.");
					throw new BadConfigFormatException("Error in the layout file.");
				}*/
				// Accounts for multiple characters (doorways)
				if (i+1 < line.length() && line.charAt(i+1) != ',') {
					s += line.charAt(i+1);
					i++;
				}
				// creates a new boardcell at that row and col with the string s that contains
				// either a room character, a walkway character, or a doorway
				//BoardCell temp = createBoardCell(row,col,s);
				BoardCell temp = new BoardCell();
				temp.setRow(row);
				temp.setColumn(col);
				cells[temp.getRow()][temp.getColumn()] = temp;
				//cells.add(createBoardCell(row,col,s));
				//cells.get(calcIndex(row, col)).setRow(row);
				//cells.get(calcIndex(row, col)).setCol(col);
				col++;
			}
			// Throws an error if the columns are not all the same size
			/*if (prevCol >= 0 && prevCol != col) {
				logger.write("Error in the legend file.");
				throw new BadConfigFormatException("Error in the layout file.");
			}*/
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
		
	}
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	public void setConfigFiles(String string, String string2) throws IOException {
		try {
			loadRoomConfig(string);
			loadBoardConfig(string2);
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		}
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
	public void setTargets(Set<BoardCell> targets) {
		this.targets = targets;
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
