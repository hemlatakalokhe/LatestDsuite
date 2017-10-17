package de.bonprix.module.style.dialogview.save;

import java.util.List;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.vaadin.mvp.dialog.MvpDialogPresenter;
import de.bonprix.vaadin.mvp.dialog.MvpDialogView;

public interface SaveMvpDialogView <PRESENTER extends SaveMvpDialogView.Presenter>
extends MvpDialogView<PRESENTER>{
    interface Presenter extends MvpDialogPresenter {

    }

    void setData(List<Country> countries,List<Season> seasons,List<Client> clients);
}
