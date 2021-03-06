package cn.cadal.rec.data;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 获取到pg里面的cbook表的相关信息
 * 
 * @author D390
 *
 */
public class GetMetadata {
	public String bookNum;
	Connection con = null;
	public int errorFlag;

	/**
	 * Construct function
	 */
	public GetMetadata() {
		// link db
		String Username = "cadal";      // dbusername
		String userPasswd = "Cadal205"; // passwd
		String url = "jdbc:postgresql:" + "//10.15.62.71:5432/cadal_metadata_full_dbo2";
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(url);
			con = DriverManager.getConnection(url, Username, userPasswd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * search in postgreSQL and write result into file
	 */
	public void MetaData(String file) {
		String BookNo= "";
		String Title= "";
		String Creator= "";
		String Publisher= "";
		String Relation= "";
		String Type="";
		
		int num = 0;
		try{
			Statement statement = this.con.createStatement();
			String sql = "SELECT \"BookNo\", \"Title\", \"Creator\", \"Publisher\", \"Relation\", \"Type\" FROM \"cbook\" " +
					"where \"BookType\" = \'ancient\' or \"BookType\" = \'book\' or \"BookType\" = \'dissertation\' or \"BookType\" = \'english\' or \"BookType\" = \'journal\' or \"BookType\" = \'minguo\' or \"BookType\" = \'modern\' or \"BookType\" = \'newspaper\'";
			ResultSet rs = statement.executeQuery(sql);
			
			FileWriter writerAudio = new FileWriter(file, true);
			System.out.println("Files Created, DB Connecting.");
			if (!rs.next()) {
				System.out.println("No result");
			}else{
				do {
					BookNo = rs.getString("BookNo");
					Title = rs.getString("Title");
					Creator = rs.getString("Creator");
					Publisher = rs.getString("Publisher");
					Relation = rs.getString("Relation");
					Type = rs.getString("Type");
					
					// Write into file
					writerAudio.write(
							BookNo + " #### " + 
							Title.replaceAll("\n", " | ") + " #### " + 
							Creator.replaceAll("\n", " | ") + " #### " + 
							Publisher.replaceAll("\n", " | ") + " #### " + 
							Relation.replaceAll("\n", " | ") + " #### " +
							Type.replaceAll("\n", "|") + "\n"
					);

					++num;
				} while (rs.next());
			}
			System.out.println(num);
			
			writerAudio.close();
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
