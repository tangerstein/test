package rocks.inspectit.ui.rcp.editor.map.model;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import rocks.inspectit.ui.rcp.editor.map.filter.FilterEventListener;
import rocks.inspectit.ui.rcp.editor.map.filter.MapFilter;


public class FilterPanel extends JPanel {

	private JComboBox<String> tagComboBox;
	private JPanel filterValuePanel;
	private String selection;
	MapFilter filter;
	FilterEventListener listener;

	public FilterPanel(MapFilter filter) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.filter = filter;
		filterValuePanel = new JPanel();
		init();
	}

	private void init() {

		createComboBox(null);
		this.add(filterValuePanel);
	}

	private void createComboBox(Set<String> keys) {
		if (tagComboBox!=null) {
			tagComboBox.removeAllItems();
		} else {
			tagComboBox = new JComboBox<>();
		}
		tagComboBox.addItem("---");
		selection = "---";
		if (keys!=null) {
			for (String tag : keys) {
				tagComboBox.addItem(tag);
			}
		}
		tagComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) tagComboBox.getSelectedItem();
				if (selectedItem==selection) {
					return;
				}
				selection = selectedItem;
				if (listener != null) {
					listener.keySelectionChanged(selectedItem);
				}
			}
		});
		this.add(tagComboBox);
	}

	public void setValuePanel(JPanel filterValuePanel) {
		this.filterValuePanel.removeAll();
		this.filterValuePanel.add(filterValuePanel);
	}

	public void setKeyAndValuePanel(Set<String> keys, JPanel filterValuePanel) {
		createComboBox(keys);
		setValuePanel(filterValuePanel);
	}

	public void setFilterEventListener(FilterEventListener listener) {
		this.listener = listener;
	}



}
