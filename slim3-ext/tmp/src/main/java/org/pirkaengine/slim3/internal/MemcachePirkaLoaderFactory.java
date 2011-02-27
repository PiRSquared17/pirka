package org.pirkaengine.slim3.internal;

import org.pirkaengine.core.PirkaLoader;

public class MemcachePirkaLoaderFactory implements PirkaLoader.Factory {

    @Override
    public PirkaLoader newInstance() {
        return new MemcachePirkaLoader();
    }

}
