/**
 * Sigh in action, just return success for sigh in
 */

package cn.cadal.user;

import java.util.Map;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;

public class SighInEng extends ActionSupport  {
	public String execute() throws Exception {
		return SUCCESS;
	}

}