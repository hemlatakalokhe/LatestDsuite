package de.bonprix.model;

public class CustomMagnifierImageUrl {

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
