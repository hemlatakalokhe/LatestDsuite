package de.bonprix.common.icons;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;

import de.bonprix.model.enums.GridStatus;
import de.bonprix.vaadin.bean.util.EnumIconProvider;

public class GridDemoStatusIconProvider implements EnumIconProvider<GridStatus> {

    @Override
    public Resource getIcon(final GridStatus theEnum) {
        return new ExternalResource(theEnum.getIconName());
    }

    @Override
    public Class<GridStatus> getModelType() {
        return GridStatus.class;
    }

}
