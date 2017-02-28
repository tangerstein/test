package rocks.inspectit.ui.rcp.repository.service.storage;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rocks.inspectit.shared.all.communication.data.eum.mobile.MobilePeriodicMeasurement;
import rocks.inspectit.shared.cs.cmr.service.IInvocationDataAccessService;
import rocks.inspectit.shared.cs.cmr.service.IMobilePeriodicMeasurementAccessService;
import rocks.inspectit.shared.cs.indexing.storage.IStorageTreeComponent;
import rocks.inspectit.shared.cs.indexing.storage.impl.UsecaseAwareStorageIndexQuery;

/**
 * {@link IInvocationDataAccessService} for storage purposes.
 *
 * @author Ivan Senic
 *
 */
public class StorageMobilePeriodicMeasurementAccessService extends AbstractStorageService<MobilePeriodicMeasurement> implements IMobilePeriodicMeasurementAccessService {
	/**
	 * Indexing tree.
	 */
	private IStorageTreeComponent<MobilePeriodicMeasurement> indexingTree;
	
	@Override
	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUseCaseInstances() {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseDescription(null);
		List<MobilePeriodicMeasurement> resultList = super.executeQuery(query);
		ConcurrentHashMap<Long, MobilePeriodicMeasurement> resultMap = new ConcurrentHashMap<Long, MobilePeriodicMeasurement>();
		for(MobilePeriodicMeasurement element : resultList){
			resultMap.put(element.getId(), element);
		}
		return resultMap;
	}

	@Override
	public MobilePeriodicMeasurement getSingleUseCaseInstance(long usecaseId) {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseId(usecaseId);
		query.setUsecaseDescription(null);
		List<MobilePeriodicMeasurement> resultList =  super.executeQuery(query);
		if(resultList.size() > 1){
			throw new IllegalStateException("Dublicate MobileUsecaseElement objects with the same Id were found");
		}
		return resultList.get(0);
	}

	@Override
	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUsecaseInstances(
			String usecaseDescription) {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseDescription(usecaseDescription);
		List<MobilePeriodicMeasurement> resultList =  super.executeQuery(query);
		ConcurrentHashMap<Long, MobilePeriodicMeasurement> resultMap = new ConcurrentHashMap<Long, MobilePeriodicMeasurement>();
		for(MobilePeriodicMeasurement element : resultList){
			resultMap.put(element.getId(), element);
		}
		return resultMap;
	}

	@Override
	protected IStorageTreeComponent<MobilePeriodicMeasurement> getIndexingTree() {
		return indexingTree;
	}

	public void setIndexingTree(
			IStorageTreeComponent<MobilePeriodicMeasurement> storageTreeComponent) {
		this.indexingTree = storageTreeComponent;
		
	}

}
