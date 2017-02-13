package rocks.inspectit.shared.all.communication.data.eum.mobile.storage;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileIOSElement;

@Component
/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileTraceStorage {

	private ConcurrentHashMap<String, MobileIOSElement> mapMobileTrace;
	
	public MobileTraceStorage() {
		this.mapMobileTrace = new ConcurrentHashMap<String, MobileIOSElement>(); 
	}
	
	public void push(MobileIOSElement element){
		this.mapMobileTrace.put(element.getUsecaseID(), element);
	}
	
	public MobileIOSElement get(String useCaseID){
		return this.mapMobileTrace.get(useCaseID);
	}
	
	public MobileIOSElement getAndRemove(String useCaseID){
		return this.mapMobileTrace.remove(useCaseID);
	}
	
	public boolean remove(String useCaseID){
		return (this.mapMobileTrace.remove(useCaseID) != null);
	}
	
}
