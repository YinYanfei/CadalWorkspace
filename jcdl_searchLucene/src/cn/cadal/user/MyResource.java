package cn.cadal.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import cn.cadal.entity.AccessControlList;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.Group;
import cn.cadal.entity.Resource;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;

public class MyResource extends ActionSupport implements SessionAware {

	private Map servletSession;
	
	private List<Resource> resourceList = new ArrayList<Resource> ();

	private boolean vip;
	
	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	@Override
	public String execute() throws Exception {
		Integer userId = 0 ;
		String userIdStr = (String) servletSession.get("userId");
		
		if (userIdStr == null) {
			return LOGIN;
		}
		if (userIdStr != null) {
			userId = Integer.parseInt(userIdStr);
		}
		if (userId == 0){
			return LOGIN;
		}
		System.out.println ("user id:"+userId);
		Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();
		session.beginTransaction();
		Cuser user = (Cuser) session.load (Cuser.class, userId);
		Set<Group> myGroups = user.getMyGroup ();
		Iterator groupIter = myGroups.iterator();
		while (groupIter.hasNext()) {
			Group group2 = (Group) groupIter.next();
			System.out.println("group name:"+group2.getName());
			if(LOG.isDebugEnabled()){
				LOG.debug("group name:"+group2.getName());
			}
			if (group2.getName().equals("VIP")) {
				vip =true;
				break;
			}
			Set<AccessControlList> aclSet =  group2.getAclSet();
			Iterator aclIter = aclSet.iterator();
			while (aclIter.hasNext ()) {
				AccessControlList acl = (AccessControlList) aclIter.next ();
				resourceList.add ( acl.getResource() );
			}
		}
		
		session.getTransaction().commit();
		
		return SUCCESS;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setSession(Map arg0) {
		servletSession = arg0;
	} 
	
	
}

