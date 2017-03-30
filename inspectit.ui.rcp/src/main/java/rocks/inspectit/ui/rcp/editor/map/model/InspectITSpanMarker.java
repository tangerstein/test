package rocks.inspectit.ui.rcp.editor.map.model;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import rocks.inspectit.shared.all.tracing.data.AbstractSpan;

public class InspectITSpanMarker<T extends AbstractSpan> extends MapMarkerDot implements InspectITMarker {

	private T span;

	public InspectITSpanMarker(T span, Coordinate coord) {
		super(coord);
		this.span = span;
	}

	@Override
	public Color getColor() {
		return new Color(0, 0, 0, 0);
	}

	@Override
	public Map<String, Object> getTags() {
		Map<String, Object> map = new HashMap<>();
		map.put("duration", String.valueOf(span.getDuration()));
		map.putAll(span.getTags());
		return map;
	}


}
