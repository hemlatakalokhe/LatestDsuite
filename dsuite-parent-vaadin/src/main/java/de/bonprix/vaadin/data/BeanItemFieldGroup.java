package de.bonprix.vaadin.data;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Field;

import de.bonprix.I18N;
import de.bonprix.vaadin.data.form.EnhancedFieldGroupFieldFactory;

/**
 * The beanItemFieldGroup is the enhanced fieldGroup for bonprix forms which
 * directly supports only bean items. The default fieldFactory is the
 * {@link EnhancedFieldGroupFieldFactory}.
 * 
 * @author cthiel
 *
 * @param <BEANTYPE>
 */
@SuppressWarnings("serial")
public class BeanItemFieldGroup<BEANTYPE> extends BeanFieldGroup<BEANTYPE> {

	public BeanItemFieldGroup(Class<BEANTYPE> beanType) {
		super(beanType);
		setFieldFactory(new EnhancedFieldGroupFieldFactory());
	}

	public BeanItemFieldGroup(Class<BEANTYPE> beanType, final BEANTYPE bean) {
		this(beanType);
		setBean(bean);
	}

	public BEANTYPE getBean() {
		return getItemDataSource().getBean();
	}

	public void setBean(final BEANTYPE bean) {
		super.setItemDataSource(new BeanItem<BEANTYPE>(bean));
	}

	@Override
	public Field<?> buildAndBind(final String captionKey, final Object propertyId) throws BindException {
		return super.buildAndBind(I18N.get(captionKey), propertyId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends Field> T buildAndBind(final String captionKey, final Object propertyId, final Class<T> fieldType)
			throws BindException {
		return super.buildAndBind(I18N.get(captionKey), propertyId, fieldType);
	}
}