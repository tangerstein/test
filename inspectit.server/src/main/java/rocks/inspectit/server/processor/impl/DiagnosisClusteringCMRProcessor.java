package rocks.inspectit.server.processor.impl;

import javax.persistence.EntityManager;

import rocks.inspectit.server.processor.AbstractCmrDataProcessor;
import rocks.inspectit.shared.all.communication.DefaultData;

public class DiagnosisClusteringCMRProcessor extends AbstractCmrDataProcessor {

	@Override
	protected void processData(DefaultData defaultData, EntityManager entityManager) {
		
	}

	@Override
	public boolean canBeProcessed(DefaultData defaultData) {
		// TODO Auto-generated method stub
		return false;
	}
}
