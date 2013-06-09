package cn.cadal.user; // Generated package name

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import cn.cadal.entity.Cbinder;
import cn.cadal.entity.Crule;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.UserBinder;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyBinder extends ActionSupport implements SessionAware {

	private Boolean saveButton;

	private Boolean delButton;

	private Boolean updateButton;

	private Map servletSession;

	private int binderId;

	private String binderName;

	private String repository;

	private List<Crule> rules = new ArrayList<Crule>();

	private Cbinder curBinder = new Cbinder();
	
	private int defaultResultNum=6;

	public void setSession(Map session) {
		this.servletSession = session;
	}

	/**
	 * Gets the value of servletSession
	 * 
	 * @return the value of servletSession
	 */
	public final Map getServletSession() {
		return this.servletSession;
	}

	/**
	 * Sets the value of servletSession
	 * 
	 * @param argServletSession
	 *            Value to assign to this.servletSession
	 */
	public final void setServletSession(final Map argServletSession) {
		this.servletSession = argServletSession;
	}

	/**
	 * Gets the value of binderId
	 * 
	 * @return the value of binderId
	 */
	public final int getBinderId() {
		return this.binderId;
	}

	/**
	 * Sets the value of binderId
	 * 
	 * @param argBinderId
	 *            Value to assign to this.binderId
	 */
	public final void setBinderId(final int argBinderId) {
		this.binderId = argBinderId;
	}

	private Cuser user;

	/**
	 * Gets the value of curBinder
	 * 
	 * @return the value of curBinder
	 */
	public final Cbinder getCurBinder() {
		return this.curBinder;
	}

	/**
	 * Sets the value of curBinder
	 * 
	 * @param argCurBinder
	 *            Value to assign to this.curBinder
	 */
	public final void setCurBinder(final Cbinder argCurBinder) {
		this.curBinder = argCurBinder;
	}

	/**
	 * Gets the value of user
	 * 
	 * @return the value of user
	 */
	public final Cuser getUser() {
		return this.user;
	}

	/**
	 * Sets the value of user
	 * 
	 * @param argUser
	 *            Value to assign to this.user
	 */
	public final void setUser(final Cuser argUser) {
		this.user = argUser;
	}

	public String execute() throws Exception {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			String userIdStr = (String) servletSession.get("userId");

			int userId = 0;
			if (userIdStr != null) {
				userId = Integer.parseInt(userIdStr);
			}
			if (userId == 0 || userIdStr == null) {
				return INPUT;
			}

			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			if (LOG.isDebugEnabled())
				LOG.debug("user :" + user);

			if (saveButton != null) {
				curBinder.setBinderId(null);
				curBinder.setBinderName(binderName);
				curBinder.setRepository(repository);

				for (int ruleIdx = 0; ruleIdx < rules.size(); ruleIdx++) {
					Crule rule = (Crule) rules.get(ruleIdx);
					rule.setRuleId(null);
					rule.setRuleIdx(ruleIdx);
					rule.setBinder(curBinder);
					if (rule.getQueryWord().length() > 0) {
						curBinder.getRules().add(rule);
					}
				}
				session.save(curBinder);
				session.save(new UserBinder(user, curBinder, defaultResultNum));
				if (LOG.isDebugEnabled())
					LOG.debug("binder :" + curBinder);
			} else if (updateButton != null) {
				if (binderId > 0) {
					curBinder = (Cbinder) session.load(Cbinder.class,
							new Integer(binderId));
					curBinder.setBinderName(binderName);
					curBinder.setRepository(repository);

					List<Crule> dbRules = curBinder.getRules();
					dbRules.clear();
					for (int ruleIdx = 0; ruleIdx < rules.size(); ruleIdx++) {
						Crule rule = (Crule) rules.get(ruleIdx);
						Integer ruleId = rule.getRuleId();
						rule.setRuleIdx(ruleIdx);
						
						if (LOG.isDebugEnabled()) {
							LOG.debug(" :database rule idx:" + rule.getRuleId()
									+ " :view rule idx:" + rule.getRuleIdx()
									+" :rule queryword:" + rule.getQueryWord()
									+" :rule schemaword:" + rule.getSchemaField()
									+" :rule operator:" + rule.getOperator());
						}
						
						if (ruleId != null && (ruleId.intValue() > 0)) {
							Crule prule = (Crule) session.merge(rule);
							prule.setBinder(curBinder);
							dbRules.add(prule);
						} else {
							if (rule.getQueryWord() != null
									&& rule.getQueryWord().length() > 0) {
								rule.setBinder(curBinder);
								dbRules.add(rule);
							}
						}
					}
					session.flush();
				}
			} else if (delButton != null) {
				if (binderId > 0) {
					curBinder = (Cbinder) session.load(Cbinder.class,
							new Integer(binderId));
					Set<UserBinder> userbinders = user.getMyBinders();
					// UserBinder query = new UserBinder (user, curBinder);
					Iterator ubiter = userbinders.iterator();
					UserBinder userBinder = null;
					while (ubiter.hasNext()) {
						userBinder = (UserBinder) ubiter.next();
						if (userBinder.equals(new UserBinder(user, curBinder))) {
							break;
						}
					}
					userbinders.remove(userBinder); // view update
					session.delete(userBinder);// delete userbinder, da update
					session.saveOrUpdate(user);// update user;
				}
			} else {
				// read current binder
				if (binderId > 0) {
					curBinder = (Cbinder) session.load(Cbinder.class,
							new Integer(binderId));
					binderName = curBinder.getBinderName();
					repository = curBinder.getRepository();

					for (int ridx = 0; ridx < 4; ridx++) {
						rules.add(new Crule());
					}

					// TODO: performance bottleneck ?
					List<Crule> brules = curBinder.getRules();
					for (int bruleIdx = 0; bruleIdx < brules.size(); bruleIdx++) {
						Crule rule = (Crule) brules.get(bruleIdx);
						// Hibernate.initialize (rule);
						rules.add(rule.getRuleIdx(), rule);
					}
				}
			}
			Hibernate.initialize(user.getMyBinders());
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}

		return SUCCESS;
	}

	/**
	 * Gets the value of saveButton
	 * 
	 * @return the value of saveButton
	 */
	public final Boolean getSaveButton() {
		return this.saveButton;
	}

	/**
	 * Sets the value of saveButton
	 * 
	 * @param argSaveButton
	 *            Value to assign to this.saveButton
	 */
	public final void setSaveButton(final Boolean argSaveButton) {
		this.saveButton = argSaveButton;
	}

	/**
	 * Gets the value of binderName
	 * 
	 * @return the value of binderName
	 */
	public final String getBinderName() {
		return this.binderName;
	}

	/**
	 * Sets the value of binderName
	 * 
	 * @param argBinderName
	 *            Value to assign to this.binderName
	 */
	public final void setBinderName(final String argBinderName) {
		this.binderName = argBinderName;
	}

	/**
	 * Gets the value of repository
	 * 
	 * @return the value of repository
	 */
	public final String getRepository() {
		return this.repository;
	}

	/**
	 * Sets the value of repository
	 * 
	 * @param argRepository
	 *            Value to assign to this.repository
	 */
	public final void setRepository(final String argRepository) {
		this.repository = argRepository;
	}

	public final Boolean getUpdateButton() {
		return this.updateButton;
	}

	public final void setUpdateButton(final Boolean argUpdateButton) {
		this.updateButton = argUpdateButton;
	}

	/**
	 * Gets the value of delButton
	 * 
	 * @return the value of delButton
	 */
	public final Boolean getDelButton() {
		return this.delButton;
	}

	/**
	 * Sets the value of delButton
	 * 
	 * @param argDelButton
	 *            Value to assign to this.delButton
	 */
	public final void setDelButton(final Boolean argDelButton) {
		this.delButton = argDelButton;
	}

	/**
	 * Gets the value of rules
	 * 
	 * @return the value of rules
	 */
	public final List getRules() {
		return this.rules;
	}

	/**
	 * Sets the value of rules
	 * 
	 * @param argRules
	 *            Value to assign to this.rules
	 */
	public final void setRules(final List argRules) {
		this.rules = argRules;
	}

}
