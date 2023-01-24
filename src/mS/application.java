package mS;

import mS.model.Board;

public class application {
	public static void main(String[] args) {
		
		Board board = new Board(6, 6, 6);
		
		board.open(3, 3);
		board.switchMark(1, 1);
		
		System.out.println(board);
	}
}
