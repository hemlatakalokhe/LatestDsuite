package de.bonprix.statuswizard;

import org.junit.Test;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.vaadin.dialog.DialogConfiguration;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.ui.statuswizard.AbstractMvpStatusWizard;
import de.bonprix.vaadin.ui.statuswizard.StatusWizardMode;
import de.bonprix.vaadin.ui.statuswizard.WizardStepStatus;

public class StatusWizardTest extends BaseConfiguredUnitTest {

	private TestCountryStep stepMock;
	private BeanFieldGroup<TestCountry> fieldGroupMock;
	private TestCountry countryMock;

	private SampleStausWizard wizard;

	@BeforeMethod
	public void init() {

		this.stepMock = Mockito.mock(TestCountryStep.class);

		this.fieldGroupMock = Mockito.mock(BeanFieldGroup.class);
		this.countryMock = Mockito.mock(TestCountry.class);

		Mockito.when(this.stepMock.getFieldGroup())
			.thenReturn(this.fieldGroupMock);
		Mockito.when(this.stepMock.getBean())
			.thenReturn(this.countryMock);
	}

	@Test
	public void addStepWithEditModeTest() {

		this.wizard = new SampleStausWizard(new DialogConfigurationBuilder().withWidth(800)
			.withHeight(500)
			.build());

		this.wizard.setMode(StatusWizardMode.EDIT);
		TestCountryStep countystep = new TestCountryStep(TestCountry.class, new TestCountry("test", "test", null, 0),
				"Country", "Lorem ipsum dolor sit amet");
		this.wizard.addStep(countystep);

		Assert.assertEquals(this.wizard.getWizardStepList()
			.size(), 1);
		Assert.assertTrue(this.wizard.getCurrentStep()
			.getStepStatus() == WizardStepStatus.FINISHED);
		Assert.assertTrue(this.wizard.getCurrentStep()
			.getSecondStepStatus() == WizardStepStatus.FINISHED);
	}

	@Test
	public void addStepWithCreateModeTest() {

		this.wizard = new SampleStausWizard(new DialogConfigurationBuilder().withWidth(800)
			.withHeight(500)
			.build());

		this.wizard.setMode(StatusWizardMode.CREATE);
		TestCountryStep countystep = new TestCountryStep(TestCountry.class, new TestCountry("test", "test", null, 0),
				"Country", "Lorem ipsum dolor sit amet");
		this.wizard.addStep(countystep);

		Assert.assertEquals(this.wizard.getWizardStepList()
			.size(), 1);
		Assert.assertTrue(this.wizard.getCurrentStep()
			.getStepStatus() == WizardStepStatus.EMPTY);
		Assert.assertTrue(this.wizard.getCurrentStep()
			.getSecondStepStatus() == WizardStepStatus.EMPTY);
	}

	@Test
	public void finisheCurrentStepTest() throws CommitException {

		this.wizard = new SampleStausWizard(new DialogConfigurationBuilder().withWidth(800)
			.withHeight(500)
			.build());

		this.wizard.setMode(StatusWizardMode.CREATE);
		TestCountryStep countystep = new TestCountryStep(TestCountry.class, new TestCountry("test", "test", null, 0),
				"Country", "Lorem ipsum dolor sit amet");
		this.wizard.addStep(countystep);
		this.wizard.finishCurrentStep();

		Assert.assertTrue(this.wizard.getCurrentStep()
			.getFieldGroup()
			.isValid());

		Assert.assertTrue(this.wizard.getCurrentStep()
			.getStepStatus() == WizardStepStatus.FINISHED);
		Assert.assertTrue(this.wizard.getCurrentStep()
			.getSecondStepStatus() == WizardStepStatus.FINISHED);
	}

	@SuppressWarnings("serial")
	private class SampleStausWizard extends AbstractMvpStatusWizard<MvpDialogPresenter> {

		public SampleStausWizard(DialogConfiguration configuration, StatusWizardMode mode) {
			super(configuration, mode);
		}

		public SampleStausWizard(DialogConfiguration configuration) {
			super(configuration);
		}

		public SampleStausWizard() {
			super(new DialogConfigurationBuilder().withWidth(800)
				.withHeight(500)
				.build(), StatusWizardMode.EDIT);
		}
	}
}
