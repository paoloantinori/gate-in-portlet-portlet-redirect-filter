package paolo.test.portal.servletfilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import paolo.test.portal.helper.OutputStreamResponseWrapper;



/**
 * Servlet Filter that intercepts HttpRequests and pass along a specialized
 * HttpResponseWrapper that buffers all the response from server, allowing to
 * fire a redirect to a different url. 
 * 
 * @author pantinor@redhat.com
 * 
 */
public class PortletRedirectFilter implements javax.servlet.Filter {

	public static final String UC_REDIRECT_TO = "uc.redirect.to";

	private static final Logger LOGGER = Logger
			.getLogger(PortletRedirectFilter.class);

	private FilterConfig filterConfig = null;

	
	public void doFilter(ServletRequest request, ServletResponse response,

	FilterChain chain) throws IOException, ServletException {

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("started filtering all urls defined in the filter url mapping ");

		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsreq = (HttpServletRequest) request;

			//search for a GET parameter called as defined in UC_REDIRECT_TO variable
			String destinationUrl = hsreq.getParameter(UC_REDIRECT_TO);

			if (destinationUrl != null) {
				LOGGER.info("found a redirect request " + destinationUrl);
				//creates the HttpResponseWrapper that will buffer the answer inside memory
				OutputStreamResponseWrapper wrappedResponse = new OutputStreamResponseWrapper(
						(HttpServletResponse) response,
						ByteArrayOutputStream.class);
				//forward the call to the subsequent actions that could modify externals or global scope variables
				chain.doFilter(request, wrappedResponse);

				//fire the redirection on the original response object
				HttpServletResponse hsres = (HttpServletResponse) wrappedResponse
						.getResponse();
				hsres.sendRedirect(destinationUrl);

			} else {
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("no redirection defined");
				chain.doFilter(request, response);
			}
		} else {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("filter invoked outside the portal scope");
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void destroy() {

	}

}