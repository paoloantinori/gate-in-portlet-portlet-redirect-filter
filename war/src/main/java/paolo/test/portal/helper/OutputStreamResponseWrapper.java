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
public class OutputStreamResponseWrapper extends HttpServletResponseWrapper {
	protected HttpServletResponse origResponse = null;
	protected OutputStream bufferedOutputStream = null;
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;


	public OutputStreamResponseWrapper(HttpServletResponse response) {
		super(response);
		origResponse = response;;
	}

	protected ServletOutputStream createOutputStream() throws IOException {
		try {

			bufferedOutputStream = new ByteArrayOutputStream();

			return new ServletOutputStreamWrapper(bufferedOutputStream);
		} catch (Exception ex) {
			throw new IOException("Unable to construct servlet output stream: "
					+ ex.getMessage(), ex);
		}
	}


	@Override
	public ServletOutputStream getOutputStream() throws IOException {

		if (stream == null) {
			stream = createOutputStream();
		}
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return (writer);
		}

		if (stream == null) {
			stream = createOutputStream();
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, "UTF-8"));
		return writer;
	}


}