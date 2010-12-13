package org.pirkaengine.core.template;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.RenderException;
import org.pirkaengine.core.expression.Function;

/**
 * BindTemplateのテスト
 * @author shuji.w6e
 * @since 0.1.0
 */
public class BindTemplateTest {

    private static final String TEXT = "This is rendering text.";
    private BindTemplate target;

    /**
     * セットアップ.
     */
    @Before
    public void setup() {
        XhtmlTemplate template = new XhtmlTemplate("test");
        template.stack(new TextNode(TEXT));
        Map<String, Object> viewModel = new HashMap<String, Object>();
        Map<String, Function> functions = new HashMap<String, Function>();
        target = new BindTemplate(template, viewModel, functions);
    }

    /**
     * renderテスト.
     */
    @Test
    public void render() {
        assertEquals(TEXT, target.render());
    }

    /**
     * renderテスト.
     */
    @Test
    public void render_outputstream() {
        final StringBuffer buf = new StringBuffer();
        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                byte[] bytes = new byte[] { (byte) b };
                buf.append(new String(bytes, "UTF-8"));
            }
        };
        target.render(output);
        assertEquals(TEXT, buf.toString());
    }

    /**
     * renderテスト(MS932).
     */
    @Test
    public void render_outputstream_MS932() {
        final StringBuffer buf = new StringBuffer();
        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                byte[] bytes = new byte[] { (byte) b };
                buf.append(new String(bytes, "MS932"));
            }
        };
        target.render(output, "MS932");
        assertEquals(TEXT, buf.toString());
    }

    /**
     * renderテスト.
     */
    @Test
    public void render_outputstream_EUC_JP() {
        final StringBuffer buf = new StringBuffer();
        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                byte[] bytes = new byte[] { (byte) b };
                buf.append(new String(bytes, "EUC-JP"));
            }
        };
        target.render(output, "EUC-JP");
        assertEquals(TEXT, buf.toString());
    }

    /**
     * renderテスト.
     */
    @Test(expected = RenderException.class)
    public void render_IOException() {
        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException();
            }
        };
        target.render(output);
    }

    /**
     * renderテスト.
     */
    @Test
    public void render_writer() {
        Writer writer = new StringWriter();
        target.render(writer);
        assertEquals(TEXT, writer.toString());
    }

    /**
     * renderテスト.
     */
    @Test(expected = IllegalArgumentException.class)
    public void render_outputstream_null() {
        OutputStream output = null;
        target.render(output);
    }

    /**
     * renderテスト.
     */
    @Test(expected = IllegalArgumentException.class)
    public void render_writer_null() {
        Writer writer = null;
        target.render(writer);
    }

}
