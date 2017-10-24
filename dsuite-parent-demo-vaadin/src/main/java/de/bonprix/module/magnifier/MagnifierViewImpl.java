package de.bonprix.module.magnifier;

import java.util.Arrays;
import java.util.List;

import org.vaadin.addons.magnifier.Magnifier;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import de.bonprix.VaadinUI;
import de.bonprix.model.CustomMagnifierImageUrl;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;
import de.bonprix.vaadin.navigator.NavigationRequest;

@SpringView(
    name = MagnifierViewImpl.VIEW_NAME,
    ui = { VaadinUI.class },
    isDefault = false,
    order = 30)
public class MagnifierViewImpl extends AbstractMvpView<MagnifierPresenter> implements MagnifierView {

    private static final long serialVersionUID = 2688782241672861374L;
    public static final String VIEW_NAME = "Magnifier";
    private CheckBox checkBox;

    private static final List<CustomMagnifierImageUrl> IMAGE_URLS = Arrays
        .asList(new CustomMagnifierImageUrl("https://www.rd.com/wp-content/uploads/sites/2/2016/02/06-train-cat-shake-hands.jpg"),
                new CustomMagnifierImageUrl("http://www.petmd.com/sites/default/files/4-meow-conversational-cat.gif"));

    public MagnifierViewImpl() {
        //
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    protected void initializeUI() {
        final Magnifier magnifier = new Magnifier();
        magnifier.setHeight(600, Unit.PIXELS);
        magnifier.setWidth(400, Unit.PIXELS);
        magnifier.setZoomFactor(1.5f);

        final VerticalLayout imageLayout = new VerticalLayout();
        imageLayout.setMargin(true);
        imageLayout.setSpacing(true);

        boolean firstTime = true;
        Image image = null;

        for (final CustomMagnifierImageUrl imageUrl : MagnifierViewImpl.IMAGE_URLS) {
            image = new Image(null, new ExternalResource(imageUrl.getImageUrl()));
            image.setImmediate(true);
            image.setHeight(200, Unit.PIXELS);
            image.setWidth(200, Unit.PIXELS);
            image.addClickListener(event -> {
                magnifier.setImageUrl(imageUrl.getImageUrl());
                magnifier.setZoomImageUrl(imageUrl.getImageZoomUrl());
            });
            imageLayout.addComponents(image);

            if (firstTime) {
                magnifier.setImageUrl(imageUrl.getImageUrl());
                magnifier.setZoomImageUrl(imageUrl.getImageZoomUrl());
                firstTime = false;
            }
        }

        final VerticalLayout magnifierLayout = new VerticalLayout();
        magnifierLayout.setStyleName("demoContentLayout");
        magnifierLayout.setSizeFull();
        magnifierLayout.addComponent(magnifier);
        magnifierLayout.setComponentAlignment(magnifier, Alignment.MIDDLE_CENTER);

        final HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
        hsplit.setFirstComponent(imageLayout);
        hsplit.setSecondComponent(magnifierLayout);

        hsplit.setSplitPosition(400, Unit.PIXELS);

        setCompositionRoot(FluentUI.vertical()
            .add(hsplit)
            .spacing(true)
            .get());

    }

    @Override
    public void checkCheckBox(final NavigationRequest request) {
        getPresenter().proceedCheckBox(this.checkBox.getValue(), request);
    }

}
