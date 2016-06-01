/**
 *
 */
package com.flatironschool.javacs;

import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;
	// size field to prevent iteration to calculate elements
	private int size = 0;

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);
		this.size++;

		//check if the number of elements per map exceeds the threshold
		if (this.size > maps.size() * FACTOR) {
			//System.out.println("REHASHING since elements = " + size() + ", maps = " + maps.size());
			rehash();
		}
		//System.out.println("Put key in map -- elements = " + size() + ", maps = " + maps.size());
		return oldValue;
	}

	@Override
	public V remove(Object key) {
		this.size--;
		return super.remove(key);
	}

	@Override
	public void clear() {
		this.size = 0;
		super.clear();
	}
	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	/**
	 *
	 */
	protected void rehash() {
        List<MyLinearMap<K, V>> currentMaps = this.maps;

				//Double the number of maps
				this.makeMaps(currentMaps.size() * 2);

				//Re-put entries
				for (MyLinearMap<K, V> map : currentMaps) {
					for (Entry<K,V> entry : map.getEntries()) {
						put(entry.getKey(), entry.getValue());
					}
				}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
