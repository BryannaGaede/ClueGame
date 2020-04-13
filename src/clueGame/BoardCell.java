//Luke Valentine
//Brianna Gaede

package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.AttributedCharacterIterator;

import clueGame.BoardCell.DoorDirection;

public class BoardCell {
	private int row;
	private int column;
	private  String initials;
	private String name;
	private boolean printFullName;
	private DoorDirection doorDirection;

	public BoardCell(int row, int column, String initial) {
		super();
		this.row = row;
		this.column = column;
		this.initials = initial;
		this.printFullName = false;
		name = Board.getRoomName(initial.charAt(0));

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
	
	public void draw(Graphics g, Board c, boolean drawName, int cellSize) {
		//for some reason it thinks every room is grey(therefore drawing every initial, so check logic in board)
		if(drawName){
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
			g.setFont(new Font("TimesRoman", Font.BOLD, 10));
			g.setColor(Color.black);
			g.drawString(getInitials(), (getColumn()*cellSize) + 3, getRow()*cellSize+(int)(.5*cellSize));
		}
		else if(doorDirection == DoorDirection.LEFT){
			g.setColor(Color.BLUE);
			g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize/4, cellSize);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(doorDirection == DoorDirection.UP){
			g.setColor(Color.BLUE);
			g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize/4);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(doorDirection == DoorDirection.DOWN){
			g.setColor(Color.BLUE);
			g.fillRect(getColumn()*cellSize, (getRow()+1)*cellSize-7, cellSize, cellSize/4);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(doorDirection == DoorDirection.RIGHT){
			g.setColor(Color.BLUE);
			g.fillRect(getColumn()*cellSize+cellSize - 7, (getRow())*cellSize, cellSize/4, cellSize);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
	}

	public void draw(Graphics g) {
		int cellSize = 30;
		if(this.isDoorway()) {
			g.setColor(Color.BLUE);
			switch(this.getDoorDirection()) {
			case LEFT:
				g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize/4, cellSize);
				break;
			case RIGHT:
				g.fillRect(getColumn()*cellSize+cellSize - 7, (getRow())*cellSize, cellSize/4, cellSize);
				break;
			case UP:
				g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize/4);
				break;
			case DOWN:
				g.fillRect(getColumn()*cellSize, (getRow()+1)*cellSize-7, cellSize, cellSize/4);
				break;
			default:
				break;
			}
			g.setColor(Color.gray);
			switch(this.getDoorDirection()) {
			case LEFT:
				g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				break;
			case RIGHT:
				g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				break;
			case UP:
				g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				break;
			case DOWN:
				g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
				break;
			default:
				break;
			}
		} else if(this.isRoom()) {
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
			if(this.printFullName) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 10));
				g.setColor(Color.black);
				g.drawString(this.name, (getColumn()*cellSize) + 3, getRow()*cellSize+(int)(.5*cellSize));
			}
		} else {
			g.setColor(Color.yellow);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
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

}

