package de.bonprix.module.sample;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.UI;

import de.bonprix.VaadinUI;
import de.bonprix.base.demo.dto.Country;
import de.bonprix.base.demo.dto.Planperiod;
import de.bonprix.common.dummydata.DummyAssets;
import de.bonprix.showcase.cases.notification.NotificationFieldGroupBean;
import de.bonprix.vaadin.bean.field.BeanItemComboBoxMultiselect;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.model.MultiSizeImage;
import de.bonprix.vaadin.model.SimpleMultiSizeImage;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;
import de.bonprix.vaadin.provider.UiNotificationProvider;
import de.bonprix.vaadin.ui.ImageBrowser.ImageBrowser;
import de.bonprix.vaadin.ui.enums.ZoomType;

@SpringView(
    name = SampleViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class SampleViewImpl extends AbstractMvpView<SamplePresenter> implements SampleView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "Sample";

    private CheckBox checkBox;

    @Resource
    private transient UiNotificationProvider notificationProvider;

    private BeanItemComboBoxMultiselect<Country> countryComboBox;

    private static final String CAPTION = "Notification Title";
    private static final String MESSAGE = "A more informative message about what has happened. Nihil hic munitissimus habendi senatus locus, nihil horum? Inmensae subtilitatis, obscuris et malesuada fames. Hi omnes lingua, institutis, legibus inter se differunt.";

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initializeUI() {
        this.checkBox = new CheckBox("SHOW_INTERCEPTOR_DIALOG");

        final NotificationFieldGroupBean notificationFieldGroupBean = new NotificationFieldGroupBean();
        notificationFieldGroupBean.setCaption(SampleViewImpl.CAPTION);
        notificationFieldGroupBean.setMessage(SampleViewImpl.MESSAGE);
        notificationFieldGroupBean.setDelay(60);

        this.countryComboBox = new BeanItemComboBoxMultiselect<>(Country.class);
        this.countryComboBox.addValueChangeListener(event -> this.notificationProvider.showInfoMessageBox("U selected" + this.countryComboBox.getSelectedItem()
            .toString()));

        setCompositionRoot(FluentUI.vertical()
            .add(FluentUI.button()
                .caption("Show Info Message")
                .onClick(event -> this.notificationProvider.showInfoNotification(SampleViewImpl.CAPTION, SampleViewImpl.MESSAGE))
                .get())
            .add(FluentUI.button()
                .caption("Show Info messasge (Only Caption) ")
                .onClick(event -> this.notificationProvider.showInfoNotification(SampleViewImpl.CAPTION))
                .get())
            .add(FluentUI.button()
                .caption("Show Info Message Custom")
                .onClick(event -> this.notificationProvider.showInfoNotification(notificationFieldGroupBean.getCaption(),
                                                                                 notificationFieldGroupBean.getMessage(),
                                                                                 notificationFieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .caption("Show Error Message")
                .onClick(event -> this.notificationProvider.showErrorNotification(SampleViewImpl.CAPTION, SampleViewImpl.MESSAGE))
                .get())
            .add(FluentUI.button()
                .caption("Show Error Message Custom")
                .onClick(event -> this.notificationProvider.showErrorNotification(notificationFieldGroupBean.getCaption(),
                                                                                  notificationFieldGroupBean.getMessage(),
                                                                                  notificationFieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .caption("Show Warning Message")
                .onClick(event -> this.notificationProvider.showWarningNotification(SampleViewImpl.CAPTION, SampleViewImpl.MESSAGE))
                .get())
            .add(FluentUI.button()
                .caption("Show Warning Message Custom")
                .onClick(event -> this.notificationProvider.showWarningNotification(notificationFieldGroupBean.getCaption(),
                                                                                    notificationFieldGroupBean.getMessage(),
                                                                                    notificationFieldGroupBean.getDelay()))
                .get())
            .add(FluentUI.button()
                .caption("Image Browser")
                .onClick(event -> openImageBrowser(getImageUrls(), ZoomType.MAGNIFIER))
                .get())
            .add(FluentUI.button()
                .caption("Image Browser Slider")
                .onClick(event -> openImageBrowser(getImageUrls(), ZoomType.SLIDERZOOM))
                .get())
            .add(this.countryComboBox)
            .spacing(true)
            .get());
    }

    public void openImageBrowser(final List<MultiSizeImage> urlList, final ZoomType zoom) {
        final ImageBrowser browser = new ImageBrowser(urlList, zoom);
        browser.maxZoomSize(1850D);
        UI.getCurrent()
            .addWindow(browser);
    }

    @SuppressWarnings("rawtypes")
    public List getImageUrls() {
        final List<MultiSizeImage> imageUrls = new ArrayList<>();
        int count = 0;
        for (final String url : DummyAssets.getAssetUrls()) {
            final MultiSizeImage images = new SimpleMultiSizeImage("Image with some caption loooooooooooooooooooooooooong textttttttttttt" + (++count), url);
            imageUrls.add(images);
        }
        return imageUrls;
    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

    @Override
    public void setAllBeans(final List<Planperiod> beans) {
        //
    }

    @Override
    public void setAllCountryBeans(final List<Country> beans) {
        this.countryComboBox.addAllBeans(beans);
    }

}
