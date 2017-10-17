/**
 *
 */
package de.bonprix.service.appinfo;

/**
 * Some dto class for providing basic statistical information about an application. This is some simple JMX.
 * 
 * @author cthiel
 * @date 16.11.2016
 *
 */
public class ApplicationInfoDto {

    private String group;
    private String key;
    private String value;

    private boolean translateKey;

    public String getGroup() {
        return this.group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public boolean translateKey() {
        return this.translateKey;
    }

    public void setTranslateKey(final boolean translateKey) {
        this.translateKey = translateKey;
    }

}
