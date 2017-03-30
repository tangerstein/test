package rocks.inspectit.ui.rcp.editor.map.model;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rocks.inspectit.ui.rcp.editor.map.filter.FilterEventListener;


public class NumericFilterPanel extends JPanel implements ValueFilterPanel{

	private JLabel lowerRangeValue;
	private JLabel upperRangeValue;
	private JSlider lowerBound;
	private JSlider upperBound;
	FilterEventListener listener;

	private NumericFilterPanel self;

	public NumericFilterPanel(NumericRange numericRange) {
		self = this;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		lowerBound = new JSlider(JSlider.HORIZONTAL);
		lowerBound.setMinimum((int) numericRange.getLowerBound());
		lowerBound.setMaximum((int) numericRange.getUpperBound());
		lowerBound.setValue((int) numericRange.getLowerBound());
		lowerBound.setMajorTickSpacing(getMajorTickSpacing((int) numericRange.getLowerBound(),
				(int) numericRange.getUpperBound()));
		lowerBound.setMinorTickSpacing(getMinorTickSpacing((int) numericRange.getLowerBound(),
				(int) numericRange.getUpperBound()));
		lowerBound.addChangeListener(new LowerSliderStateChangeListener());

		upperBound = new JSlider(JSlider.HORIZONTAL);
		upperBound.setMinimum((int) numericRange.getLowerBound());
		upperBound.setMaximum((int) numericRange.getUpperBound());
		upperBound.setValue((int) numericRange.getUpperBound());
		upperBound.setMajorTickSpacing(getMajorTickSpacing((int) numericRange.getLowerBound(),
				(int) numericRange.getUpperBound()));
		upperBound.setMinorTickSpacing(getMinorTickSpacing((int) numericRange.getLowerBound(),
				(int) numericRange.getUpperBound()));
		upperBound.addChangeListener(new UpperSliderStateChangeListener());

		JPanel rangeValuePanel = createRangeValuePanel();

		this.add(lowerBound);
		this.add(upperBound);
		this.add(rangeValuePanel);
	}

	private JPanel createRangeValuePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		JLabel range = new JLabel("Range:");
		JLabel space = new JLabel("-");
		lowerRangeValue = new JLabel(lowerBound.getValue() + "");
		upperRangeValue = new JLabel(upperBound.getValue() + "");
		panel.add(range);
		panel.add(lowerRangeValue);
		panel.add(space);
		panel.add(upperRangeValue);
		return panel;
	}

	private class LowerSliderStateChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			self.remove(upperBound);
			int min = lowerBound.getValue();
			int max = upperBound.getMaximum();
			int value = upperBound.getValue();
			upperBound = new JSlider(JSlider.HORIZONTAL);
			upperBound.setMinimum(min);

			upperBound.setMaximum(max);
			upperBound.setValue(value);
			upperBound.setMajorTickSpacing(getMajorTickSpacing(min, max));
			upperBound.setMinorTickSpacing(getMinorTickSpacing(min, max));
			upperBound.addChangeListener(new UpperSliderStateChangeListener());
			self.add(upperBound, 1);

			self.revalidate();

			lowerRangeValue.setText(lowerBound.getValue() + "");
			// Only trigger reloading of the map after selection is done
			if (!lowerBound.getValueIsAdjusting() && !upperBound.getValueIsAdjusting()) {
				if (listener!=null) {
					listener.NumericValueSelectionChanged(new NumericRange(lowerBound.getValue(), upperBound.getValue()));
				}
			}

		}

	}

	private class UpperSliderStateChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			self.remove(lowerBound);
			int min = lowerBound.getMinimum();
			int max = upperBound.getValue();
			int value = lowerBound.getValue();
			lowerBound = new JSlider(JSlider.HORIZONTAL);
			lowerBound.setMinimum(min);
			lowerBound.setMaximum(max);
			lowerBound.setValue(value);
			lowerBound.setMajorTickSpacing(getMajorTickSpacing(min, max));
			lowerBound.setMinorTickSpacing(getMinorTickSpacing(min, max));
			lowerBound.addChangeListener(new LowerSliderStateChangeListener());
			self.add(lowerBound,0);
			self.revalidate();

			upperRangeValue.setText(upperBound.getValue() + "");
			// Only trigger reloading of the map after selection is done
			if (!lowerBound.getValueIsAdjusting() && !upperBound.getValueIsAdjusting()) {
				if (listener!=null) {
					listener.NumericValueSelectionChanged(new NumericRange(lowerBound.getValue(), upperBound.getValue()));
				}
			}
		}

	}

	private int getMajorTickSpacing(int lowerBound, int upperBound) {
		return (upperBound - lowerBound) / 3;
	}

	private int getMinorTickSpacing(int lowerBound, int upperBound) {
		return (upperBound - lowerBound) / 9;
	}

	@Override
	public void setFilterEventListener(FilterEventListener listener) {
		this.listener = listener;
	}


}
