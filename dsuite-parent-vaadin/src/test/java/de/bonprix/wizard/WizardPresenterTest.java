package de.bonprix.wizard;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.vaadin.ui.Notification;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.wizard.productive.ExampleMvpWizardPresenter;
import de.bonprix.wizard.productive.ExampleMvpWizardViewImpl;
import de.bonprix.wizard.productive.WizardStepOne;
import de.bonprix.wizard.productive.WizardStepTwo;

public class WizardPresenterTest extends BaseConfiguredUnitTest {

	@InjectMocks
	private ExampleMvpWizardPresenter presenter;

	@Mock
	private ExampleMvpWizardViewImpl exampleMvpWizardViewImpl;

	@Test
	public void addWizardStepTest() {

		this.presenter.addWizardStep(new WizardStepOne((step) -> Notification.show("start"),
				(step) -> Notification.show("finish")));

		assertThat(	this.presenter	.getWizards()
									.size(),
					equalTo(1));
		assertThat(	this.presenter	.getCurrentWizard()
									.getClass(),
					equalTo(WizardStepOne.class));
	}

	@Test
	public void stepChangedTest() {

		this.presenter	.getWizards()
						.clear();
		WizardStepOne mockStepOne = Mockito.mock(WizardStepOne.class);
		WizardStepTwo mockStepTwo = Mockito.mock(WizardStepTwo.class);

		this.presenter	.getWizards()
						.add(mockStepOne);
		this.presenter	.getWizards()
						.add(mockStepTwo);

		this.presenter.setCurrentWizard(mockStepOne);

		this.presenter.stepChanged();

		Mockito	.verify(this.exampleMvpWizardViewImpl, Mockito.timeout(1))
				.setProgressBarValue(new Float(0.25));

		Mockito	.verify(this.exampleMvpWizardViewImpl, Mockito.times(1))
				.setStepPanelContent(mockStepOne.getContent());
	}

	@Test
	public void nextTest() {

		WizardStepOne mockStepOne = Mockito.mock(WizardStepOne.class);
		WizardStepTwo mockStepTwo = Mockito.mock(WizardStepTwo.class);

		this.presenter	.getWizards()
						.add(mockStepOne);
		this.presenter	.getWizards()
						.add(mockStepTwo);

		this.presenter.setCurrentWizard(mockStepOne);

		Mockito	.when(this.presenter.nextAllowed())
				.thenReturn(true);

		this.presenter.next();

		Mockito	.verify(this.presenter.getCurrentWizard())
				.onStepEnter();
	}

	@Test
	public void nextTestNextNotAllowed() {

		WizardStepOne mockStepOne = Mockito.mock(WizardStepOne.class);
		WizardStepTwo mockStepTwo = Mockito.mock(WizardStepTwo.class);

		this.presenter	.getWizards()
						.add(mockStepOne);
		this.presenter	.getWizards()
						.add(mockStepTwo);

		this.presenter.setCurrentWizard(mockStepOne);

		Mockito	.when(this.presenter.nextAllowed())
				.thenReturn(false);

		this.presenter.next();

		Mockito	.verify(this.presenter.getCurrentWizard(), Mockito.times(0))
				.onStepEnter();
	}

	@Test
	public void backTest() {

		WizardStepOne mockStepOne = Mockito.mock(WizardStepOne.class);
		WizardStepTwo mockStepTwo = Mockito.mock(WizardStepTwo.class);

		this.presenter	.getWizards()
						.add(mockStepOne);
		this.presenter	.getWizards()
						.add(mockStepTwo);

		this.presenter.setCurrentWizard(mockStepTwo);

		Mockito	.when(this.presenter.backAllowed())
				.thenReturn(true);

		this.presenter.back();

		Mockito	.verify(this.presenter.getCurrentWizard())
				.onStepEnter();
	}

	@Test
	public void backTestBackNotAllowed() {

		WizardStepOne mockStepOne = Mockito.mock(WizardStepOne.class);
		WizardStepTwo mockStepTwo = Mockito.mock(WizardStepTwo.class);

		this.presenter	.getWizards()
						.add(mockStepOne);
		this.presenter	.getWizards()
						.add(mockStepTwo);

		this.presenter.setCurrentWizard(mockStepTwo);

		Mockito	.when(this.presenter.backAllowed())
				.thenReturn(false);

		this.presenter.back();

		Mockito	.verify(this.presenter.getCurrentWizard(), Mockito.times(0))
				.onStepEnter();
	}
}
