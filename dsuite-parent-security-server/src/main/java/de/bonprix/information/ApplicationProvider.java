package de.bonprix.information;

import de.bonprix.dto.masterdata.SimpleApplication;

/**
 * Spring bean helper class for providing information about the application
 * itself.
 *
 * @author thacht
 * @date 10.02.2017
 *
 */
public interface ApplicationProvider {

	/**
	 * gives back information about the current application.
	 * 
	 * @return application
	 */
	SimpleApplication getApplication();

}
