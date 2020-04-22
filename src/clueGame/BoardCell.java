//Luke Valentine
//Brianna Gaede

package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private  String initials;
	private String name;
	private boolean printFullName;
	private DoorDirection doorDirection;
	private int cellSize = 20;
	private boolean isWalkway;

	public BoardCell(int row, int column, String initial) {
		super();
		this.row = row;
		this.column = column;
		this.initials = initial;
		this.printFullName = false;
		this.isWalkway = false;
		name = Board.getRoomName(initial.charAt(0));
		
		if(this.initials.equals("W")) {
			this.isWalkway = true;
		}
		
		if (initial.length() == 2) {
			if (initial.charAt(1) == 'U') {
				this.doorDirection = DoorDirection.UP;
			}
			else if (initial.charAt(1) == 'D') {
				this.doorDirection = DoorDirection.DOWN;
			}
			else if (initial.charAt(1) == 'R') {
				this.doorDirection = DoorDirection.RIGHT;
			}
			else if (initial.charAt(1) == 'L') {
				this.doorDirection = DoorDirection.LEFT;
			}
			else {
				this.doorDirection = DoorDirection.NONE;
				if(initial.charAt(1) == 'P') {
					printFullName = true;
				}
			}
		} else {
			this.doorDirection = DoorDirection.NONE;
		}
	}

	public BoardCell() {
	}

	public static enum DoorDirection {

		NONE(0,0),UP(-1,0),DOWN(1,0),LEFT(0,-1),RIGHT(0,1);
		private int x,y;
		DoorDirection(int X, int Y) {
			x = X;
			y = Y;
		}
		public int getRow() {
			return x;
		}
		public int getCol() {
			return y;
		}	
	};

	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isRoom() {
		if(initials.charAt(0) != 'W' && initials.charAt(0) != 'F') {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + ", Initial=" + initials + ", doorDirection="
				+ doorDirection + "]";
	}


	public void draw(Graphics g, boolean highLight) {
		//paint doors
		
		
		if(this.isDoorway()) {
			switch(this.getDoorDirection()) {
			case LEFT:
				g.setColor(new Color(228,183,245));
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				g.setColor(new Color(135,239,109));
				g.fillRect(getColumn()*cellSize-1, (getRow())*cellSize, cellSize/4-1, cellSize-1);
				break;
			case RIGHT:
				g.setColor(new Color(228,183,245));
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				g.setColor(new Color(135,239,109));
				g.fillRect(getColumn()*cellSize+cellSize - 7, (getRow())*cellSize, cellSize/4, cellSize);
				break;
			case UP:
				g.setColor(new Color(228,183,245));
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				g.setColor(new Color(135,239,109));
				g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize/4);
				break;
			case DOWN:
				g.setColor(new Color(228,183,245));
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				g.setColor(new Color(135,239,109));
				g.fillRect(getColumn()*cellSize, (getRow()+1)*cellSize-7, cellSize, cellSize/4);
				break;
			default:
				break;
			}
			//paint rooms
		} else if(this.isRoom()) {
				g.setColor(new Color(228,183,245));
				g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		} else if(this.isWalkway) {
			//paint walkways
			g.setColor(new Color(117,101,116));
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
			g.fillRect(getColumn()*cellSize-1, (getRow())*cellSize-1, cellSize-1, cellSize-1);
		} else {
			//paint closet
			g.setColor(new Color(71,146,212));
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
			g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		if (highLight == true) {
			g.setColor(Color.YELLOW);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
			g.fillRect(getColumn()*cellSize-1, (getRow())*cellSize-1, cellSize-1, cellSize-1);
		}
	}
	
	public void printName(Graphics cell) {
		cell.setFont(new Font("Arial", Font.BOLD, 12));
		cell.setColor(Color.black);
		cell.drawString(this.name, (getColumn()*cellSize) + 3, getRow()*cellSize+(int)(.5*cellSize));
	}

	/*
	 * ***********************GETTERS AND SETTERS****************************
	 */


	public DoorDirection getDoorDirection() {
		if (doorDirection == DoorDirection.UP) {
			return DoorDirection.UP;
		}
		else if(doorDirection == DoorDirection.DOWN) {
			return DoorDirection.DOWN;
		}
		else if(doorDirection == DoorDirection.LEFT) {
			return DoorDirection.LEFT;
		}
		else if(doorDirection == DoorDirection.RIGHT) {
			return DoorDirection.RIGHT;
		}
		return (doorDirection);
	}

	public char getFirstInitial() {
		return initials.charAt(0);
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String letter) {
		this.initials = new String();
		this.initials = letter;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean doPrint() {
		if(this.printFullName) {
			return true;
		}
		return false;
	}

	
}

