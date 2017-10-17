package swagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Path;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.swagger.SwaggerIndexServiceImpl;
import de.bonprix.swagger.model.SwaggerApiDto;
import de.bonprix.swagger.model.SwaggerRootDto;

/**
 * @author tsologub
 */
public class SwaggerIndexServiceImplTest extends BaseConfiguredUnitTest {

    @InjectMocks
    private SwaggerIndexServiceImpl swaggerIndexService;

    @Mock
    private ClassPathScanningCandidateComponentProvider scanner;

    @Mock
    private PathMatchingResourcePatternResolver resolver;

    @Mock
    private ClassLoader classLoader;

    @Mock
    private Path path;

    @Mock
    private ApplicationContext applicationContext;

    @Test
    public void generateServiceJsonTest() throws IOException, ClassNotFoundException {
	SwaggerRootDto root = new SwaggerRootDto();
	root.setSwaggerVersion("42");
	root.setApiVersion("42");
	root.setBasePath("./");
	Mockito.doNothing().when(this.scanner).addIncludeFilter(Mockito.any(AnnotationTypeFilter.class));

	List<SwaggerApiDto> apis = new ArrayList<>();
	Set<BeanDefinition> beanDefinitions = new HashSet<>();
	List<String> implementedRestPaths = new ArrayList<>();
	String[] interfaceNames = new String[1];
	interfaceNames[0] = "Test";
	Mockito.when(this.scanner.findCandidateComponents("Test")).thenReturn(beanDefinitions);
	for (BeanDefinition bd : beanDefinitions) {
	    Mockito.when(((ScannedGenericBeanDefinition) bd).getMetadata().getInterfaceNames())
		    .thenReturn(interfaceNames);
	}

	implementedRestPaths.add(this.path.value());
	Assert.assertEquals(1, implementedRestPaths.size());

	for (String path : implementedRestPaths) {
	    this.resolver.getResource(path);
	    Mockito.verify(this.resolver).getResource(Mockito.anyString());

	    SwaggerApiDto api = new SwaggerApiDto();
	    api.setPath("/" + "test");
	    apis.add(api);
	    Assert.assertEquals(1, apis.size());
	}
	root.setApis(apis);
	Assert.assertEquals(1, root.getApis().size());

	this.swaggerIndexService.generateServiceJson();
    }
}
