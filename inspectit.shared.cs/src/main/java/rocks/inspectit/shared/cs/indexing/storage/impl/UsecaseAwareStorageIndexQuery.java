package rocks.inspectit.shared.cs.indexing.storage.impl;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import rocks.inspectit.shared.all.indexing.IUsecaseAwareStorageIndexQuery;
import rocks.inspectit.shared.cs.indexing.storage.IStorageTreeComponent;

/**
 * Extended index query that fits better when querying the {@link IStorageTreeComponent}.
 *
 * @author Ivan Senic
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Lazy
public class UsecaseAwareStorageIndexQuery extends StorageIndexQuery implements IUsecaseAwareStorageIndexQuery {
	

private long usecaseId;
private String usecaseDescription;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = (prime * result) + ((usecaseDescription == null) ? 0 : usecaseDescription.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			UsecaseAwareStorageIndexQuery other = (UsecaseAwareStorageIndexQuery) obj;
			if (usecaseDescription == null) {
				if (other.usecaseDescription != null) {
					return false;
				}
			} else if (!usecaseDescription.equals(other.usecaseDescription)) {
				return false;
			}
			
			if (usecaseId != other.usecaseId) {
				return false;
			}
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			ToStringBuilder toStringBuilder = new ToStringBuilder(this);
			toStringBuilder.append(super.toString());
			toStringBuilder.append("usecasedescription", usecaseDescription);
			toStringBuilder.append("usecaseId",usecaseId);
			return toStringBuilder.toString();
		}

		@Override
		public long getUsecaseId() {
			return usecaseId;
		}

		@Override
		public String getUsecaseDescription() {
			return usecaseDescription;
		}
		
		public void  setUsecaseDescription(String description){
			this.usecaseDescription = description;
		}
		
		public void setUsecaseId(long usecaseId) {
			this.usecaseId = usecaseId;
		}
		
	}



