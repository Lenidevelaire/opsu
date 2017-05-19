package itdelatrisu.opsu.storyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * A map implementation that relies on lists to hold their objects.
 * @author Lyonlancer5
 */
public class ArrayMap<K, V> implements Map<K, V> {

	private final ListSet<K> keys = new ListSet<>();
	private final ListSet<V> values = new ListSet<>();
	
	@Override
	public int size() {
		return keys.size();
	}

	@Override
	public boolean isEmpty() {
		return keys.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	@Override
	public V get(Object key) {
		if(!containsKey(key)) return null;
		return values.get(keys.indexOf(key));
	}

	@Override
	public V put(K key, V value) {
		if(containsKey(key)) return values.set(keys.indexOf(key), value);
		if(keys.add(key)) values.set(keys.indexOf(key), value);
		return null;
	}

	@Override
	public V remove(Object key) {
		if(!containsKey(key)) return null;
		int index = keys.indexOf(key);
		keys.remove(index);
		return values.remove(index);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> mElem : m.entrySet()){
			put(mElem.getKey(), mElem.getValue());
		}
	}

	@Override
	public void clear() {
		keys.clear();
		values.clear();
	}

	@Override
	public ListSet<K> keySet() {
		return keys;
	}

	@Override
	public ListSet<V> values() {
		return values;
	}

	/**
	 * Returns an empty set
	 * 
	 * @deprecated Unimplemented, do not use
	 * @return <code>null</code>
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return Collections.emptySet();
	}
	
	
	private class ListSet<E> implements List<E>, Set<E> {

		private List<E> backingList = new ArrayList<>();
		
		@Override
		public int size() {
			return backingList.size();
		}

		@Override
		public boolean isEmpty() {
			return backingList.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return backingList.contains(o);
		}

		@Override
		public Iterator<E> iterator() {
			return backingList.iterator();
		}

		@Override
		public Object[] toArray() {
			return backingList.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return backingList.toArray(a);
		}

		@Override
		public boolean add(E e) {
			if(contains(e)) return false;
			return backingList.add(e);
		}

		@Override
		public boolean remove(Object o) {
			return backingList.remove(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return backingList.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			return backingList.addAll(c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			return backingList.addAll(index, c);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return backingList.removeAll(c);
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return backingList.retainAll(c);
		}

		@Override
		public void clear() {
			backingList.clear();
		}

		@Override
		public E get(int index) {
			return backingList.get(index);
		}

		@Override
		public E set(int index, E element) {
			if(contains(element)) backingList.remove(indexOf(element));
			return backingList.set(index, element);
		}

		@Override
		public void add(int index, E element) {
			backingList.add(index, element);
		}

		@Override
		public E remove(int index) {
			return backingList.remove(index);
		}

		@Override
		public int indexOf(Object o) {
			return backingList.lastIndexOf(o);
		}

		@Override
		public int lastIndexOf(Object o) {
			return backingList.lastIndexOf(o);
		}

		@Override
		public ListIterator<E> listIterator() {
			return backingList.listIterator();
		}

		@Override
		public ListIterator<E> listIterator(int index) {
			return backingList.listIterator(index);
		}

		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			return backingList.subList(fromIndex, toIndex);
		}
	}
}
