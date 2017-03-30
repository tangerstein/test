package rocks.inspectit.ui.rcp.editor.map.model;

import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Style;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import rocks.inspectit.shared.all.tracing.data.AbstractSpan;

public interface InspectITMarker<T extends AbstractSpan> extends MapMarker {

	/*	public Long getTimeStamp();

	public void setTimeStamp(Long timestamp);

	public Double getDuration();

	public void setDuration(Double duration);*/

	Map<String,Object> getTags();

	void setStyle(Style style);

	void setVisible(Boolean visible);

}
