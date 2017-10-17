package de.bonprix;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Bean for providing general application information or settings globally.
 *
 * @author cthiel
 * @date 14.11.2016
 *
 */
@Component
public class GeneralApplicationInformation extends AbstractApplicationInformation {

	public static final String APPLICATION_ID = "APPLICATION_ID";
	public static final String APPLICATION_CONTEXT_PATH = "APPLICATION_CONTEXT_PATH";
	public static final String APPLICATION_SYSTEMUSER = "APPLICATION_SYSTEMUSER";

	public static final String FRAMEWORK_VERSION = "FRAMEWORK_VERSION";
	public static final String JENKINS_BUILD_VERSION = "JENKINS_BUILD_VERSION";
	public static final String BUILD_TIME = "BUILD_TIME";

	@Autowired
	private WebApplicationContext context;

	@Value("${application.id}")
	private Long applicationId;

	private String applicationContextPath;

	@Value("${application.systemuser.username}")
	private String applicationSystemuser;

	@Value("${framework.version}")
	private String frameworkVersion;

	@Value("${jenkins.build.version}")
	private String jenkinsBuildVersion;

	@Value("${build.time}")
	private String buildTime;

	public GeneralApplicationInformation() {
		super("GENERAL_APPLICATION_INFORMATION");

		addProvider(GeneralApplicationInformation.APPLICATION_ID, () -> getApplicationId());
		addProvider(GeneralApplicationInformation.APPLICATION_CONTEXT_PATH, () -> getApplicationContextPath());
		addProvider(GeneralApplicationInformation.APPLICATION_SYSTEMUSER, () -> getApplicationSystemuser());
		addProvider(GeneralApplicationInformation.FRAMEWORK_VERSION, () -> getFrameworkVersion());
		addProvider(GeneralApplicationInformation.JENKINS_BUILD_VERSION, () -> getJenkinsBuildVersion());
		addProvider(GeneralApplicationInformation.BUILD_TIME, () -> getBuildTime());
	}

	@PostConstruct
	public void post() {
		this.applicationContextPath = this.context	.getServletContext()
													.getContextPath();
	}

	/**
	 * Returns the ID of this application. This is the ID this application has
	 * in d:masterdata and usermanagement.
	 *
	 * @return the application id
	 */
	public Long getApplicationId() {
		return this.applicationId;
	}

	/**
	 * Returns the context path of this web application when deployed on an
	 * application server. The returned value is the equivalent to
	 * ServletContext.getContextPath().
	 *
	 * @return the servletContexts path
	 */
	public String getApplicationContextPath() {
		return this.applicationContextPath;
	}

	/**
	 * Returns the name of the applications system user or null if not provided.
	 *
	 * @return
	 */
	public String getApplicationSystemuser() {
		return this.applicationSystemuser;
	}

	/**
	 * Returns the version of the base framework in use.
	 *
	 * @return
	 */
	public String getFrameworkVersion() {
		return this.frameworkVersion;
	}

	/**
	 * Returns the jenkinsbuild version for dsuite project
	 *
	 * @return
	 */
	public String getJenkinsBuildVersion() {
		return this.jenkinsBuildVersion;
	}

	/**
	 * Returns the time this WAR has been built.
	 *
	 * @return
	 */
	public String getBuildTime() {
		return this.buildTime;
	}

}
