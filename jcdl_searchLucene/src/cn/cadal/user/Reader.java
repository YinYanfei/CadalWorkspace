package cn.cadal.user;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.cadal.entity.AccessControlList;
import cn.cadal.entity.BookType;
import cn.cadal.entity.Cbook;

import cn.cadal.entity.Cuser;
import cn.cadal.entity.Group;
import cn.cadal.entity.IpAddress;
import cn.cadal.entity.Resource;
import cn.cadal.entity.ScanCenter;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class Reader extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private static final Log LOG = LogFactory.getLog(Reader.class);

	private String catalog;

	private String bookNo;

	private String bookType;

	private Integer pageNo = 1;

	private Cbook book;

	private HttpServletRequest servletRequest;

	private Map servletSession;

	public void setSession(Map arg0) {
		servletSession = arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNO) {
		this.bookNo = bookNO;
	}

	// version="1.0" encoding="utf-8"
	private static Pattern pattern = Pattern
			.compile("version=.*encoding=\"(.*)\"");

	private static Pattern ipPattern = Pattern
			.compile("(\\d{1,3}).(\\d{1,3}).(\\d{1,3}).(\\d{1,3})");

	final static Map<String, Group> ipGroupMap = new HashMap<String, Group>();
	static {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query ipQuery = session.createQuery("from IpAddress");
		List ips = ipQuery.list();
		LOG.debug("list size:" + ips.size());

		for (int ipIdx = 0; ipIdx < ips.size(); ipIdx++) {
			IpAddress ip = (IpAddress) ips.get(ipIdx);
			String ipStr = ip.getAddress();
			Group group = ip.getGroup();
			Hibernate.initialize(group.getAclSet());
			ipGroupMap.put(ipStr, group);
			if (LOG.isTraceEnabled())
				LOG.trace(ip.getAddress() + ":" + ip.getGroup().getName());
		}
		tx.commit();
	}

	public boolean isAllowedAccess() {
		boolean allowed = false;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		// path check

		if (LOG.isDebugEnabled()) {
			LOG.debug("path check");
		}
		Cbook book = (Cbook) session.load(Cbook.class, getBookNo());
		String path = null;
		try
		{
			path = book.getPath();
			tx.commit();
		}catch(Exception ex)
		{
			return false;
		}
		
		// remove minguo|| (path.indexOf("minguo") != -1)
		if ( (path.indexOf("shufa") != -1) 
			 || (path.indexOf("anc") != -1) || (path.indexOf("eng") != -1) || (path.indexOf("minguo") != -1 )) {
			return true;
		}

		// ip check
		Set groupSet = new HashSet();
		if (LOG.isDebugEnabled()) {
			LOG.debug("ip check");
		}

		String ipAddr = servletRequest.getRemoteAddr();
		Matcher matcher = ipPattern.matcher(ipAddr);
		matcher.find();

		// zju local address

		if (matcher.group(1).equals("10")) {
			return true;
		}
		
		// cmu address
		if (matcher.group(1).equals("128") 
			&& matcher.group(2).equals("2")){
			return true;
		}

		String[] query = new String[4];
		query[0] = matcher.group(1);
		query[1] = matcher.group(1) + "." + matcher.group(2);
		query[2] = matcher.group(1) + "." + matcher.group(2) + "."
				+ matcher.group(3);
		query[3] = ipAddr;

		if (LOG.isDebugEnabled()) {
			LOG.debug("ip query 0:" + query[0]);
			LOG.debug("ip query 1:" + query[1]);
			LOG.debug("ip query 2:" + query[2]);
			LOG.debug("ip query 3:" + query[3]);
		}

		for (int queryidx = 0; queryidx < query.length; queryidx++) {
			Group group1 = (Group) ipGroupMap.get(query[queryidx]);
			if (group1 != null) {
				LOG.debug("ip group:" + group1);
				groupSet.add(group1);
				break;
			}
		}

		String[] tokens = path.split("/");
		
		// hack diantong, all books are open 
		if (tokens[tokens.length - 4].equalsIgnoreCase("diantong")
				||(tokens.length-5>-1 && tokens[tokens.length - 5].equalsIgnoreCase("diantong"))
				||(tokens.length-6>-1 && tokens[tokens.length - 6].equalsIgnoreCase("diantong"))){
			return true;
		}
		
		// hack
		ScanCenter sc = new ScanCenter(tokens[tokens.length - 3]);
		Resource cResource = new Resource(sc, tokens[tokens.length - 4]);		
		
		// --------------------------------
		if (LOG.isDebugEnabled()) {
			LOG.debug("uesr check");
		}
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		tx = session.beginTransaction();
		String userIdStr = (String) servletSession.get("userId");
		Integer userId = 0;
		if (userIdStr != null) {
			userId = Integer.parseInt(userIdStr);
		}

		if (userId > 0) {
			Cuser user = (Cuser) session.load(Cuser.class, userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("user email:" + user.getEmailAddress());
			}
			Set<Group> groups = user.getMyGroup();
			groupSet.addAll(groups);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("visited resource type" + "scancenter:"
					+ tokens[tokens.length - 3] + " resource:"
					+ tokens[tokens.length - 4]);
		}

		Iterator groupIter = groupSet.iterator();
		while (groupIter.hasNext()) {
			Group group2 = (Group) groupIter.next();

			if (LOG.isDebugEnabled()) {
				LOG.debug("group name:" + group2.getName());
			}

			if (group2.getName().equals("VIP")
					|| group2.getName().equals("’„Ω≠¥Û—ß")) {
				allowed = true;
				break;
			}
			Set<AccessControlList> acls = group2.getAclSet();
			Iterator aclIter = acls.iterator();
			while (aclIter.hasNext()) {
				AccessControlList acl = (AccessControlList) aclIter.next();
				if (acl.getResource().equals(cResource)) {
					allowed = true;
				}
			}
			if (allowed) {
				break;
			}
		}
		tx.commit();
		return allowed;
	}

	public String execute() throws Exception {
		
		CheckCookie checkCookie = new CheckCookie(servletSession);
		checkCookie.checkCookieLogin();
		
		setlogInfo();

		if (!isAllowedAccess())
			return ERROR;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		URL urlConn = null;
		try {
			Transaction tx = session.beginTransaction();
			book = (Cbook) session.load(Cbook.class, bookNo);
			// decide booktype ;
			BookType bType = new BookType();
			book.accept(bType);
			bookType = bType.getBookType();
			LOG.debug("book type : " + bookType);
			Hibernate.initialize(book);
			tx.commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		if (true)
			return "redirect";
		
		String bookURL = book.getRepository().getExternalUri() + "/"
				+ book.getPath();
		String catalogFileURL = null;
		char[] headBuf = null;
		InputStreamReader headReader = null;

		try {
			// retrieve the catalog.xml
			if (bookType.equals("english")) {
				catalogFileURL = bookURL + "/TOC/Catalog.xml";
			} else {
				catalogFileURL = bookURL + "/meta/Catalog.xml";
			}
			urlConn = new URL(catalogFileURL);
			headReader = new InputStreamReader(urlConn.openStream());
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			catalogFileURL = bookURL + "/meta/catalog.xml";
		}

		try {
			urlConn = new URL(catalogFileURL);
			headBuf = new char[100];
			headReader = new InputStreamReader(urlConn.openStream());
			headReader.read(headBuf, 0, headBuf.length);
			String head = new String(headBuf);
			Matcher matcher = pattern.matcher(head);
			boolean find = matcher.find();
			String charset = "UTF-16";
			if (find)
				charset = matcher.group(1);
			if (charset.equalsIgnoreCase("gb2312"))
				charset = "GBK";

			if (LOG.isDebugEnabled())
				LOG.debug("charset :" + charset);

			InputStreamReader catReader = new InputStreamReader(urlConn
					.openStream(), charset);
			char[] catbuffer = new char[4096];
			StringBuilder sb = new StringBuilder(4096);
			do {
				sb.append(catbuffer);
				catbuffer = new char[4096];
			} while (catReader.read(catbuffer, 0, catbuffer.length) != -1);

			catalog = sb.toString().trim().replaceAll("[\r\n]+", "").replaceAll("\'", "\\\\'");
			catalog = catalog.substring(catalog.indexOf("<?xml"));
			if (LOG.isDebugEnabled()) {
				LOG.debug("catalog content :" + catalog);
			}
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}

		if (book == null) {
			return ERROR;
		}
		// else if (book.getFormat().indexOf("djvu")==-1){
		// return "english";
		// }
		else {
			return SUCCESS;
		}
	}

	public Cbook getBook() {
		return book;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getBookType() {
		return bookType;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public void setlogInfo() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String ipAd = request.getRemoteAddr();
			String ipHost = request.getRemoteHost();
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();
			Transaction tx = session.beginTransaction();
			String userIdStr = (String) servletSession.get("userId");
			Integer userId = 0;
			if (userIdStr != null) {
				userId = Integer.parseInt(userIdStr);
			}
			String emAddr = null;
			if (userId > 0) {
				Cuser user = (Cuser) session.load(Cuser.class, userId);
				emAddr = user.getEmailAddress();
			}
			String method = request.getMethod();
			String requestURI = request.getRequestURI();
			String queryString = request.getQueryString();
			String protocal = request.getProtocol();
			String referer = request.getHeader("referer");
			String ua = request.getHeader("user-agent");
			CadalAccessLog CAL = new CadalAccessLog();
			CAL.setIpAddr(ipAd);
			CAL.setIpHost(ipHost);
			CAL.setUserName(emAddr);
			CAL.setMethod(method);
			CAL.setProtocal(protocal);
			CAL.setQueryString(queryString);
			CAL.setRequestURI(requestURI);
			CAL.setReferer(referer);
			CAL.setUa(ua);
			CAL.Logging();
			tx.commit();
		} catch (Exception e) {
			LOG.warn(StackTraceUtil.getStackTrace(e));
		}
	}

	public static void main(String[] args) {
		String catalog = "xxxxx ' xxxx 'xx ";
		catalog = catalog.trim().replaceAll("[\r\n]+", "").replaceAll("'",
				"\\\\'");
		System.out.println(catalog);
	}

}
