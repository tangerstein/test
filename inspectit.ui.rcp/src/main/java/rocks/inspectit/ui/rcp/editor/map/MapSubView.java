package rocks.inspectit.ui.rcp.editor.map;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent.COMMAND;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import rocks.inspectit.ui.rcp.editor.AbstractSubView;
import rocks.inspectit.ui.rcp.editor.map.filter.MapFilter;
import rocks.inspectit.ui.rcp.editor.map.input.MapInputController;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceEventCallback.PreferenceEvent;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceId;

/**
 * @author Christopher Völker
 *
 */
public class MapSubView extends AbstractSubView {

	/**
	 * The Component holding the map and the filter panel.
	 */
	private Composite swtAwtComponent;

	private JComboBox<String> tagComboBox;
	private JPanel filterValuePanel;
	private String selection;

	/**
	 * The map filter panel.
	 */
	private JPanel filter;

	Map<String, MapFilter> filterMap;

	/**
	 * The map frame.
	 */
	JMapViewer mapViewer;

	/**
	 * The referenced input controller.
	 */
	private MapInputController mapInputController;

	/**
	 * Default constructor which needs a map input controller to create all the content etc.
	 *
	 * @param mapInputController
	 *            The map input controller.
	 */
	public MapSubView(MapInputController mapInputController) {
		this.mapInputController = mapInputController;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		mapInputController.setInputDefinition(getRootEditor().getInputDefinition());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent, FormToolkit toolkit) {
		swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		tagComboBox = new JComboBox<>();
		filterValuePanel = new JPanel();
		filter = new JPanel();
		filter.setLayout(new FlowLayout(FlowLayout.LEFT));
		filter.add(tagComboBox);
		filter.add(filterValuePanel);
		createKeyBox(null);
		frame.setLayout(new BorderLayout());
		mapViewer = new JMapViewer() {

			@Override
			public String getToolTipText(MouseEvent e) {
				List<InspectITMarker> temp = mapInputController.getClusteredMarkers(this.getPosition(e.getX(), e.getY()));
				if ((temp != null) && (temp.size() > 4)) {
					return String.valueOf(temp.size());
				}
				return "";
			}
		};
		mapViewer.setAutoscrolls(true);
		mapViewer.setInheritsPopupMenu(true);
		mapViewer.setScrollWrapEnabled(true);
		mapViewer.setToolTipText("");
		mapViewer.addJMVListener(new JMapViewerEventListener() {

			@Override
			public void processCommand(JMVCommandEvent arg0) {
				if (arg0.getCommand().equals(COMMAND.ZOOM)) {
					zoomLevelChanged();
				} else {
					// this would be triggered if the map is moved!
				}
			}
		});
		zoomLevelChanged();
		// this.add(mapInputController.getTestPanel(), BorderLayout.EAST);

		frame.add(filter, BorderLayout.NORTH);
		frame.add(mapViewer, BorderLayout.CENTER);
		mapInputController.doRefresh();
		mapViewer.setMapMarkerList((List<MapMarker>) mapInputController.getMapInput());
		filterMap = mapInputController.getMapFilter();
		createKeyBox(filterMap.keySet());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<PreferenceId> getPreferenceIds() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doRefresh() {
		// TODO Auto-generated method stub
		mapInputController.doRefresh();
		mapViewer.setMapMarkerList((List<MapMarker>) mapInputController.getMapInput());
		filterMap = mapInputController.getMapFilter();
		mapViewer.updateUI();
		filter.updateUI();
		filter.revalidate();
		mapViewer.revalidate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preferenceEventFired(PreferenceEvent preferenceEvent) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDataInput(List<? extends Object> data) {
		/*
		mapViewer.removeAllMapMarkers();
		mapViewer.setMapMarkerList((List<MapMarker>) data);
		mapViewer.updateUI();
		System.out.println(data.size());
		filter.revalidate();
		mapViewer.revalidate();
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return swtAwtComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ISelectionProvider getSelectionProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Function which is called upon the change of the zoom Level of the map. It propragates the
	 * change to the mapInputController in order to adapt the data displayed data.
	 *
	 */
	private void zoomLevelChanged() {
		mapInputController.setZoomLevel(mapViewer.getZoom());
		doRefresh();
	}

	private void createKeyBox(Set<String> keys) {
		tagComboBox.removeAllItems();
		tagComboBox.addItem("---");
		selection = "---";
		if (keys != null) {
			for (String tag : keys) {
				tagComboBox.addItem(tag);
			}
		}
		tagComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) tagComboBox.getSelectedItem();
				if ((selectedItem == null) || (selectedItem == selection)) {
					return;
				}
				selection = selectedItem;
				mapInputController.keySelectionChanged(selectedItem);
				filterValuePanel.removeAll();
				JPanel test = filterMap.get(selection).getPanel(new FilterValueObject());
				filterValuePanel.add(test);
				filterValuePanel.updateUI();
				doRefresh();
			}
		});
	}

	public class FilterValueObject {
		public void selectionChanged(String value) {
			mapInputController.stringvalueSelectionChanged(value);
			doRefresh();
		}

		public void selectionChanged(NumericRange value) {
			mapInputController.numericValueSelectionChanged(value);
			doRefresh();
		}
	}


}
