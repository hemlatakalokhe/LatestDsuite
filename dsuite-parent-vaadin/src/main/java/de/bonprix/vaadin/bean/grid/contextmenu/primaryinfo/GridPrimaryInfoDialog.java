package de.bonprix.vaadin.bean.grid.contextmenu.primaryinfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.SelectionMode;

import de.bonprix.I18N;
import de.bonprix.vaadin.bean.grid.BeanItemGrid;
import de.bonprix.vaadin.dialog.AbstractBaseDialog;
import de.bonprix.vaadin.dialog.DialogConfigurationBuilder;

/**
 * @author a.bogari
 * 
 *         The Class GridPrimaryInfoDialog. This model is used for creating the
 *         primary info dialog .
 */
@SuppressWarnings("serial")
public class GridPrimaryInfoDialog<BEANTYPE> extends AbstractBaseDialog {

	/** The bean grid. */
	private BeanItemGrid<KeyValuePair> keyValuePairGrid;

	/**
	 * Instantiates a new grid primary info dialog.
	 *
	 * @param models
	 *            the models
	 */
	public GridPrimaryInfoDialog(BEANTYPE bean, Class<? extends Object> clazz) {
		super(new DialogConfigurationBuilder()	.withHeadline(I18N.get("PRIMARY_INFORMATION"))
												.withWidth(400)
												.withHeight(413)
												.build());

		this.keyValuePairGrid = createKeyValuePairGrid();
		this.keyValuePairGrid.addAllBeans(getKeyValues(bean, clazz));
	}

	/**
	 * Gets the grid primary info models.
	 * 
	 * @bydefault show the all properties from the bean. if user wants to show
	 *            the specific grid records then provide this method
	 *            implemention.
	 * @param objectInstance
	 *            the object instance
	 * @return the grid primary info models
	 */
	private List<KeyValuePair> getKeyValues(BEANTYPE bean, Class<? extends Object> clazz) {
		List<KeyValuePair> models = new ArrayList<>();
		try {
			List<Field> allFields = getAllFields(clazz);
			for (Field field : allFields) {
				field.setAccessible(true);
				models.add(new KeyValuePair(field.getName(), field.get(bean)));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return models;
	}

	/**
	 * Gets the all fields.
	 *
	 * @param fields
	 *            the fields
	 * @param type
	 *            the type
	 * @return the all fields
	 */
	private List<Field> getAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<>();
		return getAlLFieldsRecursive(fields, type);
	}

	private List<Field> getAlLFieldsRecursive(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields = getAlLFieldsRecursive(fields, type.getSuperclass());
		}

		return fields;
	}

	private BeanItemGrid<KeyValuePair> createKeyValuePairGrid() {
		BeanItemGrid<KeyValuePair> keyValuePairGrid = new BeanItemGrid<KeyValuePair>(KeyValuePair.class);

		keyValuePairGrid.setSelectionMode(SelectionMode.SINGLE);
		keyValuePairGrid.setColumnReorderingAllowed(false);

		keyValuePairGrid.setColumns("caption", "value");
		keyValuePairGrid.setColumnHeaderKeys("CAPTION", "VALUE");

		keyValuePairGrid.setSizeFull();

		return keyValuePairGrid;
	}

	@Override
	protected Component layout() {
		return this.keyValuePairGrid;
	}

	public class KeyValuePair {

		private final String caption;

		private final Object value;

		public KeyValuePair(String caption, Object value) {
			this.caption = caption == null ? ""
					: I18N.get(CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, caption));
			this.value = value;
		}

		public String getCaption() {
			return this.caption;
		}

		public Object getValue() {
			return this.value;
		}

	}
}