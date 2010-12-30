package org.pirkaengine.slim3;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * PirkaEngineの初期化を行うServletContextListener.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class PirkaEngineInitalizer implements ServletContextListener {

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent evt) {
        PirkaengineOnSlim3.init();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent evt) {
    }

}
