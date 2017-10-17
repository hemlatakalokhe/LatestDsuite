package de.bonprix.model;

public class CustomMagnifierImageUrl {

    private static final String IMAGE_URL_FRONT = "https://vaadin.com/vaadin-fw8-documentation-portlet/framework/layout/img/";

    // private static final String IMAGE_SIZE = "src/";
    // private static final String IMAGE_SIZE_ZOOM = "main/";
    // D:\Workspaces\DsuiteWorkspace\dsuite-parent-demo-vaadin\src\main\webapp\cat.jpg
    private String imageUrlEnd = null;

    public CustomMagnifierImageUrl(final String imageUrlEnd) {
        this.imageUrlEnd = imageUrlEnd;
    }

    public String getImageUrl() {
        return this.imageUrlEnd;
    }

    public String getImageZoomUrl() {
        return this.imageUrlEnd;
    }

}
