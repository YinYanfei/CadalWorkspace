package cn.cadal.storm.analyze.cassandra.query;

import java.util.ArrayList;
import java.util.List;

public class QueryBookKeyQuery extends ParentQuery{
	
	/**
	 * Query by month and 'ip'/'name', just like 201212ip or 201212name, to query
	 * all columns of this row.
	 */
	public List<String> QueryAssignKey(String key) {
		List<String> columnList = new ArrayList<String>();

		try {

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return columnList;
	}
	
	/**
	 * Get all columns '*ip' of this CF
	 */
	public List<String> QueryAllColumnsIp() {
		List<String> columnListIp = new ArrayList<String>();
		
		
		return columnListIp;
	}

	/**
	 * Get all columns '*name' of this CF
	 */
	public List<String> QueryAllColumnsName() {
		List<String> columnListName = new ArrayList<String>();
		
		
		return columnListName;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
