package mS.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import mS.exception.ExitException;
import mS.exception.ExplosionException;
import mS.model.Board;

public class BoardConsole {

	private Board board;
	private Scanner scan = new Scanner(System.in);
	
	
	public BoardConsole(Board board) {
		this.board = board;
		
		executeGame();
	} 

	private void executeGame() {
		try {
			boolean proceed = true;
			
			while (proceed) {
				gameCicle();
				
				System.out.println("Do you want to play another match? (Y/n)");
				String asnwer = scan.nextLine();
				
				if ("n".equalsIgnoreCase(asnwer)) {
					System.out.println("Goodbye!!!");
					proceed = false;
				} else {
					board.restart();
				}
			}
			
		} catch (ExitException e) {
			System.out.println("Goodbye!!!");
		} finally {
			scan.close();
		}
	}

	private void gameCicle() {
			try {
				
				while (!board.objectiveReached()) {
					System.out.println(board);
					
					String typed = captureTypedValue("Type (x,y): ");
					
					Iterator<Integer> xy = Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
					
					typed = captureTypedValue("Choose 1 to Open or 2 to (un)Check: ");
				
					if ("1".equals(typed)) {
						board.open(xy.next(), xy.next());
					} else if ("2".equals(typed)) {
						board.switchMark(xy.next(), xy.next());
					}
				}
				
				System.out.println("Congratulations, you win! :D");
			} catch (ExplosionException e) {
				System.out.println(board);
				System.out.println("You lose! :(");
			}
	}
	
	private String captureTypedValue(String text) {
		System.out.print(text);
		String typedValue = scan.nextLine();
		
		if("exit".equalsIgnoreCase(typedValue)) {
			throw new ExitException();
		}
		
		return typedValue;
	}
	
	
}

