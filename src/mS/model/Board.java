package mS.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import mS.exception.ExplosionException;

public class Board {

	private int rows;
	private int columns;
	private int mines;
	
	private final List<MineField> minefields = new ArrayList<>();

	public Board(int rows, int columns, int mines) {
		this.rows = rows;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		sortMines();
	}
	
	public void open(int row, int column) {
		try {
			minefields.parallelStream()
			.filter(m -> m.getRow() == row && m.getColumn() == column)
			.findFirst()
			.ifPresent(m -> m.open());
		} catch (ExplosionException e) {
			minefields.forEach(m -> m.setOpened(true));
			throw e;
		}
	}

	public void switchMark(int row, int column) {
		minefields.parallelStream()
		.filter(m -> m.getRow() == row && m.getColumn() == column)
		.findFirst()
		.ifPresent(m -> m.switchMark());
	}

	private void generateFields() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				minefields.add(new MineField(r, c));
			}
		}
	}
	
	private void associateNeighbors() {
		for (MineField field1 : minefields) {
			for (MineField field2 : minefields) {
				field1.addNeighbors(field2);
			}
			
		}
	}
	
	private void sortMines() {
		long plantedMines = 0;
		Predicate<MineField> mined = c -> c.isMined();
		
		do {
			int randomValue = (int) (Math.random() * minefields.size());
			minefields.get(randomValue).plantMine();
			plantedMines = minefields.stream().filter(mined).count();
		} while (plantedMines < mines);
	}
	
	public boolean objectiveReached() {
		return minefields.stream().allMatch(m -> m.objectiveReached());
    }
	
	public void restart() {
		minefields.stream().forEach(m -> m.restart());
		sortMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int c = 0; c < columns; c++) {
			
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
			
		}
		
		sb.append("\n" + "_".repeat((columns * 3) + 3) + "\n");
		
		int i = 0;
		for (int r = 0; r < rows; r++) {
			sb.append(r);
			sb.append(" |");
			
			for (int c = 0; c < columns; c++) {
				sb.append(" ");
				sb.append(minefields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
