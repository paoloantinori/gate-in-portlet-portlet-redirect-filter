package paolo.test.portal.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.UnavailableException;

import org.apache.log4j.Logger;

import paolo.test.portal.helper.Constants;

public class Setter extends GenericPortlet {
	private static final Logger LOGGER = Logger.getLogger(Setter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest,
	 * javax.portlet.RenderResponse)
	 */
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException, UnavailableException {
		LOGGER.info("Invoked Display Phase");
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		/*
		 * generates a link to this same portlet instance, that will trigger the
		 * processAction method that will be responsible of setting the public
		 * render paramter
		 */
		PortletURL portalURL = response.createActionURL();

		String requiredDestination = "/sample-portal/classic/getterPage";
		String url = addRedirectInfo(portalURL, requiredDestination);
		

		writer.write(String
				.format("<br/><A href='%s' style='text-decoration:underline;'>REDIRECT to %s and set PublicRenderParameters</A><br/><br/>",
						url, requiredDestination));
		LOGGER.info("Generated url with redirect parameters");

		writer.close();

	}

	/**
	 * Helper local macro that add UC_REDIRECT_TO GET parameter to the Url of a
	 * Link
	 * 
	 * @param u
	 * @param redirectTo
	 * @return
	 */
	private String addRedirectInfo(PortletURL u, String redirectTo) {
		String result = u.toString();
		result += String.format("&%s=%s", Constants.REDIRECT_TO, redirectTo);
		return result;
	}

	/*
	 * sets the public render paramter
	 */
	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, PortletSecurityException, IOException {
		LOGGER.info("Invoked Action Phase");

		response.setRenderParameter("prp", "#######################");
	}

}
