package rocks.inspectit.ui.rcp.editor.map.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import rocks.inspectit.shared.all.tracing.data.AbstractSpan;

public class InspectITClusterMarker<T extends AbstractSpan> extends MapMarkerCircle implements InspectITMarker<T> {

	private List<InspectITSpanMarker<T>> spans;

	public InspectITClusterMarker(List<InspectITSpanMarker<T>> spans, Coordinate coord, Double radius) {
		super(coord, radius);
		this.spans = spans;
	}

	@Override
	public Map<String, Object> getTags() {
		Map<String, Object> map = new HashMap<>();
		//map.put("duration", )
		return map;
	}




}
