/**
 *
 */
package de.bonprix.applicationinfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import de.bonprix.ApplicationInformation;
import de.bonprix.annotation.RestService;
import de.bonprix.service.appinfo.ApplicationInfoDto;
import de.bonprix.service.appinfo.ApplicationInfoService;

/**
 * @author cthiel
 * @date 16.11.2016
 *
 */
@RestService
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

    @Resource
    private List<ApplicationInformation> informationProviders;

    @Override
    public List<ApplicationInfoDto> get() {
        final List<ApplicationInfoDto> result = new ArrayList<>();

        for (final ApplicationInformation appInfo : this.informationProviders) {
            for (final String key : appInfo.getKeys()) {
                final ApplicationInfoDto dto = new ApplicationInfoDto();
                dto.setGroup(appInfo.getI18NCaptionKey());
                dto.setKey(key);
                dto.setValue(java.util.Objects.toString(appInfo.getValue(key), null));
                dto.setTranslateKey(appInfo.translateKeys());

                result.add(dto);
            }
        }

        return result;
    }

}
