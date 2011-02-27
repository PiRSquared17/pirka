package org.pirkaengine.wiki.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.wiki.WikiEngine;

public class WikiEngineImplTest {

    private WikiEngineImpl target;
    private String ls;

    @Before
    public void setUp() throws Exception {
        target = new WikiEngineImpl();
        ls = System.getProperty("line.separator");
    }

    private String getInput(String name) throws IOException {
        return getText(name + ".wiki");
    }

    private String getExpected(String name) throws IOException {
        return getText(name + ".expected");
    }

    private String getText(String name) throws IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder text = new StringBuilder();
            for (;;) {
                String line = reader.readLine();
                if (line == null) break;
                text.append(line).append(ls);
            }
            return text.toString();
        } finally {
            if (in != null) in.close();
        }
    }

    @Test
    public void implements_WikiEngine() throws Exception {
        assertThat(target, is(instanceOf(WikiEngine.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void format_null() throws Exception {
        target.format(null);
    }

    @Test
    public void format_plain() throws Exception {
        String input = getInput("plain");
        String expected = getExpected("plain");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_2_lines() throws Exception {
        String input = getInput("2_lines");
        String expected = getExpected("2_lines");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_2_paragraph() throws Exception {
        String input = getInput("2_paragraph");
        String expected = getExpected("2_paragraph");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_2_paragraph_2_blanklines() throws Exception {
        String input = getInput("2_paragraph_2_blanklines");
        String expected = getExpected("2_paragraph_2_blanklines");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_italic() throws Exception {
        String input = getInput("italic");
        String expected = getExpected("italic");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_italic_in_paragraph() throws Exception {
        String input = getInput("italic_in_paragraph");
        String expected = getExpected("italic_in_paragraph");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_bold() throws Exception {
        String input = getInput("bold");
        String expected = getExpected("bold");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_bold_in_paragraph() throws Exception {
        String input = getInput("bold_in_paragraph");
        String expected = getExpected("bold_in_paragraph");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_bolds() throws Exception {
        String input = getInput("bolds");
        String expected = getExpected("bolds");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_strikeout() throws Exception {
        String input = getInput("strikeout");
        String expected = getExpected("strikeout");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_mix_bold_in_italic() throws Exception {
        String input = getInput("mix_bold_in_italic");
        String expected = getExpected("mix_bold_in_italic");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_mix_italic_in_bold() throws Exception {
        String input = getInput("mix_italic_in_bold");
        String expected = getExpected("mix_italic_in_bold");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_1() throws Exception {
        String input = getInput("headline_1");
        String expected = getExpected("headline_1");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_1_and_paragraph() throws Exception {
        String input = getInput("headline_1_and_paragraph");
        String expected = getExpected("headline_1_and_paragraph");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_2() throws Exception {
        String input = getInput("headline_2");
        String expected = getExpected("headline_2");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_3() throws Exception {
        String input = getInput("headline_3");
        String expected = getExpected("headline_3");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_4() throws Exception {
        String input = getInput("headline_4");
        String expected = getExpected("headline_4");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_5() throws Exception {
        String input = getInput("headline_5");
        String expected = getExpected("headline_5");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_headline_6() throws Exception {
        String input = getInput("headline_6");
        String expected = getExpected("headline_6");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_divider() throws Exception {
        String input = getInput("divider");
        String expected = getExpected("divider");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_divider_with_paragraph() throws Exception {
        String input = getInput("divider_with_paragraph");
        String expected = getExpected("divider_with_paragraph");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists() throws Exception {
        String input = getInput("lists");
        String expected = getExpected("lists");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_ordered() throws Exception {
        String input = getInput("lists_ordered");
        String expected = getExpected("lists_ordered");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_sublist() throws Exception {
        String input = getInput("lists_sublist");
        String expected = getExpected("lists_sublist");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_ordered_sublist() throws Exception {
        String input = getInput("lists_ordered_sublist");
        String expected = getExpected("lists_ordered_sublist");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_mix_ol_in_ul() throws Exception {
        String input = getInput("lists_mix_ol_in_ul");
        String expected = getExpected("lists_mix_ol_in_ul");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_mix_ul_in_ol() throws Exception {
        String input = getInput("lists_mix_ul_in_ol");
        String expected = getExpected("lists_mix_ul_in_ol");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_lists_sublist_needs_normalize() throws Exception {
        String input = getInput("lists_sublist_needs_normalize");
        String expected = getExpected("lists_sublist_needs_normalize");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_link() throws Exception {
        String input = getInput("link");
        String expected = getExpected("link");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_link_auto() throws Exception {
        String input = getInput("link_auto");
        String expected = getExpected("link_auto");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_links_auto() throws Exception {
        String input = getInput("links_auto");
        String expected = getExpected("links_auto");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_link_http_https_ftp() throws Exception {
        String input = getInput("link_http_https_ftp");
        String expected = getExpected("link_http_https_ftp");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_img() throws Exception {
        String input = getInput("img");
        String expected = getExpected("img");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_img_list() throws Exception {
        String input = getInput("img_list");
        String expected = getExpected("img_list");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_link_img() throws Exception {
        String input = getInput("link_img");
        String expected = getExpected("link_img");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_link_mix() throws Exception {
        String input = getInput("link_mix");
        String expected = getExpected("link_mix");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_br() throws Exception {
        String input = getInput("br");
        String expected = getExpected("br");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }

    @Test
    public void format_escape() throws Exception {
        String input = getInput("escape");
        String expected = getExpected("escape");
        String actual = target.format(input);
        assertThat(actual, is(expected));
    }
}
