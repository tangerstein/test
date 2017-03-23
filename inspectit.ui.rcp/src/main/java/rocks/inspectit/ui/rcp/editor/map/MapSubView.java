package rocks.inspectit.ui.rcp.editor.map;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import rocks.inspectit.ui.rcp.editor.AbstractSubView;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceEventCallback.PreferenceEvent;
import rocks.inspectit.ui.rcp.editor.preferences.PreferenceId;

/**
 * @author Christopher Völker
 *
 */
public class MapSubView extends AbstractSubView {

	private Composite swtAwtComponent;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent, FormToolkit toolkit) {
		swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(swtAwtComponent);
		JMapViewer map = new JMapViewer();

		// check below if this is needed !?
		map.setAutoscrolls(true);
		map.setInheritsPopupMenu(true);
		map.setScrollWrapEnabled(true);

		MapMarker circle = new MapMarkerDot(48.745182, 9.106806);
		map.setMapMarkerList(Collections.<MapMarker> singletonList(circle));
		frame.add(map);
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
		// TODO Auto-generated method stub

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

}
