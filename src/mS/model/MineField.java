package mS.model;

import java.util.ArrayList;
import java.util.List;

import mS.exception.ExplosionException;

public class MineField {

    private final int row;
	private final int column;
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

    private boolean opened = false;
    private boolean mined = false;
    private boolean marked = false;
    
    public boolean isOpened() {
		return opened;
	}

	public boolean isMined() {
		return mined;
	}


	public boolean isMarked() {
		return marked;
	}

    private List<MineField> neighbors = new ArrayList<MineField>();

    public MineField(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // verify if its a neighbor
    public boolean addNeighbors(MineField neighbor){
        boolean differentRow = row != neighbor.row;
        boolean differenteColumn = column != neighbor.column;
        boolean diagonal = differenteColumn && differentRow;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaGeneral = deltaColumn + deltaRow;

        if (deltaGeneral == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        } else if (deltaGeneral == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }
    
    // switch marked and mined states
    
    public void switchMark() {
    	if (!opened) {
    		marked = !marked;
		}
    }
    
    public void plantMine() {
    	mined = true;
    }
    
    // opens 
    public boolean open() {
    	if (!opened && !marked) {
			opened = true;
			
			if (mined) {
				throw new ExplosionException();
			}
			
			if (friendshipNeighbors()) {
				neighbors.forEach(n -> n.open());
			}
			return true;
		} else {
    	return false;
		}
    }
    
    boolean friendshipNeighbors() {
    	return neighbors.stream().noneMatch(n -> n.mined);
    }
    
    // objectives 
    boolean objectiveReached() {
    	boolean discovered = !mined && opened;
    	boolean proteccted = mined && marked;
    	return discovered || proteccted;
    }
    
    public long nearbyMines () {
    	return neighbors.stream().filter(n -> n.mined).count();
    }
    
    public void restart () {
    	opened = false;
    	marked = false;
    	mined = false;
    }
    
    public String toString() {
    	if(marked) {
    		return "X";
    	} else if (opened && mined) {
    		return "*";
    	} else if (opened && nearbyMines() > 0) {
    		return Long.toString(nearbyMines());
    	} else if (opened) {
    		return " ";
    	} else {
    		return "?";
    	}
    }
}