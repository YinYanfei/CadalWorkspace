package cn.cadal.quicksearch.search; // Generated package name

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.cadal.user.CheckCookie;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;

public class QuickSearch extends ActionSupport implements SessionAware {

	// private List repository = new ArrayList (4);

	private int pdis = 1;

	private int pmod = 1;

	private int pmin = 1;

	private int panc = 1;

	private int pjou = 1;

	private int ppai = 1;

	private int pvid = 1;

	private int pfig = 1;

	private String query;
	
	private Map servletSession;
	
	public void setSession(Map arg0) {
		servletSession = arg0;
	}

	private String currentPages;

	public String getCurrentPages() {
		return currentPages;
	}

	/**
	 * Gets the value of pdis
	 * 
	 * @return the value of pdis
	 */
	public final int getPdis() {
		return this.pdis;
	}

	/**
	 * Sets the value of pdis
	 * 
	 * @param argPdis
	 *            Value to assign to this.pdis
	 */
	public final void setPdis(final int argPdis) {
		this.pdis = argPdis;
	}

	/**
	 * Gets the value of pmod
	 * 
	 * @return the value of pmod
	 */
	public final int getPmod() {
		return this.pmod;
	}

	/**
	 * Sets the value of pmod
	 * 
	 * @param argPmod
	 *            Value to assign to this.pmod
	 */
	public final void setPmod(final int argPmod) {
		this.pmod = argPmod;
	}

	/**
	 * Gets the value of pmin
	 * 
	 * @return the value of pmin
	 */
	public final int getPmin() {
		return this.pmin;
	}

	/**
	 * Sets the value of pmin
	 * 
	 * @param argPmin
	 *            Value to assign to this.pmin
	 */
	public final void setPmin(final int argPmin) {
		this.pmin = argPmin;
	}

	/**
	 * Gets the value of panc
	 * 
	 * @return the value of panc
	 */
	public final int getPanc() {
		return this.panc;
	}

	/**
	 * Sets the value of panc
	 * 
	 * @param argPanc
	 *            Value to assign to this.panc
	 */
	public final void setPanc(final int argPanc) {
		this.panc = argPanc;
	}

	/**
	 * Gets the value of pjou
	 * 
	 * @return the value of pjou
	 */
	public final int getPjou() {
		return this.pjou;
	}

	/**
	 * Sets the value of pjou
	 * 
	 * @param argPjou
	 *            Value to assign to this.pjou
	 */
	public final void setPjou(final int argPjou) {
		this.pjou = argPjou;
	}

	/**
	 * Gets the value of ppai
	 * 
	 * @return the value of ppai
	 */
	public final int getPpai() {
		return this.ppai;
	}

	/**
	 * Sets the value of ppai
	 * 
	 * @param argPpai
	 *            Value to assign to this.ppai
	 */
	public final void setPpai(final int argPpai) {
		this.ppai = argPpai;
	}

	/**
	 * Gets the value of pcal
	 * 
	 * @return the value of pcal
	 */
	public final int getPvid() {
		return this.pvid;
	}

	/**
	 * Sets the value of pvid
	 * 
	 * @param argPvid
	 *            Value to assign to this.pvid
	 */
	public final void setPvid(final int argPvid) {
		this.pvid = argPvid;
	}

	/**
	 * Gets the value of pfig
	 * 
	 * @return the value of pfig
	 */
	public final int getPfig() {
		return this.pfig;
	}

	/**
	 * Sets the value of pfig
	 * 
	 * @param argPfig
	 *            Value to assign to this.pfig
	 */
	public final void setPfig(final int argPfig) {
		this.pfig = argPfig;
	}

	/**
	 * Gets the value of sdis
	 * 
	 * @return the value of sdis
	 */
	public final Boolean getSdis() {
		return this.sdis;
	}

	/**
	 * Sets the value of sdis
	 * 
	 * @param argSdis
	 *            Value to assign to this.sdis
	 */
	public final void setSdis(final Boolean argSdis) {
		this.sdis = argSdis;
	}

	/**
	 * Gets the value of smod
	 * 
	 * @return the value of smod
	 */
	public final Boolean getSmod() {
		return this.smod;
	}

	/**
	 * Sets the value of smod
	 * 
	 * @param argSmod
	 *            Value to assign to this.smod
	 */
	public final void setSmod(final Boolean argSmod) {
		this.smod = argSmod;
	}

