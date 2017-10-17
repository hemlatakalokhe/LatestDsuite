package de.bonprix.vaadin.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.sliderpanel.SliderPanel;
import org.vaadin.sliderpanel.SliderPanelBuilder;
import org.vaadin.sliderpanel.client.SliderMode;
import org.vaadin.sliderpanel.client.SliderPanelListener;
import org.vaadin.sliderpanel.client.SliderTabPosition;

import com.ejt.vaadin.sizereporter.ComponentResizeEvent;
import com.ejt.vaadin.sizereporter.ComponentResizeListener;
import com.ejt.vaadin.sizereporter.SizeReporter;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import com.vaadin.ui.Table;
import de.bonprix.vaadin.fluentui.FluentUI;

public class SliderPanelNoLayover extends CustomComponent {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SliderPanelNoLayover.class);

	private double fixedContentSize = 0;
	private double percentage;
	private Layout root = null;
	private Component mainLayout;
	private SliderPanel sliderPanel = null;
	private static int SLIDER_EXPEND_ELEMENT_HEIGHT = 55;
	private SliderMode sliderMode;
	private SizeReporter sizeReporter;
	private Unit unit = Unit.PIXELS;

	public SliderPanelNoLayover(final Component mainLayout, final Component sliderLayout, final SliderMode sliderMode,
			final SliderTabPosition tabPosition, final String caption, final String color, final float pixel,
			final float percentage, final Unit unit) {

		setSizeFull();
		this.fixedContentSize = pixel;
		this.percentage = percentage;
		this.sliderMode = sliderMode;
		this.mainLayout = mainLayout;
		this.unit = unit;
		if (Unit.PERCENTAGE.equals(unit)) {
			this.sliderPanel = new SliderPanelBuilder(sliderLayout).mode(sliderMode)
				.tabPosition(tabPosition)
				.caption(caption)
				.style(color)
				.build();
		} else {
			this.sliderPanel = new SliderPanelBuilder(sliderLayout).mode(sliderMode)
				.tabPosition(tabPosition)
				.caption(caption)
				.style(color)
				.fixedContentSize((int) pixel)
				.build();
		}

		this.sliderPanel.setAnimationDuration(0);

		// sliderMode
		if (SliderMode.TOP.equals(sliderMode)) {
			this.root = FluentUI.vertical()
				.sizeFull()
				.add(this.sliderPanel, 0f)
				.spacing()
				.add(mainLayout, 1f, Alignment.BOTTOM_CENTER)
				.get();
		} else if (SliderMode.LEFT.equals(sliderMode)) {
			this.root = FluentUI.horizontal()
				.sizeFull()
				.add(this.sliderPanel, 0f)
				.spacing()
				.add(mainLayout, 1f, Alignment.MIDDLE_RIGHT)
				.get();
		} else if (SliderMode.BOTTOM.equals(sliderMode)) {
			this.root = FluentUI.vertical()
				.sizeFull()
				.add(mainLayout, 1f, Alignment.TOP_CENTER)
				.spacing()
				.add(this.sliderPanel, 0f)
				.get();
		} else if (SliderMode.RIGHT.equals(sliderMode)) {
			this.root = FluentUI.horizontal()
				.sizeFull()
				.add(mainLayout, 1f, Alignment.MIDDLE_LEFT)
				.spacing()
				.add(this.sliderPanel, 0f)
				.get();
		}

		this.sizeReporter = new SizeReporter((AbstractComponent) this.root);

		this.sizeReporter.addResizeListener(new ComponentResizeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void sizeChanged(ComponentResizeEvent event) {
				setFixedContentSize(Unit.PERCENTAGE.equals(SliderPanelNoLayover.this.unit)
						? SliderPanelNoLayover.this.percentage : SliderPanelNoLayover.this.fixedContentSize,
									SliderPanelNoLayover.this.unit);
			}
		});

		this.sliderPanel.addListener(new SliderPanelListener() {

			@Override
			public void onToggle(final boolean expand) {
				refreshMainLayout(expand);
			}
		});

		setCompositionRoot(this.root);
	}

	public void setFixedContentSize(double value, Unit unit) {
		this.unit = unit;
		if (Unit.PERCENTAGE.equals(this.unit)) {
			this.percentage = value;
			this.fixedContentSize = calcPixel(	this.percentage, this.sizeReporter.getWidth(),
												this.sizeReporter.getHeight());
		} else {
			this.fixedContentSize = value;
		}

		this.sliderPanel.setFixedContentSize((int) this.fixedContentSize);

		// hack to reload slider size through client
		if (this.sliderPanel.isExpanded()) {
			this.sliderPanel.setExpanded(false, false);
			this.sliderPanel.setExpanded(true, false);
		}

		refreshMainLayout(this.sliderPanel.isExpanded());
	}

	private double getMainLayoutWidth() {
		if (Unit.PERCENTAGE.equals(SliderPanelNoLayover.this.unit)) {
			double mainLayoutWidth = this.sizeReporter.getWidth() - SliderPanelNoLayover.this.fixedContentSize
					- SliderPanelNoLayover.SLIDER_EXPEND_ELEMENT_HEIGHT;
			return (mainLayoutWidth / this.sizeReporter.getWidth()) * 100;
		}

		return this.sizeReporter.getWidth() - SliderPanelNoLayover.this.fixedContentSize
				- SliderPanelNoLayover.SLIDER_EXPEND_ELEMENT_HEIGHT;
	}

	private double getMainLayoutHeight() {
		if (Unit.PERCENTAGE.equals(SliderPanelNoLayover.this.unit)) {
			double mainLayoutHeight = this.sizeReporter.getHeight() - SliderPanelNoLayover.this.fixedContentSize
					- SliderPanelNoLayover.SLIDER_EXPEND_ELEMENT_HEIGHT;
			return (mainLayoutHeight / this.sizeReporter.getHeight()) * 100;
		}
		return this.sizeReporter.getHeight() - SliderPanelNoLayover.this.fixedContentSize
				- SliderPanelNoLayover.SLIDER_EXPEND_ELEMENT_HEIGHT;
	}

	private int calcPixel(double percentage, float sliderWidth, float sliderHeight) {
		if (SliderMode.RIGHT.equals(this.sliderMode) || SliderMode.LEFT.equals(this.sliderMode)) {
			return (int) ((sliderWidth / 100) * percentage);
		}
		return (int) ((sliderHeight / 100) * percentage);
	}

	private void refreshMainLayout(boolean expand) {
		if (expand) {
			if (SliderMode.TOP.equals(this.sliderMode) || SliderMode.BOTTOM.equals(this.sliderMode)) {
				this.mainLayout.setHeight((float) getMainLayoutHeight(), this.unit);
			} else {
				this.mainLayout.setWidth((float) getMainLayoutWidth(), this.unit);
			}

			final Component content = this.sliderPanel.getContent();

			if (containsTableOrGrid(content)) {
				this.sliderPanel.setContent(null);
				UI.getCurrent()
					.setPollInterval(100);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							SliderPanelNoLayover.LOGGER.error(e.getLocalizedMessage(), e);
							Thread.currentThread()
								.interrupt();
						}
						SliderPanelNoLayover.this.sliderPanel.setContent(content);
						UI.getCurrent()
							.setPollInterval(-1);
					}

				}).start();
			}
		} else {
			if (SliderMode.TOP.equals(this.sliderMode) || SliderMode.BOTTOM.equals(this.sliderMode)) {
				this.mainLayout.setHeight(100, Unit.PERCENTAGE);
			} else {
				this.mainLayout.setWidth(100, Unit.PERCENTAGE);
			}
		}
	}

	private boolean containsTableOrGrid(Component content) {
		if (content instanceof Grid || content instanceof Table) {
			return true;
		}

		if (content instanceof AbstractOrderedLayout) {
			AbstractOrderedLayout abstractOrderedLayout = (AbstractOrderedLayout) content;
			for (Component component : abstractOrderedLayout) {
				if (containsTableOrGrid(component)) {
					return true;
				}
			}
		}
		return false;
	}

	public SliderPanel getSliderPanel() {
		return this.sliderPanel;
	}
}
