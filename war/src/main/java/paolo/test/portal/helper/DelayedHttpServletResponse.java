package paolo.test.portal.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A response wrapper to be used to buffer its response in opposition to the
 * typical behavior of streaming the answer as soon as data is pushed on the
 * stream
 * 
 * @author pantinor@redhat.com
 * 
 */
public class DelayedHttpServletResponse extends HttpServletResponseWrapper {
	protected HttpServletResponse origResponse = null;
	protected OutputStream temporaryOutputStream = null;
	protected ServletOutputStream bufferedServletStream = null;
	protected PrintWriter writer = null;

	public DelayedHttpServletResponse(HttpServletResponse response) {
		super(response);
		origResponse = response;
	}

	protected ServletOutputStream createOutputStream() throws IOException {
		try {

			temporaryOutputStream = new ByteArrayOutputStream();

			return new ServletOutputStreamImpl(temporaryOutputStream);
		} catch (Exception ex) {
			throw new IOException("Unable to construct servlet output stream: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {

		if (bufferedServletStream == null) {
			bufferedServletStream = createOutputStream();
		}
		return bufferedServletStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return (writer);
		}

		bufferedServletStream = getOutputStream();

		writer = new PrintWriter(new OutputStreamWriter(bufferedServletStream,
				"UTF-8"));
		return writer;
	}

}