package de.bonprix;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Bean for providing general application information or settings globally.
 *
 * @author cthiel
 * @date 14.11.2016
 *
 */
@Component
public class DatabaseApplicationInformation extends AbstractApplicationInformation {

	public static final String DATABASE_USERNAME = "DATABASE_USERNAME";
	public static final String DATABASE_URL = "DATABASE_URL";

	@Value("${spring.datasource.username}")
	private String databaseUsername = null;
	@Value("${spring.datasource.url}")
	private String databaseUrl = null;

	public DatabaseApplicationInformation() {
		super("DATABASE_APPLICATION_INFORMATION");

		addProvider(DatabaseApplicationInformation.DATABASE_USERNAME, this::getDatabaseUsername);
		addProvider(DatabaseApplicationInformation.DATABASE_URL, this::getDatabaseUrl);
	}

	/**
	 * Returns the current username of the database.
	 * 
	 * @return
	 */
	public String getDatabaseUsername() {
		return this.databaseUsername;
	}

	/**
	 * Returns the connect URL of the database.
	 * 
	 * @return
	 */
	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

}
