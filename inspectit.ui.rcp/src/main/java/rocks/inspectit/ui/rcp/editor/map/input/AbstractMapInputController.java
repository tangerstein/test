package rocks.inspectit.ui.rcp.editor.map.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;
import rocks.inspectit.ui.rcp.editor.map.MapSubView;
import rocks.inspectit.ui.rcp.editor.map.filter.FilterEventListener;
import rocks.inspectit.ui.rcp.editor.map.filter.MapFilter;
import rocks.inspectit.ui.rcp.editor.map.filter.MarkerFilterElement;
import rocks.inspectit.ui.rcp.editor.map.filter.NumericMapFilter;
import rocks.inspectit.ui.rcp.editor.map.filter.StringMapFilter;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITClusterMarker;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;

public abstract class AbstractMapInputController implements MapInputController {

	static final String DURATION = "duration";
	Map<Coordinate, List<InspectITMarker>> coordSys = new HashMap<Coordinate, List<InspectITMarker>>();
	List<InspectITMarker> allDataMarkers = new ArrayList<InspectITMarker>();
	int zoomLevel = 0;
	int clusteringTreshhold = 4;
	double clusteringCoefficient = 0.55;
	Map<String, MapFilter> filterTypes;
	String selectedTag = null;
	private FilterEvent event;


	/**
	 * The input definition.
	 */
	private InputDefinition inputDefinition;

