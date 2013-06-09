package cn.cadal.quicksearch.search;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cpainting;
import cn.cadal.user.CheckCookie;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class QuickPaintingSearch extends ActionSupport  implements SessionAware  {

	private String queryword;
	private List resultList;
	private int pageNo=1;
	private int pageNum;
    private int recordNum=20;
    
	protected int pdis = 1;

	protected int pmod = 1;

	protected int pmin = 1;

	protected int panc = 1;

	protected int pjou = 1;

	protected int ppai = 1;

	protected int pvid = 1;

	protected int pfig = 1;
    
	private Map servletSession;
	
	public void setSession(Map arg0) {
		servletSession = arg0;
	}
	private Session session = HibernateUtil.getSessionFactory()
			.getCurrentSession();

	private String currentParams;

	public String getCurrentParams () {
		return currentParams;
	}

	public void setCurrentParams (String currentParams) {
		this.currentParams = currentParams;
	}	
	
	@Override
	public String execute() throws Exception {
		
		CheckCookie checkCookie = new CheckCookie(servletSession);
		checkCookie.checkCookieLogin();
		
		try {
			session.beginTransaction();
			resultList = query();
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}

		currentParams = "";
		if( pdis > 1 ){
			currentParams +="&pdis="+pdis;
		}if( pmod > 1 ){
			currentParams +="&pmod="+pmod;
		}if( pmin > 1 ){
			currentParams +="&pmin="+pmin;
		}if( panc > 1 ){
			currentParams +="&panc="+panc;
		}if( pjou > 1 ){
			currentParams +="&pjou="+pjou;
		}if( ppai > 1 ){
			currentParams +="&ppai="+ppai;
		}if( pvid > 1 ){
			currentParams +="&pvid="+pvid;
		}if( pfig > 1 ){
			currentParams +="&pfid="+pfig;
		}
		LOG.debug("[currentParams]:" + currentParams);
		return SUCCESS;
	}

	protected List query() {

		int recNumPerPage = 10;
		if (0 == pageNum) {
			Criteria criteria = makeCriteria();
			Disjunction disj = Restrictions.disjunction();
			if (queryword != null && queryword.length() > 0) {
				disj.add(Restrictions.like("paintingName", "%" + queryword.trim() + "%"));
				//disj.add(Restrictions.like("author", "%" + queryword.trim() + "%"));
				//disj.add(Restrictions.like("description", "%" + queryword.trim()+ "%"));
				disj.add(Restrictions.like("location", "%" + queryword.trim()+ "%"));
				disj.add(Restrictions.like("appearance", "%" + queryword.trim()+ "%"));
				
			}
			criteria = criteria.add(disj);
			List rows = criteria.setProjection(Projections.rowCount()).list();
			Iterator iter = rows.iterator();

			recordNum = ((Integer) iter.next()).intValue();
			pageNum = (recordNum - 1) / recNumPerPage;
			pageNum++;
		}
		Criteria criteria = makeCriteria();
		Disjunction disj = Restrictions.disjunction();
		if (queryword != null && queryword.length() > 0) {
			disj.add(Restrictions.like("paintingName", "%" + queryword.trim() + "%"));
			//disj.add(Restrictions.like("author", "%" + queryword.trim() + "%"));
			//disj.add(Restrictions.like("description", "%" + queryword.trim()+ "%"));
			disj.add(Restrictions.like("location", "%" + queryword.trim()+ "%"));
			disj.add(Restrictions.like("appearance", "%" + queryword.trim()+ "%"));
			
		}
		criteria = criteria.add(disj);
		orderBy(criteria);

		int firstRow = (pageNo - 1) * recNumPerPage;
		criteria.setFirstResult(firstRow);
		criteria.setMaxResults(recNumPerPage);

		List ret = criteria.list();
		return ret;
	}

	protected Criteria makeCriteria() {
		setPageNo (ppai);
		Criteria criteria = session.createCriteria(Cpainting.class).setCacheable(true);
		return criteria;
	}


	protected void orderBy(Criteria criteria) {
		criteria.addOrder(Order.desc("paintingName"));
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getQueryword() {
		return queryword;
	}

	public void setQueryword(String queryword) {
		this.queryword = queryword;
	}
	
	public List getResultList()
	{
		return resultList;
	}
	public void  setPageNum(int pageNum)
	{
		this.pageNum=pageNum;
	}
	public int getPageNum( )
	{
		return this.pageNum;
	}
	public void setRecordNum(int recordNum)
	{
		this.recordNum =recordNum;
	}
	public int getRecordNum()
	{
		return this.recordNum ;
	}
	
	public int getPanc() {
		return panc;
	}

	public void setPanc(int panc) {
		this.panc = panc;
	}

	public int getPdis() {
		return pdis;
	}

	public void setPdis(int pdis) {
		this.pdis = pdis;
	}

	public int getPfig() {
		return pfig;
	}

	public void setPfig(int pfig) {
		this.pfig = pfig;
	}

	public int getPjou() {
		return pjou;
	}

	public void setPjou(int pjou) {
		this.pjou = pjou;
	}

	public int getPmin() {
		return pmin;
	}

	public void setPmin(int pmin) {
		this.pmin = pmin;
	}

	public int getPmod() {
		return pmod;
	}

	public void setPmod(int pmod) {
		this.pmod = pmod;
	}

	public int getPpai() {
		return ppai;
	}

	public void setPpai(int ppai) {
		this.ppai = ppai;
	}

	public int getPvid() {
		return pvid;
	}

	public void setPvid(int pvid) {
		this.pvid = pvid;
	}	

	


}
