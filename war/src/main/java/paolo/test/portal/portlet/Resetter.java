package paolo.test.portal.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.UnavailableException;

public class Resetter extends GenericPortlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest,
	 * javax.portlet.RenderResponse)
	 */
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException, UnavailableException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		PortletURL u = response.createActionURL();
		writer.write(String
				.format("<br/><A href='%s' style='text-decoration:underline;'>RESET All Public Render Parameters</A><br/><br/>",
						u));

		writer.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest,
	 * javax.portlet.ActionResponse)
	 */
	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, PortletSecurityException, IOException {

		List<String> list = Collections.list(request.getParameterNames());
		for (String key : list) {
			response.setRenderParameter(key, "");

		}
	}

}
