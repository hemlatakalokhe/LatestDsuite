/**
 *
 */
package de.bonprix;

import java.util.Collection;

/**
 * @author cthiel
 * @date 15.11.2016
 *
 */
public interface ApplicationInformation {

    /**
     * Returns a collection of all information keys available on this bean.
     *
     * @return
     */
    Collection<String> getKeys();

    /**
     * Returns the value of this information key.
     *
     * @param key the key
     * @return
     */
    Object getValue(String key);

    /**
     * Returns the I18N key for a caption.
     * 
     * @return
     */
    String getI18NCaptionKey();

    /**
     * If the keys of this collection are I18N keys and should be translated for UI purposes.
     * 
     * @return
     */
    boolean translateKeys();
}
