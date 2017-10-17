package de.bonprix.model;

public class MagnifierImageUrl {

    private static final String IMAGE_URL_FRONT = "https://vaadin.com/vaadin-fw8-documentation-portlet/framework/layout/img/";
    // private static final String IMAGE_SIZE = "prev_m/";
    // private static final String IMAGE_SIZE_ZOOM = "prev_l/";

    private String imageUrlEnd = null;

    public MagnifierImageUrl(final String imageUrlEnd) {
        this.imageUrlEnd = imageUrlEnd;
    }

    // https://vaadin.com/vaadin-fw8-documentation-portlet/framework/layout/img/subwindow-basic.png
    public String getImageUrl() {
        return MagnifierImageUrl.IMAGE_URL_FRONT + this.imageUrlEnd;
    }

    public String getImageZoomUrl() {
        return MagnifierImageUrl.IMAGE_URL_FRONT + this.imageUrlEnd;
    }

}
