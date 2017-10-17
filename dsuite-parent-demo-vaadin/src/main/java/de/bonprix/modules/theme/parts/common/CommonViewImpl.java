package de.bonprix.modules.theme.parts.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import de.bonprix.VaadinUI;
import de.bonprix.modules.theme.ThemeViewImpl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.theme.DSuiteTheme;

@SpringView(parentName = ThemeViewImpl.VIEW_NAME, name = CommonViewImpl.VIEW_NAME, ui = { VaadinUI.class })
public class CommonViewImpl extends AbstractMvpView<CommonPresenter> implements CommonView {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonViewImpl.class);

	public static final String VIEW_NAME = "COMMON_PARTS";

	@Override
	protected void initializeUI() {
		setCompositionRoot(FluentUI.vertical()
			.margin()
			.add(FluentUI.label()
				.style(DSuiteTheme.LABEL_H1)
				.value("Common UI Elements")
				.get())
			.add(FluentUI.vertical()
				.widthFull()
				.spacing()
				.add(getLoadingIndicators())
				.add(getTooltips())
				.get())
			.get());
	}

	private Panel getLoadingIndicators() {
		return FluentUI.panel()
			.caption("Loading Indicator")
			.content(FluentUI.vertical()
				.spacing()
				.margin()
				.add(FluentUI.label()
					.caption("Spinner")
					.valueKey("The theme also provides a mixin that you can use to include a spinner anywhere in your application. The button below reveals a Label with a custom style name, for which the spinner mixin is added.")
					.get())
				.add(FluentUI.horizontal()
					.spacing()
					.add(FluentUI.button()
						.captionKey("SHOW_SPINNER")
						.onClick(event -> ((Layout) event.getComponent()
							.getParent()).replaceComponent(	event.getComponent(),
															FluentUI.spinner()
																.get()))
						.get())
					.add(FluentUI.button()
						.captionKey("SHOW_AUTOMATIC_LOADING_SPINNER")
						.onClick(e -> {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException exc) {
								CommonViewImpl.LOGGER.error(exc.getLocalizedMessage());
								Thread.currentThread()
									.interrupt();
							}
						})
						.get())
					.get())
				.get())
			.get();
	}

	Panel getTooltips() {
		return FluentUI.panel()
			.caption("Tooltips")
			.content(FluentUI.vertical()
				.spacing()
				.margin()
				.style(DSuiteTheme.LAYOUT_HORIZONTAL_WRAPPING)
				.add(FluentUI.label()
					.valueKey("Try out different tooltips/descriptions by hovering over the labels.")
					.get())
				.add(FluentUI.horizontal()
					.spacing()
					.add(FluentUI.label()
						.valueKey("SIMPLE")
						.descriptionKey("Simple tooltip message")
						.get())
					.add(FluentUI.label()
						.valueKey("LONG")
						.descriptionKey("Long tooltip message. Inmensae subtilitatis, obscuris et malesuada fames. Salutantibus vitae elit libero, a pharetra augue.")
						.get())
					.add(FluentUI.label()
						.valueKey("HTML_TOOLTIP")
						.descriptionKey("<div><h1>Ut enim ad minim veniam, quis nostrud exercitation</h1><p><span>Morbi fringilla convallis sapien, id pulvinar odio volutpat.</span> <span>Vivamus sagittis lacus vel augue laoreet rutrum faucibus.</span> <span>Donec sed odio operae, eu vulputate felis rhoncus.</span> <span>At nos hinc posthac, sitientis piros Afros.</span> <span>Tu quoque, Brute, fili mi, nihil timor populi, nihil!</span></p><p><span>Gallia est omnis divisa in partes tres, quarum.</span> <span>Praeterea iter est quasdam res quas ex communi.</span> <span>Cum ceteris in veneratione tui montes, nascetur mus.</span> <span>Quam temere in vitiis, legem sancimus haerentia.</span> <span>Idque Caesaris facere voluntate liceret: sese habere.</span></p></div>")
						.get())
					.add(FluentUI.label()
						.valueKey("WITH_A_LONG_ERROR_MESSAGE")
						.descriptionKey("SIMPLE_TOOLTIP_MESSAGES")
						.componentError(new UserError(
								"<h2>Contra legem facit qui id facit quod lex prohibet <span>Tityre, tu patulae recubans sub tegmine fagi  dolor.</span> <span>Tityre, tu patulae recubans sub tegmine fagi  dolor.</span> <span>Prima luce, cum quibus mons aliud  consensu ab eo.</span> <span>Quid securi etiam tamquam eu fugiat nulla pariatur.</span> <span>Fabio vel iudice vincam, sunt in culpa qui officia.</span> <span>Nihil hic munitissimus habendi senatus locus, nihil horum?</span></p><p><span>Plura mihi bona sunt, inclinet, amari petere vellent.</span> <span>Integer legentibus erat a ante historiarum dapibus.</span> <span>Quam diu etiam furor iste tuus nos eludet?</span> <span>Nec dubitamus multa iter quae et nos invenerat.</span> <span>Quisque ut dolor gravida, placerat libero vel, euismod.</span> <span>Quae vero auctorem tractata ab fiducia dicuntur.</span></h2>",
								AbstractErrorMessage.ContentMode.HTML, ErrorLevel.CRITICAL))
						.get())
					.add(FluentUI.label()
						.valueKey("ERROR_MESSAGE_ONLY")
						.componentError(new UserError("Something terrible has happened"))
						.get())
					.get())
				.get())
			.get();
	}

}
