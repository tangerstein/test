package rocks.inspectit.ui.rcp.editor.map.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import rocks.inspectit.shared.all.tracing.data.Span;
import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;
import rocks.inspectit.ui.rcp.editor.map.MapSubView;
import rocks.inspectit.ui.rcp.editor.map.filter.MapFilter;
import rocks.inspectit.ui.rcp.editor.map.filter.NumericMapFilter;
import rocks.inspectit.ui.rcp.editor.map.filter.StringMapFilter;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITClusterMarker;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITSpanMarker;
import rocks.inspectit.ui.rcp.editor.map.model.NumericRange;

public abstract class AbstractMapInputController implements MapInputController {

	/**
	 * A constant string identifying the duration flag (should be put in some kind of constants.
	 * file)
	 */
	static final String DURATION = "duration";

	/**
	 * A map which holds the markers clustered to specific coordinate spaces.
	 */
	Map<Coordinate, List<InspectITMarker>> coordSys = new HashMap<Coordinate, List<InspectITMarker>>();

	/**
	 * A list of all available data markers.
	 */
	List<InspectITMarker> allDataMarkers = new ArrayList<InspectITMarker>();

	/**
	 * The current zoom level.
	 */
	int zoomLevel = 0;

	/**
	 * The current minimum amount of markers in a coordinate space in order to cluster.
	 */
	int clusteringTreshhold = 4;

	/**
	 * The current cluster coefficient.
	 */
	double clusteringCoefficient = 0.55;

	/**
	 * The current mapFilter.
	 */
	Map<String, MapFilter> filterTypes;

	/**
	 * The current selected tag.
	 */
	String selectedTag = null;


	MapSubView subview;

	/**
	 * The input definition.
	 */
	protected InputDefinition inputDefinition;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInputDefinition(InputDefinition inputDefinition) {
		Assert.isNotNull(inputDefinition);
		this.inputDefinition = inputDefinition;
	}

	/**
	 * Default constructor for this abstract map input controller.
	 *
	 */
	public AbstractMapInputController() {
		filterTypes = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SubViewClassification getSubViewClassification() {
		return this.getSubViewClassification();
	}

	/**
	 * Creates and returns a circle marker with the given coordinate and radius.
	 *
	 * @param coord
	 *            The coordinate the marker should be displayed at.
	 * @param rad
	 *            The radius for circle marker.
	 * @return The created circle marker.
	 */
	private InspectITMarker getCircle(Coordinate coord, double rad) {
		return new InspectITClusterMarker(null, coord, rad*clusteringCoefficient);
	}

	/**
	 * refreshes the filter within the sub view controlled by this input controller.
	 *
	 */
	protected void refreshFilters() {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetClustering() {
		coordSys = new HashMap<Coordinate, List<InspectITMarker>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<InspectITMarker> getClusteredMarkers(ICoordinate coordinate) {
		return this.coordSys.get(calculateCoordinate(coordinate));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setZoomLevel(int zoomlevel) {
		this.zoomLevel = zoomlevel;
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

	protected void clusterMarkers(List<? extends Span> collection) {
		resetClustering();
		List<InspectITMarker> clusteredMarkers = new ArrayList<InspectITMarker>();
		for (int i = 0; i < collection.size(); i++) {
			InspectITMarker marker = createMarker(collection.get(i));
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
		this.allDataMarkers = clusteredMarkers;
	}

	private InspectITMarker createMarker(Span span) {
		Map<String, String> tags = span.getTags();
		return new InspectITSpanMarker(span, new Coordinate(Double.parseDouble(tags.get("http.request.latitude")), Double.parseDouble(tags.get("http.request.longitude"))));
	}

	@Override
	public Object getMapInput() {
		return allDataMarkers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, MapFilter> getMapFilter() {
		return filterTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void keySelectionChanged(String key) {
		selectedTag = key;
		doRefresh();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stringvalueSelectionChanged(String value) {
		System.out.println("changeSelection");
		filterTypes.get(selectedTag).changeSelection(value);
		doRefresh();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void numericValueSelectionChanged(NumericRange value) {
		System.out.println("changeSelectionNUmber");
		filterTypes.get(selectedTag).changeSelection(value);
		doRefresh();
	}



}
