/**
 *
 */
package de.bonprix.jersey;

/**
 * @author cthiel
 * @date 05.11.2016
 *
 */
@FunctionalInterface
public interface JaxRsClientFactory {

	/**
	 * @param uri
	 * @param clazz
	 * @param config
	 * @return
	 */
	<E> E createClient(String uri, Class<E> clazz, ClientFactoryConfig config);

}
