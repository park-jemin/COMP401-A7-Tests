package a7grader;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

import a7.*;

public class A7AdeptTests {

	@Test
	public void simpleTest() {
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 2);
		Driver d3 = makeDriver(0, 3);
		Driver d4 = makeDriver(0, 4);
		Driver d5 = makeDriver(0, 5);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		pool.add(d3);
		pool.add(d4);
		pool.add(d5);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> iter = new ExpandingProximityIterator(pool, client_position, 2);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d5, iter.next());
		
		assertFalse(iter.hasNext());
		try {
			Driver d = iter.next();
			fail("Expected NoSuchElementException to be thrown");
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			fail("Exception thrown was not NoSuchElementException");
		}
		
	}

	@Test
	public void onlyOneDriverTest() {
		Driver d1 = makeDriver(0, 2);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> iter = new ExpandingProximityIterator(pool, client_position, 3);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertFalse(iter.hasNext());
		try {
			Driver d = iter.next();
			fail("Expected NoSuchElementException to be thrown");
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			fail("Exception thrown was not NoSuchElementException");
		}
		
	}

	@Test
	public void twoIteratorsAtSameTime() {
		
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 2);
		Driver d3 = makeDriver(0, 3);
		Driver d4 = makeDriver(0, 4);
		Driver d5 = makeDriver(0, 5);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		pool.add(d3);
		pool.add(d4);
		pool.add(d5);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> iter1 = new ExpandingProximityIterator(pool, client_position, 2);
		Iterator<Driver> iter2 = new ExpandingProximityIterator(pool, client_position, 3);
		
		assertTrue(iter1.hasNext());
		assertEquals(d1, iter1.next());
		assertTrue(iter1.hasNext());
		assertEquals(d2, iter1.next());
		
		assertTrue(iter2.hasNext());
		assertEquals(d1, iter2.next());
		
		assertTrue(iter1.hasNext());
		assertEquals(d3, iter1.next());
		assertTrue(iter1.hasNext());
		assertEquals(d4, iter1.next());
		assertTrue(iter1.hasNext());
		assertEquals(d5, iter1.next());
		
		assertTrue(iter2.hasNext());
		assertEquals(d2, iter2.next());
		
		assertFalse(iter1.hasNext());
		
		assertTrue(iter2.hasNext());
		assertEquals(d3, iter2.next());
		assertTrue(iter2.hasNext());
		assertEquals(d4, iter2.next());
		assertTrue(iter2.hasNext());
		assertEquals(d5, iter2.next());
	}
	
	@Test
	public void outOfOrderTest() {
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 2);
		Driver d3 = makeDriver(0, 3);
		Driver d4 = makeDriver(0, 4);
		Driver d5 = makeDriver(0, 5);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
	
		pool.add(d5);
		pool.add(d4);
		
		pool.add(d3);
		pool.add(d2);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> iter = new ExpandingProximityIterator(pool, client_position, 2);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d5, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		assertFalse(iter.hasNext());
		
	}

	@Test
	public void reallyFarAwayTest() {
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 2);
		Driver d3 = makeDriver(0, 3);
		Driver d4 = makeDriver(0, 4);
		Driver d5 = makeDriver(0, 5);
		Driver d50 = makeDriver(0, 50);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		pool.add(d3);
		pool.add(d4);
		pool.add(d5);
		pool.add(d50);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> iter = new ExpandingProximityIterator(pool, client_position, 2);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d5, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d50, iter.next());
		assertFalse(iter.hasNext());
	}
	
	private Driver makeDriver(int i, int j) {
		Vehicle v = new VehicleImpl("make", "model", "plate", new PositionImpl(i,j));
		return new DriverImpl("first", "last", 0, v);
	}	
	
}
