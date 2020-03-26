package a7grader;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

import a7.*;

public class A7JediTests {
	
	Driver d1 = makeDriver(0, 1);
	Driver d1a = makeDriver(0, 1);
	Driver d1b = makeDriver(0, 1);
	Driver d1c = makeDriver(0, 1);
	
	Driver d2 = makeDriver(0, 2);
	Driver d2a = makeDriver(0, 2);
	Driver d2b = makeDriver(0, 2);
	
	Driver d3 = makeDriver(0, 3);
	Driver d3a = makeDriver(0, 3);
	Driver d3b = makeDriver(0, 3);
	Driver d3c = makeDriver(0, 3);
	Driver d3d = makeDriver(0, 3);
	Driver d3e = makeDriver(0, 3);
	
	Driver d4 = makeDriver(0, 4);
	Driver d4a = makeDriver(0, 4);
	Driver d4b = makeDriver(0, 4);

	@Test
	public void simpleJediTest() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		pool1.addAll(List.of(d1, d1a, d1b));
		pool2.addAll(List.of(d2, d2a, d2b));
		pool3.addAll(List.of(d3, d3a, d3b));
		pool4.addAll(List.of(d4, d4a, d4b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool1);
		pools.add(pool2);
		pools.add(pool3);
		pools.add(pool4);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d4a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1a, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d1b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4b, iter.next());
		
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
	public void unevenJediTest() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		pool1.addAll(List.of(d1, d1a, d1b, d1c));
		pool2.addAll(List.of(d2, d2a));
		pool3.addAll(List.of(d3, d3a, d3b, d3c, d3d, d3e));
		pool4.addAll(List.of(d4, d4a, d4b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool1);
		pools.add(pool2);
		pools.add(pool3);
		pools.add(pool4);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d4a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1a, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d1b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4b, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d3c, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1c, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d3d, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3e, iter.next());
		
		assertFalse(iter.hasNext());
	}

	@Test
	public void onlyOnePoolTest() {
		List<Driver> pool1 = new ArrayList<Driver>();
		
		pool1.addAll(List.of(d1, d1a, d1b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool1);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1b, iter.next());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void onePoolStartsEmpty() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		pool1.addAll(List.of(d1, d1a, d1b));
		pool3.addAll(List.of(d3, d3a, d3b));
		pool4.addAll(List.of(d4, d4a, d4b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool1);
		pools.add(pool2);
		pools.add(pool3);
		pools.add(pool4);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d1, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d4a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d1a, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d1b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4b, iter.next());
		
		assertFalse(iter.hasNext());
	}

	@Test
	public void firstPoolStartsEmpty() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		pool2.addAll(List.of(d2, d2a, d2b));
		pool3.addAll(List.of(d3, d3a, d3b));
		pool4.addAll(List.of(d4, d4a, d4b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool1);
		pools.add(pool2);
		pools.add(pool3);
		pools.add(pool4);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d4a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2a, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d2b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4b, iter.next());
		
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void lastPoolStartsEmpty() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		pool2.addAll(List.of(d2, d2a, d2b));
		pool3.addAll(List.of(d3, d3a, d3b));
		pool4.addAll(List.of(d4, d4a, d4b));
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool4);
		pools.add(pool3);
		pools.add(pool2);
		pools.add(pool1);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertTrue(iter.hasNext());
		assertEquals(d4, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d2a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3a, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d4a, iter.next());
		
		assertTrue(iter.hasNext());
		assertEquals(d4b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d3b, iter.next());
		assertTrue(iter.hasNext());
		assertEquals(d2b, iter.next());
		
		assertFalse(iter.hasNext());
	}

	@Test
	public void allPoolsEmptyTest() {
		List<Driver> pool1 = new ArrayList<Driver>();
		List<Driver> pool2 = new ArrayList<Driver>();
		List<Driver> pool3 = new ArrayList<Driver>();
		List<Driver> pool4 = new ArrayList<Driver>();
		
		List<Iterable<Driver>> pools = new ArrayList<>();
		pools.add(pool4);
		pools.add(pool3);
		pools.add(pool2);
		pools.add(pool1);
		
		Iterator<Driver> iter = new SnakeOrderAcrossPoolsIterator(pools);
		
		assertFalse(iter.hasNext());
		try {
			Driver d = iter.next();
			fail("Expected NoSuchElementException to be thrown");
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			fail("Exception thrown was not NoSuchElementException");
		}
	}
	
	private Driver makeDriver(int x, int y) {
		Vehicle v = new VehicleImpl("make", "model", "plate", new PositionImpl(x, y));
		return new DriverImpl("first", "last", 0, v);
	}	
	
}
