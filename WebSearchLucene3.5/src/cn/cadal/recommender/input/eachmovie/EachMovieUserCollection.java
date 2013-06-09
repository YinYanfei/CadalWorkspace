/*
 * Created on 2004-12-8
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import java.sql.Connection;

import cn.cadal.recommender.input.AbstractUserCollection;
import cn.cadal.recommender.spi.Visitor;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieUserCollection extends AbstractUserCollection {
        
    public static void main(String[] args) {

	
        String fp = "d:/zhangyin/CADAL/docs/Person1.txt";
        EachMovieUserFactory uf = new EachMovieUserFactory(fp);
        EachMovieUserCollection uc = new EachMovieUserCollection();
        uc.makeAllInstance(uf);
	uc.traverse("Add user info to Database", new UserToDBVisitor ());
    }
}

class UserToDBVisitor implements Visitor{
    //static Connection conn = DBUtils.getConnection ();		
    public void performedAction (Object obj){
	EachMovieUser emu = (EachMovieUser)obj;
	String insertSql1 =
	    "insert into CUser (EmailAddress,Password, UserRights, UserID, Age";
	String insertSql2 =" ) values ('futao@coer.zju.edu.cn', 'tt', 'All',";

	if ( null == emu ) return;

	insertSql2 += "'"+emu.getUserId ()+"'" +","
	    + "'" + emu.getUserAge () + "'" +')';
	System.out.println (insertSql1+insertSql2);
	/*try{
	    conn.createStatement (). executeUpdate (insertSql1+insertSql2);
	}catch (Exception e){
	    e.printStackTrace();
	}*/
    }
}
