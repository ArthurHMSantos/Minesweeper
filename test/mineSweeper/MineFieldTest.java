package mineSweeper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mS.exception.ExplosionException;
import mS.model.MineField;

class MineFieldTest {
	
	// tests for verify if is a true neighbor

	private MineField minefield;
	
	@BeforeEach
	void startMineField() {
		minefield = new MineField(3, 3);
	}
	
	@Test
	void testLeftNeighbor() {
		MineField neighbor = new MineField(3, 2);
		boolean result = minefield.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testRightNeighbor() {
		MineField neighbor = new MineField(3, 4);
		boolean result = minefield.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testAboveNeighbor() {
		MineField neighbor = new MineField(2, 3);
		boolean result = minefield.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testUnderNeighbor() {
		MineField neighbor = new MineField(4, 3);
		boolean result = minefield.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testDiagonalNeighbor() {
		MineField neighbor = new MineField(2, 2);
		boolean result = minefield.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNotNeighbor() {
		MineField neighbor = new MineField(1, 1);
		boolean result = minefield.addNeighbors(neighbor);
		assertFalse(result);
	}

	
	// tests for switchMarked Function
	
	@Test
	void testeDefaultSwitchMarked() {
		
	}
	
	@Test
	void testeSwitchMarked() {
		minefield.switchMarked();
		assertTrue(minefield.isMarked());
	}
	
	@Test
	void testeDoubleSwitchMarked() {
		minefield.switchMarked();
		minefield.switchMarked();
		assertFalse(minefield.isMarked());
	}
	
	// tests for opened 
	
	@Test
	void testeOpenNotMinedNotMarked() {
		assertTrue(minefield.open());
	}
	
	@Test
	void testeOpenNotMinedMarked() {
		minefield.switchMarked();
		assertFalse(minefield.open());
	}
	
	@Test
	void testeOpenMinedNotMarked() {
		minefield.plantMine();
		assertThrowsExactly(ExplosionException.class, () ->{
			minefield.open();
			});
	}	
	
	@Test
	void testeOpenMinedMarked() {
		minefield.plantMine();
		minefield.switchMarked();
		assertFalse(minefield.open());
	}

	@Test
	void testeOpenWithNeighborsOne() {
		
		MineField neighborOfNeighbor = new MineField(1, 1);
	
		MineField neighbor1 = new MineField(2, 2);
		neighbor1.addNeighbors(neighborOfNeighbor);
		
		minefield.addNeighbors(neighbor1);
		
		minefield.open();
		
		assertTrue(neighborOfNeighbor.isOpened() && neighbor1.isOpened());
	}
	
	@Test
	void testeOpenWithNeighborsTwo() {
		
		MineField neighborOfNeighbor = new MineField(1, 1);;
		neighborOfNeighbor.plantMine();
	
		MineField neighbor1 = new MineField(2, 2);
		neighbor1.addNeighbors(neighborOfNeighbor);
		
		minefield.addNeighbors(neighbor1);
		
		minefield.open();
		
		assertTrue(neighbor1.isOpened() && !neighborOfNeighbor.isOpened());
	}
}
