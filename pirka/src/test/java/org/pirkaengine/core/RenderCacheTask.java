package org.pirkaengine.core;

import static org.pirkaengine.core.TestUtil.*;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.Template;


public class RenderCacheTask implements Callable<String> {
    
    public RenderCacheTask() {
        
    }
    
    @Override
    public String call() throws Exception {
        PirkaContext context = new PirkaContext();
        context.setCachePath("tmp/cache");
        context.setEnableCache(true);
        context.setTemplatePath("testdata");
        PirkaLoader loader = new PirkaLoader(context);
        
        String templateName = "Repeat";
        Template tmpl = loader.load(getTemplateFileName(templateName));
        HashMap<String, Object> viewModel = new HashMap<String, Object>();
        viewModel.put("rate", 4);
        String actual = tmpl.generate(viewModel).render();
        assertRenderEquals(templateName, actual);
        return actual;
    }
}
