package rocks.inspectit.ui.rcp.editor.map.filter;

import java.awt.Color;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;

public interface MapFilter<T> {

	void addFilterConstraint(T key, MarkerFilterElement element);

	MarkerFilterElement getFilter(T key);

	Set<T> getKeys();

	List<Color> getAvailableColor();

	JPanel getPanel(FilterEventListener listener);

	void finalizeFilter();

	T getValue(T key);

	void addValue(T value);

	void ChangeSelection(Object selection);

	InspectITMarker applyFilter(InspectITMarker marker);

}
