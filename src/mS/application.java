package mS;

import mS.model.Board;
import mS.view.BoardConsole;

public class application {
	public static void main(String[] args) {
		
		Board board = new Board(8, 8, 2);
		new BoardConsole(board);
	}
}
