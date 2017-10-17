/**
 *
 */
package de.bonprix.swagger.model;

import java.util.List;

/**
 * @author cthiel
 * @date 31.10.2016
 *
 */
public class SwaggerRootDto {

    private String swaggerVersion;
    private String apiVersion;
    private String basePath;
    private List<SwaggerApiDto> apis;

    public String getSwaggerVersion() {
        return this.swaggerVersion;
    }

    public void setSwaggerVersion(final String swaggerVersion) {
        this.swaggerVersion = swaggerVersion;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(final String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(final String basePath) {
        this.basePath = basePath;
    }

    public List<SwaggerApiDto> getApis() {
        return this.apis;
    }

    public void setApis(final List<SwaggerApiDto> apis) {
        this.apis = apis;
    }

}
