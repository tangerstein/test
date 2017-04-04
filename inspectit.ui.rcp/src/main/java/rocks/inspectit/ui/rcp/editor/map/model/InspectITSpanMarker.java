package rocks.inspectit.ui.rcp.editor.map.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import rocks.inspectit.shared.all.tracing.data.Span;

public class InspectITSpanMarker<T extends Span> extends MapMarkerDot implements InspectITMarker {

	/**
	 * The span stored in this marker.
	 */
	private T span;

	/**
	 * Default constructor which needs a span and a coordinate.
	 *
	 * @param span
	 *            The span which is to be stored in this marker.
	 * @param coord
	 *            The coordinate for placing the marker on a map.
	 */
	public InspectITSpanMarker(T span, Coordinate coord) {
		super(coord);
		this.span = span;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return new Color(0, 0, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getTags() {
		Map<String, Object> map = new HashMap<>();
		map.put("duration", String.valueOf(span.getDuration()));
		map.putAll(span.getTags());
		return map;
	}


}
