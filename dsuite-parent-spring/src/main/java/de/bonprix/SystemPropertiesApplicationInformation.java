package de.bonprix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

/**
 * An application information provider for all environment properties.
 * 
 * @author cthiel
 *
 */
@Component
public class SystemPropertiesApplicationInformation extends AbstractApplicationInformation {

    /**
     * Exclude certain system properties because they contain sensible data like credentials which should not be displayed to non-sysadmins.
     */
    private static final Set<String> EXCLUSIONS = new HashSet<>(Arrays.asList("HTTP_PROXY", "HTTPS_PROXY"));

    @Autowired
    private Environment environment;

    public SystemPropertiesApplicationInformation() {
        super("SYSTEM_PROPERTIES_APPLICATION_INFORMATION");
    }

    @PostConstruct
    public void post() {
        final Map<String, Object> map = new HashMap<>();
        for (final Iterator<PropertySource<?>> it = ((AbstractEnvironment) this.environment).getPropertySources()
            .iterator(); it.hasNext();) {
            final PropertySource<?> propertySource = it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }

        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            if (!EXCLUSIONS.contains(entry.getKey())) {
                addProvider(entry.getKey(), () -> map.get(entry.getKey()));
            }
        }
    }

    @Override
    public Collection<String> getKeys() {
        final List<String> keys = new ArrayList<>(super.getKeys());
        Collections.sort(keys);

        return keys;
    }

    @Override
    public boolean translateKeys() {
        return false;
    }
}
