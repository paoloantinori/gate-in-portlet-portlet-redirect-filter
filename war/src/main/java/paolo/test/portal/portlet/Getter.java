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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.UnavailableException;

import org.apache.log4j.Logger;

public class Getter extends GenericPortlet {

	private static final Logger LOGGER = Logger.getLogger(Getter.class);

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
		
		writer.write("<br/>");
		writer.write("<div style='text-align:center;'>Parameters found</div>");
		writer.write("<br/><br/>");
		writer.write("<table>");
		writer.write("<tr>");
		writer.write("<th>KEY</th>");
		writer.write("<th>VALUE</th>");
		writer.write("</tr>");
		List<String> list = Collections.list(request.getParameterNames());
		for (String key : list) {
			writer.write("<tr>");
			String value = request.getParameter(key);
			writer.write("<td style='text-align:center;'>");
			writer.write(key);
			writer.write("</td>");
			writer.write("<td style='text-align:center;'>");
			writer.write(value);
			writer.write("</td>");
			writer.write("</tr>");

		}
		writer.write("</table>");

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
		LOGGER.info("Invoked Action Phase");
		super.processAction(request, response);
	}

}
