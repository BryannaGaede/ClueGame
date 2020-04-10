package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Room extends BoardCell {
	private char initial;
	private String name;
	private String type;
	
	public Room(char initial, String name, String type) {
		super();
		this.initial = initial;
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public char getInitial() {
		return initial;
	}
	
	public String getType() {
		return type;
	}
	
	public void draw(Graphics g, Board c, boolean drawName, int cellSize) {
		// Checks to see if it is a target and highlights it
		//if (isHighlighted())
			//g.setColor(Color.CYAN);
		
			// Set room color to gray
		//	g.setColor(Color.gray);
		//g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize);
		// Since 1 inch ~ 72 units, each is 36x36 units.
		//g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		// If we're supposed to draw the name, draw the name.
		//g.setColor(Color.WHITE);

		if(getDoorDirection() == DoorDirection.UP){
			g.setColor(Color.BLUE);
			//g.drawRect(getColumn()*CELL_SIZE, (getRow())*CELL_SIZE,getDimension(), getDimension());
			g.fillRect(getColumn()*cellSize, getRow()*cellSize, cellSize, cellSize/4);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(getDoorDirection() == DoorDirection.DOWN){
			g.setColor(Color.BLUE);
			//g.drawRect(getColumn()*CELL_SIZE, (getRow())*CELL_SIZE,getDimension(), getDimension());
			g.fillRect(getColumn()*cellSize, (getRow()+1)*cellSize-10, cellSize, cellSize/4);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(getDoorDirection() == DoorDirection.LEFT){
			g.setColor(Color.BLUE);
			//g.drawRect(getColumn()*CELL_SIZE, (getRow())*CELL_SIZE,getDimension(), getDimension());
			g.fillRect(getColumn()*cellSize, (getRow())*cellSize, cellSize/4, cellSize);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		else if(getDoorDirection() == DoorDirection.RIGHT){
			g.setColor(Color.BLUE);
			//g.drawRect(getColumn()*CELL_SIZE, (getRow())*CELL_SIZE,getDimension(), getDimension());
			g.fillRect(getColumn()*cellSize+cellSize - 10, (getRow())*cellSize, cellSize/4, cellSize);
			g.setColor(Color.gray);
			g.drawRect(getColumn()*cellSize, (getRow())*cellSize, cellSize, cellSize);
		}
		g.setColor(Color.WHITE);
		//if(drawName){
		//	g.setFont(new Font("Felix Titling", Font.BOLD, 10));
		//	g.drawString(c.getRooms().get(getInitial()), getColumn()*cellSize, getRow()*cellSize+(int)(.5*cellSize));
		//}
	}
	
}
