package de.bonprix.information;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.bonprix.dto.masterdata.SimpleApplication;
import de.bonprix.dto.masterdata.SimpleApplicationFetchOptions;
import de.bonprix.information.service.SimpleApplicationService;

@Component
public class ApplicationProviderImpl implements ApplicationProvider {

	@Resource(name = "dsuiteApplicationService")
	private SimpleApplicationService dsuiteApplicationService;

	@Value("${application.id}")
	protected Long applicationId;

	private SimpleApplication application;

	@Override
	public SimpleApplication getApplication() {
		if (this.application == null) {
			this.application = this.dsuiteApplicationService
				.findById(this.applicationId, new SimpleApplicationFetchOptions().withFetchAll());
		}
		return this.application;
	}

}
