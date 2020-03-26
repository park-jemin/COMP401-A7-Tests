package a7grader;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

import a7.*;

public class A7NoviceTests {

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
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 6);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d1, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d2, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d3, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d4, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d5, proximity_iterator.next());
	}

	@Test
	public void excludesTwoTest() {
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
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 3);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d1, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d2, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d3, proximity_iterator.next());
		assertFalse(proximity_iterator.hasNext());
		assertFalse(proximity_iterator.hasNext());
	}

	@Test
	public void sizeZeroPoolTest() {
		List<Driver> pool = new ArrayList<Driver>();
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 3);
		
		assertFalse(proximity_iterator.hasNext());
		assertFalse(proximity_iterator.hasNext());
	}

	
	@Test
	public void noEligibleDriversInPoolTest() {
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
		
		Position client_position = new PositionImpl(10,0);
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 3);
		
		assertFalse(proximity_iterator.hasNext());
		assertFalse(proximity_iterator.hasNext());
	}


	@Test
	public void severalAtSameDistanceTest() {
		Driver d1 = makeDriver(0, 2);
		Driver d2 = makeDriver(0, 2);
		Driver d3 = makeDriver(0, 4);
		Driver d4 = makeDriver(0, 4);
		Driver d5 = makeDriver(0, 4);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		pool.add(d3);
		pool.add(d4);
		pool.add(d5);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 6);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d1, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d2, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d3, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d4, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d5, proximity_iterator.next());
	}
	
	@Test
	public void skipsSomeInMiddleTest() {
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 20);
		Driver d3 = makeDriver(0, 3);
		Driver d4 = makeDriver(0, 40);
		Driver d5 = makeDriver(0, 5);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		pool.add(d3);
		pool.add(d4);
		pool.add(d5);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 6);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d1, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d3, proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d5, proximity_iterator.next());
		assertFalse(proximity_iterator.hasNext());
		assertFalse(proximity_iterator.hasNext());
	}

	@Test
	public void throwsNoSuchElementTest() {
		Driver d1 = makeDriver(0, 1);
		Driver d2 = makeDriver(0, 20);
		
		List<Driver> pool = new ArrayList<Driver>();
		
		pool.add(d1);
		pool.add(d2);
		
		Position client_position = new PositionImpl(0,0);
		
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(pool, client_position, 6);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(d1, proximity_iterator.next());
		assertFalse(proximity_iterator.hasNext());
		try {
			Driver d = proximity_iterator.next();
			fail("Expected NoSuchElementException to be thrown");
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			fail("Exception thrown was not NoSuchElementException");
		}
	}

	@Test
	public void nullPoolThrowsConstructorExceptionTest() {
		try {			
			Position client_position = new PositionImpl(0,0);
			
			Iterator<Driver> proximity_iterator = 
					new ProximityIterator(null, client_position, 6);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("Threw exception for null driver pool, but not IllegalArgumentException");
		}
	}

	@Test
	public void nullPositionConstructorExceptionTest() {
		try {			
			Driver d1 = makeDriver(0, 1);
			Driver d2 = makeDriver(0, 20);
			
			List<Driver> pool = new ArrayList<Driver>();
			
			pool.add(d1);
			pool.add(d2);
			
			Iterator<Driver> proximity_iterator = 
					new ProximityIterator(pool, null, 6);
			fail("Should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("Threw exception for null position, but not IllegalArgumentException");
		}
	}

	
	@Test
	public void worksWithAnyIterableTest() {
	
		class KMPCollection implements Iterable<Driver> {
			private List<Driver> _pool;
			
			public KMPCollection() {
				_pool = new ArrayList<Driver>();
				_pool.add(makeDriver(1,1));
				_pool.add(makeDriver(2,2));
				_pool.add(makeDriver(3,3));
				_pool.add(makeDriver(4,4));
				_pool.add(makeDriver(5,5));				
			}
			
			public Iterator<Driver> iterator() {
				return _pool.iterator();
			}
			
			public Driver getDriver(int idx) {
				return _pool.get(idx);
			}

		}
		
		
		KMPCollection kmp_collection = new KMPCollection();
		Iterator<Driver> proximity_iterator = 
				new ProximityIterator(kmp_collection, new PositionImpl(0,0), 6);
		
		assertTrue(proximity_iterator.hasNext());
		assertEquals(kmp_collection.getDriver(0), proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(kmp_collection.getDriver(1), proximity_iterator.next());
		assertTrue(proximity_iterator.hasNext());
		assertEquals(kmp_collection.getDriver(2), proximity_iterator.next());
		assertFalse(proximity_iterator.hasNext());
	}
	
	private Driver makeDriver(int i, int j) {
		Vehicle v = new VehicleImpl("make", "model", "plate", new PositionImpl(i,j));
		return new DriverImpl("first", "last", 0, v);
	}
	
	
}
