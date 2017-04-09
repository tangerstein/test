package rocks.inspectit.ui.rcp.editor.map.model;

import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import rocks.inspectit.shared.all.tracing.data.Span;

/**
 * Interface used by all Markers used for the Map.
 *
 * @param <T>
 *            The generic parameter has to be a type extending Span
 *
 * @author Christopher Völker, Simon Lehmann
 *
 */
public interface InspectITMarker<T extends Span> extends MapMarker {

	/**
	 * Retrieves the tags of the span within this marker.
	 *
	 * @return The map of tags of the span stored in this marker.
	 */
	Map<String, Object> getTags();

	/**
	 * Applies the given style to this marker.
	 *
	 * @param style
	 *            The style to be applied for this marker.
	 */
	void setStyle(Style style);

	/**
	 * Sets the visibility of this marker indicated by the given boolean value.
	 *
	 * @param visible
	 *            The visibility for the marker.
	 */
	void setVisible(Boolean visible);

}
