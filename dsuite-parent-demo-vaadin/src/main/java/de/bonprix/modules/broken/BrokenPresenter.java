package de.bonprix.modules.broken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bonprix.exception.BrokenViewExeption;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpViewPresenter;

@SpringPresenter
public class BrokenPresenter extends AbstractMvpViewPresenter<BrokenViewImpl> implements BrokenView.Presenter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrokenPresenter.class);

	@Override
	public void init() {
		// init
	}

	@Override
	public void onViewEnter() {
		throw new BrokenViewExeption("blub");
	}

}