	private MapSubView subview;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInputDefinition(InputDefinition inputDefinition) {
		// ToDo uncomment in Insptectit!
		//Assert.isNotNull(inputDefinition);

		this.inputDefinition = inputDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setView(MapSubView subview) {
		// ToDo uncomment in Insptectit!
		//Assert.isNotNull(inputDefinition);

		this.subview = subview;
		refreshFilter();

	}

	private class FilterEvent implements FilterEventListener {

		@Override
		public void keySelectionChanged(String key) {
			selectedTag = key;
			if ((key == "---") || (filterTypes.get(key) == null)) {
				subview.setValuePanel(new JPanel());
			} else {
				subview.setValuePanel(filterTypes.get(key).getPanel(event));
			}
			subview.setDataInput(clusterMarkers());
		}

		@Override
		public void StringvalueSelectionChanged(String value) {
			System.out.println("changeSelection");
			filterTypes.get(selectedTag).ChangeSelection(value);
			subview.setDataInput(clusterMarkers());
		}

		@Override
		public void NumericValueSelectionChanged(NumericRange value) {
			System.out.println("changeSelectionNUmber");
			filterTypes.get(selectedTag).ChangeSelection(value);
			subview.setDataInput(clusterMarkers());
		}

	}

	private void refreshFilter() {
		if (this.subview == null) {
			return;
		}
		if (filterTypes.get(this.selectedTag)!=null) {
			this.subview.setKeyAndValuePanel(filterTypes.keySet(), filterTypes.get(this.selectedTag).getPanel(event));
		} else {
			this.subview.setKeyAndValuePanel(filterTypes.keySet(), new JPanel());
		}
		this.subview.setFilterEventListener(event);
	}

	public AbstractMapInputController() {
		event = new FilterEvent();
		filterTypes = new HashMap<>();
	}


	/**
	 * Returns the input definition.
	 *
	 * @return The input definition.
	 */
	protected InputDefinition getInputDefinition() {
		// ToDo uncomment in Insptectit!
		//Assert.isNotNull(inputDefinition);

		return inputDefinition;
	}

	@Override
	public SubViewClassification getSubViewClassification() {
		return this.getSubViewClassification();
	}

	private InspectITMarker getCircle(Coordinate coord, double rad) {
		return new InspectITClusterMarker(null, coord, rad*clusteringCoefficient);
	}

	@Override
	public void setMarkers(List<InspectITMarker> markers) {
		System.out.println("setMarkers");
		this.allDataMarkers = markers;
		refreshFilters();
		if (subview != null) {
			this.subview.setDataInput(clusterMarkers());
		}
	}

	private void refreshFilters() {
		filterTypes.clear();
		for (InspectITMarker marker : this.allDataMarkers) {
			Map<String, String> tags = marker.getTags();
			for (String s : tags.keySet()) {
				if (s.contains("latitude") || s.contains("longitude")) {
					continue;
				}
				addSomething(s, tags.get(s));
			}
		}
		for (MapFilter t : filterTypes.values()) {
			t.finalizeFilter();
		}
		refreshFilter();
	}

	private void addSomething(String key, String value) {
		MapFilter filterType;
		if (filterTypes.containsKey(key)) {
			filterType = filterTypes.get(key);
		} else {
			try {
				Double.parseDouble(value);
				filterType = new NumericMapFilter<Double>(key);
			} catch (NumberFormatException e) {
				filterType = new StringMapFilter<String>(key);
			}
		}
		filterType.addValue(value);
		filterTypes.put(key, filterType);
	}

	@Override
	public void resetClustering() {
		coordSys = new HashMap<Coordinate, List<InspectITMarker>>();
	}

	@Override
	public List<InspectITMarker> getClusteredMarkers(ICoordinate coordinate) {
		return this.coordSys.get(calculateCoordinate(coordinate));
	}

	@Override
	public void setZoomLevel(int zoomlevel) {
		System.out.println("zoomAbstract");
		this.zoomLevel = zoomlevel;
		this.subview.setDataInput(clusterMarkers());
	}


	private double calculateRadius() {
		if (this.zoomLevel<2) {
			return 15.00;
		}
		return 15/(Math.exp((this.zoomLevel*this.clusteringCoefficient)));

	}


	private Coordinate calculateCoordinate(ICoordinate coord) {
		// Radius has to be considered for determining the Zone coordinate
		double zoneLength = 2*calculateRadius();
		double lat = calculateZoneCenter(coord.getLat(), 90.00, zoneLength);

		double lon = calculateZoneCenter(coord.getLon(), 180.00, zoneLength);

		return new Coordinate(lat, lon);
	}


	private Coordinate calculateCoordinate(InspectITMarker marker) {
		return calculateCoordinate(marker.getCoordinate());
	}


	private double calculateZoneCenter(double value, double offset, double zoneLength) {
		value = value+offset;
		int temp = (int)(value/zoneLength);
		double result = zoneLength*temp;
		result = result-offset;
		result = result+(zoneLength/2);
		return result;

	}

	private List<MapMarker> clusterMarkers() {
		resetClustering();
		System.out.println("CM: " + allDataMarkers.size());
		List<MapMarker> clusteredMarkers = new ArrayList<MapMarker>();
		for (int i=0;i<this.allDataMarkers.size();i++) {
			InspectITMarker marker = this.allDataMarkers.get(i);
			if ((filterTypes.get(selectedTag) != null) &&
					(filterTypes.get(selectedTag).applyFilter(marker)==null)) {
				continue;
			}
			Coordinate temp = calculateCoordinate(marker);
			if (coordSys.containsKey(temp)) {
				coordSys.get(temp).add(marker);
			} else{
				List<InspectITMarker> tempList = new ArrayList<InspectITMarker>();
				tempList.add(marker);
				coordSys.put(temp, tempList);
			}
		}
		for (Coordinate coord : coordSys.keySet()) {
			if (coordSys.get(coord).size()>this.clusteringTreshhold) {
				clusteredMarkers.add(getCircle(coord, calculateRadius()));
			} else {
				for (InspectITMarker mark : coordSys.get(coord)) {
					clusteredMarkers.add(mark);
				}
			}
		}
		return clusteredMarkers;
	}

	private MarkerFilterElement getSpecificFilter(InspectITMarker marker) {
		if ((this.selectedTag==null) || (this.selectedTag=="---")) {
			return new MarkerFilterElement();
		}
		MapFilter type = filterTypes.get(this.selectedTag);
		return (MarkerFilterElement) type.getValue(marker.getTags().get(this.selectedTag));
	}

}
