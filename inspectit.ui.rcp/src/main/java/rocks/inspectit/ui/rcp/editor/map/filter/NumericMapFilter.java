package rocks.inspectit.ui.rcp.editor.map.filter;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import rocks.inspectit.ui.rcp.editor.map.MapSubView.FilterValueObject;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.NumericFilterPanel;
import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;

public class NumericMapFilter<T> extends AbstractMapFilter<T> {

	NumericRange totalRange;
	NumericRange filteredRange;
	private int alphaStart = 100;
	private int alphaSteps = 5;

	public NumericMapFilter(String tagKey, Boolean colored) {
		super(tagKey, colored);
		totalRange = new NumericRange();
		filteredRange = new NumericRange();
	}

	private void initColors() {
		colorList.clear();
		if (isColored) {
			colorList.add(new Color(0, 0, 128));
			colorList.add(new Color(0, 128, 0));
			colorList.add(new Color(0, 255, 255));
			colorList.add(new Color(128, 0, 0));
			colorList.add(new Color(128, 0, 128));
		}  else {
			colorList.add(new Color(0, 0, 0, 100));
			/*
			 * int stepCount = (255 - alphaStart) / alphaSteps; int step = (int)
			 * (totalRange.getUpperBound() - totalRange.getLowerBound()) / stepCount; for (int i =
			 * 0; i < stepCount; i++) { System.out.println(alphaStart + (alphaSteps * i));
			 * colorList.add(new Color(0, 0, 0, alphaStart + (alphaSteps * i))); }
			 */
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addValue(Object value) {
		Double temp = Double.parseDouble((String) value);
		totalRange.updateBounds(temp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JPanel getPanel(FilterValueObject filterValueObject) {
		NumericFilterPanel temp = new NumericFilterPanel(filterValueObject, totalRange, filteredRange);
		return temp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateFilter() {
		filterMap.clear();
		initColors();
		double temp = (totalRange.getUpperBound() - totalRange.getLowerBound()) / colorList.size();
		List<Color> colorList = getAvailableColor();
		for (int i = 0; i < (colorList.size() - 1); i++) {
			Double section = totalRange.getLowerBound() + (temp * i);
			putFilterConstraint((T) section, new MarkerFilterElement(colorList.get(i)));
		}
		Double section = totalRange.getUpperBound();
		putFilterConstraint((T) section, new MarkerFilterElement(colorList.get(colorList.size() - 1)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InspectITMarker applyFilter(InspectITMarker marker) {
		Double temp = Double.parseDouble((String) marker.getTags().get(tagKey));
		if (!filteredRange.withinRange(temp)) {
			return null;
		}
		MarkerFilterElement element = getFilter((T)temp);
		marker.setStyle(element.style());
		marker.setVisible(element.isVisible());
		return marker;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changeSelection(Object selection) {
			filteredRange = (NumericRange) selection;
	}

}