	/**
	 * Gets the value of smin
	 * 
	 * @return the value of smin
	 */
	public final Boolean getSmin() {
		return this.smin;
	}

	/**
	 * Sets the value of smin
	 * 
	 * @param argSmin
	 *            Value to assign to this.smin
	 */
	public final void setSmin(final Boolean argSmin) {
		this.smin = argSmin;
	}

	/**
	 * Gets the value of sanc
	 * 
	 * @return the value of sanc
	 */
	public final Boolean getSanc() {
		return this.sanc;
	}

	/**
	 * Sets the value of sanc
	 * 
	 * @param argSanc
	 *            Value to assign to this.sanc
	 */
	public final void setSanc(final Boolean argSanc) {
		this.sanc = argSanc;
	}

	/**
	 * Gets the value of sjou
	 * 
	 * @return the value of sjou
	 */
	public final Boolean getSjou() {
		return this.sjou;
	}

	/**
	 * Sets the value of sjou
	 * 
	 * @param argSjou
	 *            Value to assign to this.sjou
	 */
	public final void setSjou(final Boolean argSjou) {
		this.sjou = argSjou;
	}

	/**
	 * Gets the value of spai
	 * 
	 * @return the value of spai
	 */
	public final Boolean getSpai() {
		return this.spai;
	}

	/**
	 * Sets the value of spai
	 * 
	 * @param argSpai
	 *            Value to assign to this.spai
	 */
	public final void setSpai(final Boolean argSpai) {
		this.spai = argSpai;
	}

	/**
	 * Gets the value of scal
	 * 
	 * @return the value of scal
	 */
	public final Boolean getSvid() {
		return this.svid;
	}

	/**
	 * Sets the value of scal
	 * 
	 * @param argScal
	 *            Value to assign to this.scal
	 */
	public final void setSvid(final Boolean argScal) {
		this.svid = argScal;
	}

	/**
	 * Gets the value of sfig
	 * 
	 * @return the value of sfig
	 */
	public final Boolean getSfig() {
		return this.sfig;
	}

	/**
	 * Sets the value of sfig
	 * 
	 * @param argSfig
	 *            Value to assign to this.sfig
	 */
	public final void setSfig(final Boolean argSfig) {
		this.sfig = argSfig;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public final String getQuery() {
		return this.query;
	}

	public String execute() throws Exception {
		
		CheckCookie checkCookie = new CheckCookie(servletSession);
		checkCookie.checkCookieLogin();
		
		currentPages = "";
		if (pdis > 1) {
			currentPages += "&pdis=" + pdis;
		}
		if (pmod > 1) {
			currentPages += "&pmod=" + pmod;
		}
		if (pmin > 1) {
			currentPages += "&pmin=" + pmin;
		}
		if (panc > 1) {
			currentPages += "&panc=" + panc;
		}
		if (pjou > 1) {
			currentPages += "&pjou=" + pjou;
		}
		if (ppai > 1) {
			currentPages += "&ppai=" + ppai;
		}
		if (pvid > 1) {
			currentPages += "&pvid=" + pvid;
		}
		if (pfig > 1) {
			currentPages += "&pfig=" + pfig;
		}

		if (LOG.isDebugEnabled()){
			LOG.debug("current pages:" + currentPages);
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int idx = 0; idx < cookies.length; idx++) {
				String windowName = cookies[idx].getName();
				String show = cookies[idx].getValue();

				if (windowName.equals("sdis")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						sdis = true;
				}
				if (windowName.equals("smod")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						smod = true;
				}
				if (windowName.equals("smin")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						smin = true;
				}
				if (windowName.equals("sanc")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						sanc = true;
				}
				if (windowName.equals("sjou")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						sjou = true;
				}
				if (windowName.equals("spai")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						spai = true;
				}
				if (windowName.equals("svid")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						svid = true;
				}
				if (windowName.equals("sfig")) {
					int s = Integer.parseInt(show);
					if (s > 0)
						sfig = true;
				}

			}
		}

		return SUCCESS;
	}

	private Boolean sdis;

	private Boolean smod;

	private Boolean smin;

	private Boolean sanc;

	private Boolean sjou;

	private Boolean spai;

	private Boolean svid;

	private Boolean sfig;

}
