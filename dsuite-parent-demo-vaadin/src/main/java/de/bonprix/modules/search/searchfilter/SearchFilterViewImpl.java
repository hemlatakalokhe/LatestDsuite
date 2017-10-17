package de.bonprix.modules.search.searchfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.TextField;

import de.bonprix.vaadin.mvp.SpringViewComponent;
import de.bonprix.vaadin.mvp.view.searchfilter.AbstractMvpSearchFilterView;

@SpringViewComponent
public class SearchFilterViewImpl extends AbstractMvpSearchFilterView<SearchFilterPresenter, SearchFilterBean>
		implements SearchFilterView<SearchFilterPresenter, SearchFilterBean> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchFilterViewImpl.class);

	private TextField textTextField;
	private TextField subTextTextField;

	public SearchFilterViewImpl() {
		super(new SearchFilterBean());

		this.textTextField = buildAndBindTextField("text", "text");
		this.subTextTextField = buildAndBindTextField("subText", "subText");
	}

	@Override
	protected com.vaadin.ui.Component createPrimaryLayout() {
		return createDefaultLayout(this.textTextField);
	}

	@Override
	protected com.vaadin.ui.Component createSecondaryLayout() {
		return createDefaultLayout(this.subTextTextField);
	}

	@Override
	protected void submitForm(SearchFilterBean bean) {
		SearchFilterViewImpl.LOGGER.info(bean.toString() + "submitted");
	}

	@Override
	protected void init() {
		// empty
	}

}
