package org.pirkaengine.slim3.internal;

import org.pirkaengine.core.PirkaLoader;

public class DatastorePirkaLoaderFactory  implements PirkaLoader.Factory {

    @Override
    public PirkaLoader newInstance() {
        return new DatastorePirkaLoader();
    }

}
