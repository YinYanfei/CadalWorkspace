package cn.cadal.rec.ol.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ParseDSConfig {
	/**
	 * ���캯��
	 */
	public ParseDSConfig() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * ��ȡxml�����ļ�
	 * 
	 * @param path
	 * @return
	 */
	public Vector readConfigInfo(String path) {
		Vector dsConfig = null;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(path);// ��ȡ·���ļ�
//			System.out.println("path of config file: " + path);
			dsConfig = new Vector();
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(fi);
			Element root = doc.getRootElement();
			List pools = root.getChildren();
			Element pool = null;
			Iterator allPool = pools.iterator();
			while (allPool.hasNext()) {
				pool = (Element) allPool.next();
				DSConfigBean dscBean = new DSConfigBean();
				dscBean.setType(pool.getChild("type").getText());
				dscBean.setName(pool.getChild("name").getText());
				dscBean.setDriver(pool.getChild("driver").getText());
				dscBean.setUrl(pool.getChild("url").getText());
				dscBean.setUsername(pool.getChild("username").getText());
				dscBean.setPassword(pool.getChild("password").getText());
				dscBean.setMaxconn(Integer.parseInt(pool.getChild("maxconn")
						.getText()));
				dsConfig.add(dscBean);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return dsConfig;
	}
}