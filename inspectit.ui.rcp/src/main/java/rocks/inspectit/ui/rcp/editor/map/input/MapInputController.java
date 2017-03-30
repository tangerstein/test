package rocks.inspectit.ui.rcp.editor.map.input;

import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;
import rocks.inspectit.ui.rcp.editor.map.MapSubView;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.root.SubViewClassificationController;

/**
 * The interface for all tree input controller.
 *
 * @author Patrice Bouillet
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

	void setView(MapSubView subView);

	List<InspectITMarker> getClusteredMarkers(ICoordinate coordinate);


	void resetClustering();

	void setMarkers(List<InspectITMarker> markers);

	void setZoomLevel(int zoomLevel);

}