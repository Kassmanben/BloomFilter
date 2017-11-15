
public class BloomFilter <T>{
	private int mask = (1<<16) - 1;
	private boolean[] table;
	
	/**Constructor for BloomFilter, initializes table assuming a 32-but hashCode*/
	public BloomFilter(){
		table = new boolean[65536];
	}
	
	/**Hashes the input, checks if both hi and low bits are contained in the table
	 * @return true if table contains both hi and lo bits
	 * @return false if table contains only one, or neither*/
	public boolean mightContain(T testContains){
		if (table[testContains.hashCode() & mask] 
				&& table[(testContains.hashCode() >>> 16) & mask]){
			return true;
		}
		return false;
	}
	
	/**Hashes the input, marks hi and lo hashes as true*/
	public void add(T toAdd){
		table[toAdd.hashCode() & mask] = true;
		table[(toAdd.hashCode() >>> 16)] = true;
	}
	
	/** @return number of true values in table*/
	public int trueBits(){
		int n = 0;
		for (boolean tf : table){
			if (tf) { n++; }
		}
		return n;
	}
	
	
}
