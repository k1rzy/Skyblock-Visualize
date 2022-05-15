package net.encodey.SkyblockVisualize.packages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayParameter<T> extends Parameter<T> {

	private List<T> arrayInList;
	
	public ArrayParameter(List<T> arrayInList) {
		this.arrayInList = arrayInList;
		Parameter.getParameter().params = getAllFromArray();
	}
	
	protected T getAllFromArray() {
		Set<T> sortSet = new HashSet<>();
		for(int i = 0; i < arrayInList.size(); i++) {
			sortSet.add(arrayInList.get(i));
			for(T types : sortSet) {
				return types;
			}
		}
		throw new NullPointerException();
	}
}
