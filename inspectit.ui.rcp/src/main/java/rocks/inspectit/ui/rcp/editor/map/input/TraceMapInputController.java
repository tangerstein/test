package rocks.inspectit.ui.rcp.editor.map.input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rocks.inspectit.shared.all.tracing.data.AbstractSpan;
import rocks.inspectit.shared.cs.cmr.service.ISpanService;
import rocks.inspectit.ui.rcp.editor.inputdefinition.InputDefinition;


public class TraceMapInputController extends AbstractMapInputController {

	ISpanService spanService;
	List<AbstractSpan> spans;
	List<AbstractSpan> selection;

	public TraceMapInputController() {
		super();
		spans = new ArrayList<>();
		selection = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInputDefinition(InputDefinition inputDefinition) {
		super.setInputDefinition(inputDefinition);
		spanService = inputDefinition.getRepositoryDefinition().getSpanService();
	}

	@Override
	public void setData(List<? extends Object> data) {
		Set<Long> traceIds = new HashSet<>();
		for (Object rootSpan : data) {
			traceIds.add(((AbstractSpan) rootSpan).getSpanIdent().getTraceId());
		}
		List<AbstractSpan> list = new ArrayList<>();
		for (Long l : traceIds) {
			list.addAll((List<AbstractSpan>) spanService.getSpans(l));
		}
		spans = list;
		System.out.println(spans.size());
		mapSettings.setResetFilters(true);
		refreshFilters(spans);
	}

	@Override
	public void setDataSelection(List<? extends Object> data) {
		selection = (List<AbstractSpan>) data;
		if (!selection.isEmpty()) {
			List<AbstractSpan> list = new ArrayList<>();
			for (Object rootSpan : data) {
				list.addAll((List<AbstractSpan>) spanService.getSpans(((AbstractSpan) rootSpan).getSpanIdent().getTraceId()));
			}
			spans = list;
		}
		System.out.println(selection.size());
	}

	@Override
	public void doRefresh() {
		for (Object o : spans) {
			long id = ((AbstractSpan) o).getSpanIdent().getTraceId();
		}
		refreshFilters(spans);
		clusterMarkers(spans);


	}

}
