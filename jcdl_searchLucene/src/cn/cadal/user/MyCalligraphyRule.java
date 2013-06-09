package cn.cadal.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyCalligraphies;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyCalligraphyRule extends ActionSupport implements SessionAware {

	private Map servletSession;

	private Cuser user;
	
	private int ruleId;

	private String saveButton;

	private String delButton;

	private String ruleName;
	
	private String toFileName;

	private File calligraphy;

	private String calligraphyFilePath;
	
	private String calligraphyHttpPath="/characterimage/";
	
	private int ruleNum=1;
	
	private String rootPath = "d:/userdata/";

	public File getCalligraphy() {
		return calligraphy;
	}

	public String getCalligraphyFilePath() {
		return calligraphyFilePath;
	}

	public void setCalligraphy(File calligraphy) {
		this.calligraphy = calligraphy;
	}

	public void setSession(Map session) {
		this.servletSession = session;
	}

	public String execute() throws Exception {
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;

		if (this.hasFieldErrors()) {
			return "reUpload";
		}

		if (userIdStr == null) {
			return INPUT;
		}
		
		userId = Integer.parseInt(userIdStr);
		if (userId == 0) {
			return INPUT;
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Hibernate.initialize(user);

			// the user's directory storing the calligraphy files
			String username = user.getEmailAddress();
			calligraphyHttpPath += username;
			String userPath = rootPath + username;
			File userDir = new File(userPath);
			if (!userDir.exists()) {
				userDir.mkdirs();
			}
			
			//add new rule
			if ( saveButton != null ) {
				// the filename of calligraphy file uploaded
				long ldate = System.currentTimeMillis();
				toFileName = new Long(ldate)
						.toString()+ ".jpg";
				calligraphyFilePath = userPath + "/" + toFileName ;
				File toFile = new File(calligraphyFilePath);

				FileInputStream from = null;
				FileOutputStream to = null;
				try {
					from = new FileInputStream(calligraphy);
					to = new FileOutputStream(toFile);

					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = from.read(buffer)) != -1)
						to.write(buffer, 0, bytesRead); // write

				} finally {
					if (from != null) {
						try {
							from.close();
						} catch (IOException e) {
							// TODO: logging
							return "reUpload";
						}
					}
					if (to != null) {
						try {
							to.close();
						} catch (IOException e) {
							// TODO: logging
							return "reUpload";
						}
					}
				}
				
				MyCalligraphies newone = new MyCalligraphies ();
				newone.setFileName(toFileName);
				newone.setRuleName(ruleName);
				newone.setUploadTime(new java.sql.Date(ldate));
				newone.setUser(user);
				
				session.save(newone);
				
				calligraphyHttpPath += "/"+toFileName;
			}if ( delButton != null ){ // to del a rule
				LOG.info ("ruleId : "+ruleId);
				if ( ruleId > 0 ) {
					MyCalligraphies toDelRule = (MyCalligraphies)session.load(MyCalligraphies.class, new Integer (ruleId));
					List<MyCalligraphies> myCalligraphies = user.getMyCalligraphies();
					myCalligraphies.remove(toDelRule);
					session.delete(toDelRule);
					session.saveOrUpdate(user);
				}
			}else { //read a rule
				//	String[] files = userDir.list();
				//toFileName = files[0];
				List<MyCalligraphies> myCalligraphies = user.getMyCalligraphies();
				ruleNum = myCalligraphies.size(); 
				if (ruleNum >0){
					toFileName = myCalligraphies.get(myCalligraphies.size()-1).getFileName(); 
					calligraphyHttpPath += "/"+toFileName;
				}
			}
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}

		return SUCCESS;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getDelButton() {
		return delButton;
	}

	public void setDelButton(String delButton) {
		this.delButton = delButton;
	}

	public String getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(String saveButton) {
		this.saveButton = saveButton;
	}

	public String getToFileName() {
		return toFileName;
	}

	public void setToFileName(String toFileName) {
		this.toFileName = toFileName;
	}

	public String getCalligraphyHttpPath() {
		return calligraphyHttpPath;
	}

	public void setCalligraphyHttpPath(String calligraphyHttpPath) {
		this.calligraphyHttpPath = calligraphyHttpPath;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getRuleNum() {
		return ruleNum;
	}

	public void setRuleNum(int ruleNum) {
		this.ruleNum = ruleNum;
	}

}
