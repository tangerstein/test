package rocks.inspectit.server.diagnosis.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import rocks.inspectit.server.cache.IBuffer;
import rocks.inspectit.server.cache.impl.BufferElement;
import rocks.inspectit.server.dao.ProblemOccurrenceDataDao;
import rocks.inspectit.shared.all.communication.data.diagnosis.results.ProblemOccurrence;
import rocks.inspectit.shared.all.spring.logger.Log;

/**
 * 
 * @author Alexander Wert
 *
 */
@Component
public class DiagnosisResultNotificationServiceImpl implements IDiagnosisResultNotificationService {

	@Log
	Logger log;
	
	@Autowired
	@Qualifier("problemOccurenceBuffer")
	IBuffer<ProblemOccurrence> problemOccuranceBuffer;
	@Autowired
	ProblemOccurrenceDataDao problemOccurrenceDataDao;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onNewDiagnosisResult(ProblemOccurrence problemOccurrence) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onNewDiagnosisResult(Collection<ProblemOccurrence> problemOccurrences) {
		for (ProblemOccurrence po : problemOccurrences) {
			// log.warn(po.getRootCause().getInvocationIds().size() + "");
			// Map request root with problem occurrence (atomic)
			problemOccuranceBuffer.put(new BufferElement<ProblemOccurrence>(po));
		}
	}
}

