package rocks.inspectit.shared.all.indexing;

public interface IUsecaseAwareStorageIndexQuery extends IIndexQuery{
	//TODO
	public long getUsecaseId();
	public String getUsecaseDescription();
}
