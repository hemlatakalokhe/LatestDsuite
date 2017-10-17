package de.bonprix.security;

import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.testng.annotations.Test;

import de.bonprix.dto.masterdata.SimpleApplication;
import de.bonprix.dto.masterdata.builder.SimpleApplicationBuilder;
import de.bonprix.information.ApplicationProvider;
import de.bonprix.information.ApplicationProviderImpl;
import de.bonprix.information.service.SimpleApplicationService;

public class ApplicationProviderImplTest {

	@Test
	public void getApplicationTest() throws IllegalArgumentException, IllegalAccessException {
		long applicationId = 1L;

		SimpleApplicationService applicationServiceMock = Mockito.mock(SimpleApplicationService.class);

		ApplicationProvider applicationProvider = new ApplicationProviderImpl();
		MemberModifier.field(ApplicationProviderImpl.class, "dsuiteApplicationService")
			.set(applicationProvider, applicationServiceMock);
		MemberModifier.field(ApplicationProviderImpl.class, "applicationId")
			.set(applicationProvider, applicationId);

		applicationProvider.getApplication();

		Mockito.verify(applicationServiceMock)
			.findById(Mockito.eq(applicationId), Mockito.any());
	}

	@Test
	public void getApplicationWithInitializedApplicationTest() throws IllegalArgumentException, IllegalAccessException {
		SimpleApplication application = new SimpleApplicationBuilder().withId(1L)
			.build();

		SimpleApplicationService applicationServiceMock = Mockito.mock(SimpleApplicationService.class);

		ApplicationProvider applicationProvider = new ApplicationProviderImpl();
		MemberModifier.field(ApplicationProviderImpl.class, "dsuiteApplicationService")
			.set(applicationProvider, applicationServiceMock);
		MemberModifier.field(ApplicationProviderImpl.class, "application")
			.set(applicationProvider, application);

		applicationProvider.getApplication();

		Mockito.verify(applicationServiceMock, Mockito.never())
			.findById(Mockito.anyLong(), Mockito.any());
	}
}
