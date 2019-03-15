package org.eclipse.cyclonedds.idl2j;

public class Pair<T> {
	public T first = null, second = null;
	public Pair(T key, T value) {
		this.first  = key;
		this.second = value;
	}
}
