/**
 *
 */
package de.bonprix;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic class for providing application information.
 *
 * @author cthiel
 * @date 14.11.2016
 *
 */
public abstract class AbstractApplicationInformation implements ApplicationInformation {

    public static interface InformationProvider {
        Object provideInformation();
    }

    private final String i18nCaptionKey;

    public AbstractApplicationInformation(final String i18nCaptionKey) {
        this.i18nCaptionKey = i18nCaptionKey;
    }

    private final Map<String, InformationProvider> informationProviders = new HashMap<>();

    protected void addProvider(final String key, final InformationProvider provider) {
        this.informationProviders.put(key, provider);
    }

    @Override
    public Collection<String> getKeys() {
        return Collections.unmodifiableSet(this.informationProviders.keySet());
    }

    @Override
    public Object getValue(final String key) {
        if (!this.informationProviders.containsKey(key)) {
            return null;
        }
        return this.informationProviders.get(key)
            .provideInformation();
    }

    @Override
    public String getI18NCaptionKey() {
        return this.i18nCaptionKey;
    }

    @Override
    public boolean translateKeys() {
        return true;
    }
}
