package irl.server.query;



import irl.common.tool.RecordNotFoundException;
import irl.server.data.Email;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

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

	public Email loadEmail (String _email,String _password) throws Exception, RecordNotFoundException {

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			List<?> emails = session.createCriteria(Email.class)
			.setFetchMode("user", FetchMode.JOIN)
			.add(Expression.eq("email", _email))
			.add(Expression.eq("password", _password))
			.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY).list();
			tx.commit();
			if(emails.size()!=0) {
				return (Email)emails.get(0);
			}
		}
		catch (Exception e) {
			throw e;
		}
		throw new RecordNotFoundException("Email:"+_email+" Not Found in DB");
	}

	public void createEmail(Email email) throws Exception {

		if(email.getUser().getUserId()==null) {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			try {
				session.save(email.getUser());
				tx.commit();
			}
			catch (Exception e) {
				throw e;
			}
		}

		if(email.getEmailId()==null) {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();

			try {
				session.save(email);
				tx.commit();
			}
			catch (Exception e) {
				throw e;
			}
		}
		else {
			throw new Exception("Asking to create existing EmailId:"+email.getEmailId());
		}

	}
}
