package hibernate2;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

	private static final SessionFactory factorySession;
	
	static {
		File configFile = new File("hibernate.cfg.xml");
		Configuration conf = new Configuration().configure(configFile);
		factorySession = conf.buildSessionFactory();
	}
	
	public static Session getSession() throws HibernateException{
		return factorySession.openSession();
	}
}
