package rocks.inspectit.server.util;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileUsecaseElement;

@Component
/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileTraceStorage implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 949018328887170040L;

	/**
	 * Contains mobile traces.
	 */
	private ConcurrentHashMap<Long, MobileUsecaseElement> mapMobileTrace;
	
	public MobileTraceStorage() {
		this.mapMobileTrace = new ConcurrentHashMap<Long, MobileUsecaseElement>();
	}
	
	/**
	 * Adds a new mobile trace element to the storage:
	 * 
	 * @param element
	 */
	public void push(MobileUsecaseElement element) {
		this.mapMobileTrace.put(element.getUsecaseID(), element);
	}
	
	public MobileUsecaseElement get(long useCaseID) {
		return this.mapMobileTrace.get(useCaseID);
	}
	
	public MobileUsecaseElement getAndRemove(long useCaseID) {
		return this.mapMobileTrace.remove(useCaseID);
	}
	
	public boolean remove(long useCaseID) {
		return (this.mapMobileTrace.remove(useCaseID) != null);
	}
	
	public ConcurrentHashMap<Long, MobileUsecaseElement> getMapCopy() {
		ConcurrentHashMap<Long, MobileUsecaseElement> mapMobileTraceCopy = new ConcurrentHashMap<Long, MobileUsecaseElement>();
		mapMobileTraceCopy.putAll(mapMobileTrace);
		return mapMobileTraceCopy;
	}
	
}
