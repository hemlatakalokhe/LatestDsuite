package de.bonprix.vaadin.bean.grid;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;

import de.bonprix.vaadin.bean.util.EnumIconProvider;

/**
 * Converts an enum to an icon to be rendered.
 *
 * @param <E>
 *            Enum
 */
public class EnumIconConverter<E extends Enum<?>> implements Converter<Resource, E> {
	private static final long serialVersionUID = 1L;

	private final EnumIconProvider<E> iconProvider;

	public EnumIconConverter(final EnumIconProvider<E> iconProvider) {
		super();
		this.iconProvider = iconProvider;
	}

	public EnumIconConverter(final Class<? extends EnumIconProvider<E>> iconProviderClass) {
		super();
		EnumIconProvider<E> iconProvider = null;
		try {
			iconProvider = iconProviderClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException("given iconprovider has no default constructor");
		}
		this.iconProvider = iconProvider;
	}

	@Override
	public E convertToModel(final Resource value, final Class<? extends E> targetType, final Locale locale)
			throws ConversionException {
		return null;
	}

	@Override
	public Resource convertToPresentation(final E value, final Class<? extends Resource> targetType,
			final Locale locale) throws ConversionException {
		return this.iconProvider.getIcon(value);
	}

	@Override
	public Class<E> getModelType() {
		return this.iconProvider.getModelType();
	}

	@Override
	public Class<Resource> getPresentationType() {
		return Resource.class;
	}

}
