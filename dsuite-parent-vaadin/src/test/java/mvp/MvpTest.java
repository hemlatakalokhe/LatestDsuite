package mvp;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.demo.mvp.productive.DummyService;
import de.bonprix.demo.mvp.productive.Presenter;
import de.bonprix.demo.mvp.productive.ViewImpl;
import de.bonprix.vaadin.eventbus.EventBus;

public class MvpTest extends BaseConfiguredUnitTest {

	@InjectMocks
	ViewImpl view;

	@Mock
	Presenter presenter;

	@Mock
	private DummyService dummyService;

	@Mock
	private EventBus eventbus;

	@Test
	public void testMvpWiring() {
		Assert.assertNotNull(this.view);
		Assert.assertNotNull(this.presenter);
		Assert.assertNotNull(this.dummyService);

		Mockito	.verify(this.presenter)
				.setView(this.view);
		Mockito	.verify(this.presenter)
				.init();
	}

}
