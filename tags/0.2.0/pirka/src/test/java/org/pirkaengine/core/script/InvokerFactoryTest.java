package org.pirkaengine.core.script;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.pirkaengine.core.script.InvokerFactory;
import org.pirkaengine.core.script.ScriptEngineException;

/**
 * InvokerFactoryTest
 * @author shuji
 */
public class InvokerFactoryTest {

    /**
     * シングルトン
     */
    @Test
    public void getInstance() {
        assertSame(InvokerFactory.getInstance(), InvokerFactory.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvokerFactory_null() {
        new InvokerFactory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInvoker_null() throws ScriptEngineException {
        InvokerFactory.getInstance().getInvoker(null);
    }

}
