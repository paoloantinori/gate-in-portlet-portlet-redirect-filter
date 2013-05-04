package paolo.test.portal.helper;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * A concrete implementation of the servlet output stream abstract class.
 * The constructor accept the output stream that will contain all the output data.
 * 
 * @author thein
 * 
 */
public class ServletOutputStreamImpl extends ServletOutputStream {

	OutputStream _out;
	boolean closed = false;

	public ServletOutputStreamImpl(OutputStream realStream) {
		this._out = realStream;
	}

	@Override
	public void close() throws IOException {
		if (closed) {
			throw new IOException("This output stream has already been closed");
		}
		_out.flush();
		_out.close();

		closed = true;
	}

	@Override
	public void flush() throws IOException {
		if (closed) {
			throw new IOException("Cannot flush a closed output stream");
		}
		_out.flush();
	}

	@Override
	public void write(int b) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		_out.write((byte) b);
	}

	@Override
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		if (closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		_out.write(b, off, len);
	}

}