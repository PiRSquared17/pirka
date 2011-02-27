package org.pirkaengine.core.parser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.core.parser.LineBreak;
import org.pirkaengine.core.parser.UnkownLineBreakException;

public class LineBreakTest {

    @Test
    public void lookup_none_break_line() {
        assertNull(LineBreak.loolup("abcde"));
    }

    @Test
    public void lookup_CR() {
        assertSame(LineBreak.CR, LineBreak.loolup("abcde\ref\rgh\r"));
    }

    @Test
    public void lookup_LF() {
        assertSame(LineBreak.LF, LineBreak.loolup("abcde\nef\ngh\n"));
    }

    @Test
    public void lookup_CRLF() {
        assertSame(LineBreak.CRLF, LineBreak.loolup("abcde\r\nef\r\ngh\r\n"));
    }

    @Test
    public void lookup_CR_only() {
        assertSame(LineBreak.CR, LineBreak.loolup("\r"));
    }

    @Test
    public void lookup_LF_only() {
        assertSame(LineBreak.LF, LineBreak.loolup("\n"));
    }

    @Test
    public void lookup_CRLF_only() {
        assertSame(LineBreak.CRLF, LineBreak.loolup("\r\n"));
    }

    @Test(expected = UnkownLineBreakException.class)
    public void lookup_CR_LF() {
        LineBreak.loolup("ac\rbd\n");
    }

    @Test(expected = UnkownLineBreakException.class)
    public void lookup_LF_CRLF() {
        LineBreak.loolup("ac\nbd\r\n");
    }

    @Test(expected = UnkownLineBreakException.class)
    public void lookup_CR_CRLF() {
        LineBreak.loolup("ac\rbd\r\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void lookup_null() {
        LineBreak.loolup((String) null);
    }
}
