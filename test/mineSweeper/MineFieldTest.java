package mineSweeper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mS.exception.ExplosionException;
import mS.model.MineField;

class MineFieldTest {
	
	// tests for verify if is a true neighbor

	private MineField field3_3;
	
	@BeforeEach
	void startMineField() {
		field3_3 = new MineField(3, 3);
	}
	
	@Test
	void testLeftNeighbor() {
		MineField neighbor = new MineField(3, 2);
		boolean result = field3_3.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testRightNeighbor() {
		MineField neighbor = new MineField(3, 4);
		boolean result = field3_3.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testAboveNeighbor() {
		MineField neighbor = new MineField(2, 3);
		boolean result = field3_3.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testUnderNeighbor() {
		MineField neighbor = new MineField(4, 3);
		boolean result = field3_3.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testDiagonalNeighbor() {
		MineField neighbor = new MineField(2, 2);
		boolean result = field3_3.addNeighbors(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNotNeighbor() {
		MineField neighbor = new MineField(1, 1);
		boolean result = field3_3.addNeighbors(neighbor);
		assertFalse(result);
	}

	
	// tests for switchMarked Function
	
	@Test
	void testDefaultSwitchMarked() {
		
	}
	
	@Test
	void testSwitchMarked() {
		field3_3.switchMark();
		assertTrue(field3_3.isMarked());
	}
	
	@Test
	void testDoubleSwitchMarked() {
		field3_3.switchMark();
		field3_3.switchMark();
		assertFalse(field3_3.isMarked());
	}
	
	// tests for open a field
	
	@Test
	void testOpenNotMinedNotMarked() {
		assertTrue(field3_3.open());
	}
	
	@Test
	void testOpenNotMinedMarked() {
		field3_3.switchMark();
		assertFalse(field3_3.open());
	}
	
	@Test
	void testOpenMinedNotMarked() {
		field3_3.plantMine();
		assertThrowsExactly(ExplosionException.class, () ->{
			field3_3.open();
			});
	}	
	
	@Test
	void testOpenMinedMarked() {
		field3_3.plantMine();
		field3_3.switchMark();
		assertFalse(field3_3.open());
	}

	@Test
	void testOpenWithNeighborsOne() {
		
		MineField neighborOfNeighbor = new MineField(1, 1);
	
		MineField neighbor1 = new MineField(2, 2);
		neighbor1.addNeighbors(neighborOfNeighbor);
		
		field3_3.addNeighbors(neighbor1);
		
		field3_3.open();
		
		assertTrue(neighborOfNeighbor.isOpened() && neighbor1.isOpened());
	}
	
	@Test
	void testOpenWithNeighborsTwo() {
		
		MineField neighborOfNeighbor = new MineField(1, 1);;
		neighborOfNeighbor.plantMine();
	
		MineField neighbor1 = new MineField(2, 2);
		neighbor1.addNeighbors(neighborOfNeighbor);
		
		field3_3.addNeighbors(neighbor1);
		
		field3_3.open();
		
		assertTrue(neighbor1.isOpened() && !neighborOfNeighbor.isOpened());
	}
	
	// test nearby mines
	@Test
	void testThereAreNeabyMines() {
		MineField neighbor = new MineField(3, 2);;
		neighbor.plantMine();
		MineField neighbor2 = new MineField(3, 4);;
		neighbor2.plantMine();
		
		field3_3.addNeighbors(neighbor);
		field3_3.addNeighbors(neighbor2);
		
		assertEquals(2, field3_3.nearbyMines());
		
	}
	
	// restart test
	@Test
	void test() {
	    field3_3.restart();
	    assertTrue(!field3_3.isOpened() && !field3_3.isMarked() && !field3_3.isMined());
	}
	
	
	// to string test
	
	@Test
	void testToString() {
		
		MineField field1_5 = new MineField(1,5);
		MineField field2_2 = new MineField(2,2);
		MineField field2_3 = new MineField(2,3);
		MineField field2_4 = new MineField(2,4);
		MineField field3_2 = new MineField(3,2);
		MineField field3_4 = new MineField(3,4);
		MineField field4_2 = new MineField(4,2);
		MineField field4_3 = new MineField(4,3);
		MineField field4_4 = new MineField(4,4);
		
		field2_4.addNeighbors(field1_5);
		field2_4.addNeighbors(field2_3);
		field3_3.addNeighbors(field2_2);
		field3_3.addNeighbors(field2_3);
		field3_3.addNeighbors(field2_4);
		field3_3.addNeighbors(field3_2);
		field3_3.addNeighbors(field3_4);
		field3_3.addNeighbors(field4_2);
		field3_3.addNeighbors(field4_3);
		field3_3.addNeighbors(field4_4);
		
		field1_5.plantMine();
		field3_3.open();		
		
		char c2_2 = field2_2.toString().charAt(0);
		char c2_3 = field2_3.toString().charAt(0);
		char c2_4 = field2_4.toString().charAt(0);
		char c3_3 = field3_3.toString().charAt(0);
		char c4_4 = field4_4.toString().charAt(0);
		
		char[] initialized = new char[]{c2_2,c2_3,c2_4,c3_3,c4_4};
		char[] expected = new char[]{' ',' ','1',' ',' '};
				
		assertArrayEquals(expected, initialized);
	}
}
