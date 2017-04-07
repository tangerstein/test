package rocks.inspectit.ui.rcp.editor.map.filter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.openstreetmap.gui.jmapviewer.Style;

public class MarkerFilterElement {

	Style style = new Style();
	Boolean isVisible;

	public MarkerFilterElement(Boolean visible) {
		initialize(Color.BLACK, new BasicStroke(), new Font(Font.SERIF, Font.BOLD, 12), Color.BLACK, visible);
	}

	public MarkerFilterElement(Color backColor) {
		initialize(backColor, new BasicStroke(), new Font(Font.SERIF, Font.BOLD, 12), Color.BLACK, true);
	}

	public MarkerFilterElement() {
		initialize(Color.BLACK, new BasicStroke(), new Font(Font.SERIF, Font.BOLD, 12), Color.BLACK, true);
	}

	private void initialize(Color backColor, Stroke stroke, Font font, Color color, Boolean visible) {
		this.setBackColor(backColor);
		this.setColor(color);
		this.setStroke(stroke);
		this.setFont(font);
		this.isVisible = visible;
	}

	public Style style() {
		return style;
	}

	public Boolean isVisible() {
		return isVisible;
	}

	public void setVisible(Boolean visible) {
		this.isVisible = visible;
	}

	public void setBackColor(Color backColor) {
		this.style.setBackColor(backColor);
	}

	public void setColor(Color color) {
		this.style.setColor(color);
	}

	public void setStroke(Stroke stroke) {
		this.style.setStroke(stroke);
	}

	public void setFont(Font font) {
		this.style.setFont(font);
	}


}
