package rocks.inspectit.ui.rcp.editor.map.model;

public class NumericRange {

	private double lowerBound;
	private double upperBound;

	public NumericRange(double lowerBound, double upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public NumericRange() {
		this.lowerBound = Double.MIN_VALUE;
		this.upperBound = Double.MAX_VALUE;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public void updateBounds(Double duration) {
		if ((getLowerBound()==Double.MIN_VALUE) || (getLowerBound()>duration)) {
			setLowerBound(duration);
		}
		if ((getUpperBound()==Double.MAX_VALUE) || (getUpperBound()<duration)) {
			setUpperBound(duration);
		}
	}

	public boolean withinRange(Double value) {
		if (lowerBound>value) {
			return false;
		}
		if (upperBound<value) {
			return false;
		}
		return true;
	}

}
