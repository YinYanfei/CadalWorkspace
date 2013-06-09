package common.utils; // Generated package name

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static Log log = LogFactory.getLog(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	private static final Configuration config;

	static {
		try {
			// Create the SessionFactory
			config = new AnnotationConfiguration().configure();
			sessionFactory = config.buildSessionFactory();
		} catch (Exception ex) {
			log.error( StackTraceUtil.getStackTrace (ex) );
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
	}

	public static Configuration getConfig() {
		return config;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
