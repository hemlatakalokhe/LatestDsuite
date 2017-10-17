import java.util.HashSet;
import java.util.Map;
import org.glassfish.jersey.server.ResourceConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.Test;

import de.bonprix.BaseConfiguredUnitTest;
import de.bonprix.JerseyServlet;
import de.bonprix.annotation.RestService;

public class JerseyServletTest extends BaseConfiguredUnitTest {

    @InjectMocks
    private JerseyServlet jerseyServlet;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ResourceConfig resourceConfig;

    @Test
    public void postConstructTest() {

	for (Map.Entry<String, Object> entry : this.applicationContext.getBeansWithAnnotation(RestService.class)
		.entrySet()) {
	    entry.setValue("Test");
	    Mockito.when(this.applicationContext.getBeansWithAnnotation(Mockito.any()).entrySet())
		    .thenReturn(new HashSet<>());
	    Mockito.when(entry.getValue().getClass()).thenReturn(Mockito.any());

	    Mockito.verify(this.resourceConfig).register(entry.getValue());
	    this.resourceConfig.register(entry.getValue());

	}

	this.jerseyServlet.postConstruct();
    }

}
