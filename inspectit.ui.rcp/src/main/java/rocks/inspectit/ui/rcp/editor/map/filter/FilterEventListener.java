package rocks.inspectit.ui.rcp.editor.map.filter;

import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;

public interface FilterEventListener {

	void keySelectionChanged(String key);

	void StringvalueSelectionChanged(String value);

	void NumericValueSelectionChanged(NumericRange value);

}
