package rocks.inspectit.ui.rcp.editor.map.input;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import rocks.inspectit.shared.all.tracing.data.AbstractSpan;
import rocks.inspectit.shared.all.tracing.data.ServerSpan;
import rocks.inspectit.shared.all.tracing.data.Span;
import rocks.inspectit.shared.cs.cmr.service.ISpanService;
import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITMarker;
import rocks.inspectit.ui.rcp.editor.map.model.InspectITSpanMarker;


public class TraceMapInputController extends AbstractMapInputController {

	ISpanService spanService;
	List<Span> temp;

	public TraceMapInputController() {
		// Generate Marker
		// create Data
		Random rand = new Random();
		// create five deviceIds
		long[] deviceIds = new long[] { rand.nextLong(), rand.nextLong(), rand.nextLong(), rand.nextLong(), rand.nextLong() };
		System.out.println(spanService == null);
		temp = getSpans(100, deviceIds);
		clusterMarkers(temp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInputDefinition(InputDefinition inputDefinition) {
		super.setInputDefinition(inputDefinition);
		spanService = inputDefinition.getRepositoryDefinition().getSpanService();
		System.out.println(spanService == null);
	}

	@Override
	public void doRefresh() {
		// RootSpans not sufficient, need to get all Spans by traceID (by iterating over to
		// rootspans)
		// clusterMarkers((List<Span>) spanService.getRootSpans(-1, null, null, null));
		clusterMarkers(temp);
		refreshFilters();
	}

	static Coordinate coord = new Coordinate(46.00, 7.00);
	static double latLower = -60.00;
	static double lonLower = -12.00;

	private static List<InspectITMarker> setMarker(Collection<AbstractSpan> spans) {
		List<InspectITMarker> markers = new ArrayList<InspectITMarker>();
		for (AbstractSpan abstractSpan : spans) {
			Map<String, String> tags = abstractSpan.getTags();
			/*
			 * Color color; int alpha; if
			 * (tags.get("http.request.networkConnection").equals(NetworkConnection.Edge.toString())
			 * ) { alpha = 20; } else if
			 * (tags.get("http.request.networkConnection").equals(NetworkConnection.ThreeG.toString(
			 * ))) { alpha = 60; } else if
			 * (tags.get("http.request.networkConnection").equals(NetworkConnection.LTE.toString()))
			 * { alpha = 120; } else { alpha = 240; } if
			 * (tags.get("http.request.networkProvider").equals(NetworkProvider.Telekom.toString()))
			 * { color = new Color(227, 0, 116, alpha); } else if
			 * (tags.get("http.request.networkProvider").equals(NetworkProvider.Vodafon.toString()))
			 * { color = new Color(255, 0, 0, alpha); } else { color = new Color(4, 37, 108, alpha);
			 * }
			 */
			InspectITMarker marker = new InspectITSpanMarker(abstractSpan,
					new Coordinate(Double.parseDouble(tags.get("http.request.latitude")), Double.parseDouble(tags.get("http.request.longitude"))));
			// marker.setBackColor(color);
			markers.add(marker);

		}
		return markers;
	}

	List<Span> spans;
	long[] deviceIds;

	public List<Span> getSpans(int number, long[] deviceIds) {
		// generate
		this.deviceIds = deviceIds.clone();
		spans = new ArrayList<>();
		this.generateSpans(number);
		return spans;
	}

	private void generateSpans(int number) {
		Random rand = new Random();

		for (int i = 0; i < number; i++) {

			// 17.03.17 timestamp 1489747457082
			// Two days in milliseconds: 172800000
			long timestamp = 1489747457082l - rand.nextInt(172800001);

			double duration = rand.nextDouble() * 60000;

			// use one of the deviceIds
			long deviceId = deviceIds[rand.nextInt(deviceIds.length)];

			// Only ServerSpans
			double lat = 47.40724 + (rand.nextDouble() * 7.50066);
			double lon = 5.98815 + (rand.nextDouble() * 9.00038);

			String networkProvider = NetworkProvider.values()[rand.nextInt(NetworkProvider.values().length)].toString();
			String networkConnection = NetworkConnection.values()[rand.nextInt(NetworkConnection.values().length)].toString();

			ServerSpan span = new ServerSpan();
			span.setDuration(duration);
			span.setId(rand.nextLong());
			span.setTimeStamp(new Timestamp(timestamp));
			Map<String, String> tags = new HashMap<>();
			tags.put("http.request.latitude", lat + "");
			tags.put("http.request.longitude", lon + "");
			tags.put("http.request.networkConnection", networkConnection);
			tags.put("http.request.networkProvider", networkProvider);
			tags.put("deviceID", deviceId + "");
			span.addAllTags(tags);

			spans.add(span);
		}
	}

	private enum NetworkConnection {
		Edge, ThreeG, LTE, FourG;

		@Override
		public String toString() {
			String returnVal = "";
			switch (this) {
			case Edge:
				returnVal = "Edge";
				break;
			case ThreeG:
				returnVal = "3G";
				break;
			case LTE:
				returnVal = "LTE";
				break;
			case FourG:
				returnVal = "4G";
				break;
			}
			return returnVal;
		}

	}

	private enum NetworkProvider {
		Telekom, O2, Vodafon
	}

}
