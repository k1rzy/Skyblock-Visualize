package net.encodey.SkyblockVisualize.packages;

import java.util.*;

import lombok.Getter;

@SuppressWarnings("unchecked")
public class Parameter<T> {
	
	@SuppressWarnings("rawtypes")
	@Getter
	private static final Parameter parameter = new Parameter<>();
	
	public Parameter() {}
	
	public void param(T accessor) {
		Parameter.getParameter().param = type(accessor);
	}
	
	public T type(T type) {
		return type;
	}
	
	@Getter
	private T param;
	
	public T params;
	
	public Parameter<T> arraysAsType(T... ts) {
		for(T types : ts) {
			List<T> arrayList = new ArrayList<>(Arrays.asList(types));
			return new ArrayParameter<>(arrayList);
		}
		return null;
	}
}
