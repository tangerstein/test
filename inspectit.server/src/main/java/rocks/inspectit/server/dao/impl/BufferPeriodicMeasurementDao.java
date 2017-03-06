package rocks.inspectit.server.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;

import rocks.inspectit.shared.all.communication.data.eum.mobile.MobilePeriodicMeasurement;
import rocks.inspectit.shared.all.indexing.IIndexQuery;
import rocks.inspectit.shared.cs.indexing.impl.IndexQuery;
import rocks.inspectit.shared.cs.indexing.restriction.impl.IndexQueryRestrictionFactory;

@Repository
public class BufferPeriodicMeasurementDao extends AbstractBufferDataDao<MobilePeriodicMeasurement> {
	/**
	 * Returns all available {@link MobilePeriodicMeasurement} instances.
	 * 
	 * @return
	 */
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances() {
		return super.executeQuery(new IndexQuery(), false);

	}

	/**
	 * Returns all {@link MobilePeriodicMeasurement} instances from a specific
	 * deviceID.
	 * 
	 * @return
	 */
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances(long deviceID) {
		IIndexQuery query = new IndexQuery();
		query.addIndexingRestriction(IndexQueryRestrictionFactory.equal("deviceID", deviceID));
		return super.executeQuery(query, false);

	}

	/**
	 * Returns all {@link MobilePeriodicMeasurement} instances from a specific
	 * deviceID in an interval.
	 * 
	 * @return
	 */
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances(long deviceID, long fromTimestamp,
			long toTimestamp) {
		IIndexQuery query = new IndexQuery();
		query.addIndexingRestriction(IndexQueryRestrictionFactory.equal("deviceID", deviceID));
		query.setFromDate(new Timestamp(fromTimestamp));
		query.setToDate(new Timestamp(toTimestamp));
		return super.executeQuery(query, false);

	}

}
