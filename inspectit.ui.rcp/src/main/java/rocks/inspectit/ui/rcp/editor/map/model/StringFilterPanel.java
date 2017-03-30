package rocks.inspectit.ui.rcp.editor.map.model;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.NavigableMap;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import rocks.inspectit.ui.rcp.editor.map.filter.FilterEventListener;
import rocks.inspectit.ui.rcp.editor.map.filter.MarkerFilterElement;



public class StringFilterPanel<T> extends JPanel implements ValueFilterPanel {

	FilterEventListener listener;

	public StringFilterPanel(Set<T> keys, NavigableMap<T, MarkerFilterElement> map) {
		for (Object value : keys) {
			JPanel newCboxPanel = new JPanel(new BorderLayout());
			newCboxPanel.setBorder(new LineBorder(map.get(value).style().getBackColor(), 3));
			JCheckBox newCbox = new JCheckBox(String.valueOf(value));
			newCbox.addItemListener(new CheckboxItemListener());
			newCbox.setSelected(true);
			newCboxPanel.add(newCbox, BorderLayout.CENTER);
			this.add(newCboxPanel);
		}
	}

	private class CheckboxItemListener implements ItemListener {


		@Override
		public void itemStateChanged(ItemEvent e) {
			if (listener!=null) {
				listener.StringvalueSelectionChanged(((JCheckBox) e.getItem()).getText());
			}
		}

	}

	@Override
	public void setFilterEventListener(FilterEventListener listener) {
		this.listener = listener;
	}
}
