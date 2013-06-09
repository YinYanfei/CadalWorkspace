package cn.cadal.storm.bolt.statistics;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolTypeSta extends AbstractSta{

	@Override
	public void deal(String str) {
		/*
		 * str -- "10.15.62.78 07018720 00000031 Yanfei"
		 */
		String[] strSplit = str.split(" ");
		
		System.out.println(str + " : " + strSplit[0] + "  " + strSplit[1] + "  " + strSplit[2] + "  " + strSplit[3]);
		
		// match and chance times of diffirent school
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String dateStr = sdf.format(date);
		
		java.sql.Connection con = null;
		String schoolNo = "";
		String type = "";

		try{
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");

			// get the number of school
			schoolNo = this.postgresqlMatchIp(strSplit[0]);
			
			// get the type of book
			type = this.postgresqlMatchBook(strSplit[1]);
			
			// get times of school in cassandra
			String search = "select " + schoolNo + " from cadalSchTypeTimeSta where key = '" + dateStr + "_" + type + "';" ;
			
			// System.out.println("+++++>  " + search);
			
			PreparedStatement statement = con.prepareStatement(search);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				int times = rs.getInt(1);
				times++;
				String insert = "update cadalSchTypeTimeSta set " + schoolNo +  " = " + times + " where key = '" + dateStr + "_" + type + "';";				
				
				// System.out.println(insert);
				
				statement.execute(insert);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main function
	 */
	public static void main(String [] Args) {
		SchoolTypeSta sts = new SchoolTypeSta();
		
		sts.deal("202.120.5.79 06344628 00000052 hongxin");
		// sts.deal("202.120.5.79 07018720 00000032 Yanfei");
	}

}
