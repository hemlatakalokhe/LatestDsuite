package de.bonprix.showcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Tree;

import de.bonprix.I18N;
import de.bonprix.VaadinUI;
import de.bonprix.vaadin.fluentui.FluentUI;
import de.bonprix.vaadin.mvp.view.regular.AbstractMvpView;

@SpringView(
    name = ShowCaseViewImpl.VIEW_NAME,
    ui = { VaadinUI.class })
public class ShowCaseViewImpl extends AbstractMvpView<ShowCasePresenter> implements ShowCaseView {

    private static final long serialVersionUID = 2688782241672861374L;

    public static final String VIEW_NAME = "SHOWCASE";

    private HorizontalLayout showCaseComponentWrapper;
    private HorizontalLayout samplerLayout;

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    public void initialize() {
        final List<ShowcaseWrapper> showcaseWrappers = getShowcaseWrappers();

        final Tree showcaseTree = getShowCaseTree(showcaseWrappers);
        this.showCaseComponentWrapper = FluentUI.horizontal()
            .sizeFull()
            .get();

        final BeanItemContainer<ShowcaseWrapper> optionsDataSource = new BeanItemContainer<>(ShowcaseWrapper.class, showcaseWrappers);
        optionsDataSource.sort(new Object[] { "title" }, new boolean[] { true });

        final Label linkLabel = new Label(I18N.get("LINK_VAADIN_SAMPLER_LABEL"));

        final Link vaadinSamplerLink = new Link("vaadin.com/sampler", new ExternalResource("https://demo.vaadin.com/sampler/"));

        this.samplerLayout = FluentUI.horizontal()
            .add(linkLabel)
            .add(vaadinSamplerLink)
            .spacing()
            .get();

        setCompositionRoot(FluentUI.horizontal()
            .sizeFull()
            .margin()
            .spacing()
            .add(FluentUI.vertical()
                .add(this.samplerLayout)
                .add(FluentUI.beanItemComboBox(ShowcaseWrapper.class)
                    .captionKey("SEARCH")
                    .onSelectionChange(event -> {
                        // Open example on the right side
                        addShowCase(event.getSelectedItem());
                        showcaseTree.select(event.getSelectedItem()
                            .getTitle());

                    })
                    .itemCaptionGenerator(ShowcaseWrapper::getTitle)
                    .inputPromptKey("SEARCH_AFTER")
                    .widthFull()
                    .filteringMode(FilteringMode.CONTAINS)
                    .nullSelectionAllowed(false)
                    .containerDataSource(optionsDataSource)
                    .get())
                .add(showcaseTree, 1)
                .spacing()
                .width(300, Unit.PIXELS)
                .get())
            .add(this.showCaseComponentWrapper, 1f)
            .get());
        setSizeFull();
    }

    @Override
    public boolean equals(final Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private Tree getShowCaseTree(final List<ShowcaseWrapper> showcaseWrappers) {

        final Tree showcaseTree = new Tree();

        final Map<String, ShowcaseWrapper> treeMap = new HashMap<>();

        if (showcaseWrappers != null && !showcaseWrappers.isEmpty()) {
            showcaseTree.removeAllItems();

            // Add Data as root items in the tree.
            for (final ShowcaseWrapper wrapper : showcaseWrappers) {
                showcaseTree.addItem(wrapper.getParentTitle());

                if (wrapper.getTitle() == null) {
                    // The Mother has no child so make it a leaf.
                    showcaseTree.setChildrenAllowed(wrapper.getParentTitle(), false);
                    continue;
                }

                // Put ShowcaseWrapper to Map
                treeMap.put(wrapper.getTitle(), wrapper);

                // Add children under the mother.
                showcaseTree.addItem(wrapper.getTitle());

                // Set it to be a child.
                showcaseTree.setParent(wrapper.getTitle(), wrapper.getParentTitle());

                // Make the child look like leaves.
                showcaseTree.setChildrenAllowed(wrapper.getTitle(), false);

                // Expand the subtree.
                showcaseTree.expandItemsRecursively(wrapper.getParentTitle());
            }

            // TreeClickListener
            showcaseTree.addItemClickListener(event -> {
                if (treeMap.containsKey(event.getItemId())) {
                    addShowCase(treeMap.get(event.getItemId()));
                }
            });
        }

        return showcaseTree;
    }

    // Load the showcasewrappers from applicationcontext
    private List<ShowcaseWrapper> getShowcaseWrappers() {
        final ArrayList<ShowcaseWrapper> showcaseWrappers = new ArrayList<>(this.applicationContext.getBeansOfType(ShowcaseWrapper.class)
            .values());
        Collections.sort(showcaseWrappers, (o1, o2) -> {
            final int compareParents = o1.getParentTitle()
                .compareTo(o2.getParentTitle());

            if (compareParents != 0) {
                return compareParents;
            }

            return o1.getTitle()
                .compareTo(o2.getTitle());
        });

        return showcaseWrappers;
    }

    private void addShowCase(final ShowcaseWrapper component) {
        if (component != null) {
            component.setSizeFull();
            ShowCaseViewImpl.this.showCaseComponentWrapper.removeAllComponents();
            ShowCaseViewImpl.this.showCaseComponentWrapper.addComponent(component);
        }
    }

    @Override
    public void enter(final ViewChangeEvent event) {
        // empty
    }

    @Override
    protected void initializeUI() {
        // empty
    }

}
