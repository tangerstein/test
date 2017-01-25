package rocks.inspectit.shared.all.indexing;

public interface IProblemOccurrenceIndexQuery extends IIndexQuery {

	public long getRequestRootID();

	public long getGlobalContextId();

	public long getProblemContextId();

	public long getRootCauseId();

}
