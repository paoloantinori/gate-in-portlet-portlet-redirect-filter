package paolo.test.portal.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

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
	protected OutputStream realOutputStream = null;
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;

	Class<? extends OutputStream> outputStreamClass;

	public OutputStreamResponseWrapper(HttpServletResponse response,
			Class<? extends OutputStream> outputStreamClass) {
		super(response);
		origResponse = response;
		this.outputStreamClass = outputStreamClass;
	}

	protected ServletOutputStream createOutputStream() throws IOException {
		try {

			Constructor<?> c = outputStreamClass.getConstructor();
			realOutputStream = (OutputStream) c.newInstance();

			return new ServletOutputStreamWrapper(realOutputStream);
		} catch (Exception ex) {
			throw new IOException("Unable to construct servlet output stream: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		stream.flush();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called!");
		}

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

		if (stream != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, "UTF-8"));
		return writer;
	}

	@Override
	public void setContentLength(int length) {
	}

}