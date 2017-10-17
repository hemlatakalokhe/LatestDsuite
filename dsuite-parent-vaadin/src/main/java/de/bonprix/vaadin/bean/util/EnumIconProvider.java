package de.bonprix.vaadin.bean.util;

import com.vaadin.server.Resource;

/**
 * Interface used to being able to extract a Icon from an enum.
 *
 * @param <E> ENUM
 */
public interface EnumIconProvider<E extends Enum<?>> {

    Resource getIcon(final E theEnum);

    Class<E> getModelType();
}
