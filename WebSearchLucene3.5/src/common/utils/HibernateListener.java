package common.utils; // Generated package name

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class HibernateListener implements ServletContextListener {
    

    // Implementation of javax.servlet.ServletContextListener

    /**
     * Describe <code>contextInitialized</code> method here.
     *
     * @param servletContextEvent a <code>ServletContextEvent</code> value
     */
    public final void contextInitialized(final ServletContextEvent servletContextEvent) {
	HibernateUtil.getSessionFactory ();
    }

    /**
     * Describe <code>contextDestroyed</code> method here.
     *
     * @param servletContextEvent a <code>ServletContextEvent</code> value
     */
    public final void contextDestroyed(final ServletContextEvent servletContextEvent) {
	HibernateUtil.getSessionFactory().close ();
    }

}
