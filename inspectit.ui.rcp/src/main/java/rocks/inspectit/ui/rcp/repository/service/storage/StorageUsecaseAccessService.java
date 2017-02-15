package rocks.inspectit.ui.rcp.repository.service.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.mockito.internal.progress.IOngoingStubbing;

import rocks.inspectit.shared.all.communication.comparator.DefaultDataComparatorEnum;
import rocks.inspectit.shared.all.communication.comparator.ResultComparator;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileUsecaseElement;
import rocks.inspectit.shared.cs.cmr.service.IInvocationDataAccessService;
import rocks.inspectit.shared.cs.cmr.service.IUsecaseAccessService;
import rocks.inspectit.shared.cs.indexing.query.factory.impl.InvocationSequenceDataQueryFactory;
import rocks.inspectit.shared.cs.indexing.storage.IStorageTreeComponent;
import rocks.inspectit.shared.cs.indexing.storage.impl.StorageIndexQuery;
import rocks.inspectit.shared.cs.indexing.storage.impl.UsecaseAwareStorageIndexQuery;

/**
 * {@link IInvocationDataAccessService} for storage purposes.
 *
 * @author Ivan Senic
 *
 */
public class StorageUsecaseAccessService extends AbstractStorageService<MobileUsecaseElement> implements IUsecaseAccessService {
	/**
	 * Indexing tree.
	 */
	private IStorageTreeComponent<MobileUsecaseElement> indexingTree;
	
	@Override
	public ConcurrentHashMap<Long, MobileUsecaseElement> getAllUseCaseInstances() {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseDescription(null);
		List<MobileUsecaseElement> resultList = super.executeQuery(query);
		ConcurrentHashMap<Long, MobileUsecaseElement> resultMap = new ConcurrentHashMap<Long, MobileUsecaseElement>();
		for(MobileUsecaseElement element : resultList){
			resultMap.put(element.getUsecaseID(), element);
		}
		return resultMap;
	}

	@Override
	public MobileUsecaseElement getSingleUseCaseInstance(long usecaseId) {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseId(usecaseId);
		query.setUsecaseDescription(null);
		List<MobileUsecaseElement> resultList =  super.executeQuery(query);
		if(resultList.size() > 1){
			throw new IllegalStateException("Dublicate MobileUsecaseElement objects with the same Id were found");
		}
		return resultList.get(0);
	}

	@Override
	public ConcurrentHashMap<Long, MobileUsecaseElement> getAllUsecaseInstances(
			String usecaseDescription) {
		UsecaseAwareStorageIndexQuery query = new UsecaseAwareStorageIndexQuery();
		query.setUsecaseDescription(usecaseDescription);
		List<MobileUsecaseElement> resultList =  super.executeQuery(query);
		ConcurrentHashMap<Long, MobileUsecaseElement> resultMap = new ConcurrentHashMap<Long, MobileUsecaseElement>();
		for(MobileUsecaseElement element : resultList){
			resultMap.put(element.getUsecaseID(), element);
		}
		return resultMap;
	}

	@Override
	protected IStorageTreeComponent<MobileUsecaseElement> getIndexingTree() {
		return indexingTree;
	}

	public void setIndexingTree(
			IStorageTreeComponent<MobileUsecaseElement> storageTreeComponent) {
		this.indexingTree = storageTreeComponent;
		
	}

}
