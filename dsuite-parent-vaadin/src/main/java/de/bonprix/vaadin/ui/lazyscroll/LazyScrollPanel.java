package de.bonprix.vaadin.ui.lazyscroll;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.ui.Button;
import com.vaadin.util.ReflectTools;

import de.bonprix.vaadin.ui.lazyscroll.LazyObject.ScrollDimension;

public interface LazyScrollPanel {

	public Button getNextNavigator();

	public LazyObject getNext();

	public void selectNext();

	public Button getPreviousNavigator();

	public LazyObject getPrevious();

	public void selectPrevious();

	public void updateNavigatorImages();

	public void selectLazyObject(LazyObject obj);

	public void resetScrollPanel();

	public void addLazyObject(LazyObject obj);

	public ScrollDimension getDimension();

	public Integer getContentHeight(boolean caption);

	public Integer getContentWidth(boolean caption);

	public interface LazyObjectSelectedListener extends Serializable {
		public static final Method LAZY_SCROLL_PANEL_OBJECT_SELECTED_METHOD = ReflectTools.findMethod(LazyObjectSelectedListener.class,
				"lazyObjectSelected", LazyObjectSelectedEvent.class);

		public void lazyObjectSelected(LazyObjectSelectedEvent event);

	}

	public void addLazyObjectSelectedListener(LazyObjectSelectedListener listener);
	
	public void scrollToObject(LazyObject object);

}
