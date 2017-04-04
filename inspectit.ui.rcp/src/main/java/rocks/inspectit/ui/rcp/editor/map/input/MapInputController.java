package rocks.inspectit.ui.rcp.editor.map.input;

import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;
import rocks.inspectit.ui.rcp.editor.map.filter.MapFilter;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;
import rocks.inspectit.ui.rcp.editor.root.SubViewClassificationController;

/**
 * The interface for all map input controller.
 *
 * @author Christopher Völker, Simon Lehmann
 *
 */
public interface MapInputController extends SubViewClassificationController {

	/**
	 * Sets the input definition of this controller.
	 *
	 * @param inputDefinition
	 *            The input definition.
	 */
	void setInputDefinition(InputDefinition inputDefinition);

	/**
	 * Gets the (clustered) markers at the given coordinate.
	 *
	 * @param coordinate
	 *            The coordinate for which the markers are requested.
	 * @return The list of (clustered) markers at this given coordinate.
	 */
	List<InspectITMarker> getClusteredMarkers(ICoordinate coordinate);

	/**
	 * Resets the clustering of the markers by removing all entries from the clustering map.
	 *
	 */
	void resetClustering();

	/**
	 * Sets the new zoom level and recalculates the clustering of markers.
	 *
	 * @param zoomLevel
	 *            The new zoom level.
	 */
	void setZoomLevel(int zoomLevel);

	/**
	 * @return
	 */
	Object getMapInput();

	/**
	 *
	 */
	void doRefresh();

	/**
	 * @return
	 */
	Map<String, MapFilter> getMapFilter();

	public void keySelectionChanged(String key);

	/**
	 * {@inheritDoc}
	 */
	public void stringvalueSelectionChanged(String value);

	/**
	 * {@inheritDoc}
	 */
	public void numericValueSelectionChanged(NumericRange value);

}