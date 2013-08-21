package cn.cadal.rec.ol.algo;

import cn.cadal.rec.ol.dao.DBAgent;

public abstract class RecAlgo {
	private String DBName = "cadalrectest-75";//  76 or 77 is also OK
	protected DBAgent db = null;

	/**
	 * Construct functions
	 */
	public RecAlgo() {
		this.db = new DBAgent(this.DBName);
	}
	public RecAlgo(String dbname) {
		this.DBName = dbname;
		this.db = new DBAgent(dbname);
	}
	
	/**
	 * Getter and Setter
	 */
	public String getDBName() {
		return DBName;
	}
	public DBAgent getDb() {
		return db;
	}
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	public void setDb(DBAgent db) {
		this.db = db;
	}	
}
