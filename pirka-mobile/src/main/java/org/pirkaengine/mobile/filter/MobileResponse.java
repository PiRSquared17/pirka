package org.pirkaengine.mobile.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.pirkaengine.mobile.Carrier;
import org.pirkaengine.mobile.Device;

public class MobileResponse extends HttpServletResponseWrapper {

    protected PrintWriter writer = null;
    protected final Device device;

    public MobileResponse(HttpServletResponse response, Device device) {
        super(response);
        this.device = device;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (device.getCarrier() == Carrier.UNKOWN) return super.getWriter();
        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(super.getOutputStream(), device.getCharset()));
        }
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        }
        super.flushBuffer();
    }

}
