package org.pirkaengine.core;

import java.util.HashMap;
import java.util.Map;


import org.junit.Before;
import org.pirkaengine.core.PirkaContext;
import org.pirkaengine.core.PirkaLoader;

/**
 * レンダリングテスト基底クラス.
 * @author shuji
 */
public class RenderTest {

    PirkaLoader loader;
    Map<String, Object> viewModel;
    
    /**
     * PirkaContextの初期設定と、loaderとviewModelの初期化を行う
     */
    @Before
    public void setup() {
        PirkaContext ctx = PirkaContext.getInstance(); 
        ctx.setAppRoot("app_root");
        ctx.setTemplatePath("testdata");
        ctx.setCachePath("tmp/cache");
        ctx.setEnableCache(false);
        ctx.setEnableDebug(true);
        ctx.addScriptSupport("groovy", "scripts/groovy/");
        ctx.addScriptSupport("jruby", "scripts/jruby/");
        ctx.addScriptSupport("ruby", "scripts/jruby/");
        loader = PirkaLoader.newInstance();
        viewModel = new HashMap<String, Object>();
    }

}
