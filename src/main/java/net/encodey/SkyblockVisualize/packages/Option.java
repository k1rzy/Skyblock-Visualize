package net.encodey.SkyblockVisualize.packages;

@SuppressWarnings({ "hiding", "unchecked" })
public class Option<T, Immutable> extends Parameter<T> {
	
	public <T extends Immutable> Parameter<T> optionOfType(T accessor) {
		Parameter.getParameter().param(accessor);
		return new Parameter<T>();
	}
	
	public <T extends Immutable> Parameter<T> elementOfType(T accessor, Immutable modifier) {
		Parameter.getParameter().param(accessor);
		return new Parameter<T>();
	}
	
	public <T extends Immutable> Parameter<T> optionOfArrayType(T[] accessor) {
		for(T accessors : accessor) {
			Parameter.getParameter().param(accessors);
			return new Parameter<T>();
		}
		throw new NullPointerException();
	}
}
