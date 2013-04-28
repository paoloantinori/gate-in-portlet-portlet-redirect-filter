package paolo.test.portal.servletfilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import paolo.test.portal.helper.Constants;
import paolo.test.portal.helper.DelayedHttpServletResponse;

/**
 * Servlet Filter that intercepts HttpRequests and pass along a specialized
 * HttpResponseWrapper that buffers all the response from server, allowing to
 * fire a redirect to a different url.
 * 
 * @author pantinor@redhat.com
 * 
 */
public class PortletRedirectFilter implements javax.servlet.Filter {

	private static final Logger LOGGER = Logger
			.getLogger(PortletRedirectFilter.class);

	private FilterConfig filterConfig = null;

	public void doFilter(ServletRequest request, ServletResponse response,

	FilterChain chain) throws IOException, ServletException {

		LOGGER.info("started filtering all urls defined in the filter url mapping ");

		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsreq = (HttpServletRequest) request;

			// search for a GET parameter called as defined in REDIRECT_TO
			// variable
			String destinationUrl = hsreq.getParameter(Constants.REDIRECT_TO);

			if (destinationUrl != null) {
				LOGGER.info("found a redirect request " + destinationUrl);
				// creates the HttpResponseWrapper that will buffer the answer
				// in memory
				DelayedHttpServletResponse delayedResponse = new DelayedHttpServletResponse(
						(HttpServletResponse) response);
				// forward the call to the subsequent actions that could modify
				// externals or global scope variables
				chain.doFilter(request, delayedResponse);

				// fire the redirection on the original response object
				HttpServletResponse hsres = (HttpServletResponse) response;
				hsres.sendRedirect(destinationUrl);

			} else {
				LOGGER.info("no redirection defined");
				chain.doFilter(request, response);
			}
		} else {
			LOGGER.info("filter invoked outside the portal scope");
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}