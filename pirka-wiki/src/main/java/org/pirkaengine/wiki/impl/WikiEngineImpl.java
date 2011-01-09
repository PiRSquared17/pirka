package org.pirkaengine.wiki.impl;

import org.pirkaengine.wiki.WikiEngine;

/**
 * 
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class WikiEngineImpl implements WikiEngine {

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.wiki.WikiEngine#format(java.lang.String)
     */
    @Override
    public String format(String source) {
        if (source == null) throw new IllegalArgumentException("source == null.");
        return new Convertor(source).convert();
    }
}
