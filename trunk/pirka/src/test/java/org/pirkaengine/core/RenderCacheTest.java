package org.pirkaengine.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

public class RenderCacheTest {

    @Before
    public void setup() {
        File file = new File("tmp/cache/Test.Repeat.template.html");
        if (file.exists()) file.delete();
    }

    @Test
    public void render() throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < 50; i++) {
            futures.add(service.submit(new RenderCacheTask()));
        }
        for (Future<String> future : futures) {
            future.get();
        }
    }

}
