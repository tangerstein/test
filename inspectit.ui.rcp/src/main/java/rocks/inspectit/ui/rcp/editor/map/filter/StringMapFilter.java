package rocks.inspectit.ui.rcp.editor.map.filter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import rocks.inspectit.ui.rcp.InspectITConstants;
import rocks.inspectit.ui.rcp.editor.map.MapSubView.FilterValueObject;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.StringFilterPanel;

public class StringMapFilter<T> extends AbstractMapFilter<T> {

	Set<String> values;

	public StringMapFilter(String tagKey, boolean colored) {
		super(tagKey, colored);
		values = new HashSet<>();
		initColors();
	}

	private void initColors() {

		colorList = new ArrayList<Color>();
		colorList.add(new Color(192, 0, 0));
		colorList.add(new Color(0, 112, 192));
		colorList.add(new Color(0, 176, 80));
		colorList.add(new Color(123, 123, 123));
		colorList.add(new Color(112, 48, 160));
		colorList.add(new Color(0, 32, 96));
		colorList.add(new Color(100, 255, 192));
		colorList.add(new Color(0, 176, 240));
		colorList.add(new Color(3, 135, 83));
		colorList.add(new Color(255, 0, 199));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addValue(Object value) {
		values.add((String) value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JPanel getPanel(FilterValueObject filterValueObject) {
		StringFilterPanel temp = new StringFilterPanel(filterValueObject, this.getKeys(), filterMap);
		return temp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateFilter() {
		List<Color> colorList = getAvailableColor();
		int index = 0;
		for (String value : this.values) {
			putFilterConstraint((T) value, new MarkerFilterElement(colorList.get(index)));
			index++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InspectITMarker applyFilter(InspectITMarker marker) {
		if (tagKey.equals(InspectITConstants.NOFILTER)) {
			return adaptMarker(marker, new MarkerFilterElement());
		}
		MarkerFilterElement elem = getFilter((T) marker.getTags().get(tagKey));
		if (elem.isVisible) {
			return adaptMarker(marker, elem);
		}
		return null;
	}

	private InspectITMarker adaptMarker(InspectITMarker marker, MarkerFilterElement element) {
		marker.setStyle(element.style());
		marker.setVisible(element.isVisible());
		return marker;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeSelection(Object selection) {
		MarkerFilterElement element = getFilter((T) selection);
		element.setVisible(!element.isVisible);
		putFilterConstraint((T) selection, element);
	}

}
