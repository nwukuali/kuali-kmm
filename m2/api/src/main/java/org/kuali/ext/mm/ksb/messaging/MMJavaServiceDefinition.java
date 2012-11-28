/**
 *
 */
package org.kuali.ext.mm.ksb.messaging;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.ksb.api.bus.support.JavaServiceDefinition;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author harsha07
 */
public class MMJavaServiceDefinition extends JavaServiceDefinition {
	/**
	 *
	 */
	private static final long serialVersionUID = -3319697688978946064L;


	@Override
	public URL getEndpointUrl() {
		try {
			return new URL(ConfigContext.getCurrentContextConfig().getProperty("kmm.url")
				+ "/remoting/" + this.getServiceName().getLocalPart());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
