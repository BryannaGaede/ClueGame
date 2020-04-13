package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.AttributedCharacterIterator;

import clueGame.BoardCell.DoorDirection;

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
	
	
}
