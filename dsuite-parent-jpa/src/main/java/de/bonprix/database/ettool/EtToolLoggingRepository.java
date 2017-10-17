package de.bonprix.database.ettool;

public interface EtToolLoggingRepository {
	void init();

	void setPrincipalId(Long principalId);

	Long getPrincipalId();

	void setApplicationId(Long applicationId);

	Long getApplicationId();

	void setPrincipalIdAndApplicationId(Long principalId, Long applicationId);
}
