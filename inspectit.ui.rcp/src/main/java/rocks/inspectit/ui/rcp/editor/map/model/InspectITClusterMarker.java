package rocks.inspectit.ui.rcp.editor.map.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import rocks.inspectit.shared.all.tracing.data.Span;

public class InspectITClusterMarker<T extends Span> extends MapMarkerCircle implements InspectITMarker<T> {

	/**
	 * The list of spans stored in this cluster marker.
	 */
	private List<InspectITSpanMarker<T>> spans;

	/**
	 * Default constructor which needs a list of spans as well as a coordinate and radius for this
	 * cluster marker.
	 *
	 * @param spans
	 *            The list of spans to be stored in this marker.
	 * @param coord
	 *            The coordinate need for placing this marker on a map.
	 * @param radius
	 *            The radius for the circle displayed by this marker.
	 */
	public InspectITClusterMarker(List<InspectITSpanMarker<T>> spans, Coordinate coord, Double radius) {
		super(coord, radius);
		this.spans = spans;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getTags() {
		Map<String, Object> map = new HashMap<>();
		//map.put("duration", )
		return map;
	}




}
