package de.bonprix.vaadin.ui.lazyscroll;

import com.vaadin.ui.CssLayout;

public interface LazyObject {

    public enum ScrollDimension {
        Horizontal,
        Vertical
    }

    /**
     * a unique identifier of the LazyObject needed for equals methond in order to find spefic in list
     * 
     * @return the id
     */
    public long getIdentifier();

    /**
     * true when the component is rendered
     * 
     * @return if the component is rendered
     */
    public boolean isLoaded();

    public void setLoaded(final boolean value);

    /**
     * the main Layout of the LazyObject <br>
     * this will get the onLayoutClick Listener
     * 
     * @return the css base layout
     */
    public CssLayout getBaseLayout();

    /**
     * Position of the Object within the long scrollPanel in Pixel
     * 
     * @return the position
     */
    public int getPosition();

    /**
     * position of Object within the long scrollPanel in Pixel
     * 
     * @param position
     */
    public void setPosition(final int position);

    /**
     * calculated of Object needs to get generated or if its out of scope
     * 
     * @param currentPosition
     */
    public void loadImages(final int currentPosition);

    /**
     * here needs to get placed the main content stuff generation
     */
    public void generateContent();

    /**
     * change stlyeName on the baseLayout in order to display selected Object
     * 
     * @param active
     */
    public void highlightSelected(final boolean active);

    /**
     * used by navigator when u return null simple an image will get displayed
     * 
     * @return the previous image url
     */
    public String getPreviousImageUrl();

    /**
     * used by navigator when u return null simple an image will get displayed
     * 
     * @return the next image url
     */
    public String getNextImageUrl();

}
