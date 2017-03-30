package rocks.inspectit.ui.rcp.editor.map;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import rocks.inspectit.ui.rcp.editor.map.filter.FilterEventListener;
import rocks.inspectit.ui.rcp.editor.map.input.MapInputController;
import rocks.inspectit.ui.rcp.editor.map.model.FilterPanel;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceEventCallback.PreferenceEvent;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceId;

/**
 * @author Christopher Völker
 *
 */
public class MapSubView extends AbstractSubView {

	private Composite swtAwtComponent;
	private FilterPanel filter;
	JMapViewer mapViewer;
	private MapInputController mapInputController;

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
		filter = new FilterPanel(null);
		this.mapInputController.setView(this);
		frame.setLayout(new BorderLayout());
		mapViewer = new JMapViewer() {

			@Override
			public String getToolTipText(MouseEvent e) {
				List<InspectITMarker> temp = getInputController().getClusteredMarkers(this.getPosition(e.getX(), e.getY()));
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
		mapViewer.removeAllMapMarkers();
		mapViewer.setMapMarkerList((List<MapMarker>) data);
		mapViewer.updateUI();
		System.out.println(data.size());
		filter.revalidate();
		mapViewer.revalidate();
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

	private MapInputController getInputController() {
		return mapInputController;
	}

	private void zoomLevelChanged() {
		System.out.println("zoom");
		mapInputController.setZoomLevel(mapViewer.getZoom());
	}


	public void setValuePanel(JPanel filterValuePanel) {
		this.filter.setValuePanel(filterValuePanel);
		filter.revalidate();
	}

	public void setKeyAndValuePanel(Set<String> keys, JPanel filterValuePanel) {
		this.filter.setKeyAndValuePanel(keys, filterValuePanel);
		filter.revalidate();
	}

	public void setFilterEventListener(FilterEventListener listener) {
		this.filter.setFilterEventListener(listener);
		filter.revalidate();
	}

}
