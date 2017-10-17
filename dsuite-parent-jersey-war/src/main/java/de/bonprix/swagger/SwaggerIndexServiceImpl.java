package de.bonprix.swagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import de.bonprix.annotation.RestService;
import de.bonprix.swagger.model.SwaggerApiDto;
import de.bonprix.swagger.model.SwaggerRootDto;

/**
 * @author cthiel
 * @date 31.10.2016
 *
 */
@RestService
@Path("/apidocs")
public class SwaggerIndexServiceImpl {

    @Resource
    private ApplicationContext applicationContext;

    @Path("/service.json")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public SwaggerRootDto generateServiceJson() throws IOException, ClassNotFoundException {
        final SwaggerRootDto root = new SwaggerRootDto();
        root.setSwaggerVersion("1.2");
        root.setApiVersion("1");
        root.setBasePath("./");

        final List<SwaggerApiDto> apis = new ArrayList<>();

        // Find all REST services of this application
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestService.class));

        final List<String> implementedRestPaths = new ArrayList<>();
        for (final BeanDefinition bd : scanner.findCandidateComponents("de.bonprix")) {

            // Find the interfaces of the implemented WS
            final String[] interfaces = ((ScannedGenericBeanDefinition) bd).getMetadata()
                .getInterfaceNames();

            // Find all implemented paths, based on the interface annotations
            for (final String interfaceName : interfaces) {
                final Class<?> interfaceClass = Class.forName(interfaceName);
                final Path pathAnnotation = interfaceClass.getAnnotation(Path.class);

                if (pathAnnotation != null) {
                    implementedRestPaths.add(pathAnnotation.value());
                }
            }
        }

        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for (final String path : implementedRestPaths) {
            final org.springframework.core.io.Resource resource = resolver.getResource("/META-INF/resources/apidocs" + path + ".json");
            if (!resource.exists()) {
                continue;
            }
            final SwaggerApiDto api = new SwaggerApiDto();
            api.setPath("/" + resource.getFilename());
            apis.add(api);
        }

        root.setApis(apis);

        return root;
    }

}
