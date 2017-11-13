import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BloomFilterTest {

	private BloomFilter<String> filter;
	
	private static final String[] BAD = new In("bad.txt").readAllLines();
	
	private static final String[] GOOD = new In("good.txt").readAllLines();
	
	@Before
	public void setUp() throws Exception {
		filter = new BloomFilter<String>();
	}

	@Test
	public void itemInitiallyNotPresent() {
		assertFalse(filter.mightContain("badplace.com"));
	}
	
	@Test
	public void addAddsItem() {
		filter.add("badplace.com");
		assertTrue(filter.mightContain("badplace.com"));
	}

	@Test
	public void addDoesNotAddAnotherItem() {
		// This test could fail if the two Strings hashed to the same locations in the
		// Bloom filter, but this is extremely unlikely
		filter.add("badplace.com");
		assertFalse(filter.mightContain("goodplace.com"));		
	}
	
	@Test
	public void tableHasCorrectNumberOfTrueBits() {
		for (String domain : BAD) {
			filter.add(domain);
		}
		assertEquals(2269, filter.trueBits());
	}

	@Test
	public void tableHasCorrectNumberOfFalseAlarms() {
		for (String domain : BAD) {
			filter.add(domain);
		}
		int falseAlarms = 0;
		for (String domain : GOOD) {
			if (filter.mightContain(domain)) {
				falseAlarms++;
			}
		}
		assertEquals(3, falseAlarms);
	}

}
