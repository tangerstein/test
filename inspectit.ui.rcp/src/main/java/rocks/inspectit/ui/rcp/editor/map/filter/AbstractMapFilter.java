package rocks.inspectit.ui.rcp.editor.map.filter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public abstract class AbstractMapFilter<T> implements MapFilter<T> {

	protected String tagKey;
	protected List<Color> colorList;
	protected NavigableMap<T, MarkerFilterElement> filterMap;
	boolean isColored;


	public AbstractMapFilter(String tagKey, boolean colored) {
		this.isColored = colored;
		colorList = new ArrayList<Color>();
		colorList.add(new Color(227, 0, 116));
		colorList.add(new Color(255, 0, 0));
		colorList.add(new Color(4, 37, 108));
		filterMap = new TreeMap<>();
		this.tagKey = tagKey;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putFilterConstraint(T key, MarkerFilterElement element) {
		if (key != null) {
			if (element != null) {
				filterMap.put(key, element);
			} else {
				filterMap.put(key, new MarkerFilterElement());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Color> getAvailableColor() {
		return this.colorList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColored(boolean colored) {
		this.isColored = colored;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MarkerFilterElement getFilter(T key) {
		if (filterMap.isEmpty()) {
			return new MarkerFilterElement();
		}
		if (key!=null) {
			if (filterMap.ceilingEntry(key)!=null) {
				return filterMap.ceilingEntry(key).getValue();
			}
		}
		return new MarkerFilterElement(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<T> getKeys() {
		return filterMap.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue(T key) {
		return (T) getFilter(key);
	}



}
