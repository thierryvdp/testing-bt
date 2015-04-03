package requete;



import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import data.Email;


public class QueryFactory {
	
	private static QueryFactory instance=null;
	private static SessionFactory sessionFactory;
	private final static Logger logger = Logger.getLogger(QueryFactory.class);
	
	private QueryFactory() {
		try {
			if(sessionFactory==null) sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable e) {
				logger.error("Initial SessionFactory creation failed.",e);
			}
	}
	
	public synchronized static QueryFactory getInstance() {
		if(instance==null) instance=new QueryFactory();
		return instance;
	}
	
	public Email loadEmail (String _email) throws Exception {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			List<Email> emails = (List<Email>)session.createCriteria(Email.class)
	        .setFetchMode("user", FetchMode.JOIN)
	        .add(Expression.eq("email", _email))
	        .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY).list();
			
			tx.commit();

            if(emails.size()!=0) {
                return emails.get(0);
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        return null;
		
	}

}
