package de.bonprix.module.style.dialogview.save;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import de.bonprix.base.demo.dto.Client;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Season;
import de.bonprix.base.demo.dto.Style;
import de.bonprix.base.demo.dto.builder.StyleBuilder;
import de.bonprix.base.demo.service.ClientService;
import de.bonprix.base.demo.service.CountryService;
import de.bonprix.base.demo.service.SeasonService;
import de.bonprix.base.demo.service.StyleService;
import de.bonprix.model.DialogEvent;
import de.bonprix.vaadin.eventbus.EventBus;
import de.bonprix.vaadin.eventbus.EventHandler;
import de.bonprix.vaadin.mvp.SpringPresenter;
import de.bonprix.vaadin.mvp.dialog.AbstractMvpDialogPresenter;

@SuppressWarnings("rawtypes")
@SpringPresenter
public class SaveMvpDialogPresenter  extends AbstractMvpDialogPresenter<SaveMvpDialogView>
implements SaveMvpDialogView.Presenter{

    @Resource
    private EventBus localEventBus;

    @Resource
    private CountryService countryService;

    @Resource
    private SeasonService seasonService;

    @Resource
    private ClientService clientService;

    @Resource
    private StyleService styleService;

    @PostConstruct
    public void initialize(){
        this.localEventBus.addHandler(this);
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void init(final DialogEvent event){
        getView().setData(countries(), seasons(), clients());
    }

    public List<Country> countries(){
        return this.countryService.findAll();
    }

    public List<Season> seasons(){
        return this.seasonService.findAll();
    }

    public List<Client> clients(){
        return this.clientService.findAll();
    }

    public void saveStyleData(final String styleNo,final String styleDesc,final Country country,final Season season,final Client client){
        final Style style=new StyleBuilder().withStyleNo(styleNo).withDesc(styleDesc).withCountry(country).withClient(client).withSeason(season).build();
        this.styleService.saveStyle(style);
    }

}
