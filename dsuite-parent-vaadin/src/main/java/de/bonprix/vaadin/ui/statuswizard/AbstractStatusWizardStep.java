package de.bonprix.vaadin.ui.statuswizard;

import java.util.List;
import java.util.stream.Collectors;

import org.vaadin.addons.scrollablepanel.ScrollablePanel;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

/**
 * The AbstractStatusWizardStep is a base class for statusWizardSteps.
 * 
 * 
 * @author dmut
 *
 */
public abstract class AbstractStatusWizardStep<BEANTYPE> implements StatusWizardStep {

	private final BEANTYPE bean;
	private String caption;
	private String explanation;
	private WizardStepStatus stepStatus = WizardStepStatus.EMPTY;
	private WizardStepStatus secondStepStatus = WizardStepStatus.EMPTY;
	private final ScrollablePanel layout;
	private BeanFieldGroup<BEANTYPE> fieldGroup;

	public AbstractStatusWizardStep(final Class<BEANTYPE> beanType, final BEANTYPE bean, final String caption,
			final String explanation) {
		super();
		this.fieldGroup = new BeanFieldGroup<>(beanType);
		getFieldGroup().setItemDataSource(bean);
		this.bean = bean;
		setCaption(caption);
		setExplanation(explanation);
		this.layout = new ScrollablePanel(this.layout());
		this.layout.setSizeFull();
	}

	public boolean hasMandatoryFields() {
		return this.getFieldGroup()
			.getFields()
			.stream()
			.anyMatch(Field::isRequired);
	}

	public List<Field> getOptionalFileds() {
		return this.getFieldGroup()
			.getFields()
			.stream()
			.filter(field -> !field.isRequired())
			.collect(Collectors.toList());
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public String getExplanation() {
		return this.explanation;
	}

	@Override
	public Component layout() {
		return null;
	}

	@Override
	public WizardStepStatus getStepStatus() {
		return this.stepStatus;
	}

	public void setCaption(final String caption) {
		this.caption = caption;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	@Override
	public void setStepStatus(final WizardStepStatus stepStatus) {
		this.stepStatus = stepStatus;
	}

	@Override
	public BEANTYPE getBean() {
		return this.bean;
	}

	@Override
	public BeanFieldGroup<BEANTYPE> getFieldGroup() {
		return this.fieldGroup;
	}

	public void setFieldGroup(final BeanFieldGroup<BEANTYPE> fieldGroup) {
		this.fieldGroup = fieldGroup;
	}

	@Override
	public ScrollablePanel getLayout() {
		return this.layout;
	}

	@Override
	public float getExpandRatioHeader() {
		return 0.2f;
	}

	@Override
	public float getExpandRatioLayout() {
		return 0.8f;
	}

	@Override
	public WizardStepStatus getSecondStepStatus() {
		return this.secondStepStatus;
	}

	@Override
	public void setSecondStepStatus(WizardStepStatus secondStepStatus) {
		this.secondStepStatus = secondStepStatus;
	}

}